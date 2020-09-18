import csv
import os
import shutil
import subprocess
from multiprocessing import Process

from django.conf import settings
from django.contrib import messages
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.contrib.sites.shortcuts import get_current_site
from django.core.mail import EmailMessage
from django.db.models import BooleanField, Func
from django.db.utils import IntegrityError
from django.forms.formsets import formset_factory
from django.http import (Http404, HttpResponse, HttpResponseBadRequest,
                         HttpResponseRedirect, JsonResponse)
from django.shortcuts import redirect, render
from django.template.loader import get_template, render_to_string
from django.urls import reverse, reverse_lazy
from django.utils import timezone
from django.utils.encoding import force_bytes
from django.utils.http import urlsafe_base64_decode, urlsafe_base64_encode
from django.utils.safestring import mark_safe
from django.utils.text import slugify
from django.views.generic import ListView, View
from PIL import Image

from website.choices import *
from website.decorators import email_required, email_verify_warning, ghtoken_required
from website.forms import (BaseFilterFormSet, EmailShareForm, FilterDetailForm,
                    FilterFormSet, ProfileForm, ProjectSelectionForm, ProjectSelectionManualForm,
                    StaffProfileForm, StaffUserForm, TransformOptionForm, TransformParamFormSet,
                    UserForm, UserLoginForm, UserPasswordForm,
                    UserRegisterForm)
from website.mixins import EmailRequiredMixin
from website.models import (BackendFilter, Dataset, FilterDetail, Paper,
                     Project, ProjectSelector, ProjectSnapshot, ProjectTransformer, Selection, Transform,
                     TransformOption, TransformParameter, TransformParameterValue)
from website.tokens import email_verify_token
from website.validators import string_to_urls


class IsNull(Func):
    _output_field = BooleanField()
    arity = 1
    template = '%(expressions)s IS NULL'

class PapersView(ListView):
    template_name = 'website/papers.html'
    context_object_name = 'allPapers'
    paginate_by = 25
    queryset = Paper.objects.all()
    ordering = ['-date']

class PeopleView(ListView):
    template_name = 'website/people.html'
    context_object_name = 'allPeople'
    paginate_by = 100
    queryset = User.objects.exclude(profile__staffStatus=USER)
    ordering = ['profile__staffStatus', 'last_name']

class RegisterView(View):
    form_class = UserRegisterForm
    template_name = 'website/user/register.html'

    def get(self, request):
        return render(request, self.template_name, {'form' : self.form_class(None)})

    def post(self, request):
        form = self.form_class(request.POST)

        if form.is_valid():
            user = form.save()
            user.profile.privacy_agreement = form.cleaned_data['privacy_agreement']
            user.profile.terms_agreement = form.cleaned_data['terms_agreement']
            user.profile.age_confirmation = form.cleaned_data['age_confirmation']
            user.profile.save()

            login(request, user)

            send_email_verify(request, user, 'Verify your email with PAClab')
            messages.info(request, 'Please check and confirm your email to complete registration.')

            return redirect('website:index')

        return render(request, self.template_name, {'form' : form})

def verify_email(request, uidb64, token):
    try:
        uid = urlsafe_base64_decode(uidb64)
        user = User.objects.get(pk=uid)
    except(TypeError, ValueError, OverflowError, User.DoesNotExist):
        user = None

    if user is not None and email_verify_token.check_token(user, token):
        user.profile.active_email = True
        user.profile.save()

        login(request, user)

    messages.info(request, 'If you followed a valid email verification link, your email is now verified.')
    return redirect('website:index')

class LoginView(View):
    form_class = UserLoginForm
    template_name = 'website/user/login.html'

    def get(self, request):
        return render(request, self.template_name, {
            'form': self.form_class(None),
            'next': request.GET.get('next')
        })

    def post(self, request):
        form = self.form_class(request.POST)
        next = request.POST.get('next', None)

        if form.is_valid():
            user = authenticate(request, username=form.cleaned_data['username'], password=form.cleaned_data['password'])
            if user is not None and user.is_active:
                login(request, user)

                if user.profile.staffStatus != USER:
                    if not user.first_name or not user.last_name:
                        messages.error(request, mark_safe('Your profile shows on the people page. You must <a href="' + reverse('website:edit_profile', current_app='website') + '">edit your profile</a> and set both a first and last name.'))

                if not user.profile.active_email:
                    return email_verify_warning(request)

                if next:
                    return redirect(next)

                return redirect('website:index')

        return render(request, self.template_name, {
            'form': form,
            'next': next
        })

class SelectionListView(EmailRequiredMixin, ListView):
    template_name = 'website/selections/list.html'
    context_object_name = 'projects'
    paginate_by = 20

    def get_queryset(self):
        qs = ProjectSelector.objects.filter(user=self.request.user)
        if not self.request.user.has_perm('website.view_projectselector'):
            qs = qs.exclude(enabled=False)
        return qs.order_by('-created')

class SelectionInspectView(ListView):
    template_name = 'website/selections/inspect.html'
    context_object_name = 'snapshots'
    paginate_by = 50
    inspect_kind = 'Discovered'

    def get_base_queryset(self):
        return Selection.objects.filter(project_selector=ProjectSelector.objects.filter(slug=self.kwargs['slug']).first()).select_related('snapshot').select_related('snapshot__project')

    def get_queryset(self):
        return self.get_base_queryset().order_by(IsNull('snapshot__host'), IsNull('snapshot__path'), '-retained', 'snapshot__project__url')

class ClonedInspectView(SelectionInspectView):
    inspect_kind = 'Cloned'

    def get_queryset(self):
        return self.get_base_queryset().exclude(snapshot__host__isnull=True).exclude(snapshot__path__isnull=True).order_by('-retained', 'snapshot__project__url')

class RetainedInspectView(SelectionInspectView):
    inspect_kind = 'Retained'

    def get_queryset(self):
        return self.get_base_queryset().filter(retained=True).order_by('snapshot__project__url')

class TransformListView(EmailRequiredMixin, ListView):
    template_name = 'website/transforms/list.html'
    context_object_name = 'transformers'
    paginate_by = 20

    def get_queryset(self):
        qs = ProjectTransformer.objects.filter(user=self.request.user)
        if not self.request.user.has_perm('website.view_projecttransformer'):
            qs = qs.exclude(enabled=False)
        return qs.order_by('-created')

def selection_detail(request, slug):
    try:
        model = ProjectSelector.objects.get(slug=slug)
        if not model.enabled and not request.user.has_perm('website.view_disabled_selectors'):
            raise Exception
    except:
        raise Http404

    form = None

    if model.isDone() and not request.user.is_anonymous and request.user.profile.active_email:
        if request.method == 'POST':
            form = EmailShareForm(request.POST)

            if form.is_valid():
                form.save(request, 'selection', slug)
                messages.success(request, 'Email invitation(s) sent')
                form = EmailShareForm()
            else:
                messages.error(request, 'There was a problem sharing this item')
        else:
            form = EmailShareForm()

    return render(request, 'website/selections/detail.html', {
        'project': model,
        'form': form,
        'isowner': request.user == model.user,
        'values': FilterDetail.objects.filter(project_selector=model),
        'cloned': model.projects.exclude(host__isnull=True).exclude(path__isnull=True).count(),
        'download_size': download_selection_size(model.slug)
    })

def transform_detail(request, slug):
    try:
        model = ProjectTransformer.objects.get(slug=slug)
        if not model.enabled and not request.user.has_perm('website.view_disabled_transforms'):
            raise Exception
    except:
        raise Http404

    form = None

    if model.isDone() and not request.user.is_anonymous and request.user.profile.active_email:
        if request.method == 'POST':
            form = EmailShareForm(request.POST)

            if form.is_valid():
                form.save(request, 'transform', slug)
                messages.success(request, 'Email invitation(s) sent')
                form = EmailShareForm()
            else:
                messages.error(request, 'There was a problem sharing this item')
        else:
            form = EmailShareForm()

    return render(request, 'website/transforms/detail.html', {
        'transformer': model,
        'form': form,
        'isowner': request.user == model.user,
        'transform': model.transform.transform,
        'values': TransformParameterValue.objects.filter(option=model.transform).all(),
        'input': model.input_project_count(),
        'transformed': model.transformed_projects.exclude(path__isnull=True).count(),
        'retained': model.transformed_projects.exclude(host__isnull=True).exclude(path__isnull=True).count(),
        'download_size': download_transform_size(model.slug)
    })

@email_required
def delete_selection(request, slug):
    try:
        model = ProjectSelector.objects.get(slug=slug)
    except:
        raise Http404

    if request.method == 'POST':
        if request.user == model.user or request.user.has_perm('website.delete_projectselector'):
            model.enabled = False
            model.save()
            messages.info(request, 'Project selection \'' + slug + '\' was deleted')
            return redirect('website:list_selections')
        messages.warning(request, 'You do not have access to delete this project selection')

    return render(request, 'website/delete.html', {
        'asset': 'project selection',
        'cancel': reverse_lazy('website:selection_detail', args=(slug,))
    })

@email_required
def delete_transform(request, slug):
    try:
        model = ProjectTransformer.objects.get(slug=slug)
    except:
        raise Http404

    if request.method == 'POST':
        if request.user == model.user or request.user.has_perm('website.delete_projecttransformer'):
            model.enabled = False
            model.save()
            messages.info(request, 'Project transform was deleted')
            return redirect('website:list_transforms')
        messages.warning(request, 'You are not the owner of this transform and cannot delete it')

    return render(request, 'website/delete.html', {
        'asset': 'project transform',
        'cancel': reverse_lazy('website:transform_detail', args=(slug,))
    })

def api_usernames(request):
    q = request.GET.get('term', '')
    results = []
    if len(q) > 2:
        for r in User.objects.filter(username__icontains=q).filter(is_active=True).filter(profile__active_email=True)[:10]:
            results.append(r.first_name + ' ' + r.last_name + ' (' + r.username + ')')
    return JsonResponse(results, safe=False)

@email_required
def api_rename_selection(request, slug):
    try:
        selector = ProjectSelector.objects.get(slug=slug, user=request.user)
    except:
        raise Http404

    newslug = str(request.POST.get('slug', selector.slug))

    if selector.slug != newslug:
        if not newslug:
            newslug = selector.gen_slug()
        else:
            prefix = request.user.username + ':'
            if newslug.startswith(prefix):
                newslug = newslug[len(prefix):]
            newslug = prefix + slugify(newslug)

        try:
            selector.slug = newslug
            selector.save(update_fields=['slug'])
        except IntegrityError:
            return HttpResponseBadRequest()

    return JsonResponse({ 'slug': selector.slug }, safe=False)

@email_required
def api_rename_transform(request, slug):
    try:
        transform = ProjectTransformer.objects.get(slug=slug, user=request.user)
    except:
        raise Http404

    newslug = str(request.POST.get('slug', transform.slug))

    if transform.slug != newslug:
        if not newslug:
            newslug = transform.gen_slug()
        else:
            prefix = request.user.username + ':'
            if newslug.startswith(prefix):
                newslug = newslug[len(prefix):]
            newslug = prefix + slugify(newslug)

        try:
            transform.slug = newslug
            transform.save(update_fields=['slug'])
        except IntegrityError:
            return HttpResponseBadRequest()

    return JsonResponse({ 'slug': transform.slug }, safe=False)

def logoutView(request):
    logout(request)
    messages.success(request, 'You have successfully logged out.')
    return redirect('website:index')

@login_required
def profile(request):
    if request.method == 'POST':
        if request.user.profile.hasBio():
            userForm = StaffUserForm(request.POST, instance=request.user)
            profileForm = StaffProfileForm(request.POST, files=request.FILES, instance=request.user.profile)
        else:
            userForm = UserForm(request.POST, instance=request.user)
            profileForm = ProfileForm(request.POST, files=request.FILES, instance=request.user.profile)

        if userForm.is_valid() and profileForm.is_valid():
            userForm.save()
            profileForm.save()

            if 'email' in userForm.changed_data:
                request.user.profile.active_email = False
                request.user.profile.save()
                send_email_verify(request, request.user, 'Verify your email with PAClab')

            if profileForm.cleaned_data['photo']:
                image = Image.open(request.user.profile.photo)

                try:
                    x = float(request.POST.get('crop_x', 0))
                    y = float(request.POST.get('crop_y', 0))
                    w = float(request.POST.get('crop_w', 0))
                    h = float(request.POST.get('crop_h', 0))
                    if x and y and w and h:
                        image = image.crop((x, y, w + x, h + y))
                except:
                    pass

                image = image.resize(settings.THUMBNAIL_SIZE, Image.LANCZOS)
                image.save(request.user.profile.photo.path)

            messages.success(request, 'Profile successfully updated')
            return redirect('website:edit_profile')

        messages.error(request, 'Invalid form entry')
    else:
        if request.user.profile.hasBio():
            userForm = StaffUserForm(instance=request.user)
            profileForm = StaffProfileForm(instance=request.user.profile)
        else:
            userForm = UserForm(instance=request.user)
            profileForm = ProfileForm(instance=request.user.profile)

    return render(request, 'website/user/editprofile.html', {
        'userForm': userForm,
        'profileForm': profileForm,
        'min_width': settings.THUMBNAIL_SIZE,
        'min_height': settings.THUMBNAIL_SIZE
    })

@login_required
def password_change(request):
    if request.method == 'POST':
        form = UserPasswordForm(request.user, request.POST)
        if form.is_valid():
            form.save()
            messages.success(request, 'Password updated!')
            return redirect('website:index')
        messages.error(request, 'Invalid form entry')
    else:
        form = UserPasswordForm(request.user)

    return render(request, 'website/user/password_change.html', {
        'form': form
    })

def make_create_selection(request, inputs, initial = {}, pinitial = []):
    if request.method == 'GET':
        p_form = ProjectSelectionForm(request.GET or None, initial=initial)
        p_form.fields['input_dataset'].queryset = inputs
        formset = FilterFormSet(request.GET or None, initial=pinitial)
    elif request.method == 'POST':
        p_form = ProjectSelectionForm(request.POST)
        p_form.fields['input_dataset'].queryset = inputs
        formset = FilterFormSet(request.POST)

        if p_form.is_valid() and formset.is_valid():
            selector = ProjectSelector()
            selector.user = request.user
            selector.parent = p_form.cleaned_data.get('parent', None)
            selector.input_dataset = p_form.cleaned_data['input_dataset']
            selector.save()

            for form in formset:
                if 'value' in form.cleaned_data and 'pfilter' in form.cleaned_data:
                    pfilter = form.cleaned_data.get('pfilter')
                    value = form.cleaned_data.get('value')
                    try:
                        connection = FilterDetail()
                        connection.project_selector = ProjectSelector.objects.all().last()
                        pk = form.cleaned_data.get('pfilter').id
                        connection.pfilter = BackendFilter.objects.get(pk=pk)
                        connection.value = form.cleaned_data['value']
                        connection.save()
                    except:
                        pass

            messages.success(request, 'Project selection created successfully.')
            return redirect(reverse_lazy('website:selection_detail', args=(selector.slug,)))

        messages.error(request, 'Invalid form entry - see specific error messages below')

    return render(request, 'website/selections/create.html', {
        'p_form': p_form,
        'formset': formset,
        'lastform': 'form-' + str(len(formset.forms) - 1),
    })

@email_required
@ghtoken_required
def create_selection(request):
    initial = {}
    inputs = Dataset.objects.exclude(name=MANUAL_DATASET)
    if request.method == 'GET' and inputs.count() == 1:
        initial['input_dataset'] = inputs.first()
    return make_create_selection(request, inputs=inputs, initial=initial)

@email_required
@ghtoken_required
def create_manual_selection(request):
    if request.method == 'GET':
        p_form = ProjectSelectionManualForm(request.GET or None)
    elif request.method == 'POST':
        p_form = ProjectSelectionManualForm(request.POST)

        if p_form.is_valid():
            selector = ProjectSelector()
            selector.user = request.user
            selector.parent = None
            selector.input_dataset = Dataset.objects.filter(name=MANUAL_DATASET).first()
            selector.save()

            urls = string_to_urls(p_form.cleaned_data['urls'])
            for url in urls:
                p, _ = Project.objects.get_or_create(dataset=selector.input_dataset, url=url)
                if p.snapshots.exists():
                    s = p.snapshots.order_by('-datetime_processed').first()
                else:
                    s, _ = ProjectSnapshot.objects.get_or_create(project=p)
                Selection.objects.get_or_create(project_selector=selector, snapshot=s)

            messages.success(request, 'Project selection created successfully.')
            return redirect(reverse_lazy('website:selection_detail', args=(selector.slug,)))

        messages.error(request, 'Invalid form entry - see specific error messages below')

    return render(request, 'website/selections/create_manual.html', {
        'p_form': p_form,
    })

@email_required
@ghtoken_required
def selection_duplicate(request, slug):
    initial = {}
    pinitial = []
    if request.method == 'GET':
        sel = ProjectSelector.objects.get(slug=slug)
        initial['input_dataset'] = sel.input_dataset
        initial['parent'] = sel
        for f in sel.filterdetail_set.all():
            pinitial.append({
                'pfilter': f.pfilter,
                'value': f.value,
            })
    return make_create_selection(request, inputs=Dataset.objects.exclude(name=MANUAL_DATASET), initial=initial, pinitial=pinitial)

def make_create_transform(request, selector=None, transform=None, parent=None):
    if request.method == 'GET':
        initial = {}
        pinitial = {}
        if Transform.objects.count() == 1:
            initial['transform'] = Transform.objects.first()
        form = TransformOptionForm(request.GET or None, initial=initial)
        formset = TransformParamFormSet(request.GET or None, initial=pinitial)
    elif request.method == 'POST':
        form = TransformOptionForm(request.POST)
        formset = TransformParamFormSet(request.POST)

        if form.is_valid() and formset.is_valid():
            params = {}
            for f in formset:
                if 'value' in f.cleaned_data and 'parameter' in f.cleaned_data:
                    params[f.cleaned_data.get('parameter')] = f.cleaned_data.get('value')

            from django.db.models import Count
            options = None
            for o in TransformOption.objects.annotate(params=Count("parameters")).filter(transform = form.cleaned_data['transform']).filter(params=len(params)):
                d = o.param_dict()
                if d == params:
                    options = o
                    break

            if not options:
                options = TransformOption.objects.create(transform = form.cleaned_data['transform'])
                for p in params.items():
                    try:
                        connection = TransformParameterValue()
                        connection.option = options
                        pk = p[0].id
                        connection.parameter = TransformParameter.objects.get(pk=pk)
                        connection.value = p[1]
                        connection.save()
                    except:
                        pass

            create_transform = ProjectTransformer.objects.create(
                src_selector=selector,
                src_transformer=transform,
                parent=parent,
                user=request.user,
                transform=options
            )
            messages.success(request, 'Project transform created successfully.')
            return redirect(reverse_lazy('website:transform_detail', args=(create_transform.slug,)))

        messages.error(request, 'Invalid form entry - see specific error messages below')

    return render(request, 'website/transforms/create.html', {
        'form': form,
        'formset': formset,
        'lastform': 'form-' + str(len(formset.forms) - 1),
        'src_selector': selector.slug if selector else None,
        'src_transformer': transform.slug if transform else None,
        'parent': parent,
    })

@email_required
@ghtoken_required
def create_transform_selection(request, slug):
    try:
        selector = ProjectSelector.objects.get(slug=slug)
    except:
        raise Http404

    if not selector.isDone():
        messages.error(request, 'Project selection is still processing. You can\'t run a transform on it until it finishes.')
        return HttpResponseRedirect(request.META.get('HTTP_REFERER', reverse_lazy('website:selection_detail', args=(slug,))))

    return make_create_transform(request, selector=selector)

@email_required
@ghtoken_required
def create_transform_transform(request, slug):
    try:
        transform = ProjectTransformer.objects.get(slug=slug)
    except:
        raise Http404

    if not transform.isDone():
        messages.error(request, 'Project transform is still processing. You can\'t run a transform on it until it finishes.')
        return redirect(reverse_lazy('website:transform_detail', args=(slug,)))

    return make_create_transform(request, transform=transform)

@email_required
@ghtoken_required
def transform_duplicate(request, slug):
    try:
        parent = ProjectTransformer.objects.get(slug=slug)
    except:
        raise Http404

    return make_create_transform(request, parent=parent, selector=parent.src_selector, transform=parent.src_transformer)

def api_filter_detail(request):
    try:
        val = int(request.GET.get('id', 0))
        pfilter = BackendFilter.objects.filter(enabled=True).get(pk=val)
        return JsonResponse({
            'id': val,
            'name': pfilter.flter.name,
            'val_type': pfilter.flter.val_type,
            'default': pfilter.flter.default_val,
            'help_text': pfilter.flter.help_text,
        })
    except:
        raise Http404

def api_transform_param(request):
    try:
        val = int(request.GET.get('id', 0))
        param = TransformParameter.objects.get(pk=val)
        return JsonResponse({
            'id': val,
            'name': param.name,
            'val_type': param.val_type,
            'default': param.default_val,
            'help_text': param.help_text,
        })
    except:
        raise Http404

def verify_email_link(request):
    return send_email_verify(request, request.user, 'Reconfirm email address')

def send_email_verify(request, user, title):
    message = render_to_string('website/user/verification_email.html', {
        'user': user,
        'domain': get_current_site(request).domain,
        'uid': urlsafe_base64_encode(force_bytes(user.pk)),
        'token': email_verify_token.make_token(user),
    })
    email = EmailMessage(title, message, to=[user.email])
    email.send()
    messages.info(request, 'If an account exists with the email you entered, we\'ve emailed you a link for verifying the email address. You should receive the email shortly. If you don\'t receive an email, check your spam/junk folder and please make sure your email address is entered correctly in your profile.')
    return redirect('website:index')

def generate_csv(filename, projects):
    response = HttpResponse(content_type='text/csv')
    response['Content-Disposition'] = 'attachment; filename="' + filename + '.csv"'

    writer = csv.writer(response)

    for project in projects.order_by('project__url').select_related('project'):
        writer.writerow([project.project.url])

    return response

def export_selection_csv(request, slug):
    try:
        return generate_csv(slug + '-discovered', ProjectSelector.objects.get(slug=slug).projects)
    except:
        raise Http404

def export_cloned_csv(request, slug):
    try:
        return generate_csv(slug + '-cloned', ProjectSelector.objects.get(slug=slug).projects.exclude(path__isnull=True).exclude(host__isnull=True))
    except:
        raise Http404

def export_retained_csv(request, slug):
    try:
        return generate_csv(slug + '-retained', ProjectSelector.objects.get(slug=slug).result_projects().distinct())
    except:
        raise Http404

def download_selection_size(slug):
    download_path = os.path.join(settings.MEDIA_ROOT, 'downloads/selection')
    zipfile_path = os.path.join(download_path, slug + '.zip')
    tmpdir = os.path.join(download_path, slug + '.tmp')

    if os.path.exists(tmpdir):
        return 0
    if not os.path.exists(zipfile_path):
        return -1
    return os.stat(zipfile_path).st_size

def download_selection(request, slug):
    download_path = os.path.join(settings.MEDIA_ROOT, 'downloads/selection')
    download_filename = slug + '.zip'
    zipfile_path = os.path.join(download_path, download_filename)
    tmpdir = os.path.join(download_path, slug + '.tmp')

    if os.path.exists(tmpdir):
        return redirect(reverse_lazy('website:selection_detail', args=(slug,)))

    if not os.path.exists(zipfile_path):
        paths = {}

        try:
            selector = ProjectSelector.objects.get(slug=slug)

            for project in ProjectSnapshot.objects.filter(selection__project_selector=selector).filter(selection__retained=True):
                if not project.host in paths:
                    paths[project.host] = []
                paths[project.host].append(project.path)

            if not paths:
                raise RuntimeError('empty benchmark')
        except:
            raise Http404

        if not os.path.exists(download_path):
            os.mkdir(download_path, 0o755)

        Process(target=generate_zip, args=(paths, tmpdir, zipfile_path, getattr(settings, 'REPO_PATH'))).start()

        return redirect(reverse_lazy('website:selection_detail', args=(slug,)))

    selector = ProjectSelector.objects.get(slug=slug)
    if not selector.download_count:
        selector.download_count = 0
    selector.download_count += 1
    selector.last_download = timezone.now()
    selector.save(update_fields=['download_count', 'last_download'])

    return redirect(settings.MEDIA_URL + '/downloads/selection/' + download_filename)

def download_transform_size(slug):
    download_path = os.path.join(settings.MEDIA_ROOT, 'downloads/transform')
    zipfile_path = os.path.join(download_path, slug + '.zip')
    tmpdir = os.path.join(download_path, slug + '.tmp')

    if os.path.exists(tmpdir):
        return 0
    if not os.path.exists(zipfile_path):
        return -1
    return os.stat(zipfile_path).st_size

def download_transform(request, slug):
    download_path = os.path.join(settings.MEDIA_ROOT, 'downloads/transform')
    download_filename = slug + '.zip'
    zipfile_path = os.path.join(download_path, download_filename)
    tmpdir = os.path.join(download_path, slug + '.tmp')

    if os.path.exists(tmpdir):
        return redirect(reverse_lazy('website:transform_detail', args=(slug,)))

    if not os.path.exists(zipfile_path):
        paths = {}

        try:
            for transformed_project in ProjectTransformer.objects.get(slug=slug).result_projects():
                if transformed_project:
                    if not transformed_project.host in paths:
                        paths[transformed_project.host] = []
                    paths[transformed_project.host].append(transformed_project.path)

            if not paths:
                raise RuntimeError('empty benchmark')
        except:
            raise Http404

        if not os.path.exists(download_path):
            os.mkdir(download_path, 0o755)

        Process(target=generate_zip, args=(paths, tmpdir, zipfile_path, getattr(settings, 'TRANSFORMED_PATH'))).start()

        return redirect(reverse_lazy('website:transform_detail', args=(slug,)))

    transformer = ProjectTransformer.objects.get(slug=slug)
    if not transformer.download_count:
        transformer.download_count = 0
    transformer.download_count += 1
    transformer.last_download = timezone.now()
    transformer.save(update_fields=['download_count', 'last_download'])

    return redirect(settings.MEDIA_URL + '/downloads/transform/' + download_filename)

def generate_zip(in_paths, tmpdir, zipfile_path, cwd):
    os.mkdir(tmpdir, 0o755)

    for host in in_paths:
        s = ''
        for path in in_paths[host]:
            s = s + path + '\n'
        p = subprocess.Popen(['zip', '-r', '-g', '-@', '-b', tmpdir, zipfile_path],
                                cwd=os.path.join(cwd, host),
                                stdin=subprocess.PIPE)
        p.communicate(input=str.encode(s))
        p.stdin.close()
        p.wait()

    if os.path.exists(tmpdir):
        shutil.rmtree(tmpdir)

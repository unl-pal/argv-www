var CookiesBanner = {
    createCookiesBannerCookie: function () {
        var date = new Date();
        date.setTime(date.getTime() + (10 * 365 * 24 * 60 * 60 * 1000));
        document.cookie = "cookies_accepted=1; expires=" + date.toGMTString() + "; path=/";
        $('#CookiesBanner').slideUp('fast');
    },

    hasCookiesBannerCookie: function() {
        var ca = decodeURIComponent(document.cookie).split(';');
        for(var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf("cookies_accepted=") == 0) {
                return true;
            }
        }
        return false;
    }
};
if (!CookiesBanner.hasCookiesBannerCookie()) {
    $(function () {
        $('#CookiesBanner').slideDown('fast');
    });
}
$(function () {
    window.setTimeout(function() {
        $(".alert-success").fadeTo(200, 0.2).slideUp(500);
    }, 30000);
});

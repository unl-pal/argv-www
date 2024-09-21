import os
import shutil
import subprocess
import tempfile

def generate_zip(in_paths, tmpdir, zipfile_path, cwd, readme = None):
    os.makedirs(tmpdir, mode=0o755, exist_ok=True)

    # generate README.md file and add to the zipfile
    if readme:
        readmepath = tempfile.mkdtemp()

        readmefile = os.path.join(readmepath, 'README.md')
        f = open(readmefile, 'w')
        f.write(readme)
        f.close()

        p = subprocess.Popen(['zip', '-g', '-@', '-b', tmpdir, zipfile_path], cwd=readmepath, stdin=subprocess.PIPE)
        p.communicate(input=str.encode('README.md\n'))
        p.stdin.close()
        p.wait()

        shutil.rmtree(readmepath)

    try:
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
    except:
        if os.path.exists(zipfile_path):
            shutil.rmtree(zipfile_path)
    finally:
        if os.path.exists(tmpdir):
            shutil.rmtree(tmpdir)

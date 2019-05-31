package reflection;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.io.IOException;
import java.lang.reflect.*;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Inspector {
    private URLClassLoader cl;
    private JarFile jar;
    private Set<Class> dependencies;

    public Inspector(String path) throws IOException {
        jar = new JarFile(path);
        URL[] urls = { new URL("jar:file:" + path + "!/") };
        cl = URLClassLoader.newInstance(urls);
        dependencies = new HashSet<Class>();
    }


    public void report() {
        Enumeration e = jar.entries();
        dependencies.clear();

        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }

            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');

            try {
                Class c = cl.loadClass(className);
                System.out.println(inspect(c) + "\n===============================\n");
            } catch (Exception ignored) {}
        }
    }

    private String inspect(Class cls) {
        String output = "";

        output += parseModifiers(cls.getModifiers()) + "class " + cls.getSimpleName() + "\n";

        output += "Interfaces implemented:";
        for (Class c: cls.getInterfaces()) {
            dependencies.add(c);
            output += " " + c.getSimpleName();
        }

        Class type = cls.getSuperclass();
        output += "\nExtended class: " + type.getSimpleName() + "\n\n";
        dependencies.add(type);

        output += "Fields:\n";
        for(Field f: cls.getDeclaredFields()) {
            type = f.getType();
            output += parseModifiers(f.getModifiers());
            output += type.getSimpleName() + " " + f.getName() + "\n";

            if(type.isArray())
                dependencies.add(type.getComponentType());
            else
                dependencies.add(type);
        }


        output += "\nConstructors:\n";
        for(Constructor c: cls.getDeclaredConstructors())
            output += parseMethod(c);

        output += "\nMethods:\n";
        for(Method m: cls.getDeclaredMethods())
            output += parseMethod(m);


        output += "\nDEPENDENCIES:\n";
        removeNativeTypes();
        for(Class k: dependencies)
            output += k.getSimpleName() + " ";

        return output;
    }


    private void removeNativeTypes() {
        Set<Class> out = new HashSet<Class>();

        for(Class k: dependencies) {
            if(k.getName().matches("java.*|int|float|boolean|double|byte|short|long|char|Integer|Double|Float|Boolean|Short|Byte|Long|Chararacter"))
                continue;
            out.add(k);
        }

        dependencies = out;
    }


    private String parseMethod(Member m) {
        String out = "";
        Class[] paramTypes;
        String returnTypeName = "";

        if ( m instanceof Method ) {
            Method method = ((Method) m);
            paramTypes = method.getParameterTypes();
            returnTypeName = method.getReturnType().getSimpleName();
        } else {
            paramTypes = ((Constructor) m).getParameterTypes();
        }

        out += parseModifiers(m.getModifiers()) + m.getName() + "(";
        for(Class k: paramTypes) {
            out += k.getSimpleName() + ", ";
            if(k.isArray())
                dependencies.add(k.getComponentType());
            else
                dependencies.add(k);
        }

        if(paramTypes.length > 0)
            out = out.substring(0, out.length() - 2); //remove last comma and space
        out += "): " + returnTypeName + "\n";

        return out;
    }


    private String parseModifiers(int modifiers) {
        String out = "";

        if (Modifier.isPublic(modifiers))
            out += "public ";
        else if(Modifier.isPrivate(modifiers))
            out += "private ";
        else if (Modifier.isProtected(modifiers))
            out += "protected ";

        if (Modifier.isInterface(modifiers))
            out += "interface ";

        if (Modifier.isStatic(modifiers))
            out += "static ";

        if(Modifier.isAbstract(modifiers))
            out += "abstract ";

        return out;
    }
}

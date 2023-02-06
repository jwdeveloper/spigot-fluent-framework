package io.github.jwdeveloper.spigot.fluent.core.common.java;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassTypeUtility {
    public static boolean isClassContainsType(Class<?> type, Class<?> searchType) {

        while (true) {
            if (type.isAssignableFrom(searchType)) {
                return true;
            }
            type = type.getSuperclass();

            if(type == null)
            {
                return false;
            }
            if (type.equals(Object.class)) {
                return false;
            }
        }
    }


    public static boolean isClassContainsInterface(Class<?> type, Class<?> _interface) {
        while (true) {
            for (var interfaceType : type.getInterfaces()) {
                if (interfaceType.equals(_interface)) {
                    return true;
                }
            }
            type = type.getSuperclass();

            if (type.equals(Object.class)) {
                return false;
            }
        }
    }

    public static List<String> listAllClasses(final Class<?> clazz) {
        final CodeSource source = clazz.getProtectionDomain().getCodeSource();
        if(source == null) return Collections.emptyList();
        final URL url = source.getLocation();
        try (
                final ZipInputStream zip = new ZipInputStream(url.openStream())) {
            final List<String> classes = new ArrayList<>();
            while(true) {
                final ZipEntry entry = zip.getNextEntry();
                if(entry == null) break;
                if(entry.isDirectory()) continue;
                final String name = entry.getName();
                if(name.endsWith(".class")) {
                    classes.add(name.replace('/', '.').substring(0, name.length() - 6));
                }
            }
            return classes;
        } catch (IOException exception) {
            return Collections.emptyList();
        }
    }

    private static Type[] getInterfaceGenericTypes(Class<?> _class, Class<?> _interface) {
        ParameterizedType validator = null;
        for (var classInterface : _class.getGenericInterfaces()) {
            var name = classInterface.getTypeName();
            if (name.contains(_interface.getClass().getSimpleName())) {
                validator = (ParameterizedType) classInterface;
                break;
            }
        }
        if (validator == null)
            return null;

        return validator.getActualTypeArguments();
    }


}

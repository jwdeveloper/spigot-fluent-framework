package io.github.jwdeveloper.spigot.fluent.plugin.implementation.assemby_scanner;

import io.github.jwdeveloper.spigot.fluent.core.common.java.ClassTypeUtility;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.plugin.api.assembly_scanner.FluentAssemblyScanner;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class AssemblyScanner extends ClassLoader implements FluentAssemblyScanner  {


    @Getter
    private final List<Class<?>> classes;

    private final Map<Class<?>, List<Class<?>>> byInterfaceCatch;

    private final Map<Class<?>, List<Class<?>>> byParentCatch;

    private final Map<Package, List<Class<?>>> byPackageCatch;

    private final Map<Class<? extends Annotation>, List<Class<?>>> byAnnotationCatch;

    private final SimpleLogger logger;

    public AssemblyScanner(Plugin plugin, SimpleLogger logger) {
        //TODO check if PLUGIN will works instead of JAVAPLUGIN
        this(plugin.getClass(), logger);
    }

    public AssemblyScanner(Class<?> clazz, SimpleLogger logger) {
        this.logger = logger;
        classes = loadPluginClasses(clazz);
        byInterfaceCatch = new IdentityHashMap<>();
        byParentCatch = new IdentityHashMap<>();
        byPackageCatch = new IdentityHashMap<>();
        byAnnotationCatch = new HashMap<>();
    }


    private List<Class<?>> loadPluginClasses(final Class<?> clazz) {
        final var source = clazz.getProtectionDomain().getCodeSource();
        if (source == null) return Collections.emptyList();
        final var url = source.getLocation();
        try (final var zip = new ZipInputStream(url.openStream())) {
            final List<Class<?>> classes = new ArrayList<>();
            while (true) {
                final ZipEntry entry = zip.getNextEntry();
                if (entry == null) break;
                if (entry.isDirectory()) continue;
                var name = entry.getName();
                if (!name.endsWith(".class")) continue;
                name = name.replace('/', '.').substring(0, name.length() - 6);
                try {
                    classes.add(Class.forName(name, false, clazz.getClassLoader()));
                } catch (NoClassDefFoundError | ClassNotFoundException e) {
                    logger.warning("Unable to load class:" + name);
                }
            }
            return classes;
        } catch (IOException e) {
            logger.error("Unable to open classes loader for: " + clazz.getName(), e);
            return Collections.emptyList();
        }
    }


    public Collection<Class<?>> findByAnnotation(Class<? extends Annotation> annotation) {
        if (byAnnotationCatch.containsKey(annotation)) {
            return byAnnotationCatch.get(annotation);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            if (_class.isAnnotationPresent(annotation)) {
                result.add(_class);
            }
        }
        byAnnotationCatch.put(annotation, result);
        return result;
    }

    public Collection<Class<?>> findByInterface(Class<?> _interface) {
        if (byInterfaceCatch.containsKey(_interface)) {
            return byInterfaceCatch.get(_interface);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            for (var _classInterface : _class.getInterfaces()) {
                if (_classInterface.equals(_interface)) {
                    result.add(_class);
                    break;
                }
            }
        }
        byInterfaceCatch.put(_interface, result);
        return result;
    }

    public Collection<Class<?>> findBySuperClass(Class<?> parentClass) {
        if (byParentCatch.containsKey(parentClass)) {
            return byParentCatch.get(parentClass);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            if (ClassTypeUtility.isClassContainsType(_class, parentClass)) {
                result.add(_class);
            }
        }
        byParentCatch.put(parentClass, result);
        return result;
    }

    public Collection<Class<?>> findByPackage(Package _package) {
        if (byPackageCatch.containsKey(_package)) {
            return byPackageCatch.get(_package);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            for (var _classInterface : _class.getInterfaces()) {
                if (_classInterface.getPackage().equals(_package)) {
                    result.add(_class);
                    break;
                }
            }
        }
        byPackageCatch.put(_package, result);
        return result;
    }

}

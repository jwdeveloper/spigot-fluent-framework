package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.dependecy_injection;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.FluentContainer;
import java.lang.annotation.Annotation;
import java.util.Collection;

public class FluentInjectionImpl implements FluentInjection
{
    private FluentContainer pluginContainer;

    public FluentInjectionImpl(FluentContainer pluginContainer) {
        this.pluginContainer = pluginContainer;
    }

    public  <T> T findInjection(Class<T> injectionType)
    {
        return (T)pluginContainer.find(injectionType);
    }

    @Override
    public <T> Collection<T> findAllByInterface(Class<T> _interface) {
        return pluginContainer.findAllByInterface(_interface);
    }

    @Override
    public <T> Collection<T> findAllBySuperClass(Class<T> superClass) {
        return pluginContainer.findAllBySuperClass(superClass);
    }

    @Override
    public Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation)
    {
        return pluginContainer.findAllByAnnotation(_annotation);
    }

    public Container getContainer()
    {
        return pluginContainer;
    }
}

package io.github.jwdeveloper.spigot.fluent.core.injector.api.search;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface ContainerSearch
{
    public <T> Collection<T> findAllByInterface(Class<T> _interface);

    public <T> Collection<T> findAllBySuperClass(Class<T> superClass);

    public Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation);
}

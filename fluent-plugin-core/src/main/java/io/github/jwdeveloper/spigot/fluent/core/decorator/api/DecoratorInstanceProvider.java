package io.github.jwdeveloper.spigot.fluent.core.decorator.api;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.InjectionInfo;

import java.util.Map;

public interface DecoratorInstanceProvider
{
    Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Object toSwap, Container container) throws Exception;

}

package io.github.jwdeveloper.spigot.fluent.core.injector.api.provider;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.InjectionInfo;

import java.util.Map;

public interface InstanceProvider
{
    public Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Container container) throws Exception;
}

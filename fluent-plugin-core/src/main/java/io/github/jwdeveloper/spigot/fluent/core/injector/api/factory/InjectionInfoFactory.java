package io.github.jwdeveloper.spigot.fluent.core.injector.api.factory;

import io.github.jwdeveloper.spigot.fluent.core.common.java.Pair;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.InjectionInfo;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.RegistrationInfo;


public interface InjectionInfoFactory
{
    public Pair<Class<?>, InjectionInfo> create(RegistrationInfo registrationInfo) throws Exception;
}

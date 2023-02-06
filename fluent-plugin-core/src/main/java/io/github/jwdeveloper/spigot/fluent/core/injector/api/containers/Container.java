package io.github.jwdeveloper.spigot.fluent.core.injector.api.containers;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.RegistrationInfo;

public interface Container extends Cloneable
{
     boolean register(RegistrationInfo registrationInfo) throws Exception;

     Object find(Class<?> type);
}

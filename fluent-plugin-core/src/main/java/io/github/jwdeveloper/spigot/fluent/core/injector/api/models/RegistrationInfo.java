package io.github.jwdeveloper.spigot.fluent.core.injector.api.models;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.RegistrationType;

import java.util.function.Function;

public record RegistrationInfo(Class<?> _interface,
                               Class<?> implementation,
                               Function<Container,Object> provider,
                               LifeTime lifeTime,
                               RegistrationType registrationType)
{

}

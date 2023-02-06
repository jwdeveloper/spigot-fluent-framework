package io.github.jwdeveloper.spigot.fluent.core.decorator.api.models;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.InjectionInfo;

import java.util.List;

public record DecorationDto(Class<?> _interface, List<InjectionInfo> implementations)
{

}
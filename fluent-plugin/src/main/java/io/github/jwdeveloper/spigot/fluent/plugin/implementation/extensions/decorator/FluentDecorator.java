package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.decorator;

import io.github.jwdeveloper.spigot.fluent.core.decorator.api.builder.DecoratorBuilder;
import io.github.jwdeveloper.spigot.fluent.core.decorator.implementation.DecoratorBuilderImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.factory.InjectionInfoFactoryImpl;

import java.util.HashMap;

public class FluentDecorator
{
    public static DecoratorBuilder CreateDecorator()
    {
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        return new DecoratorBuilderImpl(injectionInfoFactory, new HashMap<>());
    }

}

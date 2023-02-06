package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.mediator;

public interface FluentMediator
{
    public <Output> Output resolve(Object input, Class<Output> outputClass);
}


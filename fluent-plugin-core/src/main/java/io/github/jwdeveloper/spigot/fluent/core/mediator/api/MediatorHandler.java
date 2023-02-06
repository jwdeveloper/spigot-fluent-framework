package io.github.jwdeveloper.spigot.fluent.core.mediator.api;

public interface MediatorHandler<Input, Output>
{
    public Output handle(Input request);
}

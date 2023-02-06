package io.github.jwdeveloper.spigot.fluent.core.mediator.api;

public interface Mediator {
    <Output> Output resolve(Object input, Class<Output> outputClass);

    <T extends MediatorHandler<?, ?>> void register(Class<T> mediator);

    boolean containsClass(Class<?> clazz);
}

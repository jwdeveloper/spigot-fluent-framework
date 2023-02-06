package io.github.jwdeveloper.spigot.fluent.core.decorator.api.builder;

import io.github.jwdeveloper.spigot.fluent.core.decorator.api.Decorator;

public interface DecoratorBuilder {
    <T> DecoratorBuilder decorate(Class<T> _interface, Class<? extends T> implementation);
    Decorator build() throws Exception;
}

package io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.builders;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.ContainerConfiguration;

import java.util.function.Consumer;

public interface ContainerBuilderConfiguration
{
    public ContainerBuilder configure(Consumer<ContainerConfiguration> configuration);
}

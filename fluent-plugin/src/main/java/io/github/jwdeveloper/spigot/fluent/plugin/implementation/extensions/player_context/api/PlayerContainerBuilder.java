package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.api;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.builders.ContainerBuilder;

public interface PlayerContainerBuilder extends ContainerBuilder<PlayerContainerBuilder> {

    public Container build();

}

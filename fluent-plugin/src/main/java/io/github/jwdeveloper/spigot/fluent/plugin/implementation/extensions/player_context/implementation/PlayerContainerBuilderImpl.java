package io.github.jwdeveloper.spigot.fluent.plugin.implementation.player_context.implementation;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.containers.DefaultContainer;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.containers.builder.ContainerBuilderImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.events.EventHandlerImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.factory.InjectionInfoFactoryImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.search.SearchAgentImpl;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.player_context.api.PlayerContainerBuilder;
import java.util.logging.Logger;

public class PlayerContainerBuilderImpl extends ContainerBuilderImpl<PlayerContainerBuilder> implements PlayerContainerBuilder {
    private Container parentContainer;

    public PlayerContainerBuilder setParentContainer(Container container) {
        parentContainer = container;
        return this;
    }

    @Override
    public Container build() {
        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new PlayerContextInstanceProvider(parentContainer);
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var searchAgent = new SearchAgentImpl();

        return new DefaultContainer(
                searchAgent,
                instanceProvider,
                eventHandler,
                injectionInfoFactory,
                config.getRegistrations());
    }
}

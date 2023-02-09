package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.containers.DefaultContainer;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.containers.builder.ContainerBuilderImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.events.EventHandlerImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.factory.InjectionInfoFactoryImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.search.SearchAgentImpl;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.api.PlayerContainerBuilder;

public class PlayerContainerBuilderImpl extends ContainerBuilderImpl<PlayerContainerBuilder> implements PlayerContainerBuilder {
    private Container parentContainer;
    private SimpleLogger logger;

    public PlayerContainerBuilderImpl(SimpleLogger logger)
    {
        this.logger = logger;
    }

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
                logger,
                injectionInfoFactory,
                config.getRegistrations());
    }
}

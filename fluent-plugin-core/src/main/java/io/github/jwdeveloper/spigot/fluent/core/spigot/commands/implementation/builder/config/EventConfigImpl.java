package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.config;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.EventConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services.EventsService;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.events.CommandEvent;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.events.ConsoleCommandEvent;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.events.PlayerCommandEvent;

import java.util.function.Consumer;

public class EventConfigImpl implements EventConfig {

    private final EventsService eventsService;

    public EventConfigImpl(EventsService eventsService)
    {
        this.eventsService = eventsService;
    }

    @Override
    public EventConfig onExecute(Consumer<CommandEvent> event) {
        eventsService.onInvoke(event);
        return this;
    }

    @Override
    public EventConfig onPlayerExecute(Consumer<PlayerCommandEvent> event) {
        eventsService.onPlayerInvoke(event);
        return this;
    }

    @Override
    public EventConfig onConsoleExecute(Consumer<ConsoleCommandEvent> event) {
        eventsService.onConsoleInvoke(event);
        return this;
    }

    @Override
    public EventConfig onBlockExecute(Consumer<CommandEvent> event) {
       // eventsService.on(event);
        return this;
    }

    @Override
    public EventConfig onEntityExecute(Consumer<CommandEvent> event) {
       // eventsService.onInvoke(event);
        return this;
    }
}

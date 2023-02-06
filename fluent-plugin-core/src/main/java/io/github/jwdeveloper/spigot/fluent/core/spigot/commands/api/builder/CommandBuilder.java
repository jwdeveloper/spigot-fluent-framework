package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.ArgumentConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.EventConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.PropertiesConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.SubCommandConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.SimpleCommand;

import java.util.function.Consumer;

public interface CommandBuilder
{
    CommandBuilder propertiesConfig(Consumer<PropertiesConfig> config);
    CommandBuilder eventsConfig(Consumer<EventConfig> config);
    CommandBuilder argumentsConfig(Consumer<ArgumentConfig> config);
    CommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config);
    SimpleCommand buildAndRegister();
    SimpleCommand build();
}

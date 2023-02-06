package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.ArgumentBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.BuilderConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.ArgumentType;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandArgument;


import java.util.function.Consumer;

public interface ArgumentConfig extends BuilderConfig
{
    ArgumentConfig addArgument(CommandArgument commandArgument);

    ArgumentConfig addArgument(String name, ArgumentType argumentType);

    ArgumentConfig addArgument(String name, Consumer<ArgumentBuilder> builderConsumer);

    ArgumentConfig addArgument(String name);
}

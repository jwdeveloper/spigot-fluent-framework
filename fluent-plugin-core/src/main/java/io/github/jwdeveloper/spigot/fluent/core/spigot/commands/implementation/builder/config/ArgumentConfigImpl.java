package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.config;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.ArgumentBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.ArgumentConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.ArgumentType;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandArgument;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandModel;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.ArgumentBuilderImpl;
import java.util.function.Consumer;

public class ArgumentConfigImpl implements ArgumentConfig
{
    private final CommandModel model;

    public ArgumentConfigImpl(CommandModel model)
    {
        this.model = model;
    }

    @Override
    public ArgumentConfig addArgument(CommandArgument commandArgument)
    {
        model.getArguments().add(commandArgument);
        return this;
    }

    @Override
    public ArgumentConfig addArgument(String name, ArgumentType argumentType) {

        var builder = new ArgumentBuilderImpl(name);
        builder.setType(argumentType);
        return this.addArgument(builder.build());

    }

    @Override
    public ArgumentConfig addArgument(String name) {
        return this.addArgument(new ArgumentBuilderImpl(name).build());
    }

    public ArgumentConfig addArgument(String name, Consumer<ArgumentBuilder> builder)
    {
        var argumentBuilder = new ArgumentBuilderImpl(name);
        builder.accept(argumentBuilder);
        return this.addArgument(argumentBuilder.build());
    }
}

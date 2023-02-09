package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.command;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.FluentCommandManger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.CommandBuilderImpl;
public class FluentApiDefaultCommandBuilder extends CommandBuilderImpl implements FluentApiCommandBuilder
{
    public FluentApiDefaultCommandBuilder(String commandName, FluentCommandManger manger)
    {
        super(commandName, manger);
    }

    @Override
    public CommandBuilder setName(String  commandName) {
        model.setName(commandName);
        return this;
    }

    @Override
    public String  getName() {
        return model.getName();
    }
}

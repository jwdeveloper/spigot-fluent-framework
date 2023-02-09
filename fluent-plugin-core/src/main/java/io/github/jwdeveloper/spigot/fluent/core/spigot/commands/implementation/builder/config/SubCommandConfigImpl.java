package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.config;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.FluentCommandManger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.SubCommandConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.SimpleCommand;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.CommandBuilderImpl;

import java.util.List;
import java.util.function.Consumer;

public class SubCommandConfigImpl implements SubCommandConfig {

    private final List<SimpleCommand> commands;
    private final FluentCommandManger manger;

    public SubCommandConfigImpl(List<SimpleCommand> commands, FluentCommandManger manger) {
        this.commands = commands;
        this.manger = manger;
    }

    @Override
    public SubCommandConfig addSubCommand(CommandBuilder builder) {
        return addSubCommand(builder.build());
    }

    @Override
    public SubCommandConfig addSubCommand(SimpleCommand simpleCommand) {
        commands.add(simpleCommand);
        return this;
    }

    @Override
    public SubCommandConfig addSubCommand(List<SimpleCommand> simpleCommand) {
        commands.addAll(simpleCommand);
        return this;
    }

    @Override
    public SubCommandConfig addSubCommand(String name, Consumer<CommandBuilder> config) {
        var builder = new CommandBuilderImpl(name,  manger);
        config.accept(builder);
        return addSubCommand(builder.build());
    }
}

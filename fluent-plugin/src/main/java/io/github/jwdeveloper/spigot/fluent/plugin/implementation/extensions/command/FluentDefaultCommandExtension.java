package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.command;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;

public class FluentDefaultCommandExtension implements FluentApiExtension {

    private CommandBuilder commandBuilder;

    public FluentDefaultCommandExtension(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        commandBuilder.build();
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}

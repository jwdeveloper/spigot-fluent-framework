package io.github.jwdeveloper.spigot.fluent.plugin.implementation;


import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.SimpleMessage;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.dependecy_injection.FluentInjection;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.files.FluentFiles;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.api.FluentPermission;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.implementation.FluentPlayerContext;

public class FluentApi {
    private static FluentApiSpigot fluentApiSpigot;
    public static void setFluentApiSpigot(FluentApiSpigot fluentApiSpigot) {
        if (FluentApi.fluentApiSpigot != null) {
            FluentLogger.LOGGER.error("FluentAPI has been already initialized ");

            return;
        }
        FluentApi.fluentApiSpigot = fluentApiSpigot;
    }

    public static FluentApiSpigot getFluentApiSpigot()
    {
        if (fluentApiSpigot == null) {
            FluentLogger.LOGGER.error("FluentAPI has not been initialized initialize");
            FluentLogger.LOGGER.error("Remember not to use FluentApi class inside OnConfiguration(FluentApiSpigotBuilder builder) method");
            return null;
        }
        return fluentApiSpigot;
    }


    public static FluentPlayerContext playerContext() {
        return getFluentApiSpigot().playerContext();
    }

    public static FluentPermission permission() {
        return getFluentApiSpigot().permission();
    }

    public static FluentInjection container() {
        return getFluentApiSpigot().container();
    }

    public static FluentMediator mediator() {
        return getFluentApiSpigot().mediator();
    }

    public static FluentFiles files() {
        return getFluentApiSpigot().files();
    }

    public static FluentTranslator translator() {
        return getFluentApiSpigot().translator();
    }

    public static FluentConfig config() {
        return getFluentApiSpigot().config();
    }
    public static FluentEventManager events() {
        return getFluentApiSpigot().events();
    }

    public static SimpleMessage messages(){
        return getFluentApiSpigot().messages();
    }
    public static FluentTaskManager tasks() {
        return getFluentApiSpigot().tasks();
    }

    public static CommandBuilder commands(String commandName) {
        return getFluentApiSpigot().commands(commandName);
    }

    /*public static FluentParticlebuilder particles() {
        return getFluentApiSpigot().particles();
    }*/
}

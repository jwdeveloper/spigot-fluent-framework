package io.github.jwdeveloper.spigot.fluent.plugin.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.files.FileUtility;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.FluentCommandManger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.CommandBuilderImpl;
import io.github.jwdeveloper.spigot.fluent.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.SimpleMessage;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtensionsManager;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.dependecy_injection.FluentInjection;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.files.FluentFiles;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.api.FluentPermission;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.implementation.FluentPlayerContext;
import org.bukkit.plugin.Plugin;

public final class FluentApiSpigot {

    private final FluentInjection fluentInjection;
    private final FluentMediator fluentMediator;
    private final FluentFiles fluentFiles;
    private final FluentTranslator fluentTranslator;
    private final FluentConfig fluentConfig;
    private final FluentPermission fluentPermission;
    private final FluentPlayerContext playerContext;
    private final Plugin plugin;
    private final SimpleMessage simpleMessages;
    private final FluentEventManager fluentEvents;
    private final FluentTaskManager simpleTasks;
    private final FluentCommandManger commandManger;
    private final FluentApiExtensionsManager extensionsManager;
    private final SimpleLogger logger;

    public FluentApiSpigot(
            Plugin plugin,
            FluentInjection injection,
            FluentMediator mediator,
            FluentFiles files,
            FluentTranslator translator,
            FluentConfig fluentConfig,
            FluentPermission permission,
            FluentPlayerContext playerContext,
            FluentApiExtensionsManager extensionsManager,
            SimpleLogger logger,
            FluentCommandManger commandManger,
            FluentEventManager eventManager,
            FluentTaskManager taskManager) {
        this.plugin = plugin;
        this.fluentConfig = fluentConfig;
        this.playerContext = playerContext;
        this.extensionsManager = extensionsManager;
        this.commandManger = commandManger;
        this.logger = logger;
        fluentInjection = injection;
        fluentMediator = mediator;
        fluentFiles = files;
        fluentTranslator = translator;
        fluentPermission = permission;
        fluentEvents = eventManager;
        simpleTasks = taskManager;
        simpleMessages = new SimpleMessage();
    }
    public void enable() {
        extensionsManager.onEnable(this);
    }
    public void disable() {
        extensionsManager.onDisable(this);
    }
    public FluentPlayerContext playerContext() {
        return playerContext;
    }

    public FluentPermission permission() {
        return fluentPermission;
    }

    public FluentInjection container() {
        return fluentInjection;
    }
    public FluentMediator mediator() {
        return fluentMediator;
    }

    public FluentFiles files() {
        return fluentFiles;
    }
    public FluentTranslator translator() {
        return fluentTranslator;
    }

    public FluentConfig config() {
        return fluentConfig;
    }

    public SimpleLogger logger() {
        return logger;
    }

    public String path() {

        return FileUtility.pluginPath(plugin);
    }

    public FluentEventManager events() {
        return fluentEvents;
    }

    public SimpleMessage messages() {
        return simpleMessages;
    }

    public FluentTaskManager tasks() {
        return simpleTasks;
    }

    public CommandBuilder commands(String commandName) {
        return new CommandBuilderImpl(commandName, commandManger);
    }

   /* public static FluentParticlebuilder particles() {
        return new FluentParticlebuilder(new ParticleSettings());
    }*/

    public Plugin plugin() {
        return plugin;
    }

    public String dataPath() {
        return path() + FileUtility.separator() + "data";
    }

}


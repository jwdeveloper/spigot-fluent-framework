package io.github.jwdeveloper.spigot.fluent.plugin.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.FluentCommandManger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.events.FluentEvent;
import io.github.jwdeveloper.spigot.fluent.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.FluentTask;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiContainerBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.assembly_scanner.FluentAssemblyScanner;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.ExtentionPiority;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.assemby_scanner.AssemblyScanner;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.config.FluentConfigImpl;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.config.FluentConfigLoader;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.FluentApiExtentionsManagerImpl;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.command.FluentApiDefaultCommandBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.command.FluentDefaultCommandExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.dependecy_injection.FluentInjectionExtention;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.documentation.DocumentationOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.documentation.FluentDocumentationExtention;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.files.FluentFiles;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.files.FluentFilesExtention;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.mediator.FluentMediatorExtention;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.metrics.MetricsExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.api.FluentPermission;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.implementation.FluentPermissionBuilderImpl;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.implementation.FluentPermissionExtention;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.FluentPlayerContextExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.implementation.FluentPlayerContext;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.resourcepack.ResourcepackExtention;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.resourcepack.ResourcepackOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.translator.FluentTranslationExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.UpdaterApiOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.implementation.FluentUpdaterExtension;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class FluentApiSpigotBuilderImpl implements FluentApiSpigotBuilder {
    private final FluentApiContainerBuilderImpl containerBuilder;
    private final FluentApiDefaultCommandBuilder commandBuilder;
    private final FluentApiExtentionsManagerImpl extensionsManager;
    private final FluentPermissionBuilderImpl fluentPermissionBuilder;
    private final FluentConfigImpl configFile;
    private final Plugin plugin;
    private final AssemblyScanner assemblyScanner;
    private final SimpleLogger logger;
    private final FluentTaskManager taskManager;
    private final FluentCommandManger commandManger;
    private final FluentEventManager eventManager;

    @SneakyThrows
    public FluentApiSpigotBuilderImpl(Plugin plugin) {
        this.plugin = plugin;
        FluentLogger.setLogger(plugin.getLogger());
        logger = FluentLogger.LOGGER;
        FluentCommand.enable(plugin);
        FluentEvent.enable(plugin);
        FluentTask.enable(plugin);
        commandManger = FluentCommand.getManager();
        eventManager = FluentEvent.getManager();
        taskManager = FluentTask.getManager();

        extensionsManager = new FluentApiExtentionsManagerImpl(logger);
        containerBuilder = new FluentApiContainerBuilderImpl(extensionsManager, logger);
        commandBuilder = new FluentApiDefaultCommandBuilder(plugin.getName(), commandManger);
        fluentPermissionBuilder = new FluentPermissionBuilderImpl(plugin);
        assemblyScanner = new AssemblyScanner(plugin, logger);
        configFile = FluentConfigLoader.loadConfig(plugin, assemblyScanner);
    }

    @Override
    public FluentApiCommandBuilder defaultCommand() {
        return commandBuilder;
    }

    @Override
    public FluentApiContainerBuilder container() {
        return containerBuilder;
    }

    @Override
    public FluentConfig config() {
        return configFile;
    }

    @Override
    public FluentPermissionBuilder permissions() {
        return fluentPermissionBuilder;
    }

    @Override
    public Plugin plugin() {
        return plugin;
    }

    @Override
    public FluentAssemblyScanner classFinder() {
        return assemblyScanner;
    }

    @Override
    public SimpleLogger logger() {
        return logger;
    }

    @Override
    public FluentTaskManager tasks() {
        return taskManager;
    }

    @Override
    public FluentApiSpigotBuilder useExtension(FluentApiExtension extension) {
        extensionsManager.register(extension, ExtentionPiority.MEDIUM);
        return this;
    }

    @Override
    public FluentApiSpigotBuilder useMetrics(int metricsId) {
        extensionsManager.register(new MetricsExtension(metricsId));
        return this;
    }

    @Override
    public FluentApiSpigotBuilder useUpdater(Consumer<UpdaterApiOptions> options) {
        extensionsManager.register(new FluentUpdaterExtension(options));
        return this;
    }

    @Override
    public FluentApiSpigotBuilder useDocumentation(Consumer<DocumentationOptions> options) {
        extensionsManager.register(new FluentDocumentationExtention(options), ExtentionPiority.HIGH);
        return this;
    }

    @Override
    public FluentApiSpigotBuilder useDocumentation() {
        extensionsManager.register(new FluentDocumentationExtention((e) -> {
        }));
        return this;
    }

    @Override
    public FluentApiSpigotBuilder useResourcePack(Consumer<ResourcepackOptions> options) {
        extensionsManager.register(new ResourcepackExtention(options));
        return this;
    }


    public FluentApiSpigot build() throws Exception {
        extensionsManager.registerLow(new FluentPermissionExtention(fluentPermissionBuilder));
        extensionsManager.registerLow(new FluentMediatorExtention());
        extensionsManager.registerLow(new FluentFilesExtention());
        extensionsManager.registerLow(new FluentTranslationExtension());
        extensionsManager.register(new FluentDefaultCommandExtension(commandBuilder), ExtentionPiority.HIGH);
        extensionsManager.register(new FluentPlayerContextExtension(logger));
        extensionsManager.onConfiguration(this);

        containerBuilder.registerSigleton(FluentTaskManager.class, taskManager);
        containerBuilder.registerSigleton(FluentEventManager.class, eventManager);
        containerBuilder.registerSigleton(FluentCommandManger.class, commandManger);
        containerBuilder.registerSigleton(FluentConfig.class, configFile);
        containerBuilder.registerSigleton(Plugin.class, plugin);
        containerBuilder.registerSigleton(FluentAssemblyScanner.class, assemblyScanner);
        final var injectionFactory = new FluentInjectionExtention(containerBuilder, assemblyScanner);
        final var result = injectionFactory.create();
        useExtension(injectionFactory);

        final var injection = result.fluentInjection();
        final var mediator = injection.findInjection(FluentMediator.class);
        final var files = injection.findInjection(FluentFiles.class);
        final var translator = injection.findInjection(FluentTranslator.class);
        final var permissions = injection.findInjection(FluentPermission.class);
        final var playerContext = injection.findInjection(FluentPlayerContext.class);

        return new FluentApiSpigot(
                plugin,
                injection,
                mediator,
                files,
                translator,
                configFile,
                permissions,
                playerContext,
                extensionsManager,
                logger,
                commandManger,
                eventManager,
                taskManager);
    }
}

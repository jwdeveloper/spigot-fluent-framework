package io.github.jwdeveloper.spigot.fluent.plugin.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiContainerBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.assembly_scanner.FluentAssemblyScanner;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.documentation.DocumentationOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.resourcepack.ResourcepackOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.UpdaterApiOptions;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class FluentApiBuilder implements FluentApiSpigotBuilder {

    public static FluentApiBuilder create(Plugin plugin, FluentApiExtension extension) {
        var builder = new FluentApiBuilder(new FluentApiSpigotBuilderImpl(plugin));
        builder.useExtension(extension);
        return builder;
    }


    public static FluentApiBuilder create(Plugin plugin) {
        return create((JavaPlugin) plugin);
    }

    public static FluentApiBuilder create(JavaPlugin plugin) {
        return new FluentApiBuilder(new FluentApiSpigotBuilderImpl(plugin));
    }

    private FluentApiSpigotBuilderImpl builder;

    FluentApiBuilder(FluentApiSpigotBuilderImpl builder) {
        this.builder = builder;
    }

    @Override
    public FluentApiCommandBuilder defaultCommand() {
        return builder.defaultCommand();
    }

    @Override
    public FluentApiContainerBuilder container() {
        return builder.container();
    }

    @Override
    public FluentApiSpigotBuilder useExtension(FluentApiExtension extension) {
        return builder.useExtension(extension);
    }

    @Override
    public FluentApiSpigotBuilder useMetrics(int metricsId) {
        return builder.useMetrics(metricsId);
    }

    @Override
    public FluentApiSpigotBuilder useUpdater(Consumer<UpdaterApiOptions> options) {
        return builder.useUpdater(options);
    }

    @Override
    public FluentApiSpigotBuilder useDocumentation(Consumer<DocumentationOptions> options) {
        return builder.useDocumentation(options);
    }

    @Override
    public FluentApiSpigotBuilder useDocumentation() {
        return builder.useDocumentation();
    }

    @Override
    public FluentApiSpigotBuilder useResourcePack(Consumer<ResourcepackOptions> options) {
        return builder.useResourcePack(options);
    }

    @Override
    public FluentConfig config() {
        return builder.config();
    }

    @Override
    public FluentPermissionBuilder permissions() {
        return builder.permissions();
    }

    @Override
    public Plugin plugin() {
        return builder.plugin();
    }

    @Override
    public FluentAssemblyScanner classFinder() {
        return builder.classFinder();
    }

    @Override
    public SimpleLogger logger() {
        return builder.logger();
    }

    @Override
    public FluentTaskManager tasks() {
        return builder.tasks();
    }

    public FluentApiSpigot build() {
        try {
            var api = builder.build();
            FluentApi.setFluentApiSpigot(api);
            api.enable();
            api.events().onEvent(PluginDisableEvent.class, event ->
            {
                api.disable();
            });
            return api;
        } catch (Exception e) {
            builder.logger().error("Unable to initialize FluentAPI", e);
        }
        return null;
    }
}

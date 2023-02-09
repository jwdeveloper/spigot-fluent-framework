package io.github.jwdeveloper.spigot.fluent.plugin.api;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.plugin.api.assembly_scanner.FluentAssemblyScanner;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.documentation.DocumentationOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.resourcepack.ResourcepackOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.UpdaterApiOptions;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public interface FluentApiSpigotBuilder {
    FluentApiCommandBuilder defaultCommand();

    FluentApiContainerBuilder container();

    FluentApiSpigotBuilder useExtension(FluentApiExtension extension);

    FluentApiSpigotBuilder useMetrics(int metricsId);

    FluentApiSpigotBuilder useUpdater(Consumer<UpdaterApiOptions> options);

    FluentApiSpigotBuilder useDocumentation(Consumer<DocumentationOptions> options);

    FluentApiSpigotBuilder useDocumentation();

    FluentApiSpigotBuilder useResourcePack(Consumer<ResourcepackOptions> options);

    FluentConfig config();

    FluentPermissionBuilder permissions();

    Plugin plugin();

    FluentAssemblyScanner classFinder();

    SimpleLogger logger();

    FluentTaskManager tasks();
}

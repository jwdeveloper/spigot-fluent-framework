package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.implementation;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.ConfigProperty;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.FluentUpdater;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.UpdateInfoProvider;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options.GithubUpdaterOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.UpdaterApiOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options.UpdaterOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.implementation.providers.GithubInfoProvider;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class FluentUpdaterExtension implements FluentApiExtension {
    private final Consumer<UpdaterApiOptions> optionsConsumer;
    private UpdaterOptions options;

    public FluentUpdaterExtension(Consumer<UpdaterApiOptions> options) {
        optionsConsumer = options;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        var updaterApiOptions = new UpdaterApiOptions();
        optionsConsumer.accept(updaterApiOptions);
        options = updaterApiOptions.getOptions();

        var section = createForceUpdateSection(builder.config(), options.isForceUpdate());
        var isForceUpdate = builder.config().getOrCreate(section);
        options.setForceUpdate(isForceUpdate);

        builder.container().register(FluentUpdater.class, LifeTime.SINGLETON, (c) ->
        {
            UpdateInfoProvider updateProvider = null;
            if(options instanceof GithubUpdaterOptions githubUpdaterOptions)
            {
                updateProvider = new GithubInfoProvider(githubUpdaterOptions);
            }
            return new SimpleUpdater(
                    updateProvider,
                    builder.tasks(),
                    builder.plugin(),
                    builder.logger(),
                    "/" +  builder.defaultCommand().getName() + " " + options.getCommandName());
        });

        var permissionModel = createPermission(builder.permissions());
        builder.permissions()
                .registerPermission(permissionModel);
        builder.defaultCommand()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(updatesCommand(options, permissionModel, builder.defaultCommand().getName()));
                });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        var updater= fluentAPI.container().findInjection(FluentUpdater.class);
        if(options.isForceUpdate())
            updater.downloadUpdateAsync(Bukkit.getConsoleSender());
        else
            updater.checkUpdateAsync(Bukkit.getConsoleSender());
    }

    private CommandBuilder updatesCommand(UpdaterOptions options, PermissionModel permission, String defaultCommandName) {
        return FluentCommand.create(options.getCommandName())
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(permission.getName());
                    propertiesConfig.setDescription("Download plugin latest version, can be trigger both by player or console");
                    propertiesConfig.setUsageMessage("/" + defaultCommandName + " " + options.getCommandName());
                })
                .eventsConfig(eventConfig ->
                {
                    var updater = FluentApi.container().findInjection(FluentUpdater.class);
                    eventConfig.onConsoleExecute(consoleCommandEvent ->
                    {
                        updater.downloadUpdateAsync(consoleCommandEvent.getConsoleSender());
                    });
                    eventConfig.onPlayerExecute(event ->
                    {
                        updater.downloadUpdateAsync(event.getSender());
                    });
                });
    }

    private PermissionModel createPermission(FluentPermissionBuilder builder) {
        var permission = new PermissionModel();
        permission.setName("update");
        permission.setDescription("Players with this permission can update plugin");
        builder.defaultPermissionSections().commands().addChild(permission);
        return permission;
    }

    private ConfigProperty<Boolean> createForceUpdateSection(FluentConfig config, boolean defaultValue) {
        return new ConfigProperty<>("update.force-update",defaultValue, """
                Automatically download plugin update when it's available
                """);
    }
}

package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.updater;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.updater.api.UpdaterOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.updater.implementation.SimpleUpdater;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class FluentUpdaterExtention implements FluentApiExtension {

    private final Consumer<UpdaterOptions> consumer;
    private final String commandName = "update";
    public FluentUpdaterExtention(Consumer<UpdaterOptions> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container().register(FluentUpdater.class, LifeTime.SINGLETON, (c) ->
        {
            var options = new UpdaterOptions();
            consumer.accept(options);
            var simpleUpdater = new SimpleUpdater(options, builder.plugin(),builder.defaultCommand().getName());
            return new FluentUpdaterImpl(simpleUpdater);
        });

        var permission = createPermission(builder.permissions());
        builder.permissions().registerPermission(permission);
        builder.defaultCommand()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(updatesCommand(permission, builder.defaultCommand().getName()));
                });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        var updater= fluentAPI.container().findInjection(FluentUpdater.class);
        updater.checkUpdate(Bukkit.getConsoleSender());
    }

    private CommandBuilder updatesCommand(PermissionModel permission, String defaultCommandName) {
        return FluentCommand.create(commandName)
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(permission.getName());
                    propertiesConfig.setDescription("download plugin latest version, can be trigger both by player or console");
                    propertiesConfig.setUsageMessage("/"+defaultCommandName+" "+commandName);
                })
                .eventsConfig(eventConfig ->
                {
                    var updater= FluentApi.container().findInjection(FluentUpdater.class);
                    eventConfig.onConsoleExecute(consoleCommandEvent ->
                    {
                        updater.downloadUpdate(consoleCommandEvent.getConsoleSender());
                    });
                    eventConfig.onPlayerExecute(event ->
                    {
                        updater.downloadUpdate(event.getSender());
                    });
                });
    }

    private PermissionModel createPermission(FluentPermissionBuilder builder)
    {
        var permission = new PermissionModel();
        permission.setName("update");
        permission.setDescription("players with this permission can update plugin");
        builder.defaultPermissionSections().commands().addChild(permission);
        return permission;
    }
}

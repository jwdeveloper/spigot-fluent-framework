package io.github.jwdeveloper.spigot.fluent.plugin.implementation;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FluentPlugin extends JavaPlugin implements FluentApiExtension {

    public abstract void onConfiguration(FluentApiSpigotBuilder builder);

    public abstract void onFluentApiEnable(FluentApiSpigot fluentAPI);

    public abstract void onFluentApiDisabled(FluentApiSpigot fluentAPI);

    @Override
    public final void onEnable() {

        var apiBuilder = FluentApiBuilder.create(this);
        apiBuilder.useExtension(this);
        var api = apiBuilder.build();
        if(api == null)
        {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        debugCommand();
    }

    public void debugCommand() {
        FluentCommand.create("disable")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.setDescription("Command only for plugin development purpose. Can be only trigger by Console. disables all plugins");
                    propertiesConfig.setUsageMessage("/disable");
                    propertiesConfig.setHideFromTabDisplay(true);
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onConsoleExecute(consoleCommandEvent ->
                    {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Plugins disabled");
                        Bukkit.getPluginManager().disablePlugins();
                    });
                })
                .buildAndRegister();
    }

}

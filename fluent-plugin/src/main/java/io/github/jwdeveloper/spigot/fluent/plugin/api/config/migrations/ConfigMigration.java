package io.github.jwdeveloper.spigot.fluent.plugin.api.config.migrations;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public interface ConfigMigration
{

    String version();
    void onPluginUpdate(YamlConfiguration config) throws IOException;
}

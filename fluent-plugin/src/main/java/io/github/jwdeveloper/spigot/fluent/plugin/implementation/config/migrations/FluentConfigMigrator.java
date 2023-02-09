package io.github.jwdeveloper.spigot.fluent.plugin.implementation.config.migrations;

import io.github.jwdeveloper.spigot.fluent.core.common.versions.VersionCompare;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.common.versions.VersionNumberComparator;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.migrations.ConfigMigration;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.migrations.ConfigMigrator;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.assemby_scanner.AssemblyScanner;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class FluentConfigMigrator implements ConfigMigrator {
    private final AssemblyScanner assemblyScanner;
    private final Plugin plugin;
    private final String VERSION_PATH = "plugin.version";

    public FluentConfigMigrator(AssemblyScanner assemblyScanner, Plugin plugin) {
        this.assemblyScanner = assemblyScanner;
        this.plugin = plugin;
    }


    public boolean isPluginUpdated(YamlConfiguration yamlConfig) {
        return VersionCompare.isHigher(getCurrentPluginVersion(), getConfigVersion(yamlConfig));
    }

    @Override
    public void makeMigration(YamlConfiguration configuration) throws InstantiationException, IllegalAccessException {
        var migrations = new ArrayList<ConfigMigration>();
        var migrationsClasses = assemblyScanner.findByInterface(ConfigMigration.class);
        for (var clazz : migrationsClasses) {
            var instance = (ConfigMigration) clazz.newInstance();
            migrations.add(instance);
        }
        var currentVersion = getCurrentPluginVersion();
        var configVersion = getConfigVersion(configuration);
        var sorted = getMigrationsBetween(migrations, configVersion, currentVersion);
        FluentLogger.LOGGER.info("Migration from",configVersion,"to",currentVersion,"has started");
        for(var migration : sorted)
        {
            FluentLogger.LOGGER.info("Migrating config to plugin version",migration.version());
            try {
                migration.onPluginUpdate(configuration);
            }
            catch (Exception e)
            {
                 FluentLogger.LOGGER.warning("Error while migration to"+migration.version(),e.getMessage());
            }

        }
        configuration.set(VERSION_PATH, currentVersion);
    }


    private List<ConfigMigration> getMigrationsBetween(List<ConfigMigration> migrations, String lastVersion, String currentVersion)
    {
        var sorted = sortMigrations(migrations);;
        var result = new ArrayList<ConfigMigration>();
        for (var migration : sorted)
        {
           var equalCurrent = currentVersion.equals(migration.version());

           var isLowerThenCurrent = VersionCompare.isLower(migration.version(), currentVersion);
           var isLowerThenOld = VersionCompare.isLower(lastVersion, migration.version());
           if((equalCurrent || isLowerThenCurrent) && isLowerThenOld)
           {
               result.add(migration);
           }
        }
        return result;
    }


    private List<ConfigMigration> sortMigrations(List<ConfigMigration> migrations) {
        var result = new ArrayList<ConfigMigration>();
        var versionNames = new ArrayList<>(migrations.stream().map(ConfigMigration::version).toList());
        versionNames.sort(VersionNumberComparator.getInstance());

        for (var version : versionNames) {
            var optional = migrations.stream().filter(c -> c.version().equals(version)).findFirst();
            if (optional.isEmpty()) {
                FluentLogger.LOGGER.warning("Problem with sort migration for version", version);
                continue;
            }
            result.add(optional.get());
        }
        return result;
    }

    private String getCurrentPluginVersion() {
        return plugin.getDescription().getVersion();
    }

    private String getConfigVersion(YamlConfiguration configuration) {
        return configuration.getString(VERSION_PATH);
    }
}

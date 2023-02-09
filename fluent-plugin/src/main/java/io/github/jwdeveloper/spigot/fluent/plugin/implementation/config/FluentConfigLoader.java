package io.github.jwdeveloper.spigot.fluent.plugin.implementation.config;

import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.files.FileUtility;
import io.github.jwdeveloper.spigot.fluent.core.files.yaml.api.YamlReader;
import io.github.jwdeveloper.spigot.fluent.core.files.yaml.implementation.SimpleYamlReader;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.ConfigSection;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.assemby_scanner.AssemblyScanner;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.config.migrations.FluentConfigMigrator;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.config.sections.DefaultConfigSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FluentConfigLoader {
    private final FluentConfigMigrator migrator;
    private final YamlReader yamlMapper;
    private final AssemblyScanner assemblyScanner;
    private final Plugin plugin;
    public FluentConfigLoader(Plugin plugin, AssemblyScanner assemblyScanner, FluentConfigMigrator migrator) {
        yamlMapper = new SimpleYamlReader();
        this.assemblyScanner = assemblyScanner;
        this.migrator = migrator;
        this.plugin = plugin;
    }

    public static FluentConfigImpl loadConfig(Plugin plugin,  AssemblyScanner assemblyScanner) throws Exception {
        var loader = new FluentConfigLoader(plugin, assemblyScanner, new FluentConfigMigrator(assemblyScanner, plugin));
        var path = FileUtility.pluginPath(plugin) + File.separator + "config.yml";
        return loader.load(path);
    }


    public FluentConfigImpl load(String path) throws Exception {
        var yamlConfiguration = getConfigFile(path);
        yamlConfiguration.options().header(StringUtils.EMPTY);

        if (migrator.isPluginUpdated(yamlConfiguration)) {
            migrator.makeMigration(yamlConfiguration);
            yamlConfiguration.save(path);
        }
        var sections = createAndMapSections(yamlConfiguration);
        yamlConfiguration.save(path);
        return new FluentConfigImpl(yamlConfiguration, path, false, false);
    }


    private List<ConfigSection> createAndMapSections(YamlConfiguration configuration) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {

        var sections = new ArrayList<ConfigSection>();
        var sectionsClasses = assemblyScanner.findByInterface(ConfigSection.class);
        for (var clazz : sectionsClasses) {
            var sectionObject = (ConfigSection) clazz.newInstance();
            yamlMapper.toConfiguration(sectionObject, configuration);
        }
        return sections;
    }

    private YamlConfiguration getConfigFile(String path) throws IllegalAccessException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {


        if (!FileUtility.pathExists(path)) {
            var yamlConfig = new YamlConfiguration();
            yamlMapper.toConfiguration(new DefaultConfigSection(plugin), yamlConfig);
            yamlConfig.save(path);
            return yamlConfig;
        }

        var file = new File(path);
        return  YamlConfiguration.loadConfiguration(file);
    }

}

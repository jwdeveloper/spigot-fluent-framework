package io.github.jwdeveloper.spigot.fluent.plugin.implementation.config;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.files.yaml.implementation.SimpleYamlModelFactory;
import io.github.jwdeveloper.spigot.fluent.core.files.yaml.implementation.SimpleYamlModelMapper;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.SimpleMessage;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.ConfigProperty;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

@Getter
public class FluentConfigImpl implements FluentConfig {

    private final FileConfiguration fileConfiguration;
    private final String path;

    public FluentConfigImpl(FileConfiguration fileConfiguration, String path, boolean updated, boolean created) {
        this.fileConfiguration = fileConfiguration;
        this.path = path;
    }

    public <T> T get(String name) {
        return (T) fileConfiguration.get(name);
    }

    public FileConfiguration configFile()
    {
        return fileConfiguration;
    }

    @Override
    public <T> T toObject(Class<T> clazz)
    {
        return null;
    }

    @Override
    public void save() {
        try
        {
            fileConfiguration.save(path);
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("Unable to save config path!",e);
        }

    }


    public void save(Object configSection)
    {
        try
        {
            var factory = new SimpleYamlModelFactory();
            var model = factory.createModel(configSection.getClass());
            var mapper = new SimpleYamlModelMapper();
            mapper.mapToConfiguration(configSection, model,(YamlConfiguration)fileConfiguration ,true);
            fileConfiguration.save(path);
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("Unable to save config path!",e);
        }

    }

    public Object getRequired(String name) throws Exception {
        var value = get(name);
        if (value == null) {
            throw new Exception("Value " + name + " is required");
        }
        return value;
    }

    @Override
    public <T> T getOrCreate(String path, T defaultValue, String ... description) {

        if(!fileConfiguration.contains(path))
        {
            fileConfiguration.set(path,defaultValue);
        }

        var builder = new TextBuilder();
        builder.text(fileConfiguration.options().header());
        builder.newLine();
        builder.text(path);
        builder.newLine();
        for(var desc : description)
        {
            builder.bar(" ",3).text(desc).newLine();
        }
        builder.newLine();
        fileConfiguration.options().header(builder.toString());
        save();

        return (T)fileConfiguration.get(path);
    }

    @Override
    public <T> T getOrCreate(ConfigProperty<T> configProperty) {
        return getOrCreate(configProperty.path(),configProperty.defaultValue(),configProperty.description());
    }
}

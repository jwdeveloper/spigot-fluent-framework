package io.github.jwdeveloper.spigot.fluent.core.files.yaml.api;

import io.github.jwdeveloper.spigot.fluent.core.files.yaml.api.models.YamlModel;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.InvocationTargetException;

public interface YamlModelMapper {
    public <T> YamlConfiguration mapToConfiguration(T data, YamlModel model, YamlConfiguration configuration, boolean overrite) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    public Object mapFromConfiguration(Object object, YamlModel model, YamlConfiguration configuration) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException;
}

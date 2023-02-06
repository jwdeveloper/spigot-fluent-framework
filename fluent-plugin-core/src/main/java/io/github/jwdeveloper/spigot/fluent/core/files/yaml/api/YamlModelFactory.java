package io.github.jwdeveloper.spigot.fluent.core.files.yaml.api;

import io.github.jwdeveloper.spigot.fluent.core.files.yaml.api.models.YamlModel;

public interface YamlModelFactory
{
    public <T> YamlModel createModel(Class<T> clazz) throws ClassNotFoundException;
}

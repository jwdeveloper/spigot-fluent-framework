package io.github.jwdeveloper.spigot.fluent.plugin.implementation.file_handlers;

import io.github.jwdeveloper.spigot.fluent.core.repository.api.CustomFile;

public interface DataContext
{
    void addCustomFileObject(CustomFile object);

    void addJsonObject(Object object);

    void addObject(Class<? extends FileHandler> handlerType, Object object);

    void registerFileHandler(FileHandler fileHandler);

    void save();
}

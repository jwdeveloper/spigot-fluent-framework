package io.github.jwdeveloper.spigot.fluent.plugin.implementation.file_handlers;

import io.github.jwdeveloper.spigot.fluent.core.common.java.ObjectUtility;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.files.json.JsonUtility;

import java.util.ArrayList;
import java.util.List;

public class JsonFilesHandler implements FileHandler {

    private final List<Object> files = new ArrayList<>();
    private final String path;

    public JsonFilesHandler(String path)
    {
        this.path = path;
    }

    @Override
    public void load() {
        for (var file : files) {
            try {
                var loadedObject = JsonUtility.load(
                        path,
                        file.getClass().getSimpleName(),
                        file.getClass());

                if (loadedObject != null) {
                    ObjectUtility.copyToObject(loadedObject, file, loadedObject.getClass());
                }

            } catch (Exception e) {
                FluentLogger.LOGGER.error("Loading path data error", e);
            }
        }
    }

    @Override
    public void save() {
        for (var file : files) {
            JsonUtility.save(file, path, file.getClass().getSimpleName());
        }
    }

    @Override
    public void addObject(Object object) {
        files.add(object);
    }

    @Override
    public void removeObject(Object object) {
        files.remove(object);
    }
}

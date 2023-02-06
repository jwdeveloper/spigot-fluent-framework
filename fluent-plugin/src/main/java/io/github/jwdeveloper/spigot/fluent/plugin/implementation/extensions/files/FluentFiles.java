package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.files;

import jw.fluent.api.files.implementation.watcher.FileWatcher;

public interface FluentFiles {

    void load() throws Exception;

    void save();

    FileWatcher createFileWatcher(String path);
}

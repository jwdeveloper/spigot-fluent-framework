package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.files;

import jw.fluent.api.files.implementation.FilesDataContext;
import jw.fluent.api.files.implementation.watcher.FileWatcher;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.event.server.PluginDisableEvent;

import java.io.File;

public class FluentFilesImpl extends FilesDataContext implements FluentFiles {


    @Override
    public FileWatcher createFileWatcher(String path) {
        var watcher = new FileWatcher(new File(path));
        FluentApi.tasks().taskAsync(e ->
        {
            FluentLogger.LOGGER.info("STARTED wather");
            watcher.start();
        });
        FluentApi.events().onEvent(PluginDisableEvent.class, event ->
        {
            FluentLogger.LOGGER.info("SRTOPED wather");
            watcher.stopThread();
        });
        return watcher;
    }


}

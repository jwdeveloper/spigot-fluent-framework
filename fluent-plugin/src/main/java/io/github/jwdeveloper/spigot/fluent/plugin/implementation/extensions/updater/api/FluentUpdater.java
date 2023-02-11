package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api;

import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.info.CheckUpdateInfo;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.function.Consumer;

public interface FluentUpdater {

    void downloadUpdateAsync(CommandSender commandSender);
    void checkUpdateAsync(Consumer<CheckUpdateInfo> consumer);
    void checkUpdateAsync(CommandSender commandSender);
    CheckUpdateInfo checkUpdate() throws IOException;
}

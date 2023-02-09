package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.common.versions.VersionCompare;
import io.github.jwdeveloper.spigot.fluent.core.files.FileUtility;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.FluentUpdater;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.UpdateInfoProvider;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.info.CheckUpdateInfo;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.info.UpdateInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class SimpleUpdater implements FluentUpdater {
    private final UpdateInfoProvider provider;
    private final FluentTaskManager taskManager;
    private final Plugin plugin;
    private final SimpleLogger logger;

    private final String commandName;

    public SimpleUpdater(UpdateInfoProvider provider,
                         FluentTaskManager taskManager,
                         Plugin plugin,
                         SimpleLogger logger,
                         String commandName) {
        this.provider = provider;
        this.taskManager = taskManager;
        this.plugin = plugin;
        this.logger = logger;
        this.commandName = commandName;
    }

    @Override
    public void checkUpdateAsync(Consumer<CheckUpdateInfo> consumer) {
        taskManager.taskAsync(() ->
        {
            try {
                var updateInfo = checkUpdate();
                consumer.accept(updateInfo);
            } catch (Exception e) {
                logger.error("Checking for update error", e);
            }
        });
    }

    @Override
    public void checkUpdateAsync(CommandSender commandSender)
    {
        checkUpdateAsync(info ->
        {
            if (info.isUpdate()) {
                getMessagePrefix().text("Latest version is already downloaded").send(commandSender);
                return;
            }
            getMessagePrefix().text("New version available, use " + ChatColor.AQUA + "/" + commandName + ChatColor.RESET + " to download").send(commandSender);
            getMessagePrefix().text("Changes:").send(commandSender);
            commandSender.sendMessage(info.getUpdateInfo().getDescription());
        });
    }


    @Override
    public CheckUpdateInfo checkUpdate() throws IOException {
        var infoResponse = provider.getUpdateInfo();
        if (VersionCompare.isHigher(infoResponse.getVersion(), plugin.getDescription().getVersion())) {
            return new CheckUpdateInfo(true, infoResponse);
        }
        return new CheckUpdateInfo(false, infoResponse);
    }


    @Override
    public void downloadUpdateAsync(CommandSender commandSender) {

        checkUpdateAsync(checkUpdateInfo ->
        {
            if (!checkUpdateInfo.isUpdate()) {
                getMessagePrefix().text("Latest version is already downloaded").send(commandSender);
                return;
            }
            getMessagePrefix().text("Downloading latest version...")
                    .send(commandSender);
            if (downloadFile(checkUpdateInfo.getUpdateInfo())) {
                return;
            }
            getMessagePrefix().text("New version downloaded! use ")
                    .text("/reload", ChatColor.AQUA).color(ChatColor.GRAY)
                    .text(" to apply changes")
                    .send(commandSender);
        });
    }

    private MessageBuilder getMessagePrefix() {
        var msg = new MessageBuilder().inBrackets(plugin.getName());
        return msg.space().color(ChatColor.AQUA).inBrackets("Update info").color(ChatColor.GRAY).space();
    }

    private String getUpdatesFolder() {
        return FileUtility.pluginPath(plugin) + File.separator + "update" + File.separator;
    }

    private boolean downloadFile(UpdateInfo info) {
        var output = getUpdatesFolder() + info.getFileName();
        FileUtility.ensurePath(output);
        var downloadUrl = info.getDownloadUrl();
        try {
            var in = new BufferedInputStream(new URL(downloadUrl).openStream());
            var file = new File(output);
            file.createNewFile();
            var fileOutputStream = new FileOutputStream(output, false);
            var dataBuffer = new byte[1024];
            var bytesRead = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            logger.error("Update download error", e);
            return false;
        }
    }
}

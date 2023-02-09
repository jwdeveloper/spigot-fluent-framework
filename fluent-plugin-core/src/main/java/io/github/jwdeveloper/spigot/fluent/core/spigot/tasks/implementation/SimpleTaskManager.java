package io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.TaskAction;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class SimpleTaskManager implements FluentTaskManager
{
    private final Plugin plugin;
    private final SimpleLogger logger;

    public SimpleTaskManager(Plugin plugin, SimpleLogger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    public SimpleTaskTimer taskTimer(int ticks, TaskAction task) {
        return new SimpleTaskTimer(ticks, task, plugin, logger);
    }
    public BukkitTask task(Runnable action) {
        return Bukkit.getScheduler().runTask(plugin, action);
    }

    public void taskLater(Runnable action, int ticks) {
        Bukkit.getScheduler().runTaskLater(plugin, action, ticks);
    }
    public BukkitTask taskAsync(Runnable action) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, action);
    }
}

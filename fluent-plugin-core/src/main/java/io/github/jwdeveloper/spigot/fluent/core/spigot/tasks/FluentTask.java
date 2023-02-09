package io.github.jwdeveloper.spigot.fluent.core.spigot.tasks;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.TaskAction;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.implementation.SimpleTaskManager;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.implementation.SimpleTaskTimer;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class FluentTask {
    private static SimpleTaskManager manger;

    public SimpleTaskTimer taskTimer(int ticks, TaskAction task) {
        return getManager().taskTimer(ticks, task);
    }

    public BukkitTask task(Runnable action) {
        return getManager().task(action);
    }

    public void taskLater(int ticks, Runnable action) {
        getManager().taskLater(action, ticks);
    }

    public BukkitTask taskAsync(Runnable action) {
        return getManager().taskAsync(action);
    }

    public static FluentTaskManager getManager() {
        if (manger == null) {
            throw new RuntimeException(FluentTaskManager.class.getSimpleName() + " are disabled, use to enable it " + FluentTask.class.getSimpleName() + ".enable(plugin)");
        }
        return manger;
    }

    public static void enable(Plugin plugin) {
        manger = new SimpleTaskManager(plugin, FluentLogger.LOGGER);
    }
}

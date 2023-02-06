package io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api;

import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.implementation.SimpleTaskTimer;
import org.bukkit.scheduler.BukkitTask;

public interface TaskManager {
    SimpleTaskTimer taskTimer(int ticks, TaskAction task);

    BukkitTask task(Runnable action);

    void taskLater(Runnable action, int ticks);

    BukkitTask taskAsync(Runnable action);
}

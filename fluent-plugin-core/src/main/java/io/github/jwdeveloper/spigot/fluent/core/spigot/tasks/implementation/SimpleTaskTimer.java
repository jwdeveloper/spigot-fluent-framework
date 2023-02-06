package io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.TaskAction;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class SimpleTaskTimer {
    private final TaskAction task;
    private final Plugin plugin;

    private final SimpleLogger logger;

    private Consumer<SimpleTaskTimer> onStop;
    private Consumer<SimpleTaskTimer> onStart;
    private int speed = 20;
    private int time = 0;
    private int runAfter = 0;
    private int stopAfter = Integer.MAX_VALUE;
    private boolean isCancel = false;
    private BukkitTask bukkitTask;

    public SimpleTaskTimer(int speed,
                           TaskAction action,
                           Plugin plugin,
                           SimpleLogger logger)
    {
        this.speed = speed;
        this.task = action;
        this.plugin = plugin;
        this.logger = logger;
    }

    public void setIteration(int iteration)
    {
        this.time  =iteration;
    }
    public SimpleTaskTimer runAsync() {

        if (onStart != null)
            onStart.accept(this);
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::taskBody, runAfter, speed);
        return this;
    }

    public void reset() {
        this.time = 0;
    }

    public SimpleTaskTimer run() {
        isCancel =false;
        if (onStart != null)
            onStart.accept(this);
        bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, this::taskBody, runAfter, speed);
        return this;
    }

    private void taskBody()
    {
        try
        {
            if (time >= stopAfter || isCancel || bukkitTask.isCancelled())
            {
                if (onStop != null)
                    onStop.accept(this);
                stop();
                return;
            }
            task.execute(time, this);
            time++;
        }
        catch (Exception e)
        {
            logger.error("FluentTask error",e);
            stop();
        }
    }

    public void stop() {
        if (bukkitTask == null)
            return;

        Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
        bukkitTask.cancel();
        isCancel =true;
        bukkitTask = null;
    }

    public boolean isRunning()
    {
        return bukkitTask != null;
    }

    public void cancel() {
        isCancel = true;
    }

    public SimpleTaskTimer startAfterTicks(int iterations) {
        this.runAfter = iterations;
        return this;
    }

    public SimpleTaskTimer stopAfterIterations(int iterations) {
        this.stopAfter = iterations;
        return this;
    }

    public SimpleTaskTimer onStop(Consumer<SimpleTaskTimer> event) {
        this.onStop = event;
        return this;
    }

    public SimpleTaskTimer onStart(Consumer<SimpleTaskTimer> event) {
        this.onStart = event;
        return this;
    }

    public void runAgain() {
        this.time = 0;
        this.isCancel = false;
    }
}
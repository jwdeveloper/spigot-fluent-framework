package io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api;


import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.implementation.SimpleTaskTimer;

public interface TaskAction
{
    void execute(int iteration, SimpleTaskTimer task);
}

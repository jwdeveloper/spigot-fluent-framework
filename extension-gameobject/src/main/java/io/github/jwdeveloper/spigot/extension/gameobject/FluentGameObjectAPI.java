package io.github.jwdeveloper.spigot.extension.gameobject;

import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentGameObjectAPI
{
    public static FluentApiExtension use()
    {
        return new GameObjectExtension();
    }
}

package io.github.jwdeveloper.spigot.extension.gameobject;

import io.github.jwdeveloper.spigot.extension.gameobject.api.FluentGameObjectManager;
import io.github.jwdeveloper.spigot.extension.gameobject.implementation.GameObjectManager;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;

public class GameObjectExtension implements FluentApiExtension
{
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder)
    {
        var manager = new GameObjectManager(builder.plugin(), builder.logger());
        builder.container().registerSigleton(FluentGameObjectManager.class, manager);
    }
}

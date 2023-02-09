package io.github.jwdeveloper.spigot.extension.gameobject.api;

import io.github.jwdeveloper.spigot.extension.gameobject.implementation.GameObject;
import org.bukkit.Location;

public interface FluentGameObjectManager
{
    boolean register(GameObject gameObject, Location location);

    void unregister(GameObject gameObject);
}

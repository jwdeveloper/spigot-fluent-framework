package io.github.jwdeveloper.spigot.fluent.core.spigot.events.api;

import io.github.jwdeveloper.spigot.fluent.core.spigot.events.implementation.SimpleEvent;
import org.bukkit.event.Event;

import java.util.function.Consumer;

public interface EventManager {
    <T extends Event> SimpleEvent<T> onEvent(Class<T> eventType, Consumer<T> action);
    <T extends Event> SimpleEvent<T> onEventAsync(Class<T> tClass, Consumer<T> action);
}

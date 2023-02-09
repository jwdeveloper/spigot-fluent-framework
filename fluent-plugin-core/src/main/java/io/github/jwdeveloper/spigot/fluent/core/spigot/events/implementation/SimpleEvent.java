package io.github.jwdeveloper.spigot.fluent.core.spigot.events.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimpleEvent<T extends Event> {
    private final Consumer<T> onEvent;
    private final List<Consumer<T>> nextActions;
    private Consumer<Exception> onError;
    private boolean isActive;
    @Getter
    private boolean isRegister =true;
    private String permission;
    private final SimpleLogger logger;

    public SimpleEvent(Consumer<T> onEvent, SimpleLogger logger) {
        this.onEvent = onEvent;
        this.nextActions = new ArrayList<>();
        this.isActive = true;
        this.logger = logger;
    }

    public SimpleEvent<T> setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public SimpleEvent<T> setActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public SimpleEvent<T> unregister() {
        isRegister = false;
        return this;
    }

    public boolean invoke(T arg) {
        if (!isActive)
            return false;

        if (permission != null && arg instanceof PlayerEvent playerEvent) {
            if (!playerEvent.getPlayer().hasPermission(permission)) {
                return false;
            }
        }

        try {
            onEvent.accept(arg);
            for (var action : nextActions) {
                if (arg instanceof Cancellable cancelable) {
                    if (cancelable.isCancelled())
                        break;
                }
                action.accept(arg);
            }
            return true;
        } catch (Exception exception)
        {
            logger.error("SimpleEvent exception",exception);
            if (onError != null)
                onError.accept(exception);
            return false;
        }
    }

    public SimpleEvent<T> onError(Consumer<Exception> onError) {
        this.onError = onError;
        return this;
    }

    public SimpleEvent<T> andThen(Consumer<T> action) {
        nextActions.add(action);
        return this;
    }
}

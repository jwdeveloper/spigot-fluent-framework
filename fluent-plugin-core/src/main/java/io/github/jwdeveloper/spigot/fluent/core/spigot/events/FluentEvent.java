package io.github.jwdeveloper.spigot.fluent.core.spigot.events.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.java.JavaUtils;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.events.api.EventManager;
import org.bukkit.plugin.Plugin;

public class FluentEvent
{
    private static SimpleEventManager manger;




    public static EventManager getManager() {
        if (manger == null) {
            throw new RuntimeException(EventManager.class.getSimpleName()+" are disabled, use to enable it "+EventManager.class.getSimpleName()+".enable(plugin)");
        }
        return manger;
    }

    public static void enable(Plugin plugin)
    {
        JavaUtils.ifNotNull(manger, SimpleEventManager::unregister);
        manger = new SimpleEventManager(plugin, FluentLogger.LOGGER);
    }
}

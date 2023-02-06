package io.github.jwdeveloper.spigot.fluent.core.spigot.events;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimpleEventManager implements Listener {
    private final List<SimpleEvent<PluginDisableEvent>> onPluginDisableEvents;
    private final List<SimpleEvent<PluginEnableEvent>> onPluginEnableEvents;
    private final Plugin plugin;
    private final SimpleLogger logger;

    public SimpleEventManager(Plugin plugin, SimpleLogger logger)
    {
        this.plugin = plugin;
        this.logger = logger;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        onPluginDisableEvents = new ArrayList<>();
        onPluginEnableEvents = new ArrayList<>();
    }

    public void unregister()
    {
        PluginEnableEvent.getHandlerList().unregister(this);
        PluginDisableEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    protected final void onPluginStart(PluginEnableEvent pluginEnableEvent)
    {
        if(pluginEnableEvent.getPlugin() == plugin)
        {
            for(var fluentEvent :onPluginEnableEvents)
            {
                fluentEvent.invoke(pluginEnableEvent);
            }
        }
    }

    @EventHandler
    protected final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent)
    {
        if(pluginDisableEvent.getPlugin() == plugin)
        {
            for(var fluentEvent :onPluginDisableEvents)
            {
                fluentEvent.invoke(pluginDisableEvent);
            }
        }
    }

    public  <T extends Event> SimpleEvent<T> onEvent(Class<T> eventType, Consumer<T> action)
    {
        var fluentEvent = new SimpleEvent<T>(action, logger);

        if(eventType.equals(PluginDisableEvent.class))
        {
            onPluginDisableEvents.add((SimpleEvent<PluginDisableEvent>)fluentEvent);
            return fluentEvent;
        }
        if(eventType.equals(PluginEnableEvent.class))
        {
            onPluginEnableEvents.add((SimpleEvent<PluginEnableEvent>)fluentEvent);
            return fluentEvent;
        }
        Bukkit.getPluginManager().registerEvent(eventType,this, EventPriority.NORMAL,
                (listener, event) ->
                {
                    if(!fluentEvent.isRegister())
                    {
                        event.getHandlers().unregister(this);
                        return;
                    }

                    if(!event.getClass().getSimpleName().equalsIgnoreCase(eventType.getSimpleName()))
                    {
                        return;
                    }
                    fluentEvent.invoke((T) event);
                }, plugin);
        return fluentEvent;
    }

    public <T extends Event> SimpleEvent<T> onEventAsync(Class<T> tClass, Consumer<T> action)
    {

        var fluentEvent = new SimpleEvent<T>(action, logger);
        Bukkit.getPluginManager().registerEvent(tClass,this, EventPriority.NORMAL,
                (listener, event) ->
                {
                    Bukkit.getScheduler().runTask(plugin,()->
                    {
                        fluentEvent.invoke((T)event);
                    });
                }, plugin);
        return fluentEvent;
    }
}

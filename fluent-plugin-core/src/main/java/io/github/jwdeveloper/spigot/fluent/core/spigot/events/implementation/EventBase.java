package io.github.jwdeveloper.spigot.fluent.core.spigot.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public abstract class EventBase implements Listener
{
    @Getter
    protected final Plugin plugin;

    public EventBase(Plugin plugin)
    {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private boolean pluginDisabled = false;

    public boolean isPluginDisabled()
    {
        return pluginDisabled;
    }

    public void onPluginStart(PluginEnableEvent event)
    {

    }
    public void onPluginStop(PluginDisableEvent event)
    {

    }
    @EventHandler
    public final void onPluginStartEvent(PluginEnableEvent pluginEnableEvent)
    {
          if(pluginEnableEvent.getPlugin() == plugin)
          {
              onPluginStart(pluginEnableEvent);
          }
    }
    @EventHandler
    public final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent)
    {
        pluginDisabled = true;
        if(pluginDisableEvent.getPlugin() == plugin)
        {
            onPluginStop(pluginDisableEvent);
        }
    }

}

package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.events;

import core.implementation.button.ButtonUI;
import org.bukkit.entity.Player;

public record onSelectEvent<T>(ButtonUI buttonUI, T data, int index, Player player)
{

}

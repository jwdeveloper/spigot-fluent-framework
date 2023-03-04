package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.events;

import core.implementation.button.ButtonUI;
import org.bukkit.entity.Player;

public interface ButtonUIEvent
{
    void execute(Player player, ButtonUI button);
}

package test.api.managers;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;
import org.bukkit.inventory.Inventory;

public interface ButtonManager
{
    void factory();

    void builder();

    ButtonUI[] getButtons();

    void refresh();
}

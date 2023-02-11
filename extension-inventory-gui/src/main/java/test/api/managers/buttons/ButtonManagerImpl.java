package test.api.managers.buttons;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.ButtonObserverUI;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import test.api.managers.ButtonManager;

public class ButtonManagerImpl implements ButtonManager {
    private Inventory handle;
    private ButtonUI[] buttons;

    private int slots;
    @Override
    public void factory() {

    }

    @Override
    public void builder() {

    }

    @Override
    public ButtonUI[] getButtons() {
        return new ButtonUI[0];
    }

    @Override
    public void refresh() {
        if (handle == null)
            return;

        ButtonUI button = null;
        for (int i = 0; i < buttons.length; i++) {
            button = buttons[i];
            if (button != null && button.isActive()) {
                handle.setItem(i, button.getItemStack());
            } else
                handle.setItem(i, null);
        }
    }

    public void handleClick(InventoryClickEvent event)
    {

    }


    public ButtonUI getButton(int index)
    {
        return null;
    }

    public void updateHandle(Inventory handle)
    {
      this.handle = handle;
    }
}

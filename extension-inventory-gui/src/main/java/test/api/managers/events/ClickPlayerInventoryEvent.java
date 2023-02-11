package test.api.managers.events;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

@Getter
public class ClickPlayerInventoryEvent implements Cancellable
{
    private Player player;
    private ItemStack buttonUI;
    public ClickPlayerInventoryEvent(Player player, ItemStack buttonUI) {
        this.player = player;
        this.buttonUI = buttonUI;
    }



    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}

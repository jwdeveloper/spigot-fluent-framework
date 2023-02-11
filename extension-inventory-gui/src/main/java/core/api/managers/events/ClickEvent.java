package core.api.managers.events;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import core.api.FluentInventory;

@Getter
public class ClickEvent implements Cancellable {

    private final Player player;
    private final ButtonUI button;
    private final FluentInventory inventory;

    public ClickEvent(Player player, ButtonUI button, FluentInventory inventory) {
        this.player = player;
        this.button = button;
        this.inventory = inventory;
    }


    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}

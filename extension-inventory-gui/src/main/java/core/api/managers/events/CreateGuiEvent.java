package core.api.managers.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import core.api.InventoryDecorator;

@Getter
public class CreateGuiEvent implements Cancellable {

    @Setter
    private boolean cancelled;

    private Player player;

    private InventoryDecorator decorator;

    public CreateGuiEvent(Player player, InventoryDecorator decorator) {
        this.player = player;
        this.decorator = decorator;
    }
}

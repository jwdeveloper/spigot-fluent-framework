package core.api.managers.events;

import core.api.FluentInventory;
import lombok.Getter;
import org.bukkit.event.Cancellable;

public class OpenGuiEvent implements Cancellable {

    @Getter
    private FluentInventory inventory;
    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}

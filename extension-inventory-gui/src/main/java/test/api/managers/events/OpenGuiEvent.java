package test.api.managers.events;

import org.bukkit.event.Cancellable;

public class OpenGuiEvent implements Cancellable {
    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}

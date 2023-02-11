package test.api.builder;

import org.bukkit.entity.Player;
import test.api.FluentInventoryImpl;

public abstract class InventoryInstance {
    private final FluentInventoryImpl inventory;
    public InventoryInstance() {
        inventory = new FluentInventoryImpl();
        inventory.events().onCreate().subscribe(createGuiEvent ->
        {
            onCreate(createGuiEvent.getDecorator(), createGuiEvent.getPlayer());
        });
    }

    protected abstract void onCreate(InventoryDecorator decorator, Player player);

    public FluentInventoryImpl getInventory() {
        return inventory;
    }

}

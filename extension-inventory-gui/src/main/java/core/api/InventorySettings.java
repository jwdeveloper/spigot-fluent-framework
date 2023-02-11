package core.api;

import lombok.Data;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import core.api.enums.InventoryState;

@Data
public class InventorySettings {
    public static int INVENTORY_WIDTH = 9;

    private String title = "Inventory";
    private int height = 1;
    private InventoryState state = InventoryState.NOT_CREATED;

    private InventoryType inventoryType = InventoryType.CHEST;

    private Inventory handle;

    public int getSlots() {
        return height * INVENTORY_WIDTH;
    }
}

package test.listeners;

import io.github.jwdeveloper.spigot.fluent.core.spigot.events.implementation.EventBase;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import test.api.FluentInventoryImpl;
import test.api.enums.InventoryState;

import java.util.ArrayList;
import java.util.List;

public class InventorySpigotListener extends EventBase {
    private final List<FluentInventoryImpl> inventoriesGui;

    public InventorySpigotListener(Plugin plugin) {
        super(plugin);
        inventoriesGui = new ArrayList<>();
    }


    public void subscribe(FluentInventoryImpl InventoryUIBase) {
        if (inventoriesGui.contains(InventoryUIBase)) {
            return;
        }
        inventoriesGui.add(InventoryUIBase);
    }

    public void unsubscribe(FluentInventoryImpl InventoryUIBase) {
        inventoriesGui.remove(InventoryUIBase);
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        Inventory spigotInventory;
        for (var inventoryUI : inventoriesGui) {
            spigotInventory = inventoryUI.getHandle();
            if (spigotInventory == null || inventoryUI.getState() == InventoryState.CLOSED) continue;
            if (event.getInventory() == spigotInventory) {
                inventoryUI.close();
                break;
            }
        }
    }


    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory;
        for (var InventoryUI : inventoriesGui) {
            inventory = InventoryUI.getHandle();
            if (inventory == null || InventoryUI.getState() != InventoryState.OPEN) continue;
            if (event.getInventory() == inventory) {
                InventoryUI.refresh();
                break;
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    private void onClick(InventoryClickEvent event) {
        if (event.getRawSlot() == -999) return;
        Inventory inventory;
        ItemStack selectedItem;
        for (var inventoryUI : inventoriesGui) {
            inventory = inventoryUI.getHandle();
            if (inventory == null || inventoryUI.getState() != InventoryState.OPEN) continue;

            if (event.getInventory() == inventory) {
                selectedItem = event.getCurrentItem();
                if (selectedItem == null || selectedItem.getType() == Material.AIR) return;

                var result = inventoryUI.click(event);
                event.setCancelled(!result);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onDrag(InventoryDragEvent event) {
        if (event.getInventorySlots().size() > 2) return;

        for (var slot : event.getInventorySlots()) {
            if (slot == -999) return;
        }

        Inventory inventory;
        for (var inventoryUI : inventoriesGui) {
            inventory = inventoryUI.getHandle();
            if (inventory == null || inventoryUI.getState() != InventoryState.OPEN) continue;

            if (event.getInventory() == inventory) {
                inventoryUI.drag(event);
                break;
            }
        }
    }

    @EventHandler
    private void onPlayerExit(PlayerQuitEvent event) {
        for (int i = 0; i < inventoriesGui.size(); i++) {
            if (event.getPlayer() == inventoriesGui.get(i).getPlayer()) {
                inventoriesGui.get(i).close();
                return;
            }
        }
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        for (int i = 0; i < inventoriesGui.size(); i++) {
            inventoriesGui.get(i).close();
        }
    }

}

package inventory_gui;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class InventoryUIManager extends EventBase {
    private final ArrayList<InventoryUI> inventoriesGui = new ArrayList();
    private final HashMap<Player, Consumer<String>> textInputEvents = new HashMap<>();
    private final FluentTaskManager taskManager;

    public InventoryUIManager(FluentTaskManager manager, Plugin plugin) {
        super(plugin);
        this.taskManager = manager;
    }


    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        Inventory spigotInventory;
        for (InventoryUI inventoryUI : inventoriesGui) {
            spigotInventory = inventoryUI.getInventory();
            if (spigotInventory == null || !inventoryUI.isOpen()) continue;

            if (event.getInventory() == spigotInventory) {
                inventoryUI.close();
                break;
            }
        }
    }

    public void registerUI(InventoryUI InventoryUIBase) {
        if (!inventoriesGui.contains(InventoryUIBase)) {
            inventoriesGui.add(InventoryUIBase);
        }
    }

    public <T extends InventoryUI> void refreshAll(Class<T> _class, InventoryUI ignore) {
        for (var inventory : inventoriesGui) {
            if (!inventory.getClass().isAssignableFrom(_class)) continue;
            if (ignore != null && inventory.equals(ignore)) continue;
            inventory.refresh();
        }
    }

    public <T extends InventoryUI> void refreshAllAsync(Class<T> _class) {
        refreshAllAsync(_class, null);
    }

    public <T extends InventoryUI> void refreshAllAsync(Class<T> _class, InventoryUI ignore) {
        taskManager.task(() -> {
            refreshAll(_class, ignore);
        });
    }


    public void registerTextInput(Player player, Consumer<String> event) {

        if (textInputEvents.containsKey(player)) {
            textInputEvents.replace(player, event);
        } else {
            textInputEvents.put(player, event);
        }
    }

    public void unregisterUI(InventoryUI InventoryUIBase) {
        inventoriesGui.remove(InventoryUIBase);
    }

    public boolean isRegistered(InventoryUI InventoryUIBase) {
        return inventoriesGui.contains(InventoryUIBase);
    }

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory;
        for (InventoryUI InventoryUI : inventoriesGui) {
            inventory = InventoryUI.getInventory();
            if (inventory == null || !InventoryUI.isOpen()) continue;
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

        for (InventoryUI inventoryUI : inventoriesGui) {

            inventory = inventoryUI.getInventory();
            if (inventory == null || !inventoryUI.isOpen()) continue;

            if (event.getInventory() == inventory) {

                event.setCancelled(true);
                selectedItem = event.getCurrentItem();
                if (selectedItem == null || selectedItem.getType() == Material.AIR) return;
                inventoryUI.doClick((Player) event.getWhoClicked(), event.getRawSlot(), selectedItem, event);
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
        for (InventoryUI inventoryUI : inventoriesGui) {
            inventory = inventoryUI.getInventory();
            if (inventory == null || !inventoryUI.isOpen()) continue;

            if (event.getInventory() == inventory) {

                break;
            }
        }
    }

    @EventHandler
    private void onPlayerExit(PlayerQuitEvent event) {
        for (int i = 0; i < inventoriesGui.size(); i++) {
            if (event.getPlayer() == inventoriesGui.get(i).getPlayer()) {
                inventoriesGui.get(i).close();
                unregisterUI(inventoriesGui.get(i));
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

    @EventHandler
    private void onChatEvent(AsyncPlayerChatEvent event) {
        if (textInputEvents.containsKey(event.getPlayer())) {
            taskManager.task(() -> {
                textInputEvents.get(event.getPlayer()).accept(event.getMessage());
                textInputEvents.remove(event.getPlayer());
            });
            event.setCancelled(true);
        }
    }
}

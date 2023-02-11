package test.api;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.ButtonObserverUI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import test.api.builder.InventoryDecoratorImpl;
import test.api.enums.InventoryState;
import test.api.managers.ButtonManager;
import test.api.managers.ChildrenManager;
import test.api.managers.PermissionManager;
import test.api.managers.buttons.ButtonManagerImpl;
import test.api.managers.events.*;
import test.listeners.InventorySpigotListener;

public class FluentInventoryImpl implements FluentInventory {

    @Getter
    private String title;
    @Getter
    private int size;

    @Getter
    private int slots;
    @Getter
    private Player player;
    @Getter
    private InventoryState state;
    @Getter
    private Inventory handle;
    private InventoryType inventoryType;
    private final ChildrenManager children;
    private final ButtonManagerImpl buttonManager;
    private final EventsManager events;
    private final PermissionManager permission;
    private final SimpleLogger logger;

    private final InventorySpigotListener spigotListener;

    public FluentInventoryImpl(ChildrenManager children,
                               ButtonManager buttons,
                               EventsManager events,
                               PermissionManager permission,
                               InventorySpigotListener spigotListener) {
        this.children = children;
        this.buttonManager = (ButtonManagerImpl) buttons;
        this.events = events;
        this.permission = permission;
        this.logger = new SimpleLogger();
        this.logger.setPrefix(this.toString());
        this.spigotListener = spigotListener;
        state = InventoryState.NOT_CREATED;
    }


    @Override
    public void open(Player player, Object... args) {
        if (!validatePlayer(player))
            return;

        if (!doOnCreateEvent(player))
            return;
        state = InventoryState.CREATED;
        if (children.getParent().isPresent()) {
            children.getParent().get().close();
        }

        updateHandle();

        if (!doOnOpenEvent(player)) {
            return;
        }
        this.player = player;
        refresh();
        spigotListener.subscribe(this);
        player.openInventory(handle);
        state = InventoryState.OPEN;
        logger.info("Open Inventory for handle", handle.hashCode());
    }

    @Override
    public void close() {

    }

    @Override
    public void refresh() {
        buttonManager.refresh();
        logger.info("New content loaded for Bukkit inv ", handle.hashCode());
    }

    @Override
    public boolean click(InventoryClickEvent event) {
        try {
            if (event.getSlot() > getSlots()) {
                if (!doOnClickPlayerInventoryEvent(player, event.getCurrentItem())) {
                    return false;
                }
            }

            var button = buttonManager.getButton(event.getSlot());
            if (button == null || !button.isActive()) {
                return false;
            }

            if (!permissions().validatePlayer(player, button.getPermissions())) {
                return false;
            }

            if (button.hasSound())
                player.playSound(player.getLocation(), button.getSound(), 1, 1);

            if (!doOnClickEvent(player, button)) {
                return false;
            }
            switch (event.getClick()) {
                case SHIFT_LEFT, SHIFT_RIGHT -> {
                    button.getOnShiftClick().execute(player, button);
                }
                case RIGHT -> {
                    if (button instanceof ButtonObserverUI buttonObserverUI)
                        buttonObserverUI.onRightClick(player, buttonManager);
                    else
                        button.getOnRightClick().execute(player, button);
                }
                case LEFT -> {
                    if (button instanceof ButtonObserverUI buttonObserverUI)
                        buttonObserverUI.onClick(player, buttonManager);
                    else
                        button.getOnLeftClick().execute(player, button);
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("Error onClick, inventory " + this.getTitle() + " by player " + player.getName(), e);
            return false;
        }
    }

    @Override
    public void drag(InventoryDragEvent event) {

    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        if (player == null || !player.isOnline())
            return;
        spigotListener.unsubscribe(this);
        var currentContent = handle.getContents();
        updateHandle();
        handle.setContents(currentContent);
        if (state == InventoryState.OPEN)
            player.openInventory(handle);
        spigotListener.subscribe(this);
        logger.info("Title changed with Bukkit inv ", handle.hashCode());
    }


    private boolean doOnClickPlayerInventoryEvent(Player player, ItemStack itemStack) {
        var clickEvent = new ClickPlayerInventoryEvent(player, itemStack);
        events.onClickPlayerInventory().invoke(clickEvent);
        if (clickEvent.isCancelled()) {
            return false;
        }
        return true;
    }

    private boolean doOnClickEvent(Player player, ButtonUI buttonUI) {
        var clickEvent = new ClickEvent(player, buttonUI, this);
        events.onClick().invoke(clickEvent);
        if (clickEvent.isCancelled()) {
            return false;
        }
        return true;
    }

    private boolean doOnCreateEvent(Player player) {
        if (state != InventoryState.NOT_CREATED) {
            return true;
        }
        var event = new CreateGuiEvent(player, new InventoryDecoratorImpl(this));
        events.onCreate().invoke(event);
        return event.isCancelled();
    }

    private boolean doOnOpenEvent(Player player) {
        var event = new OpenGuiEvent();
        events.onOpen().invoke(event);
        return event.isCancelled();
    }

    private boolean validatePlayer(Player player) {
        if (player == null || !player.isOnline()) {
            logger.info("Invalid player", ChatColor.RED);
            return false;
        }
        if (!permission.validatePlayer(player)) {
            logger.info("No permissions to open", ChatColor.RED);
            return false;
        }
        return true;
    }

    private void updateHandle() {
        handle = createInventory();
        buttonManager.updateHandle(handle);
    }

    private Inventory createInventory() {
        switch (inventoryType) {
            case CHEST:
                return Bukkit.createInventory(player, size * 9, title);
            case WORKBENCH:
                return Bukkit.createInventory(player, inventoryType, title);
            default:
                logger.warning("Sorry inventory type of " + inventoryType.name() + " is not implemented yet ;<");
        }
        return Bukkit.createInventory(player, inventoryType, title);
    }


    @Override
    public ChildrenManager children() {
        return children;
    }

    @Override
    public ButtonManager buttons() {
        return buttonManager;
    }

    @Override
    public EventsManager events() {
        return events;
    }

    @Override
    public PermissionManager permissions() {
        return permission;
    }
}

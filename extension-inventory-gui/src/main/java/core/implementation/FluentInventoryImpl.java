package core.implementation;

import core.implementation.managers.ButtonManagerImpl;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.ButtonObserverUI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import core.api.FluentInventory;
import core.api.InventorySettings;
import core.api.enums.InventoryState;
import core.api.managers.buttons.ButtonManager;
import core.api.managers.ChildrenManager;
import core.api.managers.permissions.PermissionManager;
import core.api.managers.events.*;

public class FluentInventoryImpl implements FluentInventory {
    @Getter
    private Player player;

    @Getter
    private final InventorySettings inventorySettings;
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
                               InventorySpigotListener spigotListener,
                               InventorySettings inventorySettings) {
        this.children = children;
        this.buttonManager = (ButtonManagerImpl) buttons;
        this.events = events;
        this.permission = permission;
        this.spigotListener = spigotListener;
        this.inventorySettings = inventorySettings;
        this.logger = new SimpleLogger();
        this.logger.setPrefix(this.toString());
    }


    @Override
    public void open(Player player, Object... args) {
        if (!validatePlayer(player))
            return;

        if (!doOnCreateEvent(player))
            return;
        inventorySettings.setState(InventoryState.CREATED);
        if (children.getParent().isPresent()) {
            children.getParent().get().close();
        }
        inventorySettings.setHandle(createInventory());
        if (!doOnOpenEvent(player)) {
            return;
        }
        this.player = player;
        refresh();
        spigotListener.subscribe(this);
        player.openInventory(inventorySettings.getHandle());
        inventorySettings.setState(InventoryState.OPEN);
        logger.info("Open Inventory for handle",inventorySettings.getHandle());
    }

    @Override
    public void close() {

    }

    @Override
    public void refresh() {
        buttonManager.refresh();
        logger.info("New content loaded for Bukkit inv ", inventorySettings.getHandle());
    }

    @Override
    public boolean click(InventoryClickEvent event) {
        try {
            if (event.getSlot() > inventorySettings.getSlots()) {
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
            logger.error("Error onClick, inventory " + inventorySettings.getTitle() + " by player " + player.getName(), e);
            return false;
        }
    }

    @Override
    public void drag(InventoryDragEvent event)
    {

    }

    @Override
    public void setTitle(String title) {
        inventorySettings.setTitle(title);
        if (player == null || !player.isOnline())
            return;
        spigotListener.unsubscribe(this);
        var currentContent = inventorySettings.getHandle().getContents();
        inventorySettings.setHandle(createInventory());
        inventorySettings.getHandle().setContents(currentContent);
        if (inventorySettings.getState() == InventoryState.OPEN)
            player.openInventory(inventorySettings.getHandle());
        spigotListener.subscribe(this);
        logger.info("Title changed with Bukkit inv ", inventorySettings.getHandle().hashCode());
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
        if (inventorySettings.getState() != InventoryState.NOT_CREATED) {
            return true;
        }
        var decorator = new InventoryDecoratorImpl(this);
        var event = new CreateGuiEvent(player, decorator);
        events.onCreate().invoke(event);
        if(event.isCancelled())
        {
            return false;
        }
        decorator.apply();
        return true;
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

    private Inventory createInventory() {
        switch (inventorySettings.getInventoryType()) {
            case CHEST:
                return Bukkit.createInventory(player, inventorySettings.getSlots(), inventorySettings.getTitle());
            case WORKBENCH:
                return Bukkit.createInventory(player, inventorySettings.getInventoryType(), inventorySettings.getTitle());
            default:
                logger.warning("Sorry inventory type of " + inventorySettings.getInventoryType() + " is not implemented yet ;<");
        }
        return Bukkit.createInventory(player, inventorySettings.getInventoryType(), inventorySettings.getTitle());
    }


    public InventoryState state()
    {
        return inventorySettings.getState();
    }

    public Inventory handle()
    {
        return inventorySettings.getHandle();
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

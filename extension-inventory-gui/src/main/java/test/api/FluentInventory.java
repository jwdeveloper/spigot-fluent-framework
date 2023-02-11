package test.api;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import test.api.managers.ButtonManager;
import test.api.managers.ChildrenManager;
import test.api.managers.events.EventsManager;
import test.api.managers.PermissionManager;

public interface FluentInventory
{
    Player getPlayer();
    void open(Player player, Object... args);

    void close();

    void refresh();

    boolean click(InventoryClickEvent event);

    void drag(InventoryDragEvent event);

    void setTitle(String title);

    ChildrenManager children();

    ButtonManager buttons();

    EventsManager events();

    PermissionManager permissions();
}

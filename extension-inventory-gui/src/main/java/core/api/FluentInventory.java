package core.api;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import core.api.managers.buttons.ButtonManager;
import core.api.managers.ChildrenManager;
import core.api.managers.events.EventsManager;
import core.api.managers.permissions.PermissionManager;

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
    SimpleLogger logger();
}

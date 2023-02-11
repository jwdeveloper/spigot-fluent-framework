package test.api.builder;

import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.FluentButtonUIBuilder;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUIBuilder;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.observers.ButtonObserverBuilder;
import org.bukkit.event.inventory.InventoryType;
import test.api.FluentInventory;
import test.api.managers.ButtonManager;
import test.api.managers.events.EventsManager;
import test.api.managers.PermissionManager;

import java.util.function.Consumer;

public interface InventoryDecorator
{
    InventoryDecorator withChild(FluentInventory inventory);

    InventoryDecorator withButton(Consumer<FluentButtonUIBuilder> manager);

    InventoryDecorator withEvents(Consumer<EventsManager> manager);

    InventoryDecorator withPermissions(String ... permissions);

    InventoryDecorator withTitle(String title);

    InventoryDecorator withType(InventoryType type);

    InventoryDecorator withHeight(int height);

    InventoryDecorator withTasks(Consumer<FluentTaskManager> tasks);
}

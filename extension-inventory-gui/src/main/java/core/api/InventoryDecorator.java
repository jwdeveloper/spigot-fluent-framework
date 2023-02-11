package core.api;

import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.FluentButtonUIBuilder;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.FluentButtonUIFactory;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.styles.renderer.ButtonStyleRenderer;
import org.bukkit.event.inventory.InventoryType;
import core.api.managers.events.EventsManager;

import java.util.function.Consumer;

public interface InventoryDecorator
{
    InventoryDecorator withPlugin(InventoryPlugin inventoryPlugin);
    InventoryDecorator withButtonRenderer(ButtonStyleRenderer renderer);
    InventoryDecorator withChild(FluentInventory inventory);

    FluentButtonUIBuilder withButton(Consumer<FluentButtonUIBuilder> manager);

    FluentButtonUIBuilder withButton(FluentButtonUIBuilder buttonUI);

    FluentButtonUIBuilder withButtonFactory(FluentButtonUIFactory buttonUI);

    InventoryDecorator withEvents(Consumer<EventsManager> manager);

    InventoryDecorator withPermissions(String ... permissions);

    InventoryDecorator withTitle(String title);

    InventoryDecorator withType(InventoryType type);

    InventoryDecorator withHeight(int height);

    InventoryDecorator withTasks(Consumer<FluentTaskManager> tasks);
    FluentTranslator translator();
}

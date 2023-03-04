package core.implementation;

import core.api.FluentInventory;
import core.api.InventoryDecorator;
import core.api.InventoryPlugin;
import core.api.managers.events.EventsManager;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.FluentButtonUIBuilder;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.FluentButtonUIFactory;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.styles.renderer.ButtonStyleRenderer;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InventoryDecoratorImpl implements InventoryDecorator {

    private final FluentInventoryImpl inventory;
    private List<FluentButtonUIBuilder> buttons;
    private ButtonStyleRenderer styleRenderer;

    public InventoryDecoratorImpl(FluentInventoryImpl inventory) {
        this.inventory = inventory;
        buttons = new ArrayList<>();
    }

    @Override
    public InventoryDecorator withPlugin(InventoryPlugin inventoryPlugin) {
        inventoryPlugin.onCreate(this);
        return this;
    }

    @Override
    public InventoryDecorator withButtonRenderer(ButtonStyleRenderer renderer) {
        this.styleRenderer = styleRenderer;
        return this;
    }

    @Override
    public InventoryDecorator withChild(FluentInventory inventory) {
        inventory.children().addChild(inventory);
        return this;
    }


    @Override
    public FluentButtonUIBuilder withButton(Consumer<FluentButtonUIBuilder> manager) {

        var builder = new FluentButtonUIBuilder();
        manager.accept(builder);
        buttons.add(builder);
        return null;
    }

    @Override
    public FluentButtonUIBuilder withButton(FluentButtonUIBuilder buttonUI) {
        return null;
    }


    @Override
    public FluentButtonUIBuilder withButtonFactory(FluentButtonUIFactory buttonUI) {
        return null;
    }

    @Override
    public InventoryDecorator withEvents(Consumer<EventsManager> manager) {
        manager.accept(inventory.events());
        return this;
    }

    @Override
    public InventoryDecorator withPermissions(String... permissions) {
        inventory.permissions().addPermissions(permissions);
        return this;
    }


    @Override
    public InventoryDecorator withTitle(String title) {
        inventory.getInventorySettings().setTitle(title);
        return this;
    }

    @Override
    public InventoryDecorator withType(InventoryType type) {
        inventory.getInventorySettings().setInventoryType(type);
        return this;
    }

    @Override
    public InventoryDecorator withHeight(int height) {
        inventory.getInventorySettings().setHeight(height);
        return this;
    }

    @Override
    public InventoryDecorator withTasks(Consumer<FluentTaskManager> tasks) {
        return null;
    }

    @Override
    public FluentTranslator translator() {
        return null;
    }

    public void apply() {
        for (var button : buttons) {
            var btn = button.build(styleRenderer);
            inventory.buttons().addButton(btn);
        }
    }
}

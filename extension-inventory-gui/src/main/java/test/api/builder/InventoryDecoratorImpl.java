package test.api.builder;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUIBuilder;
import org.bukkit.event.inventory.InventoryType;
import test.api.FluentInventory;
import test.api.FluentInventoryImpl;
import test.api.managers.ButtonManager;
import test.api.managers.ChildrenManager;
import test.api.managers.events.EventsManager;
import test.api.managers.PermissionManager;

import java.util.function.Consumer;

public class InventoryDecoratorImpl implements InventoryDecorator {

    public FluentInventoryImpl inventory;

    public InventoryDecoratorImpl(FluentInventoryImpl inventory) {
        this.inventory = inventory;
    }

    @Override
    public InventoryDecorator withChild(FluentInventory inventory) {
        inventory.children().addChild(inventory);
        return this;
    }

    @Override
    public InventoryDecorator withButton(Consumer<ButtonUIBuilder> manager) {
        return null;
    }

    @Override
    public InventoryDecorator withEvents(Consumer<EventsManager> manager) {
        return null;
    }

    @Override
    public InventoryDecorator withPermissions(String... permissions) {
        inventory.permissions().addPermissions(permissions);
        return this;
    }


    @Override
    public InventoryDecorator withTitle(String title) {
        return null;
    }

    @Override
    public InventoryDecorator withType(InventoryType type) {
        return null;
    }

    @Override
    public InventoryDecorator withHeight(int height) {
        return null;
    }
}

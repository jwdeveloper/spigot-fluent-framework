package core.implementation;

import core.api.FluentInventory;
import core.api.InventoryDecorator;
import core.api.InventoryPlugin;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import lombok.Getter;
import org.bukkit.entity.Player;

public abstract class InventoryInstance implements InventoryPlugin
{
    @Getter
    private final FluentInventory inventory;
    @Getter
    private final FluentTranslator translator;
    @Getter
    private final InventoryFactory factory;

    public InventoryInstance(InventoryFactory factory) {
        this.factory = factory;
        translator = factory.getTranslator();
        inventory = factory.getBasicInventory();
        inventory.events().onCreate().subscribe(createGuiEvent ->
        {
            onCreate(createGuiEvent.getDecorator());
        });
    }
}

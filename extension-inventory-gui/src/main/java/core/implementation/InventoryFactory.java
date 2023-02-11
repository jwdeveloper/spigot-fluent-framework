package core.implementation;

import core.ExampleList;
import core.api.FluentInventory;
import core.api.InventorySettings;
import core.implementation.managers.ButtonManagerImpl;
import core.implementation.managers.ChildernManagerImpl;
import core.implementation.managers.EventManagerImpl;
import core.implementation.managers.PermissionManagerImpl;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import lombok.Getter;

public class InventoryFactory {
    private InventorySpigotListener listener;

    @Getter
    private FluentTranslator translator;

    public InventoryFactory(InventorySpigotListener listener, FluentTranslator translator)
    {
        this.listener = listener;
        this.translator = translator;
    }


    public FluentInventory getBasicInventory() {
        var settings = new InventorySettings();
        var childrenManager = new ChildernManagerImpl();
        var eventsManager = new EventManagerImpl();
        var permissionManager = new PermissionManagerImpl();
        var buttonManager = new ButtonManagerImpl(settings);
        return new FluentInventoryImpl(childrenManager, buttonManager,eventsManager,permissionManager, listener, settings );
    }

    public <T> ExampleList<T> getListInventory()
    {
        return new ExampleList<T>();
    }

}

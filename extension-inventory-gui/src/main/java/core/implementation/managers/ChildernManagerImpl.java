package core.implementation.managers;

import core.api.FluentInventory;
import core.api.managers.ChildrenManager;

import java.util.Optional;

public class ChildernManagerImpl implements ChildrenManager {

    FluentInventory parent;

    @Override
    public Optional<FluentInventory> getParent()
    {

    }

    @Override
    public void addChild(FluentInventory child) {

    }

    @Override
    public void removeChild(FluentInventory child) {

    }

    @Override
    public FluentInventory[] children() {
        return new FluentInventory[0];
    }
}

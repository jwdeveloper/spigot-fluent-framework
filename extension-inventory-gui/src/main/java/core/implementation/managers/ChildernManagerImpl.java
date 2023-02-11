package core.implementation.managers;

import core.api.FluentInventory;
import core.api.managers.ChildrenManager;

import java.util.Optional;

public class ChildernManagerImpl implements ChildrenManager {
    @Override
    public Optional<FluentInventory> getParent() {
        return Optional.empty();
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

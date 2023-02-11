package test.api.managers;

import test.api.FluentInventory;

import java.util.Optional;

public interface ChildrenManager
{
    Optional<FluentInventory> getParent();

    void addChild(FluentInventory child);

    void removeChild(FluentInventory child);

    FluentInventory[] children();
}

package core.api.managers;

import core.api.FluentInventory;

import java.util.Optional;

public interface ChildrenManager
{
    Optional<FluentInventory> getParent();

    void addChild(FluentInventory child);

    void removeChild(FluentInventory child);

    FluentInventory[] children();
}

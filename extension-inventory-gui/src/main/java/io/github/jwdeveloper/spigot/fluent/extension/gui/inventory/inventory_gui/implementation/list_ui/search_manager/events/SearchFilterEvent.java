package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.search_manager.events;


public interface SearchFilterEvent<T>
{
    boolean execute(SearchEvent<T> event);
}

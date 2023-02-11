package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.content_manger;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;

public interface ButtonUIMapper<T>
{
    void mapButton(T data, ButtonUI button);
}

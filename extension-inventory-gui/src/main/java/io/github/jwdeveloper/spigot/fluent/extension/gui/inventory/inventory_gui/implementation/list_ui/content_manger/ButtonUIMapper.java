package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.content_manger;
import core.implementation.button.ButtonUI;

public interface ButtonUIMapper<T>
{
    void mapButton(T data, ButtonUI button);
}

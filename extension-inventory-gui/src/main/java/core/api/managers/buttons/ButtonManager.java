package core.api.managers.buttons;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;

import java.util.List;

public interface ButtonManager {
    void refresh();

    void refresh(ButtonUI buttonUI);

    ButtonUI[] getButtons();

    ButtonUI getButton(int height, int width);

    ButtonUI getButton(int index);

    void addButton(ButtonUI button);

    void addButtons(List<ButtonUI> buttons);

    void addButtons(ButtonUI[] buttons);

    void addButton(ButtonUI button, int slotIndex);

}

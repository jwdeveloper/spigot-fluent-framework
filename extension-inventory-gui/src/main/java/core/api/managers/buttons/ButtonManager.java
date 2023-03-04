package core.api.managers.buttons;

import core.implementation.button.ButtonUI;

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

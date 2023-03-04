package core.implementation.managers;

import core.implementation.button.ButtonUI;
import core.api.InventorySettings;
import core.api.managers.buttons.ButtonManager;

import java.util.List;

public class ButtonManagerImpl implements ButtonManager {
    private ButtonUI[] buttons;
    private final InventorySettings settings;

    public ButtonManagerImpl(InventorySettings settings)
    {
        this.settings = settings;
    }

    @Override
    public ButtonUI[] getButtons()
    {
        return buttons;
    }

    @Override
    public void refresh() {
        if (settings.getHandle() == null)
            return;

        ButtonUI button = null;
        for (int i = 0; i < buttons.length; i++) {
            button = buttons[i];
            if (button != null && button.isActive()) {
                settings.getHandle().setItem(i, button.getItemStack());
            } else
                settings.getHandle().setItem(i, null);
        }
    }

    @Override
    public void refresh(ButtonUI button) {
        var index = calculateButtonSlotIndex(button);
        if (index == -1)
            return;
        if (button.isActive()) {
            settings.getHandle().setItem(index, button.getItemStack());
        } else
            settings.getHandle().setItem(index, null);
    }

    public ButtonUI getButton(int height, int width) {
        var position = calculateButtonSlotIndex(height, width);
        return getButton(position);
    }

    public ButtonUI getButton(int index) {
        var position = index >= buttons.length ? buttons.length - 1 : index;
        return buttons[position];
    }

    public void addButton(ButtonUI button) {
        var slotIndex = calculateButtonSlotIndex(button);
        addButton(button, slotIndex);
    }

    public void addButtons(List<ButtonUI> buttons) {
        for (var button : buttons) {
            addButton(button);
        }
    }

    public void addButtons(ButtonUI[] buttons) {
        for (var button : buttons) {
            addButton(button);
        }
    }

    public void addButton(ButtonUI button, int slotIndex) {
        if (slotIndex <= settings.getSlots())
            buttons[slotIndex] = button;
    }

    private int calculateButtonSlotIndex(ButtonUI button) {
        if (button.getHeight() > settings.getHeight() - 1) {
            button.setLocation(settings.getHeight()-1,button.getWidth());
        }

        return calculateButtonSlotIndex(button.getHeight(), button.getWidth());
    }

    private int calculateButtonSlotIndex(int height, int width) {
        return height * InventorySettings.INVENTORY_WIDTH + width % InventorySettings.INVENTORY_WIDTH;
    }
}

package inventory_gui.implementation.picker_list_ui;

import jw.fluent.api.spigot.gui.inventory_gui.events.ButtonUIEvent;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.ListUI;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickerUI<T> extends ListUI<T> {
    private ButtonUIEvent onItemPicked;

    public PickerUI(String name) {
        super(name, 6);
        onContentClick((player, button) ->
        {
            if (onItemPicked == null)
                return;
            onItemPicked.execute(player, button);
        });
    }

    public void onContentPicked(ButtonUIEvent onItemPicked)
    {
        this.onItemPicked = onItemPicked;
    }
}

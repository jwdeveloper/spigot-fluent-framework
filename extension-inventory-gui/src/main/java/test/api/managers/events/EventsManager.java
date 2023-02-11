package test.api.managers.events;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface EventsManager
{
     EventsGroup<CreateGuiEvent> onCreate();

     EventsGroup<ClickEvent> onClick();

     EventsGroup<ClickPlayerInventoryEvent> onClickPlayerInventory();

     EventsGroup<OpenGuiEvent> onOpen();

     EventsGroup onClose();

     EventsGroup onDrag();
}

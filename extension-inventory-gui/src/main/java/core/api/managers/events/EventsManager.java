package core.api.managers.events;

public interface EventsManager
{
     EventsGroup<CreateGuiEvent> onCreate();

     EventsGroup<ClickEvent> onClick();

     EventsGroup<ClickPlayerInventoryEvent> onClickPlayerInventory();

     EventsGroup<OpenGuiEvent> onOpen();

     EventsGroup onClose();

     EventsGroup onDrag();
}

package core.implementation.managers;

import core.api.managers.events.*;

public class EventManagerImpl implements EventsManager {
    @Override
    public EventsGroup<CreateGuiEvent> onCreate() {
        return null;
    }

    @Override
    public EventsGroup<ClickEvent> onClick() {
        return null;
    }

    @Override
    public EventsGroup<ClickPlayerInventoryEvent> onClickPlayerInventory() {
        return null;
    }

    @Override
    public EventsGroup<OpenGuiEvent> onOpen() {
        return null;
    }

    @Override
    public EventsGroup onClose() {
        return null;
    }

    @Override
    public EventsGroup onDrag() {
        return null;
    }
}

package inventory_gui.implementation.crud_list_ui;

import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserver;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserverBuilder;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserverEvent;
import jw.fluent.api.spigot.gui.inventory_gui.events.ButtonUIEvent;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.ListUI;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class CrudListController<T> {
    private final Material DEFAULT_BACKGROUND = Material.GRAY_STAINED_GLASS_PANE;
    private final ListUI<T> listUI;
    private final Observer<CrudListState> currentState;
    private CrudListState _currentState;

    public ButtonUIEvent onDeleteEvent = (a, b) -> {
    };
    public ButtonUIEvent onEditEvent = (a, b) -> {
    };
    public ButtonUIEvent onInsertEvent = (a, b) -> {
    };
    public ButtonUIEvent onGetEvent = (a, b) -> {
    };

    public CrudListController(ListUI<T> listUI) {
        _currentState = CrudListState.None;
        currentState = new Observer<CrudListState>(this, "_currentState");
        this.listUI = listUI;
    }

    public void setState(CrudListState lastState) {
        currentState.set(lastState);
        listUI.refreshButtons();
    }

    public CrudListState getState() {
        return currentState.get();
    }


    public ButtonObserver<?> deleteObserver() {
        return baseObserver(CrudListState.Delete, event ->
        {
            listUI.setBorderMaterial(Material.RED_STAINED_GLASS_PANE);
        }).build();
    }

    public ButtonObserver<?> editObserver() {
        return baseObserver(CrudListState.Edit, event ->
        {
            listUI.setBorderMaterial(Material.YELLOW_STAINED_GLASS_PANE);
        }).build();
    }

    public ButtonObserver<?> insertObserver() {
        return ButtonObserver.<CrudListState>builder()
                .withObserver(currentState)
                .onClick(event ->
                {
                    //to do insert
                    onInsertEvent.execute(event.getPlayer(),event.getButton());
                }).build();
    }

    public ButtonObserver<?> cancelObserver() {
        return baseObserver(CrudListState.None, null)
                .onValueChange(event ->
                {
                    if (event.getValue() == CrudListState.None)
                    {
                        listUI.setBorderMaterial(DEFAULT_BACKGROUND);
                        event.getButton().setMaterial(DEFAULT_BACKGROUND);
                        event.getButton().setTitle(" ");
                    } else {
                        var color = switch (event.getValue())
                                {
                                    case Delete -> ChatColor.RED;
                                    case Edit -> ChatColor.YELLOW;
                                    default -> ChatColor.GRAY;
                                };
                        event.getButton().setMaterial(Material.PLAYER_HEAD);
                        event.getButton().setTitle(new MessageBuilder()
                                .color(color)
                                .inBrackets("Cancel")
                                .toString());
                    }
                    listUI.refreshBorder();
                }).build();
    }

    private ButtonObserverBuilder<CrudListState> baseObserver(CrudListState listState, Consumer<ButtonObserverEvent<CrudListState>> observerEvent) {
        return ButtonObserver.<CrudListState>builder()
                .withObserver(currentState)
                .onClick(event ->
                {
                    if (event.getValue() != listState) {
                        event.getObserver().setValue(listState);
                    }
                })
                .onValueChange(event ->
                {
                    if (event.getValue() == listState) {
                        observerEvent.accept(event);
                    }
                });
    }


    public void selectHandler(Player player, ButtonUI buttonUI) {
        var state = getState();
        switch (state) {
            case Delete -> onDeleteEvent.execute(player, buttonUI);
            case Edit -> onEditEvent.execute(player, buttonUI);
            case Create -> onInsertEvent.execute(player, buttonUI);
            case None -> onGetEvent.execute(player, buttonUI);
        }
        setState(CrudListState.None);
    }

}

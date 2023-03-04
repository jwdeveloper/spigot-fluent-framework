package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.crud_list_ui;

import guis.crud.CrudListController;
import guis.crud.CrudListState;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.ListUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonObserverUI;
import jw.fluent.api.spigot.gui.inventory_gui.events.ButtonUIEvent;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.ListUI;
import lombok.Getter;
import org.bukkit.Material;

public class CrudListUI<T> extends ListUI<T>
{
    private final CrudListController<T> listViewModel;
    @Getter
    private ButtonObserverUI buttonCancel;
    @Getter
    private ButtonObserverUI buttonDelete;
    @Getter
    private ButtonObserverUI buttonEdit;
    @Getter
    private ButtonObserverUI buttonInsert;


    public CrudListUI(String name, int height)
    {
        super(name, height);
        listViewModel = new CrudListController<>(this);
        loadCrudButtons();
    }

    protected void loadCrudButtons()
    {
        buttonDelete = getFluentUI()
                .buttonBuilder()
                .setLocation(0, 7)
                .setDescription(options ->
                {
                    options.setTitle(getTranslator().get("gui.base.delete.title"));
                })
                .setMaterial(Material.BARRIER)
                .setObserver(listViewModel.deleteObserver())
                .build(this);

        buttonEdit = getFluentUI()
                .buttonBuilder()
                .setLocation(0, 5)
                .setDescription(options ->
                {
                    options.setTitle(getTranslator().get("gui.base.edit.title"));
                })
                .setMaterial(Material.WRITABLE_BOOK)
                .setObserver(listViewModel.editObserver())
                .build(this);

        buttonInsert = getFluentUI()
                .buttonBuilder()
                .setLocation(0, 6)
                .setDescription(options ->
                {
                    options.setTitle(getTranslator().get("gui.base.insert.title"));
                })
                .setMaterial(Material.CRAFTING_TABLE)
                .setObserver(listViewModel.insertObserver())
                .build(this);

        buttonCancel = getFluentUI()
                .buttonBuilder()
                .setLocation(getHeight() - 1, 4)
                .setObserver(listViewModel.cancelObserver())
                .build(this);

        onListOpen(player ->
        {
            listViewModel.setState(CrudListState.None);
        });

        onListClose(player ->
        {
            listViewModel.setState(CrudListState.None);
        });
        onContentClick(listViewModel::selectHandler);
    }

    public void onDelete(ButtonUIEvent event)
    {
        listViewModel.onDeleteEvent = event;
    }

    public void onEdit(ButtonUIEvent event)
    {
        listViewModel.onEditEvent = event;
    }

    public void onInsert(ButtonUIEvent event)
    {
        listViewModel.onInsertEvent = event;
    }

    public void onGet(ButtonUIEvent event)
    {
        listViewModel.onGetEvent = event;
    }

    public void hideEditButton()
    {
        getButtonEdit().setActive(false);
        ButtonUI.factory().backgroundButton(0, 5, Material.GRAY_STAINED_GLASS_PANE).buildAndAdd(this);
    }

    public void hideDeleteButton()
    {
        getButtonEdit().setActive(false);
        ButtonUI.factory().backgroundButton(0, 7, Material.GRAY_STAINED_GLASS_PANE).buildAndAdd(this);
    }
}

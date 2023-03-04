package guis.crud;

import core.ExampleList;
import core.api.InventoryDecorator;
import core.api.InventoryPlugin;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.events.ButtonUIEvent;
import org.bukkit.Material;

public class ExampleListCrud<T> implements InventoryPlugin {

    ExampleList<T> list;
    private final CrudListController<T> listViewModel;

    public ExampleListCrud(ExampleList<T> list) {
        this.list = list;
        listViewModel = new CrudListController<T>(list);
    }


    @Override
    public void onCreate(InventoryDecorator decorator) {
        decorator.withPlugin(list);
        decorator.withButton(builder ->
        {
            builder.setLocation(0, 7)
                    .setDescription(options ->
                    {
                        options.setTitle(getTranslator().get("gui.base.delete.title"));
                    })
                    .setMaterial(Material.BARRIER)
                    .setObserver(listViewModel.deleteObserver());
        });

        decorator.withButton(builder ->
        {
            builder
                    .setLocation(0, 5)
                    .setDescription(options ->
                    {
                        options.setTitle(decorator.translator().get("gui.base.edit.title"));
                    })
                    .setMaterial(Material.WRITABLE_BOOK)
                    .setObserver(listViewModel.editObserver());
        });

        decorator.withButton(builder ->
        {
            builder.setLocation(0, 6)
                    .setDescription(options ->
                    {
                        options.setTitle(decorator.translator().get("gui.base.insert.title"));
                    })
                    .setMaterial(Material.CRAFTING_TABLE)
                    .setObserver(listViewModel.insertObserver());
        });

        decorator.withButton(builder ->
        {
            builder.setLocation(getHeight() - 1, 4)
                    .setObserver(listViewModel.cancelObserver());
        });


        decorator.withEvents(eventsManager ->
        {
            eventsManager.onOpen().subscribe(openGuiEvent ->
            {
                listViewModel.setState(CrudListState.None);
            });
            eventsManager.onClose().subscribe(openGuiEvent ->
            {
                listViewModel.setState(CrudListState.None);
            });
        });
        onContentClick(listViewModel::selectHandler);
    }

    public void onDelete(ButtonUIEvent event) {
        listViewModel.onDeleteEvent = event;
    }

    public void onEdit(ButtonUIEvent event) {
        listViewModel.onEditEvent = event;
    }

    public void onInsert(ButtonUIEvent event) {
        listViewModel.onInsertEvent = event;
    }

    public void onGet(ButtonUIEvent event) {
        listViewModel.onGetEvent = event;
    }
}

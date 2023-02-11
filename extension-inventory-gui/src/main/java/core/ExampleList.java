package core;

import core.api.FluentInventory;
import core.api.InventoryDecorator;
import core.api.InventoryPlugin;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.SimpleMessage;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.FluentButtonUIBuilder;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.events.ButtonUIEvent;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.ListUIManager;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.content_manger.ButtonUIMapper;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.content_manger.FilterContentEvent;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.search_manager.SearchManager;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.list_ui.search_manager.events.SearchFilterEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ExampleList<T> implements InventoryPlugin {

    private final ListUIManager<T> listContentManager;
    private final SearchManager<T> searchManager;
    private final List<ButtonUIEvent> onClickContent;
    private FluentButtonUIBuilder buttonSearch;
    private FluentButtonUIBuilder buttonExit;
    private FluentButtonUIBuilder buttonPageUp;
    private FluentButtonUIBuilder buttonPageDown;

    private FluentInventory inventory;

    public ExampleList() {
        searchManager = new SearchManager<T>();
        onClickContent = new ArrayList<>();
        listContentManager = new ListUIManager<>();
    }

    @Override
    public void onCreate(InventoryDecorator decorator) {
        addSearchButton(decorator);
        addPageUpButton(decorator);
        addPageDownButton(decorator);
        decorator.withEvents(eventsManager ->
        {
            eventsManager.onOpen().subscribe(openGuiEvent ->
            {
                inventory = openGuiEvent.getInventory();
            });
        });
    }

    private void addSearchButton(InventoryDecorator decorator) {
        var translator = decorator.translator();
        buttonSearch = decorator.withButton(builder ->
        {
            builder.setObserver(searchManager.getButtonObserver())
                    .setLocation(0, 0)
                    .setDescription(options ->
                    {
                        options.setTitle(translator.get("gui.base.search.title"));
                        options.setOnLeftClick(translator.get("gui.base.search.desc.left-click"));
                        options.setOnRightClick(translator.get("gui.base.search.desc.right-click"));
                        options.setOnShiftClick(translator.get("gui.base.search.desc.shift-click"));
                    })
                    .setMaterial(Material.SPYGLASS)
                    .setOnLeftClick(event ->
                    {
                        if (!searchManager.hasProfiles()) {
                            return;
                        }
                        event.getInventory().close();
                        new SimpleMessage().chat().inBrackets("Enter search key", ChatColor.AQUA).send(event.getPlayer());
                        EventsListenerInventoryUI.registerTextInput(event.getPlayer(), searchedKey ->
                        {
                            addContentFilter(input ->
                            {
                                return searchManager.search(searchedKey, input, event.getPlayer());
                            });
                            applyFilters();
                            event.getInventory().open(event.getPlayer());
                        });
                    })
                    .setOnShiftClick((player, button) ->
                    {
                        resetFilter();
                    });
        });
    }


    private void addPageUpButton(InventoryDecorator decorator) {
        buttonPageUp = decorator.withButton(builder ->
        {
            builder.setLocation(Integer.MAX_VALUE, 5)
                    .setDescription(options ->
                    {
                        options.setTitle(decorator.translator().get("gui.base.page-up.title"));
                    })
                    .setMaterial(Material.ARROW)
                    .setOnLeftClick((player, button) ->
                    {
                        listContentManager.nextPage();
                    });
        });
    }

    public void addPageDownButton(InventoryDecorator decorator) {
        buttonPageDown = decorator.withButton(builder ->
        {
            builder.setLocation(Integer.MAX_VALUE, 3)
                    .setDescription(options ->
                    {
                        options.setTitle(decorator.translator().get("gui.base.page-down.title"));
                    })
                    .setMaterial(Material.ARROW)
                    .setOnLeftClick((player, button) ->
                    {
                        listContentManager.lastPage();
                    });
        });
    }

    public void setContentButtons(List<T> data, ButtonUIMapper<T> buttonMapper) {
        listContentManager.setButtonFormatter(data, buttonMapper);
        refreshContent();
        inventory.logger().info("ContentButtons set, count:" + data.size(), ChatColor.GREEN);
    }

    public void applyFilters() {
        listContentManager.applyFilters();
        refreshContent();
    }

    public void addFilter(FilterContentEvent<T> filterContentEvent) {
        listContentManager.addFilter(filterContentEvent);
    }

    public void removeFilter(FilterContentEvent<T> filterContentEvent) {
        listContentManager.removeFilter(filterContentEvent);
    }

    public void resetFilter() {
        listContentManager.resetFilter();
        refreshContent();
    }


    public void refreshContent() {
        inventory.setTitle(listContentManager.pageDescription());
        inventory.buttons().addButtons(listContentManager.getButtons());
        inventory.buttons().refresh();
    }

    public final void addSearchStrategy(String name, SearchFilterEvent<T> event) {
        searchManager.addSearchProfile(name, event);
    }
}

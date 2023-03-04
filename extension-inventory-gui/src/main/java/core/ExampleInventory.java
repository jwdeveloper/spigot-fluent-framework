package core;

import core.api.InventoryDecorator;
import core.implementation.InventoryFactory;
import core.implementation.InventoryInstance;

import java.util.ArrayList;
import java.util.List;

public class ExampleInventory extends InventoryInstance {

    Integer value;
    List<String> strings;

    public ExampleInventory(InventoryFactory factory) {
        super(factory);
        this.strings = new ArrayList<>();
    }

    @Override
    public void onCreate(InventoryDecorator decorator) {
        var list = getFactory().<String>getListInventory();
        decorator.withPlugin(list)
                .withHeight(3)
                .withTitle(translate("siema"));

        decorator.withButton(buttonUIBuilder ->
        {
            buttonUIBuilder.setOnRefresh(event ->
            {
                var btn = event.getButton();
                btn.setTitle("Clicks " + value);
                if (value > 10) {
                    btn.delete();
                }
            });
        });
    }
}

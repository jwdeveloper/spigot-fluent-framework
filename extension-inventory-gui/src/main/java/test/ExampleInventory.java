package test;

import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.Observer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import test.api.builder.InventoryDecorator;
import test.api.builder.InventoryInstance;

public class ExampleInventory extends InventoryInstance {

    Integer value;

    @Override
    protected void onCreate(InventoryDecorator decorator, Player player) {
        decorator.withHeight(3)
                .withTitle("Example inventory")
                .withChild(new ExampleInventory().getInventory())
                .withButton(buttonUIBuilder ->
                {
                    buttonUIBuilder.setOnRefresh(event ->
                    {
                        var btn = event.getButton();
                        btn.setTitle("Clicks "+value);
                        if(value > 10)
                        {
                            btn.delete();
                        }
                    });
                })
                .withTasks(taskManager ->
                {
                    taskManager.taskTimer(1,(iteration, task) ->
                    {
                        value ++;
                    });
                })
                .withEvents(eventsManager ->
                {

                });
    }
}

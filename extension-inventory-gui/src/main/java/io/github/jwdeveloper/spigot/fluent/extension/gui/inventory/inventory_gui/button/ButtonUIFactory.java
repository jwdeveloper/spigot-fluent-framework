package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.InventoryUI;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.message.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ButtonUIFactory {




    public ButtonUIBuilder goBackButton(InventoryUI inventory) {
        return goBackButton(inventory, null);
    }
    public ButtonUIBuilder goBackButton(InventoryUI inventory, InventoryUI parent) {
        var result = new ButtonUIBuilder<>().setOnClick((player, button) ->
        {
            if (parent == null) {
                inventory.close();
                return;
            }
            parent.open(player);
        }).setMaterial(Material.ARROW)
                .setLocation(inventory.getHeight() - 1, inventory.getWidth() - 1);

        if (parent == null)
            result.setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets(FluentApi.translator().get("gui.base.exit.title")));
        else
            result.setTitle(new MessageBuilder().color(ChatColor.GRAY).inBrackets(FluentApi.translator().get("gui.base.back.title")));
        return result;
    }

    public ButtonUIBuilder backgroundButton(int height, int width, Material material) {
        return new ButtonUIBuilder<>()
                .setLocation(height,width)
                .setMaterial(material)
                .setButtonType(ButtonType.BACKGROUND)
                .setTitle(" ");
    }


}

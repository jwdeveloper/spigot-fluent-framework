package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.enums.ButtonType;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.enums.PermissionType;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.events.ButtonUIEvent;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.message.MessageBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ButtonUI {
    @Setter(value = AccessLevel.NONE)
    private ItemStack itemStack;

    protected UUID uuid;

    protected String title;

    protected List<String> description;

    protected Material material;

    protected Object dataContext;

    protected boolean isActive = true;

    protected boolean isHighLighted = false;

    protected Vector location;

    protected ButtonType buttonType;

    protected Sound sound;

    protected List<String> permissions;

    protected PermissionType permissionType;

    private ButtonUIEvent onLeftClick = (player, button) -> {
    };

    private ButtonUIEvent onShiftClick = (player, button) -> {
    };

    private ButtonUIEvent onRightClick = (player, button) -> {
    };

    public ButtonUI() {
        buttonType = ButtonType.CLICKABLE;
        itemStack = new ItemStack(Material.DIRT);
        location = new Vector(0, 0, 0);
        description = new ArrayList<>();
        permissions = new ArrayList<>();
        permissionType = PermissionType.ONE_OF;
        hideAttributes();
    }

    public ButtonUI(Material material) {
        super();
        setMaterial(material);
    }

    public void setPermissions(List<String> permissions)
    {
        for(var p : permissions)
        {
            this.permissions.add(p);
        }
    }

    public void setPermissions(String ... permissions)
    {
        for(var p : permissions)
        {
            this.permissions.add(p);
        }
    }

    public void setMaterial(Material material) {
        itemStack.setType(material);
    }

    public void setPlayerHead(UUID uuid)
    {
        itemStack.setType(Material.PLAYER_HEAD);
        var skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        itemStack.setItemMeta(skullMeta);
    }

    public void setCustomMaterial(Material material, int id)
    {
        itemStack.setType(material);
        final var meta = itemStack.getItemMeta();
        if(meta == null)
        {
            return;
        }

        meta.setCustomModelData(id);
        itemStack.setItemMeta(meta);
    }



    public void setColor(Color color)
    {
        var meta = itemStack.getItemMeta();
        if(meta == null)
        {
            return;
        }
        if(meta instanceof LeatherArmorMeta horseMeta)
        {
            horseMeta.setColor(color);
            itemStack.setItemMeta(horseMeta);
        }
    }
    public void setMeta(ItemMeta meta)
    {
        itemStack.setItemMeta(meta);
    }

    public void setDescription(MessageBuilder messageBuilder) {
        setDescription(messageBuilder.toString());
    }

    public void setDescription(String... description) {
        setDescription(new ArrayList(Arrays.asList(description)));
    }


    public void setDescription(Consumer<MessageBuilder> consumer)
    {
        var builder = new MessageBuilder();
        consumer.accept(builder);
        setDescription(builder.toArray());
    }

    public void addDescription(String... description) {

        this.description.addAll(Arrays.asList(description));
        setDescription(description);
    }


    public void updateDescription(int index, String value)
    {
        if(description == null)
        {
            return;
        }
        description.set(index, value);
        final var meta = ensureMeta(itemStack);
        meta.setLore(description);
        itemStack.setItemMeta(meta);
    }

    public void setDescription(List<String> description) {
        this.description = description;
        var meta = ensureMeta(itemStack);
        meta.setLore(description);
        itemStack.setItemMeta(meta);
    }

    public void setTitle(String title) {
        this.title = title;
        setDisplayedName(title);
    }

    public void setTitlePrimary(String title, String description)
    {
        setTitle(new MessageBuilder().color(ChatColor.AQUA).inBrackets(title).space().text(description).toString());
    }

    public void setTitlePrimary(String title)
    {
      setTitle(new MessageBuilder().color(ChatColor.AQUA).inBrackets(title).toString());
    }

    public int getHeight() {
        return location.getBlockY();
    }

    public int getWidth() {
        return location.getBlockX();
    }

    public boolean hasSound() {
        return sound != null;
    }

    public void setLocation(int height, int width) {
        setLocation(new Vector(width, height, 0));
    }

    public void location(int height, int width) {
        setLocation(new Vector(width, height, 0));
    }

    public void click(Player player) {
        if (onLeftClick == null)
            return;
        this.onLeftClick.execute(player, this);
    }

    public void rightClick(Player player) {
        if (onRightClick == null)
            return;
        this.onRightClick.execute(player, this);
    }

    public <T> T getDataContext()
    {
        if (dataContext == null)
            return null;
        try {
            return (T) dataContext;
        } catch (Exception e) {
            FluentApi.logger().error("Can not cast DataContext value " + dataContext.toString() + " in button " + title, e);
        }
        return null;
    }

    private void setDisplayedName(String name) {
        var meta = ensureMeta(itemStack);
        if (meta == null)
            return;
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }

    public void setHighlighted(boolean value)
    {
        ItemMeta meta = ensureMeta(itemStack);
        if(meta == null)
            return;
        if (value)
            meta.addEnchant(Enchantment.ARROW_FIRE, 10, true);
        else
            meta.removeEnchant(Enchantment.ARROW_FIRE);
        isHighLighted = value;
        itemStack.setItemMeta(meta);
    }

    public void delete()
    {

    }

    private void hideAttributes()
    {
        ItemMeta meta = ensureMeta(itemStack);
        if(meta == null)
            return;
        Arrays.asList(ItemFlag.values()).forEach(i -> meta.addItemFlags(i));
        itemStack.setItemMeta(meta);
    }

    public static ButtonUIFactory factory() {
        return new ButtonUIFactory();
    }
    public static ButtonUIBuilder builder() {
        return new ButtonUIBuilder();
    }

    private ItemMeta ensureMeta(ItemStack itemStack)
    {
       return  itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

}

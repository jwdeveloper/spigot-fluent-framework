package io.github.jwdeveloper.spigot.fluent.core.spigot.messages.message;

import io.github.jwdeveloper.spigot.fluent.core.common.Emoticons;
import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MessageBuilder extends TextBuilder<MessageBuilder>
{
    private final StringBuilder stringBuilder;
    private final Plugin plugin;

    public MessageBuilder() {
        //TODO pass plugin reference
        stringBuilder = new StringBuilder();
        plugin = null;
    }


    public MessageBuilder reset() {
        stringBuilder.append(ChatColor.RESET);
        return this;
    }

    public MessageBuilder field(String name, Object value) {
        return field(Emoticons.dot, name, value);
    }

    public MessageBuilder field(String prefix, String name, Object value) {
        return color(ChatColor.WHITE)
                .text(prefix)
                .space()
                .text(name)
                .space()
                .color(ChatColor.GRAY)
                .text(Emoticons.arrowRight)
                .space()
                .text(value)
                .space()
                .reset();
    }

    public <T> MessageBuilder addList(List<T> name, Consumer<T> action) {
        for (var value : name) {
            action.accept(value);
        }
        return this;
    }

    public MessageBuilder addList(ArrayList<String> name) {
        for (var value : name) {
            this.text(" -" + value).newLine();
        }
        return this;
    }


    public MessageBuilder location(Location text) {
        color(ChatColor.AQUA).text("World ").color(ChatColor.WHITE).text(text.getWorld().getName()).space();
        color(ChatColor.AQUA).text("X ").color(ChatColor.WHITE).text(text.getX()).space();
        color(ChatColor.AQUA).text("Y ").color(ChatColor.WHITE).text(text.getY()).space();
        color(ChatColor.AQUA).text("Z ").color(ChatColor.WHITE).text(text.getZ()).space();
        return this;
    }

    public MessageBuilder textPrimary(Object text) {
        color(ChatColor.AQUA).text(text).reset();
        return this;
    }

    public MessageBuilder textSecondary(Object text) {
        color(ChatColor.GRAY).text(text).reset();
        return this;
    }

    public MessageBuilder bar(String bar, int length, ChatColor color) {
        return this.color(color).bar(bar, length).reset();
    }

    public MessageBuilder textFormat(String pattern, Object... args) {
        return text(String.format(pattern, args));
    }

    public MessageBuilder color(ChatColor chatColor) {
        stringBuilder.append(chatColor);
        return this;
    }

    public MessageBuilder info() {
        return bold()
                .inBrackets(plugin.getName() + " info", ChatColor.AQUA)
                .space()
                .reset();
    }

    public MessageBuilder error() {
        return inBrackets(plugin.getName() + " error", ChatColor.RED).space();
    }

    public MessageBuilder warning() {
        return inBrackets(plugin.getName() + " warning", ChatColor.YELLOW).space();
    }

    public MessageBuilder color(int r, int g, int b) {
        stringBuilder.append(net.md_5.bungee.api.ChatColor.of(new java.awt.Color(r, g, b)));
        return this;
    }

    public MessageBuilder bold(Object text) {
        stringBuilder.append(ChatColor.BOLD).append(text);
        return this;
    }

    public MessageBuilder bold() {
        stringBuilder.append(ChatColor.BOLD);
        return this;
    }

    public MessageBuilder color(String color) {
        stringBuilder.append(color);
        return this;
    }

    public MessageBuilder space(int count) {
        for (; count > 0; count--) {
            space();
        }
        return this;
    }

    public MessageBuilder space() {
        stringBuilder.append(" ");
        return this;
    }

    public MessageBuilder inBrackets(String message) {
        stringBuilder.append("[").append(message).append("]").append(ChatColor.RESET);
        return this;
    }


    public MessageBuilder inBrackets(String message, ChatColor color) {
        stringBuilder.append(color).append("[").append(message).append("]");
        return this;
    }

    public MessageBuilder inBrackets(String message, ChatColor color, ChatColor colorBorder) {
        stringBuilder.append(colorBorder).append("[").append(color).append(message).append(colorBorder).append("]");
        return this;
    }

    public MessageBuilder withFix(String message, String fix) {
        stringBuilder.append(fix).append(message).append(fix);
        return this;
    }

    public MessageBuilder withFix(String message, String prefix, String endfix) {
        stringBuilder.append(prefix).append(message).append(endfix);
        return this;
    }


    public MessageBuilder withPrefix(String message, String prefix) {
        stringBuilder.append(prefix).append(message);
        return this;
    }

    public MessageBuilder withEndfix(String message, String endfix) {
        stringBuilder.append(message).append(endfix);
        return this;
    }

    public MessageBuilder underLine(String message) {
        stringBuilder.append(ChatColor.UNDERLINE).append(message).append(ChatColor.RESET);
        return this;
    }

    public MessageBuilder strikeThrough(String message) {
        stringBuilder.append(ChatColor.STRIKETHROUGH).append(message).append(ChatColor.RESET);
        return this;
    }

    public String[] toArray() {

        return toString().split(System.lineSeparator());
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }


    public void send(CommandSender... receivers) {
        var messages = toArray();
        for (var receiver : receivers) {
            for (var message : messages) {
                receiver.sendMessage(message);
            }
        }
    }

    public TextComponent toTextComponent() {
        return new TextComponent(toString());
    }

    public void sendActionBar(Player player) {
        var tc = new TextComponent();
        tc.setText(this.toString());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, tc);
    }

    public void sendToConsole() {
        if (Bukkit.getServer() == null) {

            System.out.println(stringBuilder.toString());
            return;
        }
        Bukkit.getConsoleSender().sendMessage(toString());
    }

    public void sendLog() {
        sendLog("");
    }

    public void sendLog(String prefix) {

        var message = stringBuilder.toString();
        if (!StringUtils.isNullOrEmpty(prefix)) {

            message = prefix + message;
        }

        if (Bukkit.getServer() == null) {
            System.out.println(message);
            return;
        }
       // FluentLogger.LOGGER.log(message);
    }


    public void sendToAllPlayer() {
        if (Bukkit.getServer() == null) {
            System.out.println(stringBuilder.toString());
            return;
        }
        for (var player : Bukkit.getOnlinePlayers()) {
            send(player);
        }
    }
}

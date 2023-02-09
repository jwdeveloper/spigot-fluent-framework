package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.services;


import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services.MessagesService;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.message.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class MessageServiceImpl implements MessagesService
{

    @Override
    public String noPermission(CommandSender sender, String permission)
    {
        return new MessageBuilder()
                .inBrackets(sender.getName())
                .space()
                .color(ChatColor.RED)
                .color(ChatColor.BOLD)
                .text("has no permission ->")
                .space()
                .color(ChatColor.RESET)
                .text(permission)
                .toString();
    }
}

package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

public interface FluentCommandManger {
    boolean register(SimpleCommand command);

    boolean unregister(SimpleCommand command);

    List<String> getAllServerCommandsName();

    List<Command> getAllServerCommands();

    Collection<SimpleCommand> getRegisteredCommands();
    Plugin getPlugin();
}

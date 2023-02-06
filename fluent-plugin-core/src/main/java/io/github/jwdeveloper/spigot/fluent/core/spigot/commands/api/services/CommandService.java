package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.AccessType;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandArgument;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandTarget;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.ValidationResult;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.SimpleCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandService {

    Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments);

    boolean hasSenderAccess(CommandSender commandSender, List<AccessType> commandAccessType);

    CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args);

    boolean hasSenderAccess(CommandSender commandSender, AccessType commandAccessType);

    ValidationResult hasSenderPermissions(CommandSender commandSender, List<String> permissions);

    ValidationResult validateArguments(String[] args, List<CommandArgument> commandArguments);
}

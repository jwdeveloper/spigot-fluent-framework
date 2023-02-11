package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation;


import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandArgument;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandModel;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services.CommandService;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services.EventsService;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services.MessagesService;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.implementation.PermissionsUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SimpleCommand extends Command {
    @Getter
    private final EventsService eventsService;
    @Getter
    private final CommandService commandService;
    @Getter
    private final MessagesService messagesService;

    @Setter
    private List<SimpleCommand> subCommands;
    @Setter
    private SimpleCommand parent;

    private final CommandModel commandModel;

    @Setter
    private boolean logs;
    @Setter
    private boolean active = true;


    public SimpleCommand(CommandModel commandModel,
                         List<SimpleCommand> simpleCommands,
                         CommandService commandService,
                         MessagesService messagesService,
                         EventsService eventsService) {
        super(commandModel.getName());
        this.commandModel = commandModel;
        this.subCommands = simpleCommands;
        this.commandService = commandService;
        this.messagesService = messagesService;
        this.eventsService = eventsService;
        this.setPermissionMessage(commandModel.getPermissionMessage());
        this.setDescription(commandModel.getDescription());
        this.setUsage(commandModel.getUsageMessage());
        this.setLabel(commandModel.getLabel());
        if (commandModel.getPermissions().size() != 0) {
            this.setPermission(commandModel.getPermissions().get(0));
        }
    }


    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
        displayLog("command triggered");
        var commandTarget = commandService.isSubcommandInvoked(this, args);
        return commandTarget.getSimpleCommand().invokeCommand(sender, args, commandTarget.getArgs());
    }

    public boolean invokeCommand(CommandSender sender, String[] args, String[] commandArgs) {
        if (!isActive()) {
            displayLog("inactive");
            sender.sendMessage(messagesService.inactiveCommand(this.getName()));
            return false;
        }

        if (!commandService.hasSenderAccess(sender, commandModel.getCommandAccesses())) {
            displayLog(sender.getName() + " has no access");
            sender.sendMessage(messagesService.noAccess(sender));
            return false;
        }

        var permissionResult = commandService.hasSenderPermissions(sender, commandModel.getPermissions());
        if (!permissionResult.isSuccess()) {
            displayLog(sender.getName() + " has no permissions " + permissionResult.getMessage());
            return false;
        }

        var validationResult = commandService.validateArguments(commandArgs, commandModel.getArguments());
        if (!validationResult.isSuccess()) {
            displayLog("invalid arguments");
            sender.sendMessage(messagesService.invalidArgument(validationResult.getMessage()));
            return false;
        }

        try {
            var invokeStatus = eventsService.invokeEvent(sender, args, commandArgs);
            displayLog("command invoked with status " + invokeStatus);
            return invokeStatus;
        } catch (Exception exception) {
            FluentLogger.LOGGER.error("error while invoking command " + this.getName(), exception);
            return false;
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        var commandTarget = commandService.isSubcommandInvoked(this, args);
        return commandTarget.getSimpleCommand().displayTabComplete(sender, args, commandTarget.getArgs());
    }


    public List<String> displayTabComplete(CommandSender sender, String[] args, String[] commandArgs) {
        var arguments = this.getArguments();
        if (commandArgs.length > arguments.size()) {
            if (commandArgs.length == arguments.size() + 1) {

                if (sender instanceof Player player) {
                    List res = new ArrayList<>();
                    for (var cmd : subCommands) {
                        if (cmd.getCommandModel().isHideFromTabDisplay()) {
                            continue;
                        }
                        if (PermissionsUtility.hasOnePermissionWithoutMessage(player, cmd.getPermission())) {
                            res.add(cmd.getName());
                        }
                    }
                    return res;
                }
            } else
                return List.of();
        }
        if (arguments.isEmpty()) {
            return List.of();
        }

        var argIndex = commandArgs.length - 1;
        argIndex = Math.max(argIndex, 0);
        var argument = arguments.get(argIndex);
        switch (argument.getArgumentDisplayMode()) {
            case TAB_COMPLETE -> {
                //TODO onTabCompleter for players and other stuff
                return argument.getOnTabCompleter().get();
            }
            case NAME -> {
                return List.of(argument.getType().name());
            }
            case TYPE -> {
                return List.of(argument.getType().name().toLowerCase());
            }
        }
        return List.of();
    }


    public void addSubCommand(SimpleCommand command) {
        if (command == this)
            return;
        command.setParent(this);
        subCommands.add(command);
    }

    public void removeSubCommand(SimpleCommand command) {
        if (command.getParent() != this)
            return;
        command.setParent(null);
        subCommands.remove(command);
    }

    public String getName() {
        return commandModel.getName();
    }

    public List<CommandArgument> getArguments() {
        return commandModel.getArguments();
    }

    private void displayLog(String log) {
        if (logs) {
            FluentLogger.LOGGER.info("Command " + this.getName() + " " + log);
        }
    }

    public boolean hasParent() {
        return parent != null;
    }

}

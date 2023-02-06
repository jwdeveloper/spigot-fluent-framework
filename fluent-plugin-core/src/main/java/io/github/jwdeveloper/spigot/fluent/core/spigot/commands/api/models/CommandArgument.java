package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.ArgumentType;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.validators.CommandArgumentValidator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandArgument
{
    private String name;

    private ArgumentType type = ArgumentType.TEXT;

    private ArgumentDisplay argumentDisplayMode = ArgumentDisplay.TYPE;

    private List<CommandArgumentValidator> validators = new ArrayList<>();

    private String description;

    private ChatColor color = ChatColor.WHITE;

    private List<String> tabCompleter = new ArrayList<>();

    public void addValidator(CommandArgumentValidator validator)
    {
        validators.add(validator);
    }
}

package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.ArgumentType;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.validators.CommandArgumentValidator;

import java.util.List;

public interface ArgumentBuilder
{
    ArgumentBuilder setType(ArgumentType type);

    ArgumentBuilder setTabComplete(List<String> tabComplete);

    ArgumentBuilder setTabComplete(String tabComplete);

    ArgumentBuilder setTabComplete(String tabComplete, int index);

    ArgumentBuilder setValidator(CommandArgumentValidator validator);

    ArgumentBuilder setArgumentDisplay(ArgumentDisplay argumentDisplayMode);

    ArgumentBuilder setDescription(String description);

}

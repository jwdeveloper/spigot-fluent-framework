package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.SimpleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandTarget
{
    private final SimpleCommand simpleCommand;

    private final String[] args;
}

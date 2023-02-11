package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.AccessType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandModel {

    private String name;

    private String shortDescription = "";

    private String description = "";

    private String usageMessage = "";

    private String permissionMessage = "";

    private String label = "";

    private List<AccessType> commandAccesses = new ArrayList<>();

    private List<String> permissions = new ArrayList<>();

    private List<CommandArgument> arguments = new ArrayList<>();

    private boolean allParametersRequired = true;

    private boolean hideFromTabDisplay = false;
}

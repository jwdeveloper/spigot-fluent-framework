package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.config;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.PropertiesConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.AccessType;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandModel;
import java.util.Arrays;

public class PropertiesConfigImpl implements PropertiesConfig {

    private final CommandModel model;

    public PropertiesConfigImpl(CommandModel model)
    {
        this.model = model;
    }

    @Override
    public PropertiesConfig setUsageMessage(String usageMessage) {
        model.setUsageMessage(usageMessage);
        return this;
    }

    @Override
    public PropertiesConfig setPermissionMessage(String permissionMessage) {
        model.setPermissionMessage(permissionMessage);
        return this;
    }

    @Override
    public PropertiesConfig setLabel(String label) {
        model.setLabel(label);
        return this;
    }

    @Override
    public PropertiesConfig setAccess(AccessType accessType) {
        model.getCommandAccesses().add(accessType);
        return this;
    }

    @Override
    public PropertiesConfig setShortDescription(String shortDescription) {
        model.setShortDescription(shortDescription);
        return this;
    }

    @Override
    public PropertiesConfig setDescription(String description) {
        model.setDescription(description);
        return this;
    }


    @Override
    public PropertiesConfig addPermissions(String... permissions) {
        model.getPermissions().addAll(Arrays.asList(permissions));
        return this;
    }
}

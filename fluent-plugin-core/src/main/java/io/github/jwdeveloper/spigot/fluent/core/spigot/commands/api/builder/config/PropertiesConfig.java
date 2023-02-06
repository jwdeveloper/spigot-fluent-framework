package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.BuilderConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.enums.AccessType;

public interface PropertiesConfig extends BuilderConfig {
    PropertiesConfig setUsageMessage(String usageMessage);

    PropertiesConfig setPermissionMessage(String permissionMessage);

    PropertiesConfig setLabel(String label);

    PropertiesConfig setShortDescription(String shortDescription);

    PropertiesConfig setDescription(String description);

    PropertiesConfig addPermissions(String... permissions);

     PropertiesConfig setAccess(AccessType accessType);
}

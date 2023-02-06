package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.permissions.api;

import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.permissions.implementation.DefaultPermissions;

public interface FluentPermissionBuilder
{
    public FluentPermissionBuilder registerPermission(PermissionModel model);

    public FluentPermissionBuilder setBasePermissionName(String name);

    public DefaultPermissions defaultPermissionSections();

    public String getBasePermissionName();

}

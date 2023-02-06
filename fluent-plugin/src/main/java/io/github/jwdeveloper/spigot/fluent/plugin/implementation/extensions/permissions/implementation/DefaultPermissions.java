package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.permissions.implementation;

import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionModel;

public class DefaultPermissions
{
    private PermissionModel commands;
    private PermissionModel gui;
    private PermissionModel plugin;

    public DefaultPermissions()
    {
        plugin = createDefaultModel("plugin");
        commands = createDefaultModel("commands");
        gui = createDefaultModel("gui");
    }


    public PermissionModel commands()
    {
        return commands;
    }

    public PermissionModel gui()
    {
        return gui;
    }

    public PermissionModel plugin()
    {
        return plugin;
    }


    private PermissionModel createDefaultModel(String name)
    {
        var permissionModel = new PermissionModel();
        permissionModel.setName(name);
        permissionModel.setTitle(name);
        return permissionModel;
    }
}

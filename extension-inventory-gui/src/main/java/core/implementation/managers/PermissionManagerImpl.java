package core.implementation.managers;

import org.bukkit.entity.Player;
import core.api.managers.permissions.PermissionManager;

import java.util.List;

public class PermissionManagerImpl implements PermissionManager
{

    @Override
    public void addPermissions(String... permissions) {

    }

    @Override
    public void setPermissions(String... permissions) {

    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    public boolean validatePlayer(Player player) {
        return false;
    }

    @Override
    public boolean validatePlayer(Player player, List<String> permissions) {
        return false;
    }
}

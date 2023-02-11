package core.api.managers.permissions;

import org.bukkit.entity.Player;

import java.util.List;

public interface PermissionManager
{
     void addPermissions(String ... permissions);

     void setPermissions(String ... permissions);

     String[] getPermissions();
     boolean validatePlayer(Player player);
     boolean validatePlayer(Player player, List<String> permissions);
}

package test.api.managers;

import org.bukkit.entity.Player;

public interface PermissionManager
{
     void addPermissions(String ... permissions);

     void setPermissions(String ... permissions);

     String[] getPermissions();
     boolean validatePlayer(Player player);
     boolean validatePlayer(Player player, String[] permissions);
}

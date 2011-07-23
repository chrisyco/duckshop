package tk.kirlian.permissions;

import org.bukkit.entity.Player;

/**
 * Thrown to indicate someone doesn't have permission to do something.
 */
public class PermissionsException extends Exception {
    private Player player;
    private String permission;

    public PermissionsException(Player player, String permission) {
        super(player + " does not have the permission " + permission);
        this.player = player;
        this.permission = permission;
    }
}

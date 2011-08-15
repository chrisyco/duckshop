package tk.allele.permissions;

import org.bukkit.entity.Player;

/**
 * Thrown to indicate someone doesn't have permission to do something.
 */
public class PermissionsException extends Exception {
    private final Player player;
    private final String permission;

    public PermissionsException(Player player, String permission) {
        this.player = player;
        this.permission = permission;
    }

    @Override
    public String getMessage() {
        return (player + " does not have the permission " + permission);
    }
}

package tk.allele.permissions;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class PermissionsManager {
    final Permission method;
    final String prefix;

    public PermissionsManager(Permission method, String prefix) {
        this.method = method;
        this.prefix = prefix;
    }

    /**
     * Decide whether a player has permission to do something.
     */
    public boolean has(Player player, String permission) {
        return method.has(player, prefix + permission);
    }

    /**
     * Throw a {@link PermissionsException} if a player doesn't have permission to do this.
     *
     * @throws PermissionsException if the player doesn't have permission.
     */
    public void throwIfCannot(Player player, String permission) throws PermissionsException {
        if (!has(player, permission)) {
            throw new PermissionsException(player, prefix + permission);
        }
    }
}

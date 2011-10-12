package tk.allele.permissions.methods;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.allele.permissions.PermissionsException;
import tk.allele.permissions.PermissionsMethod;

import java.util.logging.Logger;

/**
 * Permissions handler that uses Permissions by TheYeti.
 * @see PermissionsMethod
 */
public class TheYetiPermissions implements PermissionsMethod {
    final PermissionHandler permissionHandler;
    final Logger log;
    final String prefix;

    public TheYetiPermissions(Plugin plugin, Logger log, String prefix) {
        this.log = log;
        this.prefix = prefix;
        Plugin permissionsPlugin = plugin.getServer().getPluginManager().getPlugin("Permissions");

        if(permissionsPlugin != null && permissionsPlugin instanceof Permissions) {
            this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            log.info("Permissions plugin detected!");
        } else {
            this.permissionHandler = null;
        }
    }

    @Override
    public String toString() {
        return "TheYeti";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean isAvailable() {
        return (permissionHandler != null);
    }

    @Override
    public boolean playerHasPermission(Player player, String permission) {
        return permissionHandler.has(player, prefix + permission);
    }

    @Override
    public void throwIfCannot(Player player, String permission)
      throws PermissionsException {
        if(!playerHasPermission(player, permission)) {
            throw new PermissionsException(player, permission);
        }
    }
}

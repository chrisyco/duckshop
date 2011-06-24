package tk.kirlian.SignTraderWithDucks.permissions;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import java.util.logging.Logger;

/**
 * Permissions handler that uses Permissions by TheYeti.
 * @see PermissionsProvider
 */
public class TheYetiPermissionsProvider extends PermissionsProvider {
    private static TheYetiPermissionsProvider provider;
    private SignTraderPlugin plugin;
    private PermissionHandler permissionHandler;
    private Logger log;

    private TheYetiPermissionsProvider(SignTraderPlugin plugin) {
        this.plugin = plugin;
        this.log = plugin.log;
        Plugin permissionsPlugin = plugin.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
                log.info("Hooked into Permissions!");
            } else {
                log.info("Permissions system not detected.");
            }
        }
    }

    public static TheYetiPermissionsProvider getInstance(SignTraderPlugin plugin) {
        if(provider == null) {
            provider = new TheYetiPermissionsProvider(plugin);
        }
        return provider;
    }

    public String getName() {
        return "TheYeti";
    }

    public int getPriority() {
        return 1;
    }

    public boolean isAvailable() {
        return (permissionHandler != null);
    }

    public boolean playerHasPermission(Player player, String permission) {
        return permissionHandler.has(player, permission);
    }
}

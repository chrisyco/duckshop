package tk.kirlian.SignTraderWithDucks.permissions;

import org.bukkit.entity.Player;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import java.util.logging.Logger;

/**
 * A fallback permissions handler that only lets admins do anything.
 * @see PermissionsProvider
 */
public class OpsOnlyPermissionsProvider extends PermissionsProvider {
    private static OpsOnlyPermissionsProvider provider;
    private SignTraderPlugin plugin;
    private Logger log;

    private OpsOnlyPermissionsProvider(SignTraderPlugin plugin) {
        this.plugin = plugin;
        this.log = log;
        System.out.println("WOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOBUFFET!!!");
    }

    public static OpsOnlyPermissionsProvider getInstance(SignTraderPlugin plugin) {
        if(provider == null) {
            provider = new OpsOnlyPermissionsProvider(plugin);
        }
        return provider;
    }

    public String getName() {
        return "OpsOnly";
    }

    public int getPriority() {
        return 100;
    }

    public boolean isAvailable() {
        return true;
    }

    public boolean playerHasPermission(Player player, String permission) {
        return player.isOp();
    }
}

package tk.kirlian.SignTraderWithDucks.permissions;

import org.bukkit.entity.Player;

import tk.kirlian.util.provider.PriorityProviderManager;
import tk.kirlian.util.Misc;

/**
 * A fallback permissions handler that only lets admins do anything.
 * @see PermissionsProvider
 */
public class OpsOnlyPermissionsProvider extends PermissionsProvider {
    private static final OpsOnlyPermissionsProvider provider
        = new OpsOnlyPermissionsProvider();

    private OpsOnlyPermissionsProvider() {
        System.out.println("WOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOBUFFET!!!");
        //PermissionsProvider.getManager().register(this);
    }

    public static OpsOnlyPermissionsProvider getInstance() {
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

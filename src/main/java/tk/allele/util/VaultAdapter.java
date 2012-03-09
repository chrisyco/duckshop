package tk.allele.util;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import tk.allele.economy.DummyEconomy;

/**
 * Hooking into Vault.
 */
public class VaultAdapter {
    private VaultAdapter() {}

    public static Permission getPermissionsAdapter(ServicesManager manager) {
        RegisteredServiceProvider<Permission> permissionProvider = manager.getRegistration(Permission.class);
        if (permissionProvider != null) {
            return permissionProvider.getProvider();
        } else {
            throw new IllegalStateException("The impossible happened! (could not load permissions)");
        }
    }

    public static Economy getEconomyAdapter(ServicesManager manager) {
        RegisteredServiceProvider<Economy> economyProvider = manager.getRegistration(Economy.class);
        if (economyProvider != null) {
            return economyProvider.getProvider();
        } else {
            return DummyEconomy.INSTANCE;
        }
    }
}

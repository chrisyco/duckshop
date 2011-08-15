package tk.allele.protection.methods;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.yi.acru.bukkit.Lockette.Lockette;
import tk.allele.protection.ProtectionMethod;

/**
 * Support for Lockette by Acru Jovian.
 *
 * @see <a href="http://forums.bukkit.org/threads/4336/">Lockette</a>
 */
public class LocketteMethod implements ProtectionMethod {
    private Lockette lockette;

    public LocketteMethod(Plugin plugin) {
        Plugin lockettePlugin = plugin.getServer().getPluginManager().getPlugin("Lockette");
        if(lockettePlugin != null && lockettePlugin instanceof Lockette) {
            this.lockette = (Lockette)lockettePlugin;
        }
    }

    @Override
    public boolean isEnabled() {
        return (lockette != null);
    }

    @Override
    public String toString() {
        return "Lockette";
    }

    @Override
    public boolean canAccess(String playerName, Block block) {
        if(lockette != null) {
            String ownerName = Lockette.getProtectedOwner(block);
            return (ownerName == null || ownerName.equalsIgnoreCase(playerName));
        } else {
            return true;
        }
    }

    @Override
    public boolean canAccess(Player player, Block block) {
        return canAccess(player.getName(), block);
    }
}

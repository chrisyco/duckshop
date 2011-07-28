package tk.kirlian.protection.methods;

import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
import com.griefcraft.model.Protection;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.kirlian.protection.ProtectionMethod;

import java.util.List;

/**
 * Support for LWC by Hidendra.
 *
 * @see <a href="http://forums.bukkit.org/threads/967/">LWC</a>
 */
public class LWCMethod implements ProtectionMethod {
    private LWC lwc;

    public LWCMethod(Plugin plugin) {
        Plugin lwcPlugin = plugin.getServer().getPluginManager().getPlugin("LWC");
        if(lwcPlugin != null && lwcPlugin instanceof LWCPlugin) {
            lwc = ((LWCPlugin) lwcPlugin).getLWC();
        }
    }

    @Override
    public boolean isEnabled() {
        return (lwc != null);
    }

    @Override
    public String toString() {
        return "LWC";
    }

    @Override
    public boolean canAccess(String playerName, Block block) {
        // Use getProtectionSet() to grab both parts of a double chest
        List<Block> blocksProtected = lwc.getProtectionSet(block.getWorld(), block.getX(), block.getY(), block.getZ());
        for(Block chestBlock : blocksProtected) {
            Protection protection = lwc.findProtection(chestBlock);
            // Check if the player is the owner
            if(protection != null) {
                return protection.getOwner().equalsIgnoreCase(playerName);
            }
        }
        // If none of the blocks are protected, anyone can access it
        return true;
    }

    @Override
    public boolean canAccess(Player player, Block block) {
        return canAccess(player.getName(), block);
    }
}


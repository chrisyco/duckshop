package tk.allele.protection.methods;

import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
import com.griefcraft.model.AccessRight;
import com.griefcraft.model.Protection;
import com.griefcraft.model.ProtectionTypes;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.allele.protection.ProtectionMethod;

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

    // Ripped off <https://github.com/Hidendra/LWC/blob/master/src/main/java/com/griefcraft/lwc/LWC.java#L340>
    private boolean canAccessProtection(String playerName, Protection protection) {
        switch(protection.getType()) {
            case ProtectionTypes.PUBLIC:
                return true;

            case ProtectionTypes.PASSWORD:
                return lwc.getMemoryDatabase().hasAccess(playerName, protection);

            case ProtectionTypes.PRIVATE:
                if(playerName.equalsIgnoreCase(protection.getOwner())) {
                    return true;
                }

                if(protection.getAccess(AccessRight.PLAYER, playerName) >= 0) {
                    return true;
                }

                return false;

            default:
                return false;
        }
    }

    @Override
    public boolean canAccess(String playerName, Block block) {
        // Use getProtectionSet() to grab both parts of a double chest
        List<Block> blocksProtected = lwc.getProtectionSet(block.getWorld(), block.getX(), block.getY(), block.getZ());
        for(Block chestBlock : blocksProtected) {
            Protection protection = lwc.findProtection(chestBlock);
            if(protection != null && !canAccessProtection(playerName, protection)) {
                return false;
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


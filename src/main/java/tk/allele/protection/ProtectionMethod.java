package tk.allele.protection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Represents the interface to a protection plugin.
 */
public interface ProtectionMethod {
    /**
     * Return whether this method is enabled.
     */
    public boolean isEnabled();
    /**
     * Get the human-readable name of this method.
     */
    public String toString();
    /**
     * Return whether a player with this name can access a block.
     */
    public boolean canAccess(String playerName, Block block);
    /**
     * Return whether a player can access a block.
     */
    public boolean canAccess(Player player, Block block);
}

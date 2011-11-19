package tk.allele.protection.methods;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.allele.protection.ProtectionMethod;

/**
 * Support for WorldGuard by sk89q.
 *
 * @see <a href="http://wiki.sk89q.com/wiki/WorldGuard">WorldGuard</a>
 */
public class WorldGuardMethod implements ProtectionMethod {
    private static class DummyPlayer extends LocalPlayer {
        private String playerName;

        public DummyPlayer(String playerName) {
            this.playerName = playerName;
        }

        @Override
        public String getName() {
            return playerName;
        }

        @Override
        public boolean hasGroup(String s) {
            return false;
        }

        @Override
        public Vector getPosition() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void kick(String s) {
            // Do nothing
        }

        @Override
        public void ban(String s) {
            // Do nothing
        }

        @Override
        public void printRaw(String s) {
            // Do nothing
        }

        @Override
        public String[] getGroups() {
            return new String[0];
        }

        @Override
        public boolean hasPermission(String s) {
            return false;
        }
    }

    private WorldGuardPlugin worldGuard;

    public WorldGuardMethod(Plugin plugin) {
        Plugin worldGuard = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if (worldGuard != null && worldGuard instanceof WorldGuardPlugin) {
            this.worldGuard = (WorldGuardPlugin) worldGuard;
        }
    }

    @Override
    public boolean isEnabled() {
        return (worldGuard != null);
    }

    @Override
    public String toString() {
        return "WorldGuard";
    }

    @Override
    public boolean canAccess(String playerName, Block block) {
        RegionManager regionManager = worldGuard.getRegionManager(block.getWorld());
        ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitUtil.toVector(block.getLocation()));
        DummyPlayer player = new DummyPlayer(playerName);
        return (worldGuard.getGlobalRegionManager().hasBypass(player, block.getWorld())
                || set.allows(DefaultFlag.CHEST_ACCESS)
                || set.canBuild(player));
    }

    public boolean canAccess(Player player, Block block) {
        return canAccess(player.getName(), block);
    }
}

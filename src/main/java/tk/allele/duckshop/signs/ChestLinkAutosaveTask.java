package tk.allele.duckshop.signs;

import org.bukkit.plugin.Plugin;

/**
 * Saves chest links automatically every few seconds.
 */
public class ChestLinkAutosaveTask implements Runnable {
    static final long STORE_DELAY = 30 * 20; // 30 seconds

    final ChestLinkManager chestLinkManager;

    public ChestLinkAutosaveTask(Plugin plugin, ChestLinkManager chestLinkManager) {
        this.chestLinkManager = chestLinkManager;
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, STORE_DELAY, STORE_DELAY);
    }

    @Override
    public void run() {
        chestLinkManager.store();
    }
}

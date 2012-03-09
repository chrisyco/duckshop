package tk.allele.duckshop.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import tk.allele.duckshop.DuckShop;
import tk.allele.duckshop.errors.InvalidSyntaxException;
import tk.allele.duckshop.signs.SignManager;
import tk.allele.duckshop.signs.TradingSign;
import tk.allele.permissions.PermissionsException;

/**
 * Listens for block events -- like placing a sign.
 */
public class DuckShopBlockListener implements Listener {
    private final DuckShop plugin;

    public DuckShopBlockListener(DuckShop plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();

        TradingSign sign = null;
        try {
            sign = new TradingSign(plugin,
                    player,
                    event.getBlock().getLocation(),
                    event.getLines());
        } catch (InvalidSyntaxException ex) {
            // Do nothing
        } catch (PermissionsException ex) {
            // Science fiction allusions FTW
            event.setCancelled(true);
            player.sendMessage("I'm sorry, " + player.getName() + ". I'm afraid I can't do that.");
        }

        if (sign != null) {
            sign.writeToStringArray(event.getLines());
            player.sendMessage("Created sign successfully.");
            if (!sign.isGlobal()) {
                player.sendMessage("Type \"/duckshop link\" to connect this sign with a chest.");
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockState state = (block != null ? block.getState() : null);

        if (state instanceof Sign) {
            TradingSign sign = null;
            try {
                sign = new TradingSign(plugin,
                        block.getLocation(),
                        ((Sign) state).getLines());
            } catch (InvalidSyntaxException ex) {
                // Do nothing!
            }

            if (sign != null) {
                try {
                    sign.destroy(player);
                } catch (PermissionsException ex) {
                    event.setCancelled(true);
                    player.sendMessage("You can't break this!");
                    // Fixes the sign ending up blank
                    state.update();
                }
            }
        } else if (state instanceof Chest) {
            if (SignManager.getInstance(plugin).isChestConnected(block.getLocation())) {
                player.sendMessage("Warning: This chest is used by a DuckShop sign. The sign will no longer work unless the chest is replaced.");
            }
        }
    }
}

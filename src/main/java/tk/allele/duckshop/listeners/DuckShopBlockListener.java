package tk.allele.duckshop.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.PluginManager;
import tk.allele.duckshop.DuckShop;
import tk.allele.duckshop.errors.InvalidSyntaxException;
import tk.allele.duckshop.signs.SignManager;
import tk.allele.duckshop.signs.TradingSign;
import tk.allele.permissions.PermissionsException;

import java.util.logging.Logger;

/**
 * Listens for block events -- like placing a sign.
 */
public class DuckShopBlockListener extends BlockListener {
    private final DuckShop plugin;
    private final Logger log;

    public DuckShopBlockListener(DuckShop plugin) {
        this.log = plugin.log;
        this.plugin = plugin;

        register(plugin.getServer().getPluginManager());
    }

    private void register(PluginManager pm) {
        pm.registerEvent(Event.Type.BLOCK_PLACE, this, Event.Priority.Normal, plugin);
        pm.registerEvent(Event.Type.BLOCK_DAMAGE, this, Event.Priority.Normal, plugin);
        pm.registerEvent(Event.Type.BLOCK_BREAK, this, Event.Priority.Normal, plugin);
        pm.registerEvent(Event.Type.SIGN_CHANGE, this, Event.Priority.Low, plugin);
    }

    @Override
    public void onSignChange(SignChangeEvent event) {
        if (event.isCancelled()) return;

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

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

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

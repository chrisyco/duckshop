package tk.kirlian.DuckShop;

import java.util.Map;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.logging.Logger;
import tk.kirlian.DuckShop.errors.*;
import tk.kirlian.DuckShop.signs.*;
import tk.kirlian.DuckShop.trading.*;

/**
 * Listens for player events -- such as clicking a sign.
 */
public class DuckShopPlayerListener extends PlayerListener {
    private final DuckShop plugin;
    private final Logger log;

    public Map<Player, Boolean> playerStartedLink = new HashMap<Player, Boolean>();
    public Map<Player, TradingSign> playerLinkSign = new HashMap<Player, TradingSign>();

    public DuckShopPlayerListener(DuckShop plugin) {
        this.log = plugin.log;
        this.plugin = plugin;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!event.isCancelled()) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            BlockState state = block.getState();
            // Right click sign --> Trade
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK && state instanceof Sign) {
                event.setCancelled(useSign(player, block, (Sign)state));
            // Left clicks are used for linking signs and chests
            } else if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if(state instanceof Sign) {
                    markSign(player, block, (Sign)state);
                } else if(state instanceof Chest) {
                    markChest(player, block);
                }
            }
        }
    }

    private boolean useSign(Player player, Block block, Sign state) {
        TradingSign sign = null;
        try {
            sign = new TradingSign(plugin,
                                   null, // There is no placingPlayer as the sign is being used, not placed
                                   block.getLocation(),
                                   state.getLines());
        } catch(InvalidSyntaxException ex) {
            // Do nothing!
        } catch(PermissionsException ex) {
            // See above note on placingPlayer
            throw new RuntimeException(ex);
        }
        if(sign != null) {
            try {
                sign.tradeWith(player);
            } catch(InvalidChestException ex) {
                player.sendMessage("Invalid chest. Make sure it is connected properly.");
            } catch(ChestProtectionException ex) {
                player.sendMessage("The owner of the sign doesn't have access to the connected chest.");
            } catch(CannotTradeException ex) {
                player.sendMessage("Oh noes! Cannot trade!");
            } catch(PermissionsException ex) {
                player.sendMessage("You're not allowed to use this for some reason.");
            }
            return true;
        } else {
            return false;
        }
    }

    private void markSign(Player player, Block block, Sign state) {
        if(playerStartedLink.containsKey(player)) {
            TradingSign sign = null;
            try {
                sign = new TradingSign(plugin,
                                       null, // There is no placingPlayer as the sign is being marked, not placed.
                                       block.getLocation(),
                                       state.getLines());
            } catch(InvalidSyntaxException ex) {
                player.sendMessage("That's not a valid trading sign.");
            } catch(PermissionsException ex) {
                // See above note on placingPlayer
                throw new RuntimeException(ex);
            }
            if(sign != null) {
                player.sendMessage("Now left click a chest to connect it.");
                player.sendMessage("Or left click another sign if that's not the right one.");
                playerLinkSign.put(player, sign);
            }
        }
    }

    private void markChest(Player player, Block block) {
        if(playerLinkSign.containsKey(player)) {
            TradingSign sign = playerLinkSign.get(player);
            sign.setChestLocation(block.getLocation());
            player.sendMessage("Sign connected successfully.");
            cancelLink(player);
        }
    }

    public void cancelLink(Player player) {
        playerStartedLink.remove(player);
        playerLinkSign.remove(player);
    }
}

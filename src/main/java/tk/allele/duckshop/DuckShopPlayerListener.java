package tk.allele.duckshop;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import tk.allele.duckshop.errors.*;
import tk.allele.duckshop.signs.TradingSign;
import tk.allele.permissions.PermissionsException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Listens for player events -- such as clicking a sign.
 */
public class DuckShopPlayerListener extends PlayerListener {
    final DuckShop plugin;
    final Logger log;
    final LinkState linkState;

    public DuckShopPlayerListener(DuckShop plugin, LinkState linkState) {
        this.log = plugin.log;
        this.plugin = plugin;
        this.linkState = linkState;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!event.isCancelled()) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if(block != null) {
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
            } catch(TradingException ex) {
                if(ex instanceof TooMuchException) {
                    if(player.equals(ex.getPlayer())) {
                        player.sendMessage("You don't have enough space for " + ex.getItem() + ".");
                    } else {
                        player.sendMessage("The sign owner doesn't have enough space for " + ex.getItem() + ".");
                    }
                } else if(ex instanceof TooLittleException) {
                    if(player.equals(ex.getPlayer())) {
                        player.sendMessage("You need " + ex.getItem() + " to trade.");
                    } else {
                        player.sendMessage("The sign owner doesn't have " + ex.getItem() + ".");
                    }
                } else {
                    player.sendMessage("Oh noes! Cannot trade!");
                    plugin.log.warning("Unknown TradingException: " + ex.getClass().getName());
                }
            } catch(PermissionsException ex) {
                player.sendMessage("You're not allowed to use this for some reason.");
            }
            return true;
        } else {
            return false;
        }
    }

    private void markSign(Player player, Block block, Sign state) {
        if(linkState.hasStartedLink(player)) {
            TradingSign sign = null;
            // Parse and validate the sign
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
            // Check if the player can link the sign first
            if(sign != null) {
                try {
                    sign.preSetChestLocation(player);
                } catch(UnsupportedOperationException ex) {
                    player.sendMessage("Global signs don't need to be connected to chests.");
                    sign = null;
                } catch(PermissionsException ex) {
                    player.sendMessage("You don't have permission to link this sign.");
                    sign = null;
                }
            }
            // If there aren't any problems, proceed to the next step
            if(sign != null) {
                player.sendMessage("Now left click a chest to connect it.");
                player.sendMessage("Or left click another sign if that's not the right one.");
                linkState.markSign(player, sign);
            // If there were problems, prompt the user again
            } else {
                player.sendMessage("Try another sign, or type \"/duckshop cancel\" to quit.");
            }
        }
    }

    private void markChest(Player player, Block block) {
        if(linkState.hasMarkedSign(player)) {
            linkState.markChest(player, block.getLocation());
            player.sendMessage("Sign connected successfully.");
        }
    }
}

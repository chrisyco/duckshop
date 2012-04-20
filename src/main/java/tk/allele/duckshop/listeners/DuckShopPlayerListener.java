package tk.allele.duckshop.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import tk.allele.duckshop.DuckShop;
import tk.allele.duckshop.errors.*;
import tk.allele.duckshop.signs.TradingSign;
import tk.allele.permissions.PermissionsException;
import tk.allele.util.commands.CommandSenderPlus;

import java.util.logging.Logger;

/**
 * Listens for player events -- such as clicking a sign.
 */
public class DuckShopPlayerListener implements Listener {
    final DuckShop plugin;
    final Logger log;
    final LinkState linkState;

    public DuckShopPlayerListener(DuckShop plugin, LinkState linkState) {
        this.log = plugin.log;
        this.plugin = plugin;
        this.linkState = linkState;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        BlockState state = (block != null ? block.getState() : null);

        switch (event.getAction()) {
            // Right clicks start a trade
            case RIGHT_CLICK_BLOCK:
                if (state instanceof Sign) {
                    if (useSign(player, block, (Sign) state)) {
                        event.setCancelled(true);
                    }
                }
                break;

            // Left clicks are used for linking signs and chests
            case LEFT_CLICK_BLOCK:
                if (state instanceof Sign) {
                    if (handleLeftClickSign(player, block, (Sign) state)) {
                        event.setCancelled(true);
                    }
                } else if (state instanceof Chest) {
                    if (markChest(player, block)) {
                        event.setCancelled(true);
                    }
                }
                break;
        }
    }

    /**
     * Handle a right click event on a sign.
     *
     * @return true if the event was handled, otherwise false.
     */
    private boolean useSign(Player player, Block block, Sign state) {
        CommandSenderPlus playerPlus = new CommandSenderPlus(player);
        TradingSign sign = null;

        // Parse and validate the sign
        try {
            sign = new TradingSign(plugin,
                    block.getLocation(),
                    state.getLines());
        } catch (InvalidSyntaxException ex) {
            // Do nothing!
        }

        if (sign != null) {
            // Go through with the trade, telling the player if it does not work
            try {
                sign.tradeWith(player);
            } catch (InvalidChestException ex) {
                playerPlus.error("Invalid chest. Make sure it is connected properly.");
            } catch (ChestProtectionException ex) {
                playerPlus.error("The owner of the sign doesn't have access to the connected chest.");
            } catch (TradingException ex) {
                if (ex instanceof TooMuchException) {
                    if (player.equals(ex.getPlayer())) {
                        playerPlus.error("You don't have enough space for " + ex.getItem() + ".");
                    } else {
                        playerPlus.error("The sign owner doesn't have enough space for " + ex.getItem() + ".");
                    }
                } else if (ex instanceof TooLittleException) {
                    if (player.equals(ex.getPlayer())) {
                        playerPlus.error("You need " + ex.getItem() + " to trade.");
                    } else {
                        playerPlus.error("The sign owner doesn't have " + ex.getItem() + ".");
                    }
                } else {
                    playerPlus.error("Oh noes! Cannot trade!");
                    plugin.log.severe("Unknown TradingException: " + ex.getClass().getName());
                }
            } catch (PermissionsException ex) {
                playerPlus.error("You're not allowed to use this for some reason.");
            }

            // Update the sign's status
            sign.writeToStringArray(state.getLines());
            state.update();

            // Cancel the event
            return true;
        } else {
            return false;
        }
    }

    /**
     * Handle a left click event on a sign.
     *
     * @return true if a link was started, otherwise false.
     */
    private boolean handleLeftClickSign(Player player, Block block, Sign state) {
        CommandSenderPlus playerPlus = new CommandSenderPlus(player);
        TradingSign sign = null;

        // Parse and validate the sign
        try {
            sign = new TradingSign(plugin,
                    block.getLocation(),
                    state.getLines());
        } catch (InvalidSyntaxException ex) {
            // Do nothing
        }

        boolean linkedSuccessfully = false;

        if (linkState.hasStartedLink(player)) {
            // If it isn't valid, tell the user
            if (sign == null) {
                playerPlus.error("That's not a valid trading sign.");
            } else {
                // Check if the player can link the sign first
                boolean canLinkSign = true;
                try {
                    sign.preSetChestLocation(player);
                } catch (UnsupportedOperationException ex) {
                    playerPlus.error("Global signs don't need to be connected to chests.");
                    canLinkSign = false;
                } catch (PermissionsException ex) {
                    playerPlus.error("You don't have permission to link this sign.");
                    canLinkSign = false;
                }

                // If we can link the sign, lead the user to the next step
                if (canLinkSign) {
                    playerPlus.action("Now left click a chest to connect it.");
                    playerPlus.action("Or left click another sign if that's not the right one.");
                    linkState.markSign(player, sign);
                    linkedSuccessfully = true;
                } else {
                    // Otherwise, prompt the user again
                    playerPlus.action("Try another sign, or type \"/duckshop cancel\" to quit.");
                }
            }
        }

        // Let the user update a sign's status by clicking on it
        if (sign != null) {
            sign.writeToStringArray(state.getLines());
            state.update();
        }

        return linkedSuccessfully;
    }

    /**
     * Complete a link to this chest, if possible.
     *
     * @return true if the link was completed, false otherwise.
     */
    private boolean markChest(Player player, Block block) {
        if (linkState.hasMarkedSign(player)) {
            linkState.markChest(player, block.getLocation());
            (new CommandSenderPlus(player)).info("Sign connected successfully.");
            return true;
        } else {
            return false;
        }
    }
}

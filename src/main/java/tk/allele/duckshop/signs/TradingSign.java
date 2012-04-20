package tk.allele.duckshop.signs;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tk.allele.duckshop.DuckShop;
import tk.allele.duckshop.errors.*;
import tk.allele.duckshop.items.Item;
import tk.allele.duckshop.trading.ChestInventoryAdapter;
import tk.allele.duckshop.trading.GlobalSignAdapter;
import tk.allele.duckshop.trading.PlayerInventoryAdapter;
import tk.allele.duckshop.trading.TradeAdapter;
import tk.allele.permissions.PermissionsException;
import tk.allele.util.Locations;
import tk.allele.util.StringTools;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Logger;

/**
 * Represents a sign that can be used as a shop.
 */
public class TradingSign {
    private DuckShop plugin;
    private Logger log;
    private String ownerName;
    private Location signLocation;
    private boolean global;
    private Item sellerToBuyer, buyerToSeller;

    /**
     * Create a TradingSign instance, as the sign is being <em>placed</em>.
     *
     * @param plugin        the DuckShop plugin.
     * @param placingPlayer the player who placed the sign.
     * @param signLocation  the location of the sign.
     * @param lines         the contents of the sign, as a four-element
     *                      array.
     *
     * @throws InvalidSyntaxException if the lines cannot be parsed.
     * @throws PermissionsException   if the syntax is valid, but the
     *                                placing player does not have the
     *                                required permissions.
     */
    public TradingSign(DuckShop plugin, @Nonnull Player placingPlayer, Location signLocation, String[] lines)
            throws InvalidSyntaxException, PermissionsException {
        // Initialize the object
        initialize(plugin, placingPlayer, signLocation, lines);

        // Do permissions check at the end, after trying to parse
        // So players who don't have permissions can still place non-trading signs
        plugin.permissions.throwIfCannot(placingPlayer, "create." + getActionType(placingPlayer));
    }

    /**
     * Create a TradingSign instance, as the sign is being <em>used</em>.
     *
     * @param plugin        the DuckShop plugin.
     * @param signLocation  the location of the sign.
     * @param lines         the contents of the sign, as a four-element
     *                      array.
     *
     * @throws InvalidSyntaxException if the lines cannot be parsed.
     */
    public TradingSign(DuckShop plugin, Location signLocation, String[] lines)
            throws InvalidSyntaxException {
        initialize(plugin, null, signLocation, lines);
    }

    private void initialize(DuckShop plugin, @Nullable Player placingPlayer, Location signLocation, String[] lines)
            throws InvalidSyntaxException {
        this.plugin = plugin;
        this.log = plugin.log;
        this.signLocation = signLocation;

        // Trim whitespace
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = lines[i].trim();
        }

        // Check if global or personal
        this.global = lines[0].equalsIgnoreCase("[Global]");
        if (!global) {
            // If name is blank, use the placing player's name
            if (lines[0].length() == 0 || lines[0].equalsIgnoreCase("[Personal]")) {
                if (placingPlayer != null) {
                    this.ownerName = placingPlayer.getName();
                } else {
                    throw new InvalidSyntaxException();
                }
                // Otherwise, use the name written on the sign
            } else if (StringTools.isValidUsername(lines[0])) {
                this.ownerName = lines[0];
                // Normalize the name to use correct capitalization
                Player owner = plugin.getServer().getPlayer(ownerName);
                if (owner != null) {
                    this.ownerName = owner.getName();
                }
            } else {
                throw new InvalidSyntaxException();
            }
        }

        // Parse the two middle lines
        sellerToBuyer = Item.fromString(lines[1]);
        buyerToSeller = Item.fromString(lines[2]);
    }

    /**
     * Utility method for permissions checking.
     */
    private String getActionType(Player actingPlayer) {
        if (global) {
            return "global";
        } else if (ownerName.equalsIgnoreCase(actingPlayer.getName())) {
            return "personal";
        } else {
            return "other";
        }
    }

    /**
     * Update a String array with the contents of this TradingSign.
     *
     * @throws IllegalArgumentException if the array is not of length 4.
     */
    public void writeToStringArray(String[] lines) {
        if (lines.length != 4) {
            throw new IllegalArgumentException("String array must be of length 4");
        }
        if (global) {
            lines[0] = "[Global]";
        } else {
            lines[0] = ownerName;
        }
        lines[1] = sellerToBuyer.getOriginalString();
        lines[2] = buyerToSeller.getOriginalString();

        TradeAdapter adapter;
        try {
            adapter = getAdapter();
        } catch (InvalidChestException e) {
            adapter = null;
        } catch (ChestProtectionException e) {
            adapter = null;
        }
        lines[3] = FillOMeter.generateStatusLine(adapter, sellerToBuyer);
    }

    /**
     * Return a TradeAdapter corresponding to this sign.
     *
     * @throws InvalidChestException    if it requires a chest, but is not connected to one.
     * @throws ChestProtectionException if the owner does not have access to the chest.
     * @see TradeAdapter
     */
    public TradeAdapter getAdapter() throws InvalidChestException, ChestProtectionException {
        if (global) {
            return new GlobalSignAdapter(plugin);
        } else {
            Location chestLocation = getChestLocation();
            if (chestLocation != null) {
                return new ChestInventoryAdapter(plugin, ownerName, chestLocation);
            } else {
                throw new InvalidChestException();
            }
        }
    }

    /**
     * Return whether this sign is <i>global</i>.
     * <p>
     * A sign is global if it is not associated with a player.
     */
    public boolean isGlobal() {
        return global;
    }

    /**
     * Attempt to trade with another TradeAdapter.
     *
     * @throws InvalidChestException    if the chest is invalid (duh)
     * @throws TradingException         if any party doesn't have enough to trade
     * @throws ChestProtectionException if the chest is protected
     */
    public void tradeWith(final Player buyer, final TradeAdapter buyerAdapter)
            throws InvalidChestException, TradingException, ChestProtectionException {
        final TradeAdapter sellerAdapter = getAdapter();
        // Check each combination
        if (!buyerToSeller.canAddTo(sellerAdapter)) {
            throw new TooMuchException(null, buyerToSeller);
        }
        if (!sellerToBuyer.canTakeFrom(sellerAdapter)) {
            throw new TooLittleException(null, sellerToBuyer);
        }
        if (!sellerToBuyer.canAddTo(buyerAdapter)) {
            throw new TooMuchException(buyer, sellerToBuyer);
        }
        if (!buyerToSeller.canTakeFrom(buyerAdapter)) {
            throw new TooLittleException(buyer, buyerToSeller);
        }
        // If all is well, do the actual trade
        buyerToSeller.addTo(sellerAdapter);
        sellerToBuyer.takeFrom(sellerAdapter);
        sellerToBuyer.addTo(buyerAdapter);
        buyerToSeller.takeFrom(buyerAdapter);
    }

    /**
     * Attempt to trade with a Player.
     *
     * @throws InvalidChestException    if the chest is invalid (duh)
     * @throws TradingException         if any party doesn't have enough to trade
     * @throws ChestProtectionException if the chest is protected
     * @throws PermissionsException     if the player doesn't have "use" permissions
     */
    public void tradeWith(final Player buyer)
            throws InvalidChestException, TradingException, ChestProtectionException, PermissionsException {
        plugin.permissions.throwIfCannot(buyer, "use." + getActionType(buyer));
        tradeWith(buyer, new PlayerInventoryAdapter(plugin, buyer.getName()));
    }

    /**
     * Create a human-readable representation of this TradingSign, for debugging.
     * <p>
     * Do not use this for updating the actual sign; to do this, use
     * updateSign() and writeToStringArray().
     */
    public String toString() {
        Location chestLocation = getChestLocation();
        return "TradingSign: Global=" + global + "; " + sellerToBuyer + "; " + buyerToSeller + "; Chest=" + (chestLocation != null ? Locations.toString(chestLocation) : "<null>");
    }

    /**
     * Get the location of the chest for this sign.
     */
    public Location getChestLocation() {
        return ChestLinkManager.getInstance(plugin).getChestLocation(signLocation);
    }

    /**
     * Called before the player starts linking the sign.
     *
     * @throws UnsupportedOperationException if this is a global sign
     * @throws PermissionsException          if the player doesn't have permission to
     *                                       link this sign.
     */
    public void preSetChestLocation(Player linkingPlayer)
            throws UnsupportedOperationException, PermissionsException {
        if (isGlobal()) {
            throw new UnsupportedOperationException("global signs cannot be connected to chests");
        }
        plugin.permissions.throwIfCannot(linkingPlayer, "create." + getActionType(linkingPlayer));
    }

    /**
     * Set the location of the chest for this sign.
     * <p>
     * If the chest location is set interactively, be sure to call
     * {@link #preSetChestLocation(Player)} to make sure the player has
     * permission first.
     */
    public void setChestLocation(Location chestLocation) {
        ChestLinkManager.getInstance(plugin).setChestLocation(signLocation, chestLocation);
    }

    /**
     * Called when the sign is destroyed.
     *
     * @throws PermissionsException if player is not allowed to break
     *                              the sign.
     */
    public void destroy(Player breakingPlayer) throws PermissionsException {
        plugin.permissions.throwIfCannot(breakingPlayer, "break." + getActionType(breakingPlayer));
        ChestLinkManager.getInstance(plugin).removeChestLocation(signLocation);
    }
}

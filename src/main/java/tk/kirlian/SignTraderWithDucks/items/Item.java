package tk.kirlian.SignTraderWithDucks.items;

import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import tk.kirlian.SignTraderWithDucks.*;
import tk.kirlian.SignTraderWithDucks.errors.*;

/**
 * Represents an item to be traded in a {@link TradingSign}.
 */
public class Item implements Serializable {
    /**
     * An item ID that represents money, rather than a tangible item.
     */
    public static final int MONEY = -1;
    private static final Pattern itemPattern = Pattern.compile("\\$(\\d+)|(\\d+)\\s+(.+)");
    private static final ItemDB itemDB = ItemDB.getInstance();

    private String itemString;
    private int itemId;
    private int amount;
    private short damage;
    private boolean hasDamage;

    /**
     * Create a new Item instance.
     * <p>
     * The item string is not parsed; it is simply kept so it can be
     * later retrieved by {@link #getOriginalString()}.
     */
    public Item(final int itemId, final int amount, final short damage, final String itemString) {
        this.itemId = itemId;
        this.amount = amount;
        this.damage = damage;
        this.itemString = itemString;
        this.hasDamage = false;
    }

    /**
     * Create a new Item instance without a damage value.
     * <p>
     * The item string is not parsed; it is simply kept so it can be
     * later retrieved by {@link #getOriginalString()}.
     */
    public Item(final int itemId, final int amount, final String itemString) {
        this.itemId = itemId;
        this.amount = amount;
        this.damage = 0;
        this.itemString = itemString;
        this.hasDamage = false;
    }

    /**
     * Create a new Item instance.
     */
    public Item(final int itemId, final int amount, final short damage) {
        this(itemId, amount, damage, "");
    }

    /**
     * Create a new Item instance without a damage value.
     */
    public Item(final int itemId, final int amount) {
        this(itemId, amount, "");
    }

    /**
     * Parse a single line from a TradingSign.
     */
    public static Item fromString(final String itemString)
      throws InvalidSyntaxException {
        int itemId;
        int amount;
        Matcher matcher = itemPattern.matcher(itemString);
        if(matcher.matches()) {
            /*int groupCount = matcher.groupCount()+1;
            for(int i = 0; i < groupCount; ++i) {
                System.out.println("Parser: " + Integer.toString(i) + ": " + matcher.group(i));
            }*/
            // Item
            if(matcher.group(1) == null) {
                // Group 2 is definitely an integer, since it was matched with "\d+"
                amount = Integer.parseInt(matcher.group(2));
                String itemName = matcher.group(3);
                try {
                    itemId = Integer.parseInt(itemName);
                } catch(NumberFormatException ex) {
                    // If it isn't an integer, treat it as an item name
                    ItemDefinition itemDfn = itemDB.getItemByAlias(itemName);
                    if(itemDfn == null) {
                        throw new InvalidSyntaxException();
                    } else {
                        itemId = itemDfn.getId();
                    }
                }
            // Money
            } else if(matcher.group(2) == null && matcher.group(3) == null) {
                amount = Integer.parseInt(matcher.group(1));
                itemId = MONEY;
            } else {
                throw new InvalidSyntaxException();
            }
            return new Item(itemId, amount, (short)0, itemString);
        } else {
            throw new InvalidSyntaxException();
        }
    }

    /**
     * Retrieve the original item definition as a String.
     * <p>
     * This is the original String passed to the constructor; for a
     * normalized string, use {@link #toString()}.
     * <p>
     * The resulting String is not guaranteed to be valid.
     */
    public String getOriginalString() {
        return itemString;
    }

    /**
     * Create a normalized human readable representation of this object.
     * <p>
     * It is normalized as in any two equivalent Item objects would
     * have the same String.
     * <p>
     * The resulting String can be passed back to {@link #fromString(String)}.
     */
    public String toString() {
        if(itemId == MONEY) {
            return "$" + amount;
        } else {
            return amount + " " + itemDB.getItemById(itemId).getShortName();
        }
    }

    /**
     * Get the item ID, or the data value.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Get the number of items represented by this object.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Get the damage value of this object.
     */
    public short getDamage() {
        return damage;
    }

    /**
     * Return whether a damage value was specified when the object was
     * constructed.
     */
    public boolean hasDamage() {
        return (hasDamage && !isMoney());
    }

    /**
     * Return whether this represents money, or a tangible item.
     */
    public boolean isMoney() {
        return (itemId == MONEY);
    }
}

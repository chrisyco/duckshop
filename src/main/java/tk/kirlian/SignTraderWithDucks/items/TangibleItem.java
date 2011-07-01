package tk.kirlian.SignTraderWithDucks.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import tk.kirlian.SignTraderWithDucks.*;
import tk.kirlian.SignTraderWithDucks.errors.*;

/**
 * Represents a tangible item, rather than money.
 */
public class TangibleItem extends Item {
    /**
     * The format for a tangible item: the amount as an integer, then a
     * space, then the item name.
     */
    private static Pattern tangibleItemPattern = Pattern.compile("(\\d+)\\s+(.+)");
    private static final ItemDB itemDB = ItemDB.getInstance();

    private int itemId;
    private int amount;
    private short damage;
    private boolean hasDamage;

    /**
     * Create a new TangibleItem instance.
     * <p>
     * The item string is not parsed; it is simply kept so it can be
     * later retrieved by {@link #getOriginalString()}.
     */
    public TangibleItem(final int itemId, final int amount, final short damage, final String itemString) {
        super(itemString);
        this.itemId = itemId;
        this.amount = amount;
        this.damage = damage;
    }

    /**
     * Create a new TangibleItem.
     */
    public TangibleItem(final int itemId, final int amount, final short damage) {
        super();
        this.itemId = itemId;
        this.amount = amount;
        this.damage = damage;
    }

    /**
     * Parse a TangibleItem from a String.
     */
    public static TangibleItem fromString(final String itemString)
      throws InvalidSyntaxException {
        Matcher matcher = tangibleItemPattern.matcher(itemString);
        if(matcher.matches()) {
            // Group 1 is definitely an integer, since it was matched with "\d+"
            int amount = Integer.parseInt(matcher.group(1));
            String itemName = matcher.group(2);
            int itemId;
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
            return new TangibleItem(itemId, amount, (short)0);
        } else {
            throw new InvalidSyntaxException();
        }
    }

    /**
     * Get the item ID, or the data value.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Get the number of items.
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
        return hasDamage;
    }

    /**
     * Create an array of ItemStacks with the same data as this object,
     * but grouped into stacks.
     */
    public ItemStack[] toItemStacks() {
        int maxStackSize = Material.getMaterial(itemId).getMaxStackSize();
        int leftover = amount;
        ItemStack[] stacks;
        int quotient = amount / maxStackSize;
        if(amount % maxStackSize == 0) {
            stacks = new ItemStack[quotient];
        } else {
            // If it cannot be divided evenly, the last cell will
            // contain the part left over
            stacks = new ItemStack[quotient+1];
            stacks[quotient] = new ItemStack(itemId, amount % maxStackSize, damage);
        }
        for(int i = 0; i < quotient; ++i) {
            stacks[i] = new ItemStack(itemId, maxStackSize, damage);
        }
        return stacks;
    }

    @Override
    public String toString() {
        return amount + " " + itemDB.getItemById(itemId).getShortName();
    }
}

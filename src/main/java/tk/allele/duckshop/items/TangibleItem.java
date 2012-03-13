package tk.allele.duckshop.items;

import info.somethingodd.OddItem.OddItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tk.allele.duckshop.errors.InvalidSyntaxException;
import tk.allele.duckshop.trading.TradeAdapter;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a tangible item, rather than money.
 */
public class TangibleItem extends Item {
    /**
     * The format for a tangible item: the amount as an integer, then a
     * space, then the item name, then an optional durability value.
     */
    private static final Pattern tangibleItemPattern = Pattern.compile("(\\d+)\\s+([A-Za-z0-9_-]+)\\s*(\\d*)");
    private static final int NAME_LENGTH = 10;

    private final int itemId;
    private final int amount;
    private final short damage;

    /**
     * Create a new TangibleItem instance.
     * <p>
     * The item string is not parsed; it is simply kept so it can be
     * later retrieved by {@link #getOriginalString()}.
     */
    public TangibleItem(final int itemId, final int amount, final short damage, @Nullable final String itemString) {
        super(itemString);
        this.itemId = itemId;
        this.amount = amount;
        this.damage = damage;
    }

    /**
     * Create a new TangibleItem.
     *
     * @throws IllegalArgumentException if the amount is zero.
     */
    public TangibleItem(final int itemId, final int amount, final short damage) {
        this(itemId, amount, damage, null);
    }

    /**
     * Parse a TangibleItem from a String.
     *
     * @throws InvalidSyntaxException if the item cannot be parsed.
     */
    public static TangibleItem fromString(final String itemString)
            throws InvalidSyntaxException {
        Matcher matcher = tangibleItemPattern.matcher(itemString);
        if (matcher.matches()) {

            // Group 1 is definitely an integer, since it was matched with "\d+"
            int amount = Integer.parseInt(matcher.group(1));
            String itemName = matcher.group(2);
            int itemId;
            short damage = 0;

            // Try parsing it as an item ID first
            try {
                itemId = Integer.parseInt(itemName);
            } catch (NumberFormatException ex) {
                // If it isn't an integer, treat it as an item name
                ItemStack itemDfn;
                try {
                    itemDfn = OddItem.getItemStack(itemName);
                } catch (IllegalArgumentException ex2) {
                    throw new InvalidSyntaxException();
                }

                itemId = itemDfn.getTypeId();
                damage = itemDfn.getDurability();
            }

            // If there's another number after that, it's a damage value
            try {
                damage = Short.parseShort(matcher.group(3));
            } catch (NumberFormatException ex) {
                // Do nothing -- keep the damage value from the code above
            }

            // Check if it's actually a real item
            if (Material.getMaterial(itemId) == null) {
                throw new InvalidSyntaxException();
            }

            // Create the object!
            return new TangibleItem(itemId, amount, damage, itemString);
        } else {
            throw new InvalidSyntaxException();
        }
    }

    @Override
    public int countAddTo(TradeAdapter adapter) {
        return adapter.countAddTangibleItem(this);
    }

    @Override
    public int countTakeFrom(TradeAdapter adapter) {
        return adapter.countSubtractTangibleItem(this);
    }

    @Override
    public void addTo(TradeAdapter adapter) {
        adapter.addTangibleItem(this);
    }

    @Override
    public void takeFrom(TradeAdapter adapter) {
        adapter.subtractTangibleItem(this);
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
     * Create a single ItemStack corresponding to this object.
     */
    public ItemStack toItemStack() {
        return new ItemStack(itemId, amount, damage);
    }

    /**
     * Create an array of ItemStacks with the same data as this object,
     * but grouped into stacks.
     */
    public ItemStack[] toItemStackArray() {
        int maxStackSize = Material.getMaterial(itemId).getMaxStackSize();
        ItemStack[] stacks;
        int quotient = amount / maxStackSize;
        if (amount % maxStackSize == 0) {
            stacks = new ItemStack[quotient];
        } else {
            // If it cannot be divided evenly, the last cell will
            // contain the part left over
            stacks = new ItemStack[quotient + 1];
            stacks[quotient] = new ItemStack(itemId, amount % maxStackSize, damage);
        }
        for (int i = 0; i < quotient; ++i) {
            stacks[i] = new ItemStack(itemId, maxStackSize, damage);
        }
        return stacks;
    }

    @Override
    public boolean equals(Object thatObj) {
        if (thatObj instanceof TangibleItem) {
            TangibleItem that = (TangibleItem) thatObj;
            return (this.itemId == that.itemId && this.damage == that.damage);
        } else if (thatObj instanceof ItemStack) {
            ItemStack that = (ItemStack) thatObj;
            return (this.itemId == that.getTypeId() && this.damage == that.getDurability());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = itemId - 199;
        hash = hash * 887 + damage;
        hash = hash * 887 + amount;
        return hash;
    }

    private static String getBestName(Collection<String> aliases) {
        for (String name : aliases) {
            // Skip names which are too long
            if (name.length() <= NAME_LENGTH) {
                return name;
            }
        }
        return null;
    }

    private static String getBestNameForId(int itemId, short damage) {
        String name;
        try {
            name = getBestName(OddItem.getAliases(new ItemStack(itemId, 1, damage)));
        } catch (IllegalArgumentException ex) {
            // Fall through
            name = null;
        }
        return name;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(15);
        buffer.append(Integer.toString(amount));
        buffer.append(" ");
        String name = getBestNameForId(itemId, damage);
        if (name != null) {
            // If there is a specific name for this, use it
            buffer.append(name);
        } else {
            // Otherwise, use the generic name + damage value
            name = getBestNameForId(itemId, (short) 0);
            if (name != null) {
                buffer.append(name);
                buffer.append(Short.toString(damage));
            } else {
                // If there isn't even a generic name, just use the ID
                buffer.append(Integer.toString(itemId));
                if (damage != 0) {
                    buffer.append(" ");
                    buffer.append(Short.toString(damage));
                }
            }
        }
        return buffer.toString();
    }
}

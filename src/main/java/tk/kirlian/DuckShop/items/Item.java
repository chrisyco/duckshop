package tk.kirlian.DuckShop.items;

import java.io.Serializable;

import tk.kirlian.DuckShop.*;
import tk.kirlian.DuckShop.errors.*;

/**
 * Represents an item to be traded.
 */
public abstract class Item implements Serializable {
    private String itemString;

    /**
     * Create a new Item instance.
     * <p>
     * The item string is not parsed; it is simply kept so it can be
     * later retrieved by {@link #getOriginalString()}.
     */
    protected Item(final String itemString) {
        this.itemString = itemString;
    }

    /**
     * Create a new Item instance.
     */
    protected Item() {
        this("");
    }

    /**
     * Parse a single line from a TradingSign.
     */
    public static Item fromString(final String itemString)
      throws InvalidSyntaxException {
        // Call the subclasses' parsers, returning the first one that works
        try {
            return TangibleItem.fromString(itemString);
        } catch(InvalidSyntaxException ex) {
            try {
                return Money.fromString(itemString);
            } catch(InvalidSyntaxException ey) {
                throw ey;
            }
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
    public abstract String toString();
}

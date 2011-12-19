package tk.allele.duckshop.items;

import tk.allele.duckshop.errors.InvalidSyntaxException;
import tk.allele.duckshop.trading.TradeAdapter;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;

/**
 * Represents something that can be traded.
 */
public abstract class Item implements Serializable {
    /**
     * The set of words that mean "nothing".
     * <p>
     * <b>Implementation note</b>: We use a LinkedHashSet here because
     * {@code Money.toString()} depends on the ordering of the aliases.
     */
    protected static final Set<String> nothingAliases = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList("nothing", "free")));

    private final String itemString;

    /**
     * Create a new Item instance.
     * <p>
     * The item string is not parsed; it is simply kept so it can be
     * later retrieved by {@link #getOriginalString()}.
     */
    protected Item(@Nullable final String itemString) {
        this.itemString = itemString;
    }

    /**
     * Parse a single line from a TradingSign.
     *
     * @throws InvalidSyntaxException if the item cannot be parsed.
     */
    public static Item fromString(final String itemString)
            throws InvalidSyntaxException {
        // Call the subclasses' parsers, returning the first one that works
        try {
            return TangibleItem.fromString(itemString);
        } catch (InvalidSyntaxException ex) {
            return Money.fromString(itemString);
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
        if (itemString == null) {
            return toString();
        } else {
            return itemString;
        }
    }

    /**
     * Return whether this item can be added to the adapter.
     */
    public boolean canAddTo(TradeAdapter adapter) {
        return countAddTo(adapter) > 0;
    }

    /**
     * Return whether this item can be subtracted from the adapter.
     */
    public boolean canTakeFrom(TradeAdapter adapter) {
        return countTakeFrom(adapter) > 0;
    }

    public abstract int countAddTo(TradeAdapter adapter);
    public abstract int countTakeFrom(TradeAdapter adapter);
    public abstract void addTo(TradeAdapter adapter);
    public abstract void takeFrom(TradeAdapter adapter);

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

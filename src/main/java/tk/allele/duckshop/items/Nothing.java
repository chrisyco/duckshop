package tk.allele.duckshop.items;

import tk.allele.duckshop.errors.InvalidSyntaxException;
import tk.allele.duckshop.trading.TradeAdapter;

import javax.annotation.Nullable;

/**
 * An empty item.
 * <p>
 *
 */
public class Nothing extends Item {
    public Nothing(@Nullable final String itemString) {
        super(itemString);
    }

    public Nothing() {
        this(null);
    }

    /**
     * Parse a Nothing instance from a string.
     *
     * @throws InvalidSyntaxException if the string cannot be parsed.
     */
    public static Item fromString(final String itemString)
            throws InvalidSyntaxException {
        if (nothingAliases.contains(itemString.toLowerCase())) {
            return new Nothing(itemString);
        } else {
            throw new InvalidSyntaxException();
        }
    }

    @Override
    public boolean canAddTo(TradeAdapter adapter) {
        return true;
    }

    @Override
    public boolean canTakeFrom(TradeAdapter adapter) {
        return true;
    }

    @Override
    public void addTo(TradeAdapter adapter) {
        // Do nothing!
    }

    @Override
    public void takeFrom(TradeAdapter adapter) {
        // Do nothing!
    }

    @Override
    public String toString() {
        return nothingAliases.iterator().next();
    }
}

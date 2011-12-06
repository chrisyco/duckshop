package tk.allele.duckshop.items;

import tk.allele.duckshop.errors.InvalidSyntaxException;
import tk.allele.duckshop.trading.TradeAdapter;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents money as a floating point value.
 */
public class Money extends Item {
    /**
     * The format for money: a dollar sign, then a floating point number.
     */
    private static final Pattern moneyPattern = Pattern.compile("\\$((\\d*\\.)?\\d+)");

    private final double amount;

    /**
     * Create a new Money instance.
     * <p>
     * The item string is not parsed; it is simply kept so it can be
     * later retrieved by {@link #getOriginalString()}.
     *
     * @throws IllegalArgumentException if the amount is zero.
     */
    public Money(final double amount, @Nullable final String itemString) {
        super(itemString);

        if (amount == 0.0) {
            throw new IllegalArgumentException("amount must != 0");
        }

        this.amount = amount;
    }

    /**
     * Create a new Money instance.
     *
     * @throws IllegalArgumentException if the amount is zero.
     */
    public Money(final double amount) {
        this(amount, null);
    }

    /**
     * Parse a Money instance from a String.
     *
     * @throws InvalidSyntaxException if the string cannot be parsed.
     */
    public static Item fromString(final String itemString)
            throws InvalidSyntaxException {
        Matcher matcher = moneyPattern.matcher(itemString);
        if (matcher.matches()) {
            double amount = Double.parseDouble(matcher.group(1));
            if (amount == 0.0) {
                return new Nothing(itemString);
            } else {
                return new Money(amount, itemString);
            }
        } else {
            throw new InvalidSyntaxException();
        }
    }

    @Override
    public boolean canAddTo(TradeAdapter adapter) {
        return adapter.canAddMoney(this);
    }

    @Override
    public boolean canTakeFrom(TradeAdapter adapter) {
        return adapter.canSubtractMoney(this);
    }

    @Override
    public void addTo(TradeAdapter adapter) {
        adapter.addMoney(this);
    }

    @Override
    public void takeFrom(TradeAdapter adapter) {
        adapter.subtractMoney(this);
    }

    /**
     * Get the amount of money.
     */
    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Money) {
            Money other = (Money) obj;
            return (this.amount == other.amount);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int) (amount * 100);
    }

    @Override
    public String toString() {
        return "$" + amount;
    }
}

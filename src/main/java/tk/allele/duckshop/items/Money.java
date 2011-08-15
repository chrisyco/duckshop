package tk.allele.duckshop.items;

import tk.allele.duckshop.errors.InvalidSyntaxException;

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

    /**
     * The set of words that mean "nothing".
     * <p>
     * Overkill? I think not!
     */
    private static final Set<String> nothingAliases = new HashSet<String>(Arrays.asList("nothing", "free"));

    private final double amount;

    /**
     * Create a new Money instance.
     * <p>
     * The item string is not parsed; it is simply kept so it can be
     * later retrieved by {@link #getOriginalString()}.
     */
    public Money(final double amount, final String itemString) {
        super(itemString);
        this.amount = amount;
    }

    /**
     * Create a new Money instance.
     */
    public Money(final double amount) {
        super();
        this.amount = amount;
    }

    /**
     * Parse a Money instance from a String.
     *
     * @throws InvalidSyntaxException if the string cannot be parsed.
     */
    public static Money fromString(final String itemString)
      throws InvalidSyntaxException {
        if(nothingAliases.contains(itemString.toLowerCase())) {
            return new Money(0.0);
        } else {
            Matcher matcher = moneyPattern.matcher(itemString);
            if(matcher.matches()) {
                double amount = Double.parseDouble(matcher.group(1));
                return new Money(amount);
            } else {
                throw new InvalidSyntaxException();
            }
        }
    }

    /**
     * Get the amount of money.
     */
    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Money) {
            Money other = (Money)obj;
            return (this.amount == other.amount);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int)(amount * 100);
    }

    @Override
    public String toString() {
        if(amount == 0.0) {
            return "nothing";
        } else {
            return "$" + amount;
        }
    }
}

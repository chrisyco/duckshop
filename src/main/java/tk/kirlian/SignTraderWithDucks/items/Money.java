package tk.kirlian.SignTraderWithDucks.items;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import tk.kirlian.SignTraderWithDucks.*;
import tk.kirlian.SignTraderWithDucks.errors.*;

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
    private static final Set<String> nothingAliases = new HashSet<String>(Arrays.asList(new String[] {"nothing", "free"}));

    private double amount;

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
    public String toString() {
        if(amount == 0.0) {
            return "nothing";
        } else {
            return "$" + amount;
        }
    }
}

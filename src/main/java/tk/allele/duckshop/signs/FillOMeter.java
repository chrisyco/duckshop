package tk.allele.duckshop.signs;

import org.bukkit.ChatColor;
import tk.allele.duckshop.items.Item;
import tk.allele.duckshop.trading.TradeAdapter;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * The fill-o-meter: the status line on a TradingSign.
 * <p>
 * The line consists of multiple plus ({@code +}) signs which indicate
 * how many transactions can occur before the supply of items runs out.
 * For example, three symbols ({@code +++}) means there is enough for
 * three transactions, while eight symbols ({@code ++++++++}) means
 * there is enough for eight.
 */
public class FillOMeter {
    private FillOMeter() {}

    /**
     * The maximum length of the status line. Anything longer than this
     * will be truncated to fit.
     */
    private static final int STATUS_LENGTH = 8;

    /**
     * Something went wrong getting the information, e.g. there is no
     * connected chest.
     */
    private static final int INVALID_ADAPTER = -1;

    /**
     * Generate a line of text that represents the number of items
     * left in the chest.
     */
    public static String generateStatusLine(@Nullable TradeAdapter seller, Item sellerToBuyer) {
        final int transLeft = (seller != null ? sellerToBuyer.countTakeFrom(seller) : INVALID_ADAPTER);
        return getChatColor(transLeft) + getMessage(transLeft);
    }

    /**
     * Get a suitable color for a status line, given the number of
     * possible transactions left.
     */
    private static ChatColor getChatColor(int transLeft) {
        switch (transLeft) {
            case INVALID_ADAPTER:
                return ChatColor.DARK_PURPLE;
            case 0:
            case 1:
            case 2:
                return ChatColor.DARK_RED;
            case 3:
            case 4:
            case 5:
                return ChatColor.YELLOW;
        }

        if (transLeft <= STATUS_LENGTH) {
            return ChatColor.GREEN;
        } else {
            // I have so much stuff, I can't even fit it on one line!
            return ChatColor.AQUA;
        }
    }

    private static String getMessage(int transLeft) {
        if (transLeft == INVALID_ADAPTER) {
            return "<invalid>";
        } else if (transLeft == 0) {
            return "(empty)";
        } else if (transLeft > STATUS_LENGTH) {
            // Any more than the maximum is truncated
            return getMessage(STATUS_LENGTH);
        } else {
            // Otherwise, fill it with plus signs
            char[] message = new char[transLeft];
            Arrays.fill(message, '+');
            return new String(message);
        }
    }
}

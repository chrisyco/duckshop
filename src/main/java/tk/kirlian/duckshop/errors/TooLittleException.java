package tk.kirlian.duckshop.errors;

import org.bukkit.entity.Player;
import tk.kirlian.duckshop.items.Item;

/**
 * Thrown to indicate a player does not have enough of something, usually money or items.
 */
public class TooLittleException extends TradingException {
    public TooLittleException(Player player, Item item) {
        super(player, item);
    }
}

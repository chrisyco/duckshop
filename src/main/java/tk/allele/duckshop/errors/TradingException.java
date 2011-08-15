package tk.allele.duckshop.errors;

import org.bukkit.entity.Player;
import tk.allele.duckshop.items.Item;

/**
 * Thrown to indicate a trade could not be completed.
 */
public abstract class TradingException extends DuckShopException {
    final Player player;
    final Item item;

    public TradingException(Player player, Item item) {
        this.player = player;
        this.item = item;
    }

    /**
     * Get the player who could not complete the trade.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the item which could not be traded.
     */
    public Item getItem() {
        return item;
    }
}

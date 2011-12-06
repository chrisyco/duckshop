package tk.allele.duckshop.trading;

import tk.allele.duckshop.DuckShop;
import tk.allele.duckshop.items.Item;
import tk.allele.duckshop.items.Money;
import tk.allele.duckshop.items.TangibleItem;

/**
 * An object representing something which can trade -- whether it be a
 * seller, a buyer, or a global sign.
 */
public abstract class TradeAdapter {
    protected final DuckShop plugin;

    /**
     * Create a new TradeAdapter instance.
     */
    protected TradeAdapter(DuckShop plugin) {
        this.plugin = plugin;
    }

    /**
     * Despite what some people think, it's actually possible to have
     * too much money.
     */
    public abstract boolean canAddMoney(Money money);

    /**
     * Figure out whether the target of this adapter has enough money or not.
     */
    public abstract boolean canSubtractMoney(Money money);

    /**
     * Return true if the inventory has enough space, otherwise false.
     */
    public abstract boolean canAddTangibleItem(TangibleItem tangibleItem);

    /**
     * Is there enough?
     */
    public abstract boolean canSubtractTangibleItem(TangibleItem tangibleItem);

    public abstract void addMoney(Money money)
            throws IllegalArgumentException;

    public abstract void subtractMoney(Money money)
            throws IllegalArgumentException;

    public abstract void addTangibleItem(TangibleItem tangibleItem)
            throws IllegalArgumentException;

    public abstract void subtractTangibleItem(TangibleItem tangibleItem)
            throws IllegalArgumentException;
}

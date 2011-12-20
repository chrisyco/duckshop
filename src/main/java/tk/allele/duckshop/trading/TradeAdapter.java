package tk.allele.duckshop.trading;

import tk.allele.duckshop.DuckShop;
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
     * Return the number of times the amount of money can be added to this adapter.
     * 
     * @return the number, or {@link Integer#MAX_VALUE} if infinite.
     */
    public abstract int countAddMoney(Money money);

    /**
     * Return the number of times the amount of money can be subtracted from this adapter.
     *
     * @return the number, or {@link Integer#MAX_VALUE} if infinite.
     */
    public abstract int countSubtractMoney(Money money);

    /**
     * Return the number of times the item can be added to this adapter.
     *
     * @return the number, or {@link Integer#MAX_VALUE} if infinite.
     */
    public abstract int countAddTangibleItem(TangibleItem tangibleItem);

    /**
     * Return the number of times the item can be subtracted from this adapter.
     *
     * @return the number, or {@link Integer#MAX_VALUE} if infinite.
     */
    public abstract int countSubtractTangibleItem(TangibleItem tangibleItem);

    public abstract void addMoney(Money money)
            throws IllegalArgumentException;

    public abstract void subtractMoney(Money money)
            throws IllegalArgumentException;

    public abstract void addTangibleItem(TangibleItem tangibleItem)
            throws IllegalArgumentException;

    public abstract void subtractTangibleItem(TangibleItem tangibleItem)
            throws IllegalArgumentException;
}

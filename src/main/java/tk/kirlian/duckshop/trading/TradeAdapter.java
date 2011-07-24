package tk.kirlian.duckshop.trading;

import tk.kirlian.duckshop.DuckShop;
import tk.kirlian.duckshop.items.Item;
import tk.kirlian.duckshop.items.Money;
import tk.kirlian.duckshop.items.TangibleItem;

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

    public final boolean canAddItem(Item item) {
        if(item instanceof Money) {
            return canAddMoney((Money)item);
        } else if(item instanceof TangibleItem) {
            return canAddTangibleItem((TangibleItem)item);
        } else {
            throw new IllegalArgumentException("unknown Item type");
        }
    }

    public final boolean canSubtractItem(Item item) {
        if(item instanceof Money) {
            return canSubtractMoney((Money)item);
        } else if(item instanceof TangibleItem) {
            return canSubtractTangibleItem((TangibleItem)item);
        } else {
            throw new IllegalArgumentException("unknown Item type");
        }
    }

    public final void addItem(Item item) {
        if(item instanceof Money) {
            addMoney((Money)item);
        } else if(item instanceof TangibleItem) {
            addTangibleItem((TangibleItem)item);
        } else {
            throw new IllegalArgumentException("unknown Item type");
        }
    }

    public final void subtractItem(Item item) {
        if(item instanceof Money) {
            subtractMoney((Money)item);
        } else if(item instanceof TangibleItem) {
            subtractTangibleItem((TangibleItem)item);
        } else {
            throw new IllegalArgumentException("unknown Item type");
        }
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

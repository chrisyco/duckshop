package tk.kirlian.SignTraderWithDucks.trading;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import tk.kirlian.SignTraderWithDucks.items.Item;

/**
 * An object representing something which can trade -- whether it be a
 * seller, a buyer, or a global sign.
 */
public abstract class TradeAdapter {
    protected SignTraderPlugin plugin;

    /**
     * Create a new TradeAdapter instance.
     */
    public TradeAdapter(SignTraderPlugin plugin) {
        this.plugin = plugin;
    }

    public final boolean canAddItem(Item item) {
        if(item.isMoney()) {
            return canAddMoney(item.getAmount());
        } else {
            return canAddTangibleItem(item.getItemId(), item.getAmount(), item.getDamage());
        }
    }

    public final boolean canSubtractItem(Item item) {
        if(item.isMoney()) {
            return canSubtractMoney(item.getAmount());
        } else {
            return canSubtractTangibleItem(item.getItemId(), item.getAmount(), item.getDamage());
        }
    }

    public final void addItem(Item item)
      throws IllegalArgumentException {
        if(item.isMoney()) {
            addMoney(item.getAmount());
        } else {
            addTangibleItem(item.getItemId(), item.getAmount(), item.getDamage());
        }
    }

    public final void subtractItem(Item item)
      throws IllegalArgumentException {
        if(item.isMoney()) {
            subtractMoney(item.getAmount());
        } else {
            subtractTangibleItem(item.getItemId(), item.getAmount(), item.getDamage());
        }
    }

    /**
     * Despite what some people think, it's actually possible to have
     * too much money.
     */
    public abstract boolean canAddMoney(double amount);

    /**
     * Figure out whether the target of this adapter has enough money or not.
     */
    public abstract boolean canSubtractMoney(double amount);

    /**
     * Return true if the inventory has enough space, otherwise false.
     */
    public abstract boolean canAddTangibleItem(int itemId, int amount, short damage);

    /**
     * Is there enough?
     */
    public abstract boolean canSubtractTangibleItem(int itemId, int amount, short damage);

    public abstract void addMoney(double amount)
        throws IllegalArgumentException;
    public abstract void subtractMoney(double amount)
        throws IllegalArgumentException;
    public abstract void addTangibleItem(int itemId, int amount, short damage)
        throws IllegalArgumentException;
    public abstract void subtractTangibleItem(int itemId, int amount, short damage)
        throws IllegalArgumentException;
}

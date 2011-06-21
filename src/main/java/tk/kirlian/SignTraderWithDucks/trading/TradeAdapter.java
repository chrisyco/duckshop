package tk.kirlian.SignTraderWithDucks.trading;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import tk.kirlian.SignTraderWithDucks.signs.SignItem;

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

    public final boolean canAddSignItem(SignItem item) {
        if(item.isMoney()) {
            return canAddMoney(item.getAmount());
        } else {
            return canAddItem(item.getItemId(), item.getAmount(), item.getDamage());
        }
    }

    public final boolean canSubtractSignItem(SignItem item) {
        if(item.isMoney()) {
            return canSubtractMoney(item.getAmount());
        } else {
            return canSubtractItem(item.getItemId(), item.getAmount(), item.getDamage());
        }
    }

    public final void addSignItem(SignItem item)
      throws IllegalArgumentException {
        if(item.isMoney()) {
            addMoney(item.getAmount());
        } else {
            addItem(item.getItemId(), item.getAmount(), item.getDamage());
        }
    }

    public final void subtractSignItem(SignItem item)
      throws IllegalArgumentException {
        if(item.isMoney()) {
            subtractMoney(item.getAmount());
        } else {
            subtractItem(item.getItemId(), item.getAmount(), item.getDamage());
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
    public abstract boolean canAddItem(int itemId, int amount, short damage);

    /**
     * Is there enough?
     */
    public abstract boolean canSubtractItem(int itemId, int amount, short damage);

    public abstract void addMoney(double amount)
        throws IllegalArgumentException;
    public abstract void subtractMoney(double amount)
        throws IllegalArgumentException;
    public abstract void addItem(int itemId, int amount, short damage)
        throws IllegalArgumentException;
    public abstract void subtractItem(int itemId, int amount, short damage)
        throws IllegalArgumentException;
}

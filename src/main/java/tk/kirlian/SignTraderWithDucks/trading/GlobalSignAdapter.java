package tk.kirlian.SignTraderWithDucks.trading;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import tk.kirlian.SignTraderWithDucks.items.Item;

/**
 * Global trade signs have unlimited supplies of everything. Its
 * implementation is so simple it's funny.
 * @see TradeAdapter
 */
public class GlobalSignAdapter extends TradeAdapter {
    /**
     * Creates a new GlobalSignAdapter instance.
     */
    public GlobalSignAdapter(SignTraderPlugin plugin) {
        super(plugin);
    }
    public boolean canAddMoney(double amount) {
        return true;
    }
    public boolean canSubtractMoney(double amount) {
        return true;
    }
    public boolean canAddTangibleItem(int itemId, int amount, short damage) {
        return true;
    }
    public boolean canSubtractTangibleItem(int itemId, int amount, short damage) {
        return true;
    }
    public void addMoney(double amount) {}
    public void subtractMoney(double amount) {}
    public void addTangibleItem(int itemId, int amount, short damage) {}
    public void subtractTangibleItem(int itemId, int amount, short damage) {}
}

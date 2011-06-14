package tk.kirlian.SignTraderWithDucks.trading;

import tk.kirlian.SignTraderWithDucks.signs.SignItem;

/**
 * Global trade signs have unlimited supplies of everything. Its
 * implementation is so simple it's funny.
 * @see TradeAdapter
 */
public class GlobalSignAdapter extends TradeAdapter {
    public boolean canAddMoney(double amount) {
        return true;
    }
    public boolean canSubtractMoney(double amount) {
        return true;
    }
    public boolean canAddItem(int itemId, int amount, short damage) {
        return true;
    }
    public boolean canSubtractItem(int itemId, int amount, short damage) {
        return true;
    }
    public void addMoney(double amount) {}
    public void subtractMoney(double amount) {}
    public void addItem(int itemId, int amount, short damage) {}
    public void subtractItem(int itemId, int amount, short damage) {}
}

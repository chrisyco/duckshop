package tk.kirlian.duckshop.trading;

import tk.kirlian.duckshop.DuckShop;
import tk.kirlian.duckshop.items.*;

/**
 * Global trade signs have unlimited supplies of everything. Its
 * implementation is so simple it's funny.
 * @see TradeAdapter
 */
public class GlobalSignAdapter extends TradeAdapter {
    /**
     * Creates a new GlobalSignAdapter instance.
     */
    public GlobalSignAdapter(DuckShop plugin) {
        super(plugin);
    }
    public boolean canAddMoney(Money money) {
        return true;
    }
    public boolean canSubtractMoney(Money money) {
        return true;
    }
    public boolean canAddTangibleItem(TangibleItem tangibleItem) {
        return true;
    }
    public boolean canSubtractTangibleItem(TangibleItem tangibleItem) {
        return true;
    }
    public void addMoney(Money money) {}
    public void subtractMoney(Money money) {}
    public void addTangibleItem(TangibleItem tangibleItem) {}
    public void subtractTangibleItem(TangibleItem tangibleItem) {}
}

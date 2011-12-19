package tk.allele.duckshop.trading;

import tk.allele.duckshop.DuckShop;
import tk.allele.duckshop.items.Money;
import tk.allele.duckshop.items.TangibleItem;

/**
 * Global trade signs have unlimited supplies of everything. Its
 * implementation is so simple it's funny.
 *
 * @see TradeAdapter
 */
public class GlobalSignAdapter extends TradeAdapter {
    /**
     * Creates a new GlobalSignAdapter instance.
     */
    public GlobalSignAdapter(DuckShop plugin) {
        super(plugin);
    }

    public int countAddMoney(Money money) {
        return Integer.MAX_VALUE;
    }

    public int countSubtractMoney(Money money) {
        return Integer.MAX_VALUE;
    }

    public int countAddTangibleItem(TangibleItem tangibleItem) {
        return Integer.MAX_VALUE;
    }

    public int countSubtractTangibleItem(TangibleItem tangibleItem) {
        return Integer.MAX_VALUE;
    }

    public void addMoney(Money money) {
    }

    public void subtractMoney(Money money) {
    }

    public void addTangibleItem(TangibleItem tangibleItem) {
    }

    public void subtractTangibleItem(TangibleItem tangibleItem) {
    }
}

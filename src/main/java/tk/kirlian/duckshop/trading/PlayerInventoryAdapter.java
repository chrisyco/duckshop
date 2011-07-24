package tk.kirlian.duckshop.trading;

import tk.kirlian.duckshop.DuckShop;
import tk.kirlian.duckshop.items.TangibleItem;

/**
 * Extends the InventoryAdapter to support players.
 * @see InventoryAdapter
 */
public class PlayerInventoryAdapter extends InventoryAdapter {
    public PlayerInventoryAdapter(DuckShop plugin, String playerName) {
        super(plugin);
        setPlayerName(playerName);
        setInventory(getPlayer().getInventory());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void addTangibleItem(TangibleItem tangibleItem) throws IllegalArgumentException {
        super.addTangibleItem(tangibleItem);
        getPlayer().updateInventory();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void subtractTangibleItem(TangibleItem tangibleItem) throws IllegalArgumentException {
        super.subtractTangibleItem(tangibleItem);
        getPlayer().updateInventory();
    }
}

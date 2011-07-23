package tk.kirlian.duckshop.trading;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tk.kirlian.duckshop.DuckShop;
import tk.kirlian.duckshop.items.*;

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
    public void addTangibleItem(TangibleItem tangibleItem) throws IllegalArgumentException {
        super.addTangibleItem(tangibleItem);
        getPlayer().updateInventory();
    }

    @Override
    public void subtractTangibleItem(TangibleItem tangibleItem) throws IllegalArgumentException {
        super.subtractTangibleItem(tangibleItem);
        getPlayer().updateInventory();
    }
}

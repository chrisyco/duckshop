package tk.kirlian.SignTraderWithDucks.trading;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;

/**
 * Extends the InventoryAdapter to support players.
 * @see InventoryAdapter
 */
public class PlayerInventoryAdapter extends InventoryAdapter {
    public PlayerInventoryAdapter(SignTraderPlugin plugin, Player player) {
        super(plugin);
        setPlayer(player);
        setInventory(player.getInventory());
    }

    @Override
    public void addTangibleItem(int itemId, int amount, short damage) throws IllegalArgumentException {
        super.addTangibleItem(itemId, amount, damage);
        player.updateInventory();
    }

    @Override
    public void subtractTangibleItem(int itemId, int amount, short damage) throws IllegalArgumentException {
        super.subtractTangibleItem(itemId, amount, damage);
        player.updateInventory();
    }
}

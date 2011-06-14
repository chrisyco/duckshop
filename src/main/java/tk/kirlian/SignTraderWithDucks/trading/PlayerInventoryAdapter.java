package tk.kirlian.SignTraderWithDucks.trading;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tk.kirlian.SignTraderWithDucks.signs.SignItem;

/**
 * Extends the InventoryAdapter to support players.
 * @see InventoryAdapter
 */
public class PlayerInventoryAdapter extends InventoryAdapter {
    public PlayerInventoryAdapter(Player player) {
        setPlayer(player);
        setInventory(player.getInventory());
    }

    @Override
    public void addItem(int itemId, int amount, short damage) throws IllegalArgumentException {
        super.addItem(itemId, amount, damage);
        player.updateInventory();
    }

    @Override
    public void subtractItem(int itemId, int amount, short damage) throws IllegalArgumentException {
        super.subtractItem(itemId, amount, damage);
        player.updateInventory();
    }
}

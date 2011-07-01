package tk.kirlian.SignTraderWithDucks.trading;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import tk.kirlian.SignTraderWithDucks.items.*;

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
    public void addTangibleItem(TangibleItem tangibleItem) throws IllegalArgumentException {
        super.addTangibleItem(tangibleItem);
        player.updateInventory();
    }

    @Override
    public void subtractTangibleItem(TangibleItem tangibleItem) throws IllegalArgumentException {
        super.subtractTangibleItem(tangibleItem);
        player.updateInventory();
    }
}

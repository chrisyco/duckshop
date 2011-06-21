package tk.kirlian.SignTraderWithDucks.trading;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import tk.kirlian.SignTraderWithDucks.signs.SignItem;
import tk.kirlian.SignTraderWithDucks.errors.*;

/**
 * Extends the InventoryAdapter to support chests.
 * @see InventoryAdapter
 */
public class ChestInventoryAdapter extends InventoryAdapter {
    public ChestInventoryAdapter(SignTraderPlugin plugin, Player owner, Chest chest) {
        super(plugin);
        setPlayer(owner);
        setInventory(chest.getInventory());
    }
    public ChestInventoryAdapter(SignTraderPlugin plugin, Player owner, Location chestLocation)
      throws InvalidChestException {
        super(plugin);
        BlockState state = chestLocation.getBlock().getState();
        if(state instanceof Chest) {
            setPlayer(owner);
            setInventory(((Chest)state).getInventory());
        } else {
            throw new InvalidChestException();
        }
    }
}

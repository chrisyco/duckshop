package tk.kirlian.duckshop.trading;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import tk.kirlian.duckshop.DuckShop;
import tk.kirlian.duckshop.errors.ChestProtectionException;
import tk.kirlian.duckshop.errors.InvalidChestException;

/**
 * Extends the InventoryAdapter to support chests.
 * @see InventoryAdapter
 */
@SuppressWarnings({"JavaDoc"})
public class ChestInventoryAdapter extends InventoryAdapter {
    private void initialize(String ownerName, Chest chest)
      throws ChestProtectionException {
        setPlayerName(ownerName);
        setInventory(chest.getInventory());

        if(!plugin.protection.canAccess(ownerName, chest.getBlock())) {
            throw new ChestProtectionException();
        }
    }

    /**
     * Create an instance of this adapter.
     *
     * @throws ChestProtectionException if the chest is protected
     */
    public ChestInventoryAdapter(DuckShop plugin, String ownerName, Chest chest)
      throws ChestProtectionException {
        super(plugin);
        initialize(ownerName, chest);
    }

    /**
     * Create an instance of this adapter.
     *
     * @throws InvalidChestException if the location doesn't point to a chest
     * @throws ChestProtectionException if the chest is protected
     */
    public ChestInventoryAdapter(DuckShop plugin, String ownerName, Location chestLocation)
      throws InvalidChestException, ChestProtectionException {
        super(plugin);
        BlockState state = chestLocation.getBlock().getState();
        if(state instanceof Chest) {
            initialize(ownerName, (Chest)state);
        } else {
            throw new InvalidChestException();
        }
    }
}

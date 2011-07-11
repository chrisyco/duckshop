package tk.kirlian.DuckShop.trading;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.block.BlockFace;

import tk.kirlian.DuckShop.DuckShop;
import tk.kirlian.DuckShop.items.Item;
import tk.kirlian.DuckShop.errors.*;

/**
 * Extends the InventoryAdapter to support chests.
 * @see InventoryAdapter
 */
public class ChestInventoryAdapter extends InventoryAdapter {
    private void initialize(Player owner, Chest chest)
      throws ChestProtectionException {
        // Test if the owner of the sign can access the chest
        PlayerInteractEvent event = new PlayerInteractEvent(owner, Action.RIGHT_CLICK_BLOCK, null, chest.getBlock(), BlockFace.SELF);
        plugin.getServer().getPluginManager().callEvent(event);
        // A chest protection plugin would usually cancel a right-click
        // event on a protected chest
        if(!event.isCancelled()) {
            setPlayer(owner);
            setInventory(chest.getInventory());
        } else {
            throw new ChestProtectionException();
        }
    }

    /**
     * Create an instance of this adapter.
     *
     * @throws InvalidChestException if the location doesn't point to a chest
     * @throws ChestProtectionException if the chest is protected
     */
    public ChestInventoryAdapter(DuckShop plugin, Player owner, Chest chest)
      throws ChestProtectionException {
        super(plugin);
        initialize(owner, chest);
    }

    /**
     * Create an instance of this adapter.
     *
     * @throws InvalidChestException if the location doesn't point to a chest
     * @throws ChestProtectionException if the chest is protected
     */
    public ChestInventoryAdapter(DuckShop plugin, Player owner, Location chestLocation)
      throws InvalidChestException, ChestProtectionException {
        super(plugin);
        BlockState state = chestLocation.getBlock().getState();
        if(state instanceof Chest) {
            initialize(owner, (Chest)state);
        } else {
            throw new InvalidChestException();
        }
    }
}

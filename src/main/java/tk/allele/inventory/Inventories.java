package tk.allele.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Miscellaneous inventory utility methods that aren't implemented in Bukkit.
 */
public class Inventories {
    private Inventories() {}

    /**
     * Remove an item from the inventory, taking into account durability
     * values.
     *
     * @return The amount left over.
     */
    public static int removeItem(Inventory inventory, ItemStack delItem) {
        int toDelete = delItem.getAmount();

        for (int i = 0; i != inventory.getSize(); ++i) {
            ItemStack invItem = inventory.getItem(i);

            // Skip if the slot is empty
            if (invItem == null) {
                continue;
            }

            // Check if this is the right kind of item
            if (invItem.getTypeId() == delItem.getTypeId() &&
                    invItem.getDurability() == delItem.getDurability()) {
                int amount = invItem.getAmount();

                if (amount <= toDelete) {
                    toDelete -= amount;
                    // We've used up the whole stack, so clear it
                    inventory.clear(i);
                } else {
                    // Split the stack and store
                    invItem.setAmount(amount - toDelete);
                    inventory.setItem(i, invItem);
                    toDelete = 0;
                }
            }

            // Quit when done
            if (toDelete == 0) {
                break;
            }
        }
        return toDelete;
    }
}

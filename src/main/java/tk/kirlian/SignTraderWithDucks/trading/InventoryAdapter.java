package tk.kirlian.SignTraderWithDucks.trading;

import com.nijikokun.register.payment.Method.MethodAccount;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import java.util.Map;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import tk.kirlian.SignTraderWithDucks.items.Item;

/**
 * TradeAdapter that works with things that have inventories -- such as
 * players and chests.
 * @see TradeAdapter
 */
public abstract class InventoryAdapter extends TradeAdapter {
    /**
     * The Inventory to be used by this adapter.
     */
    protected Inventory inventory;

    /**
     * The Player that receives and pays any money required by this adapter.
     */
    protected Player player;

    /**
     * The Account associated with the Player.
     */
    protected MethodAccount account;

    /**
     * Creates a new InventoryAdapter instance.
     */
    public InventoryAdapter(SignTraderPlugin plugin) {
        super(plugin);
    }

    /**
     * Get the Inventory which is used by this adapter.
     * @see Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Set the Inventory to be used by this adapter.
     * @see Inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Get the Player which is used by this adapter.
     * @see Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the Player to be used by this adapter.
     * @see Player
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.account = plugin.economyMethod.getAccount(player.getName());
    }

    @Override
    public boolean canAddMoney(double amount) {
        return true;
    }

    @Override
    public boolean canSubtractMoney(double amount) {
        return account.hasEnough(amount);
    }

    @Override
    public void addMoney(double amount) throws IllegalArgumentException {
        if(!canAddMoney(amount)) {
            throw new IllegalArgumentException("Too much money");
        } else {
            account.add(amount);
        }
    }

    @Override
    public void subtractMoney(double amount) throws IllegalArgumentException {
        if(!canSubtractMoney(amount)) {
            throw new IllegalArgumentException("Not enough money");
        } else {
            account.subtract(amount);
        }
    }

    @Override
    public boolean canAddTangibleItem(int itemId, int amount, short damage) {
        int total = 0;
        int size = inventory.getSize();
        int maxStackSize = Material.getMaterial(itemId).getMaxStackSize();
        ItemStack item;
        for(int i = 0; i < size; ++i) {
            item = inventory.getItem(i);
            if(item.getTypeId() == itemId && item.getDurability() == damage) {
                total += maxStackSize - item.getAmount();
            } else if(item.getType() == Material.AIR) {
                total += maxStackSize;
            }
            if(total >= amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canSubtractTangibleItem(int itemId, int amount, short damage) {
        int total = 0;
        int size = inventory.getSize();
        ItemStack item;
        for(int i = 0; i < size; ++i) {
            item = inventory.getItem(i);
            if(item.getTypeId() == itemId && item.getDurability() == damage) {
                total += item.getAmount();
                if(total >= amount) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void addTangibleItem(int itemId, int amount, short damage) throws IllegalArgumentException {
        if(!canAddTangibleItem(itemId, amount, damage)) {
            throw new IllegalArgumentException("Inventory full");
        } else {
            int leftover = amount;
            int size = inventory.getSize();
            int maxStackSize = Material.getMaterial(itemId).getMaxStackSize();
            ItemStack item;
            for(int i = 0; i < size; ++i) {
                item = inventory.getItem(i);
                if(item.getTypeId() == itemId && item.getDurability() == damage && item.getAmount() < maxStackSize) {
                    if(item.getAmount() + leftover >= maxStackSize) {
                        leftover -= maxStackSize - item.getAmount();
                        item.setAmount(maxStackSize);
                    } else {
                        item.setAmount(item.getAmount() + leftover);
                        break;
                    }
                } else if(item.getType() == Material.AIR) {
                    if(leftover > maxStackSize) {
                        leftover -= maxStackSize;
                        inventory.setItem(i, new ItemStack(itemId, maxStackSize, damage));
                    } else {
                        inventory.setItem(i, new ItemStack(itemId, leftover, damage));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void subtractTangibleItem(int itemId, int amount, short damage) throws IllegalArgumentException {
        if(!canSubtractTangibleItem(itemId, amount, damage)) {
            throw new IllegalArgumentException("Inventory empty");
        } else {
            Map<Integer, ItemStack> leftover = inventory.removeItem(new ItemStack(itemId, amount));
            if(leftover.size() > 0) {
                System.err.println(leftover.size() + " items disappeared!");
            }
        }
    }
}

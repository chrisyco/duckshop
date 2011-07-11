package tk.kirlian.DuckShop.trading;

import com.nijikokun.register.payment.Method.MethodAccount;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import java.util.Map;

import tk.kirlian.DuckShop.DuckShop;
import tk.kirlian.DuckShop.items.*;
import tk.kirlian.util.DummyEconomy;

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
    public InventoryAdapter(DuckShop plugin) {
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
    public boolean canAddMoney(Money money) {
        // This is absolutely disgusting
        return (money.getAmount() == 0.0 || !(account instanceof DummyEconomy.DummyAccount));
    }

    @Override
    public boolean canSubtractMoney(Money money) {
        return account.hasEnough(money.getAmount());
    }

    @Override
    public void addMoney(Money money) throws IllegalArgumentException {
        if(!canAddMoney(money)) {
            throw new IllegalArgumentException("Too much money");
        } else {
            account.add(money.getAmount());
        }
    }

    @Override
    public void subtractMoney(Money money) throws IllegalArgumentException {
        if(!canSubtractMoney(money)) {
            throw new IllegalArgumentException("Not enough money");
        } else {
            account.subtract(money.getAmount());
        }
    }

    @Override
    public boolean canAddTangibleItem(TangibleItem addItem) {
        int total = 0;
        int size = inventory.getSize();
        int maxStackSize = Material.getMaterial(addItem.getItemId()).getMaxStackSize();
        ItemStack invItem;
        for(int i = 0; i < size; ++i) {
            invItem = inventory.getItem(i);
            if(invItem.getTypeId() == addItem.getItemId() && invItem.getDurability() == addItem.getDamage()) {
                total += maxStackSize - invItem.getAmount();
            } else if(invItem.getType() == Material.AIR) {
                total += maxStackSize;
            }
            if(total >= addItem.getAmount()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canSubtractTangibleItem(TangibleItem subItem) {
        int total = 0;
        int size = inventory.getSize();
        ItemStack invItem;
        for(int i = 0; i < size; ++i) {
            invItem = inventory.getItem(i);
            if(invItem.getTypeId() == subItem.getItemId() && invItem.getDurability() == subItem.getDamage()) {
                total += invItem.getAmount();
                if(total >= subItem.getAmount()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void addTangibleItem(TangibleItem addItem) throws IllegalArgumentException {
        if(!canAddTangibleItem(addItem)) {
            throw new IllegalArgumentException("Inventory full");
        } else {
            Map<Integer, ItemStack> leftover = inventory.addItem(addItem.toItemStacks());
            if(leftover.size() > 0) {
                System.err.println(leftover.size() + " items disappeared!");
            }
        }
    }

    @Override
    public void subtractTangibleItem(TangibleItem subItem) throws IllegalArgumentException {
        if(!canSubtractTangibleItem(subItem)) {
            throw new IllegalArgumentException("Inventory empty");
        } else {
            Map<Integer, ItemStack> leftover = inventory.removeItem(subItem.toItemStacks());
            if(leftover.size() > 0) {
                System.err.println(leftover.size() + " items disappeared!");
            }
        }
    }
}

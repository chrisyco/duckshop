package tk.allele.itemdb;

import org.bukkit.inventory.ItemStack;

/**
 * Specifies an item using its type ID and damage value.
 */
public class ItemSpecifier {
    final int typeId;
    final short durability;

    public ItemSpecifier(int typeId, short durability) {
        this.typeId = typeId;
        this.durability = durability;
    }

    public int getTypeId() {
        return typeId;
    }

    public short getDurability() {
        return durability;
    }

    public ItemStack toItemStack(int amount) {
        return new ItemStack(typeId, amount, durability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSpecifier that = (ItemSpecifier) o;

        if (typeId != that.typeId) return false;
        if (durability != that.durability) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId;
        result = 31 * result + (int) durability;
        return result;
    }

    @Override
    public String toString() {
       return typeId + ":" + durability;
    }
}

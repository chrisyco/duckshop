package tk.allele.itemdb;

import org.bukkit.inventory.ItemStack;

/**
 * Specifies an item using its type ID and damage value.
 */
public class ItemSpecifier implements Comparable<ItemSpecifier> {
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
    public boolean equals(Object thatObject) {
        if (this == thatObject) return true;
        if (thatObject == null || getClass() != thatObject.getClass()) return false;

        ItemSpecifier that = (ItemSpecifier) thatObject;

        if (typeId != that.typeId) return false;
        if (durability != that.durability) return false;

        return true;
    }

    @Override
    public int compareTo(ItemSpecifier that) {
        if(this == that) return 0;

        int dTypeId = this.typeId - that.typeId;
        if(dTypeId != 0) return dTypeId;

        int dDurability = this.durability - that.durability;
        if(dDurability != 0) return dDurability;

        return 0;
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

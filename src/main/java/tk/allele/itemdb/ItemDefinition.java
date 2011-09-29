package tk.allele.itemdb;

import tk.allele.util.StringLengthComparator;

import java.util.*;

public class ItemDefinition {
    final int id;
    final short durability;
    final String canonicalName;
    final SortedSet<String> aliases;

    public ItemDefinition(int id, short durability, String canonicalName, Collection<String> aliases) {
        this.id = id;
        this.durability = durability;
        this.canonicalName = canonicalName;

        this.aliases = new TreeSet<String>(StringLengthComparator.getInstance());
        this.aliases.addAll(aliases);
    }

    /**
     * Get an ItemSpecifier that represents this item.
     */
    public ItemSpecifier getKey() {
        return new ItemSpecifier(id, durability);
    }

    /**
     * Return a name for this item that has at most a certain number of characters.
     *
     * @param maxLength The maximum length (inclusive) of the returned name.
     * @return The name if available, otherwise null.
     */
    public String getNameOfLength(int maxLength) {
        // Canonical name always has priority
        if(canonicalName.length() <= maxLength) {
            return canonicalName;
        }

        String currentName = null;
        for(String name : aliases) {
            if(name.length() <= maxLength) {
                if(currentName != null && currentName.length() == maxLength) {
                    break;
                } else {
                    currentName = name;
                }
            } else {
                break;
            }
        }
        return currentName;
    }

    public int getId() { return id; }

    public short getDurability() { return durability; }

    public String getCanonicalName() { return canonicalName; }

    /**
     * Get the aliases for this item, sorted by length.
     */
    public SortedSet<String> getAliases() { return aliases; }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ItemDefinition) {
            ItemDefinition other = (ItemDefinition) o;
            return this.getKey().equals(other.getKey());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return id + ":" + durability + " = " + canonicalName;
    }
}

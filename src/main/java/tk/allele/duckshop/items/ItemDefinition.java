package tk.allele.duckshop.items;

import tk.allele.util.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ItemDefinition {
    private final Integer id;
    private final Short damage;
    private final String canonicalName;
    private final String shortName;
    private final Set<String> aliases;

    public ItemDefinition(Integer id, Short damage, String canonicalName, String shortName, Collection<String> aliases) {
        this.id = id;
        this.damage = damage;
        this.canonicalName = canonicalName;
        this.shortName = shortName;
        this.aliases = new HashSet<String>(aliases);
    }

    public Pair<Integer, Short> getKey() {
        return new Pair<Integer, Short>(id, damage);
    }

    public Integer getId() { return id; }

    public Short getDamage() { return damage; }

    public String getCanonicalName() { return canonicalName; }

    public String getShortName() { return shortName; }

    public Set<String> getAliases() { return aliases; }

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
        return id + "," + damage + ": " + canonicalName + " (" + shortName + ")";
    }
}

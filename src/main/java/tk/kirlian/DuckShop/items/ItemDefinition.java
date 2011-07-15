package tk.kirlian.DuckShop.items;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import tk.kirlian.util.Pair;

public class ItemDefinition {
    private Integer id;
    private Short damage;
    private String canonicalName;
    private String shortName;
    private Set<String> aliases;

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

    public String toString() {
        return id + "," + damage + ": " + canonicalName + " (" + shortName + ")";
    }
}

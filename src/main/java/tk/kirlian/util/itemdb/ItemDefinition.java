package tk.kirlian.util.itemdb;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

public class ItemDefinition {
    private int id;
    private String canonicalName;
    private String shortName;
    private Set<String> aliases;
    public ItemDefinition(int id, String canonicalName, String shortName, Collection<String> aliases) {
        this.id = id;
        this.canonicalName = canonicalName;
        this.shortName = shortName;
        this.aliases = new HashSet<String>(aliases);
    }
    public int getId() { return id; }
    public String getCanonicalName() { return canonicalName; }
    public String getShortName() { return shortName; }
    public Set<String> getAliases() { return aliases; }
    public String toString() {
        return id + ": " + canonicalName + " (" + shortName + ")";
    }
}

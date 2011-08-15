package tk.allele.duckshop.items;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import tk.allele.util.Pair;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A database of Minecraft items and their data values and names.
 * <p>
 * The item data is loaded from a the file resource
 * <code>item_aliases.yml</code>.
 */
public class ItemDB {
    private static ItemDB instance = ItemDB.fromResourcePath("/item_aliases.yml");
    private Map<Pair<Integer, Short>, ItemDefinition> byIdDamagePair;
    private Map<String, ItemDefinition> byAlias;

    /**
     * Read an ItemDB instance from an InputStream.
     */
    @SuppressWarnings("unchecked")
    public ItemDB(InputStream stream) {
        byIdDamagePair = new HashMap<Pair<Integer, Short>, ItemDefinition>();
        byAlias = new HashMap<String, ItemDefinition>();
        Yaml yaml = new Yaml(new SafeConstructor());
        // This is absolutely disgusting :D
        Map<Integer, Map<String, Object>> document =
            (Map<Integer, Map<String, Object>>) yaml.load(stream);

        for(Map.Entry<Integer, Map<String, Object>> entry : document.entrySet()) {
            Integer id = entry.getKey();
            Map<String, Object> itemData = entry.getValue();

            // Multiple durability values such as wool
            if(itemData.containsKey("subtypes")) {
                Map<Integer, Map<String, Object>> subtypes =
                    (Map<Integer, Map<String, Object>>) itemData.get("subtypes");
                for(Map.Entry<Integer, Map<String, Object>> subentry : subtypes.entrySet()) {
                    Short durability = subentry.getKey().shortValue();
                    ItemDefinition item = fromMap(id, durability, subentry.getValue());
                    addItemDefinition(item);
                }
            // Items without specific durability values
            } else {
                ItemDefinition item = fromMap(id, (short)0, itemData);
                addItemDefinition(item);
            }
        }
    }

    /**
     * Create an ItemDB instance from a resource.
     */
    public static ItemDB fromResourcePath(String resourcePath) {
        return new ItemDB(ItemDB.class.getResourceAsStream(resourcePath));
    }

    /**
     * Get the default item database.
     */
    public static ItemDB getDefault() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    private static ItemDefinition fromMap(Integer typeId, Short durability, Map<String, Object> map) {
        String canonicalName = (String) map.get("canonical_name");
        String shortName = (String) map.get("short_name");
        List<String> aliases = (List<String>) map.get("aliases");
        return new ItemDefinition(typeId, durability, canonicalName, shortName, aliases);
    }

    /**
     * Add an ItemDefinition to the database.
     */
    private void addItemDefinition(ItemDefinition item) {
        byIdDamagePair.put(item.getKey(), item);
        for(String alias : item.getAliases()) {
            byAlias.put(alias, item);
        }
    }

    /**
     * Return the set of all the keys that can be passed to {@link #getItemById(Pair)}
     */
    public Set<Pair<Integer, Short>> getDataValuesSet() {
        return byIdDamagePair.keySet();
    }

    /**
     * Get the ItemDefinition corresponding to this (ID, durability) pair.
     *
     * @return an ItemDefinition if found, otherwise null.
     */
    public ItemDefinition getItemById(final Pair<Integer, Short> idPair) {
        return byIdDamagePair.get(idPair);
    }

    /**
     * Get the ItemDefinition which has this data value.
     *
     * @return an ItemDefinition if found, otherwise null.
     */
    public ItemDefinition getItemById(final Integer typeId, final Short durability) {
        return getItemById(new Pair<Integer, Short>(typeId, durability));
    }

    /**
     * Get the ItemDefinition with this alias.
     *
     * @return an ItemDefinition if found, otherwise null.
     */
    public ItemDefinition getItemByAlias(final String alias) {
        String aliasLower = alias.toLowerCase().replace(" ", "");
        ItemDefinition def = byAlias.get(aliasLower);
        // If plural, remove the suffix 's'
        if(def == null && aliasLower.charAt(aliasLower.length()-1) == 's') {
            def = byAlias.get(aliasLower.substring(0, aliasLower.length()-1));
        }
        return def;
    }
}

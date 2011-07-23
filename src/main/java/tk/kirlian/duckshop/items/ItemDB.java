package tk.kirlian.duckshop.items;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import tk.kirlian.util.Pair;

/**
 * A database of Minecraft items and their data values and names.
 * <p>
 * The item data is loaded from a the file resource
 * <code>item_aliases.yml</code>.
 */
public class ItemDB {
    private static ItemDB instance;
    private Map<Pair<Integer, Short>, ItemDefinition> byIdDamagePair;
    private Map<String, ItemDefinition> byAlias;

    private ItemDB() {
        byIdDamagePair = new HashMap<Pair<Integer, Short>, ItemDefinition>();
        byAlias = new HashMap<String, ItemDefinition>();
        Yaml yaml = new Yaml(new SafeConstructor());
        // This is absolutely disgusting :D
        Map<Integer, Map<String, Object>> document =
            (Map<Integer, Map<String, Object>>) yaml.load(getClass().getResourceAsStream("/item_aliases.yml"));

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
     * Get an instance of this class, or create it if none exists.
     */
    public static ItemDB getInstance() {
        if(instance == null) {
            instance = new ItemDB();
        }
        return instance;
    }

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
     * Get the ItemDefinition which has this data value.
     *
     * @return an ItemDefinition if found, otherwise null.
     */
    public ItemDefinition getItemById(final Integer typeId, final Short durability) {
        return byIdDamagePair.get(new Pair<Integer, Short>(typeId, durability));
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

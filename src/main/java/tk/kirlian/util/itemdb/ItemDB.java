package tk.kirlian.util.itemdb;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * A database of Minecraft items and their data values and names.
 *
 * The item data is loaded from a the file resource
 * <code>item_aliases.yml</code>.
 */
public class ItemDB {
    private static ItemDB instance;
    private Map<Integer, ItemDefinition> byItemId;
    private Map<String, ItemDefinition> byAlias;
    private ItemDB() {
        byItemId = new HashMap<Integer, ItemDefinition>();
        byAlias = new HashMap<String, ItemDefinition>();
        // This is absolutely disgusting :D
        Yaml yaml = new Yaml(new SafeConstructor());
        Map<Integer, Map<String, Object>> doc =
            (Map<Integer, Map<String, Object>>) yaml.load(getClass().getResourceAsStream("/item_aliases.yml"));
        for(Map.Entry<Integer, Map<String, Object>> entry : doc.entrySet()) {
            Map<String, Object> itemData = entry.getValue();
            Integer id = entry.getKey();
            String canonicalName = (String) itemData.get("canonical_name");
            String shortName = (String) itemData.get("short_name");
            List<String> aliases = (List<String>) itemData.get("aliases");
            ItemDefinition item = new ItemDefinition(id, canonicalName, shortName, aliases);
            byItemId.put(id, item);
            for(String alias : aliases) {
                byAlias.put(alias, item);
            }
        }
        /*for(Map.Entry<String, ItemDefinition> entry : byAlias.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getCanonicalName());
        }*/
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
    /**
     * Get the ItemDefinition which has this data value.
     */
    public ItemDefinition getItemById(final Integer id) {
        return byItemId.get(id);
    }
    /**
     * Get the ItemDefinition with this alias.
     */
    public ItemDefinition getItemByAlias(final String alias) {
        String aliasLower = alias.toLowerCase();
        ItemDefinition def = byAlias.get(aliasLower);
        // If plural, remove the suffix 's'
        if(def == null && aliasLower.charAt(aliasLower.length()-1) == 's') {
            def = byAlias.get(aliasLower.substring(0, aliasLower.length()-1));
        }
        return def;
    }
}

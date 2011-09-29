package tk.allele.itemdb;

import org.bukkit.Material;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import tk.allele.util.StringTools;

import java.io.InputStream;
import java.util.*;

/**
 * Maps item names to their data values.
 */
public class ItemDB {
    static final ItemDB defaultDB = ItemDB.fromResourcePath("/item_aliases.yml");

    Map<ItemSpecifier, ItemDefinition> byIdDamagePair;
    Map<String, ItemDefinition> byName;

    /**
     * Create an ItemDB instance.
     *
     * @param stream The configuration file from which the item names are read.
     */
    public ItemDB(InputStream stream) {
        byIdDamagePair = new HashMap<ItemSpecifier, ItemDefinition>();
        byName = new HashMap<String, ItemDefinition>();

        Map<String, Set<String>> config = parseConfigFile(stream);

        for(Material material : Material.values()) {
            Set<String> names = generateNames(config, material.name());
            ItemDefinition itemDfn = new ItemDefinition(material.getId(), (short)0, normalizeName(material.name()), names);
            for(String name : names) {
                byName.put(name, itemDfn);
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
     * Get the default instance of the database.
     */
    public static ItemDB getDefault() {
        return defaultDB;
    }

    /**
     * Set the maximum length of the short names of any ItemDefinitions that are returned.
     */

    /**
     * Find an ItemDefinition given an item name.
     *
     * @return The ItemDefinition if found, otherwise null.
     */
    public ItemDefinition getItemByName(String name) {
        return byName.get(name);
    }

    /**
     * Find an ItemDefinition given an ItemSpecifier.
     *
     * @return The ItemDefinition if found, otherwise null.
     */
    public ItemDefinition getItemById(ItemSpecifier id) {
        return byIdDamagePair.get(id);
    }

    /**
     * Find an ItemDefinition given the item ID and damage value.
     *
     * @return The ItemDefinition if found, otherwise null.
     */
    public ItemDefinition getItemById(int typeId, short durability) {
        return getItemById(new ItemSpecifier(typeId, durability));
    }

    /**
     * Find an ItemDefinition given the item ID and a damage value of zero.
     *
     * @return The ItemDefinition if found, otherwise null.
     */
    public ItemDefinition getItemById(int typeId) {
        return getItemById(new ItemSpecifier(typeId, (short)0));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Set<String>> parseConfigFile(InputStream stream) {
        Yaml yaml = new Yaml(new SafeConstructor());
        Map<String, List<String>> rawData = (Map<String, List<String>>) yaml.load(stream);
        Map<String, Set<String>> cleanData = new HashMap<String, Set<String>>();

        for(Map.Entry<String, List<String>> entry : rawData.entrySet()) {
            String cleanBaseName = normalizeName(entry.getKey());
            Set<String> cleanAliases = new LinkedHashSet<String>();

            // A word is a synonym of itself
            cleanAliases.add(cleanBaseName);

            // Add each alias to the set, normalizing each one
            for(String rawAlias : entry.getValue()) {
                cleanAliases.add(normalizeName(rawAlias));
            }

            // Add the aliases to the result
            cleanData.put(cleanBaseName, cleanAliases);
        }

        return cleanData;
    }

    private static Set<String> generateNames(Map<String, Set<String>> config, String itemName) {
        Set<String> resultSet = new HashSet<String>();

        // Check the full name first (e.g. "diamondpick")
        String normalizedName = normalizeName(itemName);
        Set<String> fullAliases = config.get(normalizedName);
        if(fullAliases != null) {
            resultSet.addAll(fullAliases);
        }

        // Split the name of the material into lowercase words
        String[] words = itemName.split("_");

        @SuppressWarnings("unchecked")
        Set<String>[] aliasesForEachWord = (Set<String>[]) new Set[words.length];
        for(int i = 0; i != words.length; ++i) {
            String cleanWord = normalizeName(words[i]);
            Set<String> aliases = config.get(cleanWord);
            if(aliases != null) {
                aliasesForEachWord[i] = aliases;
            } else {
                aliasesForEachWord[i] = Collections.singleton(cleanWord);
            }
        }

        // Generate the aliases for each word individually
        resultSet.addAll(StringTools.combineStrings(aliasesForEachWord));

        return resultSet;
    }

    /**
     * Normalize the name of an item.
     *
     * <p>This removes all non-alphanumeric characters and converts the string
     * to lowercase.
     *
     * @return The normalized string.
     */
    public static String normalizeName(String rawName) {
        return rawName.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
}

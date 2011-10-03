package tk.allele.itemdb;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.material.*;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import tk.allele.util.StringTools;

import java.io.InputStream;
import java.util.*;

/**
 * Maps item names to their data values.
 */
public class ItemDB {
    static final ItemDB defaultDB = new ItemDB(ItemDB.class.getResourceAsStream("/item_subst.yml"),
                                               ItemDB.class.getResourceAsStream("/item_aliases.yml"));

    Map<ItemSpecifier, ItemDefinition> byIdDamagePair;
    Map<String, ItemDefinition> byName;

    /**
     * Create an ItemDB instance.
     *
     * @param aliasesStream The configuration file from which the item names are read.
     */
    public ItemDB(InputStream substStream, InputStream aliasesStream) {
        byIdDamagePair = new HashMap<ItemSpecifier, ItemDefinition>();
        byName = new HashMap<String, ItemDefinition>();

        Map<String, String> subst = parseSubstFile(substStream);
        SetMultimap<String, String> aliases = parseAliasesFile(aliasesStream);

        for(Material material : Material.values()) {
            Map<ItemSpecifier, ItemDefinition> currentDefinitions = generateDefinitionsForMaterial(aliases, material);
            byIdDamagePair.putAll(currentDefinitions);
            for(ItemDefinition itemDfn : currentDefinitions.values()) {
                for(String alias : itemDfn.getAliases()) {
                    byName.put(alias, itemDfn);
                }
            }
        }

        List<ItemDefinition> definitions = new ArrayList<ItemDefinition>(byIdDamagePair.values());
        Collections.sort(definitions);
        for(ItemDefinition itemDfn : definitions) {
            System.out.println(itemDfn);
            for(String alias : itemDfn.getAliases()) {
                System.out.println("  " + alias);
            }
        }
    }

    /**
     * Get the default instance of the database.
     */
    public static ItemDB getDefault() {
        return defaultDB;
    }

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
    private static SetMultimap<String, String> parseAliasesFile(InputStream stream) {
        Yaml yaml = new Yaml(new SafeConstructor());
        Map<String, List<String>> rawData = (Map<String, List<String>>) yaml.load(stream);
        SetMultimap<String, String> cleanData = HashMultimap.create();

        for(Map.Entry<String, List<String>> entry : rawData.entrySet()) {
            String cleanBaseName = normalizeName(entry.getKey());

            // Add each alias to the set, normalizing each one
            for(String rawAlias : entry.getValue()) {
                cleanData.put(cleanBaseName, normalizeName(rawAlias));
            }
        }

        return cleanData;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> parseSubstFile(InputStream stream) {
        Yaml yaml = new Yaml(new SafeConstructor());
        Map<String, String> rawData = (Map<String, String>) yaml.load(stream);
        Map<String, String> cleanData = new HashMap<String, String>();

        for(Map.Entry<String, String> entry : rawData.entrySet()) {
            cleanData.put(normalizeName(entry.getKey()), normalizeName(entry.getValue()));
        }

        return cleanData;
    }

    @SuppressWarnings("unchecked")
    private static Map<ItemSpecifier, ItemDefinition> generateDefinitionsForMaterial(SetMultimap<String, String> config, Material material) {
        Map<Short, String> prefixes = new LinkedHashMap<Short, String>();

        // Dye or wool
        if(Colorable.class.isAssignableFrom(material.getData())) {
            if(Dye.class.isAssignableFrom(material.getData())) {
                // Damage values for dyes are reversed for some reason
                for(DyeColor color : DyeColor.values()) {
                    prefixes.put((short) (15 - color.getData()), color.name());
                }
            } else {
                for(DyeColor color : DyeColor.values()) {
                    prefixes.put((short) color.getData(), color.name());
                }
            }
        }

        // Slabs, Stairs...
        if(TexturedMaterial.class.isAssignableFrom(material.getData())) {
            Class<? extends TexturedMaterial> dataClass = (Class<? extends TexturedMaterial>) material.getData();

            List<Material> textures;
            TexturedMaterial texturedMaterial;
            try {
                // Create a new instance of the TexturedMaterial ...
                texturedMaterial = dataClass.getConstructor(Integer.TYPE).newInstance(material.getId());
            } catch(NoSuchMethodException ex) {
                throw new NoSuchMethodError(ex.getMessage());
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
            // ... then get the list of textures from it
            textures = texturedMaterial.getTextures();

            // Add each texture to the map
            short index = 0;
            for(Material texture : textures) {
                prefixes.put(index, texture.name());
                ++index;
            }
        }

        // Hack so that the below loop actually runs if the material doesn't have any prefixes
        if(prefixes.isEmpty()) {
            prefixes.put((short) 0, "");
        }

        // Generate!
        Map<ItemSpecifier, ItemDefinition> returnValue = new LinkedHashMap<ItemSpecifier, ItemDefinition>();
        if(!prefixes.isEmpty()) {
            for(Map.Entry<Short, String> entry : prefixes.entrySet()) {
                String prefix = entry.getValue();
                String nameWithPrefix;
                if(!prefix.isEmpty()) { // See above hack
                    nameWithPrefix = prefix + "_" + material.name();
                } else {
                    nameWithPrefix = material.name();
                }

                Set<String> aliases = generateAliasesForName(config, nameWithPrefix);
                if(entry.getKey() == 0) {
                    aliases.addAll(generateAliasesForName(config, material.name()));
                }

                // Create the ItemDefinition and add it to the result
                ItemDefinition itemDfn = new ItemDefinition(material.getId(), entry.getKey(), normalizeName(nameWithPrefix), aliases);
                returnValue.put(itemDfn.getSpecifier(), itemDfn);
            }
        }

        return returnValue;
    }

    private static Set<String> generateAliasesForName(SetMultimap<String, String> config, String itemName) {
        Set<String> resultSet = new HashSet<String>();

        // Check the full name first (e.g. "diamondpick")
        String normalizedName = normalizeName(itemName);
        Set<String> fullAliases = config.get(normalizedName);
        fullAliases.add(normalizedName); // We need to add the whole word
        resultSet.addAll(fullAliases);

        // Split the name of the material into lowercase words
        String[] words = itemName.replace('_', ' ').split(" ");

        @SuppressWarnings("unchecked")
        Set<String>[] aliasesForEachWord = (Set<String>[]) new Set[words.length];
        for(int i = 0; i != words.length; ++i) {
            String cleanWord = normalizeName(words[i]);
            Set<String> aliases = config.get(cleanWord);
            aliases.add(cleanWord); // Remember!
            aliasesForEachWord[i] = aliases;
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

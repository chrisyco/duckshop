package tk.allele.duckshop.signs;

import org.bukkit.Location;
import tk.allele.duckshop.DuckShop;
import tk.allele.util.Locations;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Keeps track of chest locations, among other things.
 */
public class SignManager {
    private final static String CHESTS_FILE_NAME = "chests.properties";
    private final static String CHESTS_FILE_COMMENT = "This file is used internally to store sign-chest links.\nFormat is signLocation=chestLocation\nDo not edit unless you know what you are doing.";
    private static SignManager instance;

    private DuckShop plugin;
    private Logger log;
    private Map<Location, Location> chestLocations;
    private Map<Location, Boolean> chestIsConnected;
    private File propertiesFile;

    private SignManager(DuckShop plugin) {
        this.plugin = plugin;
        this.log = plugin.log;
        this.chestLocations = new HashMap<Location, Location>();
        this.chestIsConnected = new HashMap<Location, Boolean>();
        this.propertiesFile = new File(plugin.getDataFolder(), CHESTS_FILE_NAME);
        load();
    }

    /**
     * Return an instance, or create it if it does not exist.
     */
    public static SignManager getInstance(DuckShop plugin) {
        if(instance == null) {
            instance = new SignManager(plugin);
        }
        return instance;
    }

    /**
     * Get the location of the Chest connected with a Sign.
     *
     * @return a Location object, or null if none can be found.
     */
    public Location getChestLocation(Location signLocation) {
        return chestLocations.get(signLocation);
    }

    /**
     * Set the location of the Chest connected with a Sign.
     */
    public void setChestLocation(Location signLocation, Location chestLocation) {
        chestLocations.put(signLocation, chestLocation);
        chestIsConnected.put(chestLocation, Boolean.TRUE);
    }

    /**
     * Removes the location of the Chest connected with a Sign, if present.
     */
    public void removeChestLocation(Location signLocation) {
        Location chestLocation = getChestLocation(signLocation);
        if(chestLocation != null) {
            chestIsConnected.remove(chestLocation);
        }
        chestLocations.remove(signLocation);
    }

    /**
     * Return whether a chest is connected to a sign.
     */
    public boolean isChestConnected(Location chestLocation) {
        return chestIsConnected.containsKey(chestLocation);
    }

    /**
     * Reads chest links from a properties file.
     */
    public void load() {
        try {
            FileInputStream in = new FileInputStream(propertiesFile);
            Properties properties = new Properties();
            try {
                properties.load(in);
            } finally {
                in.close();
            }
            int entriesLoaded = 0;
            for(Map.Entry<Object, Object> entry : properties.entrySet()) {
                Location signLocation = Locations.parseLocation(plugin.getServer(), (String)entry.getKey());
                Location chestLocation = Locations.parseLocation(plugin.getServer(), (String)entry.getValue());
                setChestLocation(signLocation, chestLocation);
                ++entriesLoaded;
            }
            log.info("Loaded " + entriesLoaded + " chest link(s).");
        } catch(FileNotFoundException ex) {
            log.warning("Chest link file does not exist. This is probably the first time you've used this plugin.");
        } catch(IOException ex) {
            ex.printStackTrace();
            log.severe("Could not load chest link file.");
        }
    }

    /**
     * Writes chest links to a properties file.
     */
    public void store() {
        try {
            FileOutputStream out = new FileOutputStream(propertiesFile);
            Properties properties = new Properties();
            int entriesStored = 0;
            for(Map.Entry<Location, Location> entry : chestLocations.entrySet()) {
                properties.setProperty(Locations.toString(entry.getKey()), Locations.toString(entry.getValue()));
                ++entriesStored;
            }
            try {
                properties.store(out, CHESTS_FILE_COMMENT);
            } finally {
                out.close();
            }
            log.info("Stored " + entriesStored + " chest link(s).");
        } catch(IOException ex) {
            ex.printStackTrace();
            log.warning("Could not write chest link file. Any personal signs will need to be reconnected.");
        }
    }
}

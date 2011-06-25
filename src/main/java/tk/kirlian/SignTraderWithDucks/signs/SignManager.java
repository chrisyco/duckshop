package tk.kirlian.SignTraderWithDucks.signs;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.Location;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import java.util.logging.Logger;
import tk.kirlian.util.CustomLogger;
import tk.kirlian.util.Locations;

/**
 * Keeps track of chest locations, among other things.
 */
public class SignManager {
    private final static String CHESTS_FILE_NAME = "chests.properties";
    private final static String CHESTS_FILE_COMMENT = "This file is used internally to store sign-chest links.\nFormat is signLocation=chestLocation\nDo not edit unless you know what you are doing.";
    private static SignManager instance;
    private SignTraderPlugin plugin;
    private Logger log;
    private Map<Location, Location> chestLocations;
    private File propertiesFile;
    private SignManager(SignTraderPlugin plugin) {
        this.plugin = plugin;
        this.log = plugin.log;
        this.chestLocations = new HashMap<Location, Location>();
        this.propertiesFile = new File(plugin.getDataFolder(), CHESTS_FILE_NAME);
        load();
    }
    /**
     * Return an instance, or create it if it does not exist.
     *
     * This must be called at least once before {@link #getInstance()}
     * is called.
     */
    public static SignManager getInstance(SignTraderPlugin plugin) {
        if(instance == null) {
            instance = new SignManager(plugin);
        }
        return instance;
    }
    /**
     * Return an instance if it has already been created.
     *
     * @throws NullPointerException if it has not been created yet.
     */
    /*public static SignManager getInstance() {
        if(instance == null) {
            throw new NullPointerException("SignManager must be initialized with getInstance(SignTraderPlugin) first");
        }
        return instance;
    }*/
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
    }
    /**
     * Removes the location of the Chest connected with a Sign, if present.
     */
    public void removeChestLocation(Location signLocation) {
        chestLocations.remove(signLocation);
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
            for(Map.Entry<Object, Object> entry : properties.entrySet()) {
                Location signLocation = Locations.parseLocation(plugin.getServer(), (String)entry.getKey());
                Location chestLocation = Locations.parseLocation(plugin.getServer(), (String)entry.getValue());
                chestLocations.put(signLocation, chestLocation);
            }
        } catch(FileNotFoundException ex) {
            log.warning("Chest link file does not exist. This is probably the first time you've used this plugin.");
        } catch(IOException ex) {
            log.warning("Could not load chest link file.");
        }
    }
    /**
     * Writes chest links to a properties file.
     */
    public void store() {
        try {
            FileOutputStream out = new FileOutputStream(propertiesFile);
            Properties properties = new Properties();
            for(Map.Entry<Location, Location> entry : chestLocations.entrySet()) {
                properties.setProperty(Locations.toString(entry.getKey()), Locations.toString(entry.getValue()));
            }
            try {
                properties.store(out, CHESTS_FILE_COMMENT);
            } finally {
                out.close();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
            log.warning("Could not write chest link file. Any personal signs will need to be reconnected.");
        }
    }
}

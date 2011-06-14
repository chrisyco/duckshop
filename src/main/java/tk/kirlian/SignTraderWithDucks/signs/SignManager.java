package tk.kirlian.SignTraderWithDucks.signs;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.Location;
import java.util.Map;
import java.util.HashMap;

import tk.kirlian.SignTraderWithDucks.SignTraderPlugin;
import java.util.logging.Logger;
import tk.kirlian.util.CustomLogger;

/**
 * Keeps track of chest locations, among other things.
 */
public class SignManager {
    private static SignManager instance;
    private SignTraderPlugin plugin;
    private Logger log;
    private Map<Location, Location> chestLocations;
    private SignManager(SignTraderPlugin plugin) {
        this.plugin = plugin;
        this.log = plugin.log;
        this.chestLocations = new HashMap<Location, Location>();
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
}

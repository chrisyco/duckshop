package tk.kirlian.duckshop;

import com.nijikokun.register.payment.Method;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.kirlian.duckshop.items.ItemDB;
import tk.kirlian.duckshop.signs.SignManager;
import tk.kirlian.permissions.PermissionsManager;
import tk.kirlian.permissions.PermissionsMethod;
import tk.kirlian.protection.ProtectionManager;
import tk.kirlian.util.CustomLogger;
import tk.kirlian.util.StringTools;

import java.util.logging.Logger;

/**
 * A Bukkit plugin that allows fully automated shops using signs!
 */
public class DuckShop extends JavaPlugin {
    private static final String PERMISSIONS_PREFIX = "DuckShop.";

    private DuckShopBlockListener blockListener;
    private DuckShopServerListener serverListener;
    private SignManager signManager;

    public Logger log;
    public DuckShopPlayerListener playerListener; // Needed by DuckShopCommand
    public Method economyMethod; // Needed by various inventory adapters
    public ProtectionManager protection; // Needed by ChestInventoryAdapter
    public PermissionsManager permissions; // Needed by TradingSign

    @Override
    public void onEnable() {
        // Logger needs to be created here because the description file
        // isn't loaded until now
        log = CustomLogger.getLogger(getDescription().getName());

        // I don't know where to put this, so it's going here!
        if(!getDataFolder().isDirectory()) {
            if(getDataFolder().mkdirs()) {
                log.info("Created data folder.");
            } else {
                log.warning("Could not create data folder. Stuff may fail later.");
            }
        }

        // Load the ItemDB beforehand, so if it fails, it fails now, and
        // not in the obscure depths of the sign handling code :)
        ItemDB itemDB = ItemDB.getInstance();

        // Register events
        PluginManager pm = getServer().getPluginManager();
        blockListener = new DuckShopBlockListener(this);
        playerListener = new DuckShopPlayerListener(this);
        serverListener = new DuckShopServerListener(this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Event.Priority.High, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, serverListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLUGIN_DISABLE, serverListener, Event.Priority.Normal, this);

        // Initialize the sign manager
        signManager = SignManager.getInstance(this);

        // Initialize Permissions
        permissions = new PermissionsManager(this, log, PERMISSIONS_PREFIX);
        PermissionsMethod permissionsMethod = permissions.getBest();
        log.info("Using " + permissionsMethod + " for permissions.");

        // Initialize chest protection
        protection = new ProtectionManager(this);
        if(protection.isEnabled()) {
            log.info("Using " + StringTools.join(protection.getEnabledMethods(), ", ") + " for chest protection.");
        } else {
            log.info("No chest protection found.");
        }

        // Register commands
        getCommand(DuckShopCommand.COMMAND_NAME).setExecutor(new DuckShopCommand(this));

        final String version = getDescription().getVersion();
        log.info("Version " + version + " enabled. " +
                 "No viruses, honest!");
    }

    @Override
    public void onDisable() {
        final String version = getDescription().getVersion();
        log.info("Version " + version + " disabled. " +
                 "Have a nice day!");
        signManager.store();
    }
}

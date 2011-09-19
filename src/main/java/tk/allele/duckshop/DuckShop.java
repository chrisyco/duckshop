package tk.allele.duckshop;

import com.nijikokun.register.payment.Method;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.allele.duckshop.items.ItemDB;
import tk.allele.duckshop.signs.SignManager;
import tk.allele.permissions.PermissionsManager;
import tk.allele.permissions.PermissionsMethod;
import tk.allele.protection.ProtectionManager;
import tk.allele.util.CustomLogger;
import tk.allele.util.StringTools;
import tk.allele.util.commands.CommandDispatcher;

import java.util.logging.Logger;

/**
 * A Bukkit plugin that allows fully automated shops using signs!
 */
public class DuckShop extends JavaPlugin {
    private static final String PERMISSIONS_PREFIX = "duckshop.";

    private DuckShopBlockListener blockListener;
    private DuckShopPlayerListener playerListener;
    private DuckShopServerListener serverListener;
    private CommandDispatcher commandListener;
    private SignManager signManager;

    public Logger log;
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
        ItemDB itemDB = ItemDB.getDefault();

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

        LinkState linkState = new LinkState(signManager, permissions);

        // Register events
        PluginManager pm = getServer().getPluginManager();
        blockListener = new DuckShopBlockListener(this);
        playerListener = new DuckShopPlayerListener(this, linkState);
        serverListener = new DuckShopServerListener(this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Event.Priority.High, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, serverListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLUGIN_DISABLE, serverListener, Event.Priority.Normal, this);

        // Register commands
        commandListener = new DuckShopCommand(this, linkState);

        final String version = getDescription().getVersion();
        log.info("Version " + version + " enabled. " +
                 "No viruses, honest!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return commandListener.onCommand(sender, command, commandLabel, args);
    }

    @Override
    public void onDisable() {
        final String version = getDescription().getVersion();
        log.info("Version " + version + " disabled. " +
                 "Have a nice day!");
        signManager.store();
    }
}

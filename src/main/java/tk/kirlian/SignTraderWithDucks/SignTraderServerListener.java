package tk.kirlian.SignTraderWithDucks;

//import com.nijikokun.register.payment.Methods;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import java.util.logging.Logger;
import tk.kirlian.util.CustomLogger;

/**
 * Listens for events such as loading and unloading another plugin.
 */
public class SignTraderServerListener extends ServerListener {
    private SignTraderPlugin plugin;
    private Logger log;

    public SignTraderServerListener(SignTraderPlugin plugin) {
        this.log = plugin.log;
        this.plugin = plugin;
    }
}

package tk.kirlian.SignTraderWithDucks;

import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Methods;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import java.util.logging.Logger;

/**
 * Listens for events such as loading and unloading another plugin.
 */
public class SignTraderServerListener extends ServerListener {
    private SignTraderPlugin plugin;
    private Logger log;
    private Methods methods = new Methods();

    public SignTraderServerListener(SignTraderPlugin plugin) {
        this.log = plugin.log;
        this.plugin = plugin;
    }

    // Many thanks to Nijikokun for this code.
    // If it weren't for him, this wouldn't be under the AGPL! :P
    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        if(methods != null && methods.hasMethod() && methods.checkDisabled(event.getPlugin())) {
            plugin.economyMethod = null;
            log.info("Economy system disabled. Money transactions will now fail like a skydiving elephant.");
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        if(!methods.hasMethod()) {
            if(methods.setMethod(event.getPlugin())) {
                plugin.economyMethod = methods.getMethod();
                log.info("Hooked into " + plugin.economyMethod.getName() + " version " + plugin.economyMethod.getVersion() + ".");
            }
        }
    }
}

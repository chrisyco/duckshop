package tk.allele.economy;

import com.nijikokun.register.payment.Methods;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

/**
 * Listens for when an economy plugin is loaded or unloaded.
 */
public class RegisterServerListener extends ServerListener {
    final PluginManager manager;
    final EconomyPluginListener listener;

    public RegisterServerListener(Plugin plugin, EconomyPluginListener listener) {
        this.manager = plugin.getServer().getPluginManager();
        this.listener = listener;
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        if(Methods.hasMethod() && Methods.checkDisabled(event.getPlugin())) {
            listener.onDisable();
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        if(!Methods.hasMethod()) {
            if(Methods.setMethod(manager)) {
                listener.onEnable(Methods.getMethod());
            }
        }
    }
}

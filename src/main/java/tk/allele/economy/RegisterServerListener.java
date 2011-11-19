package tk.allele.economy;

import com.nijikokun.register.payment.Methods;
import org.bukkit.event.Event;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Listens for when an economy plugin is loaded or unloaded.
 */
public class RegisterServerListener extends ServerListener {
    final PluginManager manager;
    final EconomyPluginListener listener;

    public RegisterServerListener(Plugin plugin, EconomyPluginListener listener) {
        this.manager = plugin.getServer().getPluginManager();
        this.listener = listener;

        manager.registerEvent(Event.Type.PLUGIN_ENABLE, this, Event.Priority.Normal, plugin);
        manager.registerEvent(Event.Type.PLUGIN_DISABLE, this, Event.Priority.Normal, plugin);
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        if (Methods.hasMethod() && Methods.checkDisabled(event.getPlugin())) {
            listener.onDisable();
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        if (!Methods.hasMethod()) {
            if (Methods.setMethod(manager)) {
                listener.onEnable(Methods.getMethod());
            }
        }
    }
}

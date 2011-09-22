package tk.allele.duckshop.listeners;

import com.nijikokun.register.payment.Method;
import tk.allele.duckshop.DuckShop;
import tk.allele.economy.DummyEconomy;
import tk.allele.economy.EconomyPluginListener;

import java.util.logging.Logger;

/**
 * Detects economy plugins.
 */
public class DuckShopEconomyPluginListener implements EconomyPluginListener {
    final DuckShop plugin;
    final Logger log;

    public DuckShopEconomyPluginListener(DuckShop plugin) {
        this.plugin = plugin;
        this.log = plugin.log;
        plugin.economyMethod = DummyEconomy.getInstance();
    }

    @Override
    public void onEnable(Method method) {
        plugin.economyMethod = method;
        log.info("Hooked into " + plugin.economyMethod.getName() + " version " + plugin.economyMethod.getVersion() + ".");
    }

    @Override
    public void onDisable() {
        plugin.economyMethod = DummyEconomy.getInstance();
        log.info("Economy system disabled. Money transactions will now fail like a skydiving elephant.");
    }
}

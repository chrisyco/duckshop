package tk.allele.economy;

import com.nijikokun.register.payment.Method;

/**
 * Represents an action to take after an economy plugin is detected.
 */
public interface EconomyPluginListener {
    /**
     * Called when an economy plugin is detected.
     *
     * @param method The plugin that was enabled
     */
    public void onEnable(Method method);

    /**
     * Called when the economy plugin is disabled.
     */
    public void onDisable();
}

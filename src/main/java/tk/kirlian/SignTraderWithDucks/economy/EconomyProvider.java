package tk.kirlian.SignTraderWithDucks.economy;

import org.bukkit.entity.Player;

import tk.kirlian.util.provider.Provider;
import tk.kirlian.util.provider.PriorityProviderManager;

/**
 * Provides a consistent interface to various ways of managing money.
 */
public abstract class EconomyProvider implements Provider {
    private static PriorityProviderManager<EconomyProvider> manager;

    /**
     * Get the {@link PriorityProviderManager} for this class.
     */
    public static PriorityProviderManager<EconomyProvider> getManager() {
        if(manager == null) {
            manager = new PriorityProviderManager<EconomyProvider>();
            /* vvv Add new providers below vvv */
            manager.register(FailEconomyProvider.getInstance());
            /* ^^^ Add new providers above ^^^ */
        }
        return manager;
    }

    /**
     * Get the amount of money held by a Player.
     */
    public abstract double getMoney(Player player);
    /**
     * Add money to the amount held by a Player.
     */
    public abstract void addMoney(Player player, double amount);
    /**
     * Subtract money from the amount held by a Player.
     */
    public abstract void subtractMoney(Player player, double amount);
}

package tk.kirlian.SignTraderWithDucks.economy;

import org.bukkit.entity.Player;

import tk.kirlian.util.provider.PriorityProviderManager;

/**
 * A fallback no-op economy handler.
 * @see EconomyProvider
 */
public class FailEconomyProvider extends EconomyProvider {
    private static final FailEconomyProvider provider
        = new FailEconomyProvider();

    private FailEconomyProvider() {
        //EconomyProvider.getManager().register(this);
    }

    public static FailEconomyProvider getInstance() {
        return provider;
    }

    public String getName() {
        return "FailEconomy";
    }

    public int getPriority() {
        return 100;
    }

    public boolean isAvailable() {
        return true;
    }

    public double getMoney(Player player) {
        return 42.0; // The answer to life, the universe, and everything
    }
    public void addMoney(Player player, double amount) {}
    public void subtractMoney(Player player, double amount) {}
}

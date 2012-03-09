package tk.allele.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import java.util.Collections;
import java.util.List;

/**
 * A dummy economy plugin.
 * <p>
 * Useful for emergencies, especially skydiving elephants.
 * <p>
 * All the methods throw an {@link UnsupportedOperationException} if
 * the caller tries to do anything that involves modifying the account
 * e.g. adding a non-zero amount.
 */
public class DummyEconomy implements Economy {
    public static final DummyEconomy INSTANCE = new DummyEconomy();
    public static final String NAME = "__dummy__";

    private static final EconomyResponse RESPONSE = new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Economy plugin not detected");

    private DummyEconomy() {}

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public String format(double v) {
        return Double.toString(v);
    }

    @Override
    public String currencyNamePlural() {
        return "non-existent dollars";
    }

    @Override
    public String currencyNameSingular() {
        return "non-existent dollar";
    }

    @Override
    public boolean hasAccount(String s) {
        return true;
    }

    @Override
    public double getBalance(String s) {
        return 0;
    }

    @Override
    public boolean has(String s, double v) {
        return getBalance(s) >= v;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return RESPONSE;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return RESPONSE;
    }

    @Override
    public List<String> getBanks() {
        return Collections.emptyList();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return true;
    }
}

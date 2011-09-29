package tk.allele.duckshop.listeners;

import tk.allele.duckshop.DuckShop;
import tk.allele.duckshop.commands.CancelCommand;
import tk.allele.duckshop.commands.LinkCommand;
import tk.allele.duckshop.commands.VersionCommand;
import tk.allele.util.commands.CommandDispatcher;

/**
 * Handles commands.
 */
public class DuckShopCommand extends CommandDispatcher {
    public DuckShopCommand(DuckShop plugin, LinkState linkState) {
        super("duckshop");
        registerCommand(new VersionCommand(plugin));
        registerCommand(new LinkCommand(linkState));
        registerCommand(new CancelCommand(linkState));
    }
}

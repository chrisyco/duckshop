package tk.kirlian.SignTraderWithDucks;

import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import java.util.logging.Logger;
import tk.kirlian.util.CustomLogger;
import tk.kirlian.SignTraderWithDucks.errors.*;
import tk.kirlian.SignTraderWithDucks.signs.*;

/**
 * Listens for block events -- like placing a sign.
 */
public class SignTraderBlockListener extends BlockListener {
    private final SignTraderPlugin plugin;
    private final Logger log;

    public SignTraderBlockListener(SignTraderPlugin plugin) {
        this.log = plugin.log;
        this.plugin = plugin;
    }

    @Override
    public void onSignChange(SignChangeEvent event) {
        TradingSign sign = null;
        try {
            sign = new TradingSign(plugin,
                                   event.getPlayer(),
                                   event.getBlock().getLocation(),
                                   event.getLines());
        } catch(InvalidSyntaxException ex) {
            // Do nothing
        }
        if(sign != null) {
            sign.updateSign(event);
            event.getPlayer().sendMessage("Created sign! Yay!");
            if(!sign.isGlobal()) {
                event.getPlayer().sendMessage("Type \"/signtrader link\" to connect this sign with a chest.");
            }
        }
    }
}

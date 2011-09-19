package tk.allele.duckshop;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import tk.allele.duckshop.signs.SignManager;
import tk.allele.duckshop.signs.TradingSign;
import tk.allele.permissions.PermissionsException;
import tk.allele.permissions.PermissionsManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds the state of any chest links in progress.
 */
public class LinkState {
    final SignManager manager;
    final PermissionsManager permissions;
    final Map<Player, Boolean> playerStartedLink = new HashMap<Player, Boolean>();
    final Map<Player, TradingSign> playerLinkSign = new HashMap<Player, TradingSign>();

    public LinkState(SignManager manager, PermissionsManager permissions) {
        this.manager = manager;
        this.permissions = permissions;
    }

    public void startLink(Player player) {
        playerStartedLink.put(player, Boolean.TRUE);
    }

    public boolean hasStartedLink(Player player) {
        return playerStartedLink.containsKey(player);
    }

    public void markSign(Player player, TradingSign sign) {
        playerLinkSign.put(player, sign);
    }

    public boolean hasMarkedSign(Player player) {
        return playerLinkSign.containsKey(player);
    }

    public void markChest(Player player, Location location) {
        TradingSign sign = playerLinkSign.get(player);
        sign.setChestLocation(location);
        cancelLink(player);
    }

    public void cancelLink(Player player) {
        playerStartedLink.remove(player);
        playerLinkSign.remove(player);
    }
}

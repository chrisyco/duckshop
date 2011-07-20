package tk.kirlian.util.protection;

import java.util.List;
import java.util.LinkedList;
import org.bukkit.plugin.Plugin;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import tk.kirlian.util.protection.methods.*;

/**
 * Manages multiple {@link ProtectionMethod}s.
 */
public class ProtectionManager {
    private Plugin plugin;
    private List<ProtectionMethod> methods;

    public ProtectionManager(Plugin plugin) {
        this.plugin = plugin;
        this.methods = new LinkedList<ProtectionMethod>();
        // vvv Add methods below vvv
        registerMethod(new LocketteMethod(plugin));
        //registerMethod(new FailMethod(plugin)); // Testing only
        // ^^^ Add methods above ^^^
    }

    /**
     * Register a new method.
     */
    public void registerMethod(ProtectionMethod method) {
        methods.add(method);
    }

    /**
     * Get the list of methods.
     */
    public List<ProtectionMethod> getMethods() {
        return methods;
    }

    /**
     * Get the list of enabled methods.
     */
    public List<ProtectionMethod> getEnabledMethods() {
        List<ProtectionMethod> enabledMethods = new LinkedList<ProtectionMethod>();
        for(ProtectionMethod method : methods) {
            if(method.isEnabled()) {
                enabledMethods.add(method);
            }
        }
        return enabledMethods;
    }

    /**
     * Return whether any of the protection methods are enabled.
     */
    public boolean isEnabled() {
        for(ProtectionMethod method : methods) {
            if(method.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether a player with this name can access a block.
     */
    public boolean canAccess(String playerName, Block block) {
        for(ProtectionMethod method : methods) {
            if(!method.canAccess(playerName, block)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether a player can access a block.
     */
    public boolean canAccess(Player player, Block block) {
        for(ProtectionMethod method : methods) {
            if(!method.canAccess(player, block)) {
                return false;
            }
        }
        return true;
    }
}

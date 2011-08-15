package tk.allele.permissions;

import org.bukkit.plugin.Plugin;
import tk.allele.permissions.methods.OpsOnlyPermissions;
import tk.allele.permissions.methods.TheYetiPermissions;
import tk.allele.util.priority.PriorityComparator;

import java.util.PriorityQueue;
import java.util.logging.Logger;

public class PermissionsManager {
    private PriorityQueue<PermissionsMethod> queue;

    public PermissionsManager(Plugin plugin, Logger log, String prefix) {
        queue = new PriorityQueue<PermissionsMethod>(4, new PriorityComparator());
        /* vvv Add new methods below vvv */
        registerMethod(new OpsOnlyPermissions(plugin, log));
        registerMethod(new TheYetiPermissions(plugin, log, prefix));
        /* ^^^ Add new providers above ^^^ */
    }

    public void registerMethod(PermissionsMethod method) {
        queue.offer(method);
    }

    /**
     * Get the PermissionsMethod with the lowest priority that is available.
     *
     * @return the best PermissionsMethod, or if there are no available
     *         methods, null.
     */
    public PermissionsMethod getBest() {
        PermissionsMethod method = queue.peek();
        if(method == null || !method.isAvailable()) {
            return null;
        } else {
            return method;
        }
    }

    /**
     * Reorder the queue, if anything has changed since the last rehash.
     */
    public void rehash() {
        PermissionsMethod[] items = (PermissionsMethod[]) queue.toArray();
        queue.clear();
        for (PermissionsMethod item : items) {
            queue.offer(item);
        }
    }
}

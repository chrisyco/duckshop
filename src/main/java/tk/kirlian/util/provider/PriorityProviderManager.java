package tk.kirlian.util.provider;

import java.util.PriorityQueue;

/**
 * Manages multiple implementations of a service and automatically
 * chooses the best one.
 * @see Provider
 */
public class PriorityProviderManager<P extends Provider> implements ProviderManager<P> {
    private PriorityQueue<P> queue;

    /**
     * Create a new ProviderManager instance.
     */
    public PriorityProviderManager() {
        queue = new PriorityQueue<P>(4, new PriorityProviderComparator());
    }

    /**
     * Add a provider to the queue.
     */
    public void register(P provider) {
        queue.offer(provider);
    }

    /**
     * Get the provider with the lowest priority that is available.
     *
     * @return the best {@link Provider}, or if there are no available
     *         providers, null.
     */
    public P getBest() {
        P provider = queue.peek();
        if(provider == null || !provider.isAvailable()) {
            return null;
        } else {
            return provider;
        }
    }

    /**
     * Reorder the queue, if anything has changed since the last rehash.
     */
    public void rehash() {
        P[] items = (P[]) queue.toArray();
        queue.clear();
        for(int i = 0; i < items.length; ++i) {
            queue.offer(items[i]);
        }
    }
}

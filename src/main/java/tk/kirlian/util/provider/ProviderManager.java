package tk.kirlian.util.provider;

/**
 * Keeps track of multiple Providers. A class that implements this
 * interface would usually provide a way to choose one of the Providers
 * that were registered.
 * @see Provider
 */
public interface ProviderManager<P extends Provider> {
    /**
     * Register a {@link Provider} with this ProviderManager.
     */
    public void register(P provider);
}

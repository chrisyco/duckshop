package tk.kirlian.util.provider;

/**
 * A single implementation of a service.
 * @see ProviderManager
 */
public interface Provider {
    /**
     * Return the name of this Provider.
     */
    public String getName();
    /**
     * Get the priority of this Provider. The
     * {@link PriorityProviderManager} will choose the Provider with the
     * lowest priority available.
     */
    public int getPriority();
    /**
     * Decide if this particular implementation of the service is
     * available. The {@link PriorityProviderManager} will choose the
     * Provider with the lowest priority that returns true for this
     * method.
     */
    public boolean isAvailable();
}

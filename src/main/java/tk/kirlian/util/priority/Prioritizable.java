package tk.kirlian.util.priority;

/**
 * Represents an object that can be ranked in relation to other similar objects.
 */
public interface Prioritizable {
    /**
     * Get the priority of this object. Lower priorities are chosen first.
     */
    public int getPriority();

    /**
     * Decide if this particular object is available.
     * <p>
     * If it is not available, the object should not be used.
     */
    public boolean isAvailable();
}

package tk.allele.util.priority;

import java.util.Comparator;

/**
 * A Comparator that compares {@link Prioritizable}s based on their priority.
 *
 * @see Prioritizable#getPriority()
 */
public class PriorityComparator implements Comparator<Prioritizable> {
    public int compare(Prioritizable o1, Prioritizable o2) {
        if (o1.isAvailable() && !o2.isAvailable()) {
            return -1;
        } else if (!o1.isAvailable() && o2.isAvailable()) {
            return 1;
        } else {
            return o1.getPriority() - o2.getPriority();
        }
    }
}

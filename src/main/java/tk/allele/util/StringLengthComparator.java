package tk.allele.util;

import java.util.Comparator;

/**
 * A comparator that orders strings based on their length, rather than their
 * character values.
 *
 * <p>If two strings have equal length but different characters, they will
 * still be treated as unequal.
 */
public class StringLengthComparator implements Comparator<String> {
    private static final StringLengthComparator instance = new StringLengthComparator();

    private StringLengthComparator() {}

    /**
     * Get an instance of this singleton.
     */
    public static StringLengthComparator getInstance() {
        return instance;
    }

    @Override
    public int compare(String left, String right) {
        int result = left.length() - right.length();
        if(result != 0) {
            // Different lengths
            return result;
        } else {
            // Same lengths --> compare normally
            return left.compareTo(right);
        }
    }
}

package tk.kirlian.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Some simple tools for manipulating strings.
 */
public class StringTools {
    private StringTools() {}
    /**
     * Join a {@link Collection} of objects into a single String.
     * @return The concatenation of the items in the collection,
     * separated by the delimiter.
     */
    public static <T>
    String join(final Collection<T> objs, final String delimiter) {
        if (objs == null || objs.isEmpty()) {
            return "";
        } else {
            Iterator<T> iter = objs.iterator();
            StringBuilder buffer = new StringBuilder(iter.next().toString());
            while(iter.hasNext()) {
                buffer.append(delimiter).append(iter.next().toString());
            }
            return buffer.toString();
        }
    }

    /**
     * Return a new string with the first letter uppercase and the rest
     * lowercase.
     */
    public static String capitalizeFirstLetter(final String s) {
        if(s.length() == 0) {
            return s;
        } else if(s.length() == 1) {
            return s.toUpperCase();
        } else {
            return s.substring(0, 1).toUpperCase() +
                   s.substring(1).toLowerCase();
        }
    }
}

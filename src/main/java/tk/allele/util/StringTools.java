package tk.allele.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Some simple tools for manipulating strings.
 */
public class StringTools {
    private StringTools() {}

    /**
     * Join a {@link Collection} of objects into a single String.
     *
     * @return The concatenation of the items in the collection,
     * separated by the delimiter.
     */
    public static String join(Iterable<? extends Object> list, String separator) {
        Iterator<? extends Object> iter;
        if(list == null) {
            return "";
        } else {
            iter = list.iterator();
            if(!iter.hasNext()) {
                return "";
            }
        }
        StringBuilder buffer = new StringBuilder(String.valueOf(iter.next()));
        while(iter.hasNext()) {
            buffer.append(separator).append(iter.next());
        }
        return buffer.toString();
    }
}

package tk.allele.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some simple tools for manipulating strings.
 */
public class StringTools {
    private static final Pattern usernamePattern = Pattern.compile("\\w+");

    private StringTools() {
    }

    /**
     * Join a {@link Collection} of objects into a single String.
     *
     * @return The concatenation of the items in the collection,
     *         separated by the delimiter.
     */
    public static String join(Iterable<?> list, String separator) {
        Iterator<?> iter;
        if (list == null) {
            return "";
        } else {
            iter = list.iterator();
            if (!iter.hasNext()) {
                return "";
            }
        }
        StringBuilder buffer = new StringBuilder(String.valueOf(iter.next()));
        while (iter.hasNext()) {
            buffer.append(separator).append(iter.next());
        }
        return buffer.toString();
    }

    /**
     * Test whether a string is a valid username.
     */
    public static boolean isValidUsername(String username) {
        Matcher usernameMatcher = usernamePattern.matcher(username);
        return usernameMatcher.matches();
    }
}

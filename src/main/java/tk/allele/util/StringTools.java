package tk.allele.util;

import java.text.BreakIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some simple tools for manipulating strings.
 */
public class StringTools {
    private static final Pattern usernamePattern = Pattern.compile("\\w+");

    private StringTools() {}

    /**
     * Join a {@link Collection} of objects into a single String.
     *
     * @return The concatenation of the items in the collection,
     * separated by the delimiter.
     */
    public static String join(Iterable<?> list, String separator) {
        Iterator<?> iter;
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

    public static String capitalizeFirstLetter(String s) {
        if(s.length() == 0) return s;
        StringBuilder buffer = new StringBuilder(s.length());
        buffer.append(Character.toUpperCase(s.charAt(0)));
        buffer.append(s.substring(1));
        return buffer.toString();
    }

    /**
     * Capitalize the first letter of each word.
     */
    public static String toTitleCase(String s) {
        StringBuilder buffer = new StringBuilder(s.length());

        BreakIterator wordBreaker = BreakIterator.getWordInstance();
        wordBreaker.setText(s);

        int start = wordBreaker.first();
        for(int end = wordBreaker.next();
            end != BreakIterator.DONE;
            start = end, end = wordBreaker.next()) {
            String word = capitalizeFirstLetter(s.substring(start, end));
            buffer.append(word);
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

    /**
     * Combine each string in the first iterable with each string in the second,
     * and so on.
     */
    public static Set<String> combineStrings(Iterable<String>... wordsList) {
        Set<String> resultSet = new HashSet<String>();
        combineStringsInternal(wordsList, 0, new ArrayDeque<String>(wordsList.length), resultSet);
        return resultSet;
    }

    private static void combineStringsInternal(Iterable<String>[] wordsList, int wordIndex, Deque<String> result, Set<String> resultSet) {
        if(wordIndex < wordsList.length) {
            Iterable<String> words = wordsList[wordIndex];
            for(String word : words) {
                result.addLast(word);
                combineStringsInternal(wordsList, wordIndex + 1, result, resultSet);
                result.removeLast();
            }
        } else {
            resultSet.add(StringTools.join(result, ""));
        }
    }
}

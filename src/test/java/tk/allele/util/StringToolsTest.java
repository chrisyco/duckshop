package tk.allele.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class StringToolsTest {
    @Test
    public void testJoin() throws Exception {
        assertEquals("Normal use",
                "a,aa,aaa",
                StringTools.join(Arrays.asList("a", "aa", "aaa"), ","));
        assertEquals("Empty strings",
                " | b |  | ",
                StringTools.join(Arrays.asList("", "b", "", ""), " | "));
        assertEquals("Singleton list",
                "c",
                StringTools.join(Collections.singletonList("c"), "FAIL"));
        assertEquals("Empty list",
                "",
                StringTools.join(Collections.emptyList(), "FAIL"));
    }

    @Test
    public void testCapitalizeFirstLetter() throws Exception {
        assertEquals("Normal use",
                "Hello",
                StringTools.capitalizeFirstLetter("hello"));
        assertEquals("Already capitalized",
                "Hello",
                StringTools.capitalizeFirstLetter("Hello"));
        assertEquals("Random capitalization",
                "HELlO",
                StringTools.capitalizeFirstLetter("hELlO"));
        assertEquals("Empty string",
                "",
                StringTools.capitalizeFirstLetter(""));
        assertEquals("Random symbols",
                "A$$_b",
                StringTools.capitalizeFirstLetter("a$$_b"));
        assertEquals("Spaces",
                "  h eL  Lo",
                StringTools.capitalizeFirstLetter("  h eL  Lo"));
    }

    @Test
    public void testToTitleCase() throws Exception {
        assertEquals("One word",
                "Hello",
                StringTools.toTitleCase("hello"));
        assertEquals("Already capitalized",
                "Hello",
                StringTools.toTitleCase("Hello"));
        assertEquals("Spaces",
                "  Hello ",
                StringTools.toTitleCase("  hello "));
        assertEquals("Multiple words",
                "Hello, World!",
                StringTools.toTitleCase("hello, world!"));
    }

    @Test
    public void testIsValidUsername() throws Exception {
        assertTrue("Letters",
                StringTools.isValidUsername("aAbBcC"));
        assertTrue("Numbers",
                StringTools.isValidUsername("012345"));
        assertTrue("Letters and numbers",
                StringTools.isValidUsername("01ab23CD45eF"));
        assertTrue("Underscores",
                StringTools.isValidUsername("__val_id__"));
        assertFalse("Spaces",
                StringTools.isValidUsername("inval id"));
        assertFalse("Punctuation",
                StringTools.isValidUsername("adot.inthemiddle"));
    }
}

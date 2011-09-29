package tk.allele.itemdb;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemDBTest {
    @Test
    public void testNormalizeName() {
        assertEquals("Already normalized",
                "diamond",
                ItemDB.normalizeName("diamond"));
        assertEquals("Capital letters",
                "diamond",
                ItemDB.normalizeName("DiaMonD"));
        assertEquals("Spaces and puncuation and numbers oh my!",
                "d123iamo5nd",
                ItemDB.normalizeName("-d123i. a _m!!o5 [ n] d  "));
    }
}

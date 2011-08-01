package tk.kirlian.duckshop.items;

import org.junit.*;
import tk.kirlian.util.Pair;

import static org.junit.Assert.*;

public class ItemDBTest {
    private static ItemDB defaultDB;
    private static ItemDB testDB;

    @BeforeClass
    public static void setUp() {
        defaultDB = ItemDB.getDefault();
        testDB = ItemDB.fromResourcePath("/test_item_aliases.yml");
    }

    @Test
    public void itemSearch() {
        ItemDefinition byId;
        ItemDefinition byAlias;
        // Zero durability
        byId = testDB.getItemById(202, (short)0);
        byAlias = testDB.getItemByAlias("wobbuffet");
        assertEquals(byId, byAlias);
        // Custom durability values
        byId = testDB.getItemById(0, (short)2);
        byAlias = testDB.getItemByAlias("friedegg");
        assertEquals(byId, byAlias);
    }

    @Test
    public void multipleAliases() {
        ItemDefinition egg = testDB.getItemByAlias("egg");
        ItemDefinition alsoEgg;
        alsoEgg = testDB.getItemByAlias("rawegg");
        assertEquals(egg, alsoEgg);
        alsoEgg = testDB.getItemByAlias("unbornchicken");
        assertEquals(egg, alsoEgg);
    }

    @Test
    public void normalization() {
        ItemDefinition wobbuffet = testDB.getItemByAlias("wobbuffet");
        ItemDefinition test;
        // Mixed case
        test = testDB.getItemByAlias("woBbuFfeT");
        assertEquals(wobbuffet, test);
        // Spaces
        test = testDB.getItemByAlias(" w        o bB u fFe t  ");
        assertEquals(wobbuffet, test);
        // Plurals (Wobbuffets?)
        test = testDB.getItemByAlias("WobBUffEtS");
        assertEquals(wobbuffet, test);
    }

    @Test
    public void invalidAlias() {
        ItemDefinition itemDfn = testDB.getItemByAlias("notwobbuffet");
        assertNull(itemDfn);
    }

    @Test
    public void invalidId() {
        ItemDefinition itemDfn;
        // Invalid type ID
        itemDfn = testDB.getItemById(123456789, (short)0);
        assertNull(itemDfn);
        // Valid ID (egg), but invalid durability value
        itemDfn = testDB.getItemById(0, (short)32323);
        assertNull(itemDfn);
    }

    @Test
    public void shortNamesIncludedInAliases() {
        for(Pair<Integer, Short> idPair : defaultDB.getDataValuesSet()) {
            ItemDefinition baseDfn = defaultDB.getItemById(idPair);
            ItemDefinition testDfn = defaultDB.getItemByAlias(baseDfn.getShortName());
            //System.out.println(idPair.toString());
            assertTrue(baseDfn.getShortName() + " is not included in aliases field",
                       baseDfn.equals(testDfn));
        }
    }

}

// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {

    public void testStrings() {
        List<String> rules = Arrays.asList("x", "y", "x", "z", null, "a", "b");
        Taboo<String> taboo = new Taboo<>(rules);
        assertEquals(new HashSet<>(Arrays.asList("y", "z")), taboo.noFollow("x"));
        assertEquals(new HashSet<>(Collections.singletonList("b")), taboo.noFollow("a"));
        assertEquals(Collections.emptySet(), taboo.noFollow("b"));
        assertEquals(Collections.emptySet(), taboo.noFollow("w"));
    }

    public void testIntegers() {
        List<Integer> rules = Arrays.asList(1, 2, 1, 3, null, 4, 5);
        Taboo<Integer> taboo = new Taboo<>(rules);
        assertEquals(new HashSet<>(Arrays.asList(2, 3)), taboo.noFollow(1));
        assertEquals(new HashSet<>(Collections.singletonList(5)), taboo.noFollow(4));
        assertEquals(Collections.emptySet(), taboo.noFollow(5));
        assertEquals(Collections.emptySet(), taboo.noFollow(10));
    }

    public void testWithNulls() {
        List<String> rules = Arrays.asList("a", "b", null, "c", "d");
        Taboo<String> taboo = new Taboo<>(rules);
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        taboo.reduce(list);
        assertEquals(Arrays.asList("a", "c", "e"), list);
    }

    public void testEmpty() {
        List<Integer> rules = Arrays.asList(1, 2);
        Taboo<Integer> taboo = new Taboo<>(rules);
        List<Integer> list = new ArrayList<>();
        taboo.reduce(list);
        assertTrue(list.isEmpty());
    }

    public void testNoMatch() {
        List<String> rules = Arrays.asList("x", "y");
        Taboo<String> taboo = new Taboo<>(rules);
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        taboo.reduce(list);
        assertEquals(Arrays.asList("a", "b", "c"), list);
    }

    public void testCycle() {
        List<Integer> rules = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            rules.add(i);
        }
        Taboo<Integer> taboo = new Taboo<>(rules);
        List<Integer> list = new ArrayList<>(rules);
        taboo.reduce(list);
        assertEquals(Collections.singletonList(1), list);
    }

    public void testOne() {
        List<String> rules = Arrays.asList("a");
        Taboo<String> taboo = new Taboo<>(rules);
        assertEquals(Collections.emptySet(), taboo.noFollow("a"));
    }

    public void testOnlyNulls() {
        List<String> rules = Arrays.asList(null, null, null);
        Taboo<String> taboo = new Taboo<>(rules);
        assertEquals(Collections.emptySet(), taboo.noFollow("a"));
    }
}

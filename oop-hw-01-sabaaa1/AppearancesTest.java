import junit.framework.TestCase;

import java.util.*;

public class AppearancesTest extends TestCase {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}
	
	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}
	
	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}
	public void testEmpty() {
		List<Integer> a = Collections.emptyList();
		assertEquals(0, Appearances.sameCount(a, Collections.emptyList()));
		assertEquals(0, Appearances.sameCount(a, Arrays.asList(1, 2, 3, 4)));
		assertEquals(0, Appearances.sameCount(a, Collections.singletonList(1)));
	}
	public void testEmptyB() {
		List<Integer> a = Arrays.asList(1, 2, 3);
		assertEquals(0, Appearances.sameCount(a, Collections.emptyList()));
	}
	public void testKeySensitivity() {
		List<String> a = stringToList("ACB");
		List<String> b = stringToList("acb");
		List<String> c = stringToList("AcB");
		assertEquals(0, Appearances.sameCount(a, b));
		assertEquals(2, Appearances.sameCount(a, c));
	}
	public void testMany() {
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5, 1, 2, 3);
		assertEquals(0, Appearances.sameCount(a, Arrays.asList(6, 7, 8, 9)));
		assertEquals(3, Appearances.sameCount(
						Arrays.asList(1, 3, 1, 2, 2, 3, 4, 4, 5),
						Arrays.asList(1, 1, 2, 2, 3, 5, 3, 5, 4)
				));
	}
}

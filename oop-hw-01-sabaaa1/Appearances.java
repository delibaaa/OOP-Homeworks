import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */


	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		Map<Integer, Integer> hashA = new HashMap<>();
		Map<Integer, Integer> hashB = new HashMap<>();
		for (T item : a) {
			hashA.putIfAbsent(item.hashCode(), 0);
			hashA.put(item.hashCode(), hashA.get(item.hashCode()) + 1);
		}
		for (T item : b) {
			hashB.putIfAbsent(item.hashCode(), 0);
			hashB.put(item.hashCode(), hashB.get(item.hashCode()) + 1);
		}
		int count = 0;
		for(int aElems : hashA.keySet()) {
			if(hashA.get(aElems) == hashB.get(aElems)) {
				count++;
			}
		}
		return count;
	}
	
}

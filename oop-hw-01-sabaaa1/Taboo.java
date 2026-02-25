
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	Map <T,Set<T>> elems = new HashMap<T, Set<T>> ();
	public Taboo(List<T> rules) {
		//if(rules.size() <= 1) return;
		for(int i = 0 ; i < rules.size() - 1 ; i++) {
			if(rules.get(i) != null && rules.get(i+1) != null) {
				this.elems.putIfAbsent(rules.get(i), new HashSet<>());
				this.elems.get(rules.get(i)).add(rules.get(i + 1));
			}
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {

		if (!this.elems.containsKey(elem)) {
			return Collections.emptySet();
		}
		return this.elems.get(elem);
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		boolean change = false;
        do {
            change = false;
            for (int i = list.size() - 1; i >= 1; i--) {
                T currElem = list.get(i);
                T prevElem = list.get(i - 1);
                if (this.elems.containsKey(prevElem) && this.elems.get(prevElem).contains(currElem)) {
                    list.remove((int) (i));
                    change = true;
                }
            }
        } while (change);
	}
}

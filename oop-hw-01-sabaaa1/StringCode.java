import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	private static int startWith(String str, int i){
		char currChar = str.charAt(i);
		int currLen = 0;
		while(true){
			if(i + currLen >= str.length() || currChar != str.charAt(i + currLen)) break;
			currLen++;
		}
		return currLen;
	}
	public static int maxRun(String str) {
		int currentMax = 0;
		for	(int i = 0 ; i < str.length() ; i++) {
			currentMax = Math.max(startWith(str,i), currentMax);
		}
		return currentMax;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		String res = "";
		for(int i = 0 ; i < str.length() ; i++) {
			if(!Character.isDigit(str.charAt(i)) ) res += (str.charAt(i));
			else if(i < str.length() - 1 ) {
				for(int j = 0; j < Character.getNumericValue(str.charAt(i)); j++) {
					res+=(str.charAt(i + 1));
				}
			}
		}
		return res;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {

		HashSet<String> forA = new HashSet<>();
		for(int i = 0 ; i <= a.length() - len ; i++) {
			forA.add(a.substring(i, i+len));
		}

		for(int i = 0 ; i <= b.length() - len ; i++) {
			if(forA.contains(b.substring(i, i+len))) return true;
		}
		return false;
	}
}

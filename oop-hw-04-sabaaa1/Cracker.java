// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	public static CountDownLatch latch;
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
		if( args.length == 1){
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] hash = md.digest(args[0].getBytes());
			System.out.println(hexToString(hash));
		}
		else if (args.length < 1) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		}
		else{
			stringToHex(args);
			latch.await();
			System.out.println("all done");
		}
	}

	private static void stringToHex(String[] args) throws NoSuchAlgorithmException {
		String targ = args[0];
		int len = Integer.parseInt(args[1]);
		int num = 1;
		if (args.length>2) {
			num = Integer.parseInt(args[2]);
		}
		int charNumForThread = CHARS.length / num;
		int extra = CHARS.length %num;
		byte[] target = hexToArray(targ);
		latch = new CountDownLatch(num);
		int firstSym = 0;
		int i = 0;
		while (true) {
			if (i >= num) return;
			CrackerWorkers w;

			// checkExtra();
			if (i <extra) {
				w =new CrackerWorkers(target,latch,firstSym, 1 + firstSym + charNumForThread, len, MessageDigest.getInstance("SHA"));
				firstSym =firstSym +charNumForThread + 1;
			} else {
				w =new CrackerWorkers(target,latch,firstSym, firstSym + charNumForThread, len, MessageDigest.getInstance("SHA"));
				firstSym =firstSym +charNumForThread;
			}
			w.start();
			i++;
		}
	}
}

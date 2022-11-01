package utils;

import java.math.BigInteger;

/**
 * 
 * This is class containing methods for BigInteger comparison
 *
 */
public class CompareBigNumers {

	/**
	 * 
	 * @param a
	 * @param b
	 * @return returns true if a<=b else false
	 */
	public static boolean lesserOrEqual(BigInteger a, BigInteger b) {
		return a.compareTo(b)== 0 || a.compareTo(b) == -1;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return returns true if a>=b else false
	 */
	public static boolean greaterOrEqual(BigInteger a, BigInteger b) {
		return a.compareTo(b)== 0 || a.compareTo(b) == 1;
	}
	
}

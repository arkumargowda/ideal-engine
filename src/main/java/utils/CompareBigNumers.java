package utils;

import java.math.BigInteger;

public class CompareBigNumers {

	
	public static boolean lesserOrEqual(BigInteger a, BigInteger b) {
		return a.compareTo(b)== 0 || a.compareTo(b) == -1;
	}

	
	public static boolean greaterOrEqual(BigInteger a, BigInteger b) {
		return a.compareTo(b)== 0 || a.compareTo(b) == 1;
	}
	
}

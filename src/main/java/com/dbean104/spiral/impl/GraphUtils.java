package com.dbean104.spiral.impl;

public class GraphUtils {

	private GraphUtils() { /* Avoid instantiation */ }
	
	/**
	 * Returns 1 if boolean is true
	 * @param isPent
	 * @return
	 */
	public static int pentTo1(boolean isPent) {
		return isPent ? 1 : 0;
	}
	
	/**
	 * Returns 5 if true (a pentagon) and 6 if false (a hexagon)
	 * @param b
	 * @return
	 */
	public static int boolToHexOrPent(boolean b) {
		return b ? 5 : 6;
	}
}

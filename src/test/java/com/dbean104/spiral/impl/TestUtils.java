package com.dbean104.spiral.impl;

public class TestUtils {

	public static boolean[] indexFrom1ArrayToSpiral(int[] array, int m) {
		boolean[] spiral = new boolean[m];
		for (int i : array)
			spiral[i-1] = true;
		return spiral;
	}
}

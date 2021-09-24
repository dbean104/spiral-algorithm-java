package com.dbean104.spiral.impl;

import org.junit.Test;

public class WindupTest {

	@Test
	public void test() {
		int[] array = new int[] {1,2,3,4,5,7,8,10,11,12,13,14};
		boolean[] spiral = indexFrom1ArrayToSpiral(array, 14);
		Windup.windup(spiral, spiral.length, false, new boolean[spiral.length][spiral.length]);
	}
	
	private boolean[] indexFrom1ArrayToSpiral(int[] array, int m) {
		boolean[] spiral = new boolean[m];
		for (int i : array)
			spiral[i-1] = true;
		return spiral;
	}
}

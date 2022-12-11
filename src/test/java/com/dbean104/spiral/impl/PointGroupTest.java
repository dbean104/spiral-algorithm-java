package com.dbean104.spiral.impl;

import org.junit.Assert;
import org.junit.Test;

public class PointGroupTest {

	@Test
	public void test() {
		test("C1", 1, null);
		test("Ci", 2, getMS(0,0));
		test("C2", 2, getMS(1,2));
		test("Cs", 2, getMS(1,4));
		test("C3", 3, null);
		test("S4", 4, getMS(1,1));
		test("D2", 4, getMS(1,3));
		test("C2h", 4, getMS(1,5));
		test("C2v", 4, getMS(3,2));
		test("S6", 6, getMS(0,0));
		test("D3", 6, getMS(1,2));
		test("C3h", 6, getMS(1,6));
		test("C3v", 6, getMS(5,2));
		test("D2d", 8, getMS(3,1));
		test("D2h", 8, getMS(3,3));
		test("D5", 10, null);
		test("T", 12, getMS(0,0));
		test("D6", 12, getMS(5,1,3,0,1,2));
		test("D3d", 12, getMS(5,1,3,0,1,4));
		test("D3h", 12, getMS(5,1,3,2));
		test("D5d", 20, getMS(0,0));
		test("D5h", 20, getMS(3,2));
		test("Th", 24, getMS(0,0));
		test("Td", 24, getMS(5,2));
		test("D6d", 24, getMS(11,1));
		test("D6h", 24, getMS(11,1,3,2));
		test("I", 60, null);
		test("Ih", 120, null);
	}
	
	private void test(String expectedPointGroup, int order, int[] ms) {
		Assert.assertEquals(expectedPointGroup, PointGroup.getGroup(order, ms));
	}
	
	private int[] getMS(int ... mappings) {
		final int[] ms = new int[12];
		for (int i = 0; i < mappings.length; i += 2)
		ms[mappings[i]] = mappings[i+1];
		return ms;
	}
}

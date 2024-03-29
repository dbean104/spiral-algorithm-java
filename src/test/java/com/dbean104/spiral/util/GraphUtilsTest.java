package com.dbean104.spiral.util;

import static com.dbean104.spiral.util.GraphUtils.boolToHexOrPent;
import static com.dbean104.spiral.util.GraphUtils.getFaceCount;
import static com.dbean104.spiral.util.GraphUtils.getNuclearity;
import static com.dbean104.spiral.util.GraphUtils.pentTo1;
import static com.dbean104.spiral.util.GraphUtils.verifyNuclearity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GraphUtilsTest {

	@Test
	void testPentTo1() {
		assertEquals(0, pentTo1(false));
		assertEquals(1, pentTo1(true));
	}
	
	@Test
	void testBoolToHexOrPent() {
		assertEquals(5, boolToHexOrPent(true));
		assertEquals(6, boolToHexOrPent(false));
	}
	
	@Test
	void testGetFaceCount() {
		assertEquals(12, getFaceCount(20));
		assertEquals(32, getFaceCount(60));
	}
	
	@Test
	void testGetNuclearity() {
		assertEquals(20, getNuclearity(12));
		assertEquals(60, getNuclearity(32));
	}
	
	@Test
	void testGetNuclearityThrowsExceptionOnNumberLessThan12() {
		assertThrows(IllegalArgumentException.class, () -> getNuclearity(10));
	}
	
	@Test
	void testVerifyNuclearityDoesNotThrowExceptionOnValidInput() {
		verifyNuclearity(20);
		verifyNuclearity(60);
	}
	
	@Test
	void testVerifyNuclearityThrowsExceptionOnOddNumber() {
		assertThrows(IllegalArgumentException.class, () -> verifyNuclearity(21));
	}
	
	@Test
	void testVerifyNuclearityThrowsExceptionNumberLessThan20() {
		assertThrows(IllegalArgumentException.class, () -> verifyNuclearity(18));
	}
	
}

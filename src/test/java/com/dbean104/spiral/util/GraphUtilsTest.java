package com.dbean104.spiral.util;

import static com.dbean104.spiral.util.GraphUtils.boolToHexOrPent;
import static com.dbean104.spiral.util.GraphUtils.getFaceCount;
import static com.dbean104.spiral.util.GraphUtils.getNuclearity;
import static com.dbean104.spiral.util.GraphUtils.pentTo1;
import static com.dbean104.spiral.util.GraphUtils.verifyNuclearity;

import org.junit.Assert;
import org.junit.Test;

public class GraphUtilsTest {

	@Test
	public void testPentTo1() {
		Assert.assertEquals(0, pentTo1(false));
		Assert.assertEquals(1, pentTo1(true));
	}
	
	@Test
	public void testBoolToHexOrPent() {
		Assert.assertEquals(5, boolToHexOrPent(true));
		Assert.assertEquals(6, boolToHexOrPent(false));
	}
	
	@Test
	public void testGetFaceCount() {
		Assert.assertEquals(12, getFaceCount(20));
		Assert.assertEquals(32, getFaceCount(60));
	}
	
	@Test
	public void testGetNuclearity() {
		Assert.assertEquals(20, getNuclearity(12));
		Assert.assertEquals(60, getNuclearity(32));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetNuclearityThrowsExceptionOnNumberLessThan12() {
		getNuclearity(10);
	}
	
	@Test
	public void testVerifyNuclearityDoesNotThrowExceptionOnValidInput() {
		verifyNuclearity(20);
		verifyNuclearity(60);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerifyNuclearityThrowsExceptionOnOddNumber() {
		verifyNuclearity(21);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerifyNuclearityThrowsExceptionNumberLessThan20() {
		verifyNuclearity(18);
	}
	
}

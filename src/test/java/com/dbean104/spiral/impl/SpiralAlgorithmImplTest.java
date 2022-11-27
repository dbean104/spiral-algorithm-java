package com.dbean104.spiral.impl;

import org.junit.jupiter.api.Test;

import com.dbean104.spiral.SpiralAlgorithm;

public class SpiralAlgorithmImplTest {

	@Test
	public void testMostBasic() {
		final SpiralAlgorithm spa = new SpiralAlgorithmImpl();
		spa.generateIsomers(20, false);
	}
	
	@Test
	public void testNonTrivial() {
		final SpiralAlgorithm spa = new SpiralAlgorithmImpl();
		spa.generateIsomers(24, false);
	}
	
/*	@Test
	public void testIcosohedralC60() {
		final SpiralAlgorithm spa = new SpiralAlgorithmImpl();
		spa.generateIsomers(60, true);
	}*/
}

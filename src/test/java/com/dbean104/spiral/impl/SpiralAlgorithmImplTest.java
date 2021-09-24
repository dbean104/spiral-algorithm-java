package com.dbean104.spiral.impl;

import org.junit.jupiter.api.Test;

import com.dbean104.spiral.SpiralAlgorithm;

public class SpiralAlgorithmImplTest {

	@Test
	public void test() {
		final SpiralAlgorithm spa = new SpiralAlgorithmImpl();
		spa.generateIsomers(24, false);
	}
}

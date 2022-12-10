package com.dbean104.spiral.impl;

import java.util.SortedSet;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.dbean104.spiral.FullereneIsomer;
import com.dbean104.spiral.SpiralAlgorithm;
import com.dbean104.spiral.output.OutputUtils;

public class SpiralAlgorithmImplTest {

	@Test
	public void testMostBasic() {
		final SpiralAlgorithm spa = new SpiralAlgorithmImpl();
		final SortedSet<FullereneIsomer> isomers = spa.generateIsomers(20, false);
		Assert.assertEquals(1, isomers.size());
	}
	
	@Test
	public void testNonTrivial() {
		final SpiralAlgorithm spa = new SpiralAlgorithmImpl();
		final SortedSet<FullereneIsomer> isomers = spa.generateIsomers(24, false);
		Assert.assertEquals(1, isomers.size());
	}
	
	@Test
	public void testMoreThanOneFound() {
		final int nuclearity = 32;
		final SpiralAlgorithm spa = new SpiralAlgorithmImpl();
		final SortedSet<FullereneIsomer> isomers = spa.generateIsomers(nuclearity, false);
//		Assert.assertEquals(2, isomers.size());
		OutputUtils.dumpResult(nuclearity, isomers);
	}
	
	@Test
	public void testIcosohedralC60() {
		final SpiralAlgorithm spa = new SpiralAlgorithmImpl();
		final SortedSet<FullereneIsomer> isomers = spa.generateIsomers(60, true);
		Assert.assertEquals(1, isomers.size());
	}
	
	
}

package com.dbean104.spiral.atlas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import com.dbean104.spiral.FullereneIsomer;
import com.dbean104.spiral.SpiralAlgorithm;
import com.dbean104.spiral.output.atlas.IsomerOutputWriterImpl;

class SpiralAlgorithmImplTest {

	@Test
	void testMostBasic() {
		test(20, false, 1, false);
	}
	
	@Test
	void testNonTrivial() {
		test(24, false, 1, false);
	}
	
	@Test
	void testMoreThanOneFound() {
		test(32, false, 6, false);
	}
	
	@Test
	void testOutputMatchesC36BookExample() {
		test(36, false, 15, true);
	}
	
	@Test
	void testIcosohedralC60() {
		test(60, true, 1, false);
	}
	
//	@Test
	void testIsolatedPentagonOutputMatchesC86BookExample() {
		// Takes about 10 minutes to run
		test(86, true, 19, true);
	}
	
	private static void test(int nuclearity, boolean isIsolatedPentagons, int expectedIsomerNumber, boolean dumpResult) {
		final SpiralAlgorithm spa = SpiralAlgorithmImpl.getDefaultInstance();
		final SortedSet<FullereneIsomer> isomers = spa.generateIsomers(nuclearity, isIsolatedPentagons);
		if (expectedIsomerNumber > 0) {
			assertEquals(expectedIsomerNumber, isomers.size());
		}
		if (dumpResult) {
			new IsomerOutputWriterImpl(System.out).dumpResult(nuclearity, isIsolatedPentagons, isomers);
		}
	}
	
	
}

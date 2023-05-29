package com.dbean104.spiral.atlas;

import static com.dbean104.spiral.util.TestUtils.indexFrom1ArrayToSpiral;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.dbean104.spiral.UnwindResult;
import com.dbean104.spiral.util.GraphUtils;

public class UnwindImplTest {

	@Test
	public void testUnwindWithMostBasic() {
		final int[] pentagonPositions = new int[] {1,2,3,4,5,6,7,8,9,10,11,12};
		testUnwind(pentagonPositions, 20, false, "Ih");
	}
	
	@Test
	public void testUnwindWithLowestC24Isomer() {
		final int[] pentagonPositions = new int[] {1,2,3,4,5,7,8,10,11,12,13,14};
		testUnwind(pentagonPositions, 24, false, "D6d");
	}
	
	@Test
	public void testUnwindWithHighSymmetryC32Isomer() {
		final int[] pentagonPositions = new int[] {1,2,3,5,7,9,10,12,14,16,17,18};
		testUnwind(pentagonPositions, 32, false, "D3");
	}
	
	@Test
	public void testUnwindWithNotLowestC32Isomer() {
		final int[] pentagonPositions = new int[] {1,2,3,4,7,10,12,13,15,16,17,18};
		testUnwind(pentagonPositions, 32, false, null);
	}
	
	@Test
	public void testUnwindWithNotLowestC24Isomer() {
		final int[] pentagonPositions = new int[] {1,2,3,4,6,7,9,10,11,12,13,14};
		testUnwind(pentagonPositions, 24, false, null);
	}
	
	private static void testUnwind(int[] pentagonPositions, int nuclearity, boolean isIsolatedPentagons, String expectedPointGroup) {
		boolean[] spiral = indexFrom1ArrayToSpiral(pentagonPositions, GraphUtils.getFaceCount(nuclearity));
		// call windup to get the dual adjacency matrix first and assert that the test has been configured with a valid fullerence
		final boolean[][] dualAdjacencyMatrix = new boolean[spiral.length][spiral.length];
		final int windup = WindupImpl.getInstance().windup(spiral, isIsolatedPentagons, dualAdjacencyMatrix);
		assertEquals(0, windup);
		
		
		final UnwindResult unwindResult = UnwindImpl.getInstance().unwind(spiral, dualAdjacencyMatrix);
		if (expectedPointGroup == null) {
			assertNull(unwindResult);
		} else {
			assertNotNull(unwindResult);
			assertEquals(expectedPointGroup, unwindResult.getPointGroup());			
		}
	}
}

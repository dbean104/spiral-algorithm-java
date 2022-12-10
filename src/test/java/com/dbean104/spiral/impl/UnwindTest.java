package com.dbean104.spiral.impl;

import static com.dbean104.spiral.impl.TestUtils.indexFrom1ArrayToSpiral;

import org.junit.Assert;
import org.junit.Test;


public class UnwindTest {

	@Test
	public void testUnwindWithMostBasic() {
		final int[] pentagonPositions = new int[] {1,2,3,4,5,6,7,8,9,10,11,12};
		testUnwind(pentagonPositions, 20, false, 120);
	}
	
	@Test
	public void testUnwindWithLowestC24Isomer() {
		final int[] pentagonPositions = new int[] {1,2,3,4,5,7,8,10,11,12,13,14};
		testUnwind(pentagonPositions, 24, false, 24);
	}
	
	@Test
	public void testUnwind() {
		final int[] pentagonPositions = new int[] {1,2,3,4,7,10,12,13,15,16,17,18};
		testUnwind(pentagonPositions, 32, false, -1);
	}
	
	@Test
	public void testUnwindWithNotLowestC24Isomer() {
		final int[] pentagonPositions = new int[] {1,2,3,4,6,7,9,10,11,12,13,14};
		testUnwind(pentagonPositions, 24, false, -1);
	}
	
	private static void testUnwind(int[] pentagonPositions, int nuclearity, boolean isIsolatedPentagons, int expectedPointGroupOrder) {
		boolean[] spiral = indexFrom1ArrayToSpiral(pentagonPositions, GraphUtils.getFaceCount(nuclearity));
		// call windup to get the dual adjacency matrix first and assert that the test has been configured with a valid fullerence
		final boolean[][] dualAdjacencyMatrix = new boolean[spiral.length][spiral.length];
		final int windup = Windup.windup(spiral, isIsolatedPentagons, dualAdjacencyMatrix);
		Assert.assertEquals(0, windup);
		
		
		final UnwindResult unwindResult = Unwind.unwind(spiral, dualAdjacencyMatrix);
		if (expectedPointGroupOrder == -1) {
			Assert.assertNull(unwindResult);
		} else {
			Assert.assertNotNull(unwindResult);
			Assert.assertEquals(expectedPointGroupOrder, unwindResult.getPointGroupOrder());			
		}
	}
}

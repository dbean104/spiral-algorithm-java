package com.dbean104.spiral.impl;

import static com.dbean104.spiral.impl.TestUtils.indexFrom1ArrayToSpiral;

import org.junit.Assert;
import org.junit.Test;


public class UnwindTest {

	@Test
	public void testUnwindWithMostBasic() {
		// Call windup to get dual adjacency matrix first
		int[] array = new int[] {1,2,3,4,5,6,7,8,9,10,11,12};
		boolean[] spiral = indexFrom1ArrayToSpiral(array, GraphUtils.getFaceCount(20));
		final boolean isIsolatedPentagons = false;
		final boolean[][] dualAdjacencyMatrix = new boolean[spiral.length][spiral.length];
		final int windup = Windup.windup(spiral, isIsolatedPentagons, dualAdjacencyMatrix);
		Assert.assertEquals(0, windup);
		final int ier = Unwind.unwind(spiral, dualAdjacencyMatrix);
		Assert.assertEquals(0, ier);
	}
}

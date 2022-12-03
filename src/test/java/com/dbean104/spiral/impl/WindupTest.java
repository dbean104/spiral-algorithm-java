package com.dbean104.spiral.impl;

import static com.dbean104.spiral.impl.TestUtils.indexFrom1ArrayToSpiral;

import org.junit.Assert;
import org.junit.Test;

public class WindupTest {

	/**
	 * This test checks that a valid C24, 14-face fullerene with non-isolated pentagons will be validated by the wind-up algorithm
	 */
	@Test
	public void testWindupWithAdjacentPentagons() {
		int[] array = new int[] {1,2,3,4,5,7,8,10,11,12,13,14};
		boolean[] spiral = indexFrom1ArrayToSpiral(array, GraphUtils.getFaceCount(24));
		final boolean isIsolatedPentagons = false;
		final boolean[][] dualAdjacencyMatrix = new boolean[spiral.length][spiral.length];
		final int result = Windup.windup(spiral, isIsolatedPentagons, dualAdjacencyMatrix);
		Assert.assertEquals(0, result);
		// test some of the adjacency matrix
		// each face should be connected to the correct number of other faces
		for (int i = 0; i < spiral.length; i++) {
			final boolean[] connectionsForFace = dualAdjacencyMatrix[i];
			int adjacencyCount = 0;
			for (boolean b : connectionsForFace) {
				if (b)
					adjacencyCount++;
			}
			Assert.assertEquals("Failure in face " + i, GraphUtils.boolToHexOrPent(spiral[i]), adjacencyCount);
		}
		// the central pentagon should be attached to the surrounding atoms
		testAdjacencyMatrixRow(0, dualAdjacencyMatrix[0], new int[] {1,2,3,4,5});
		// and the final pentagon
		testAdjacencyMatrixRow(0, dualAdjacencyMatrix[13], new int[] {8,9,10,11,12});
	}
	
	/**
	 * This test checks that the C60 icosahedral fullerene with isolated pentagons will be validated by the wind-up algorithm
	 */
	@Test
	public void testWindupWithNonAdjacentPentagons() {
		int[] array = new int[] {1,7,9,11,13,15,18,20,22,24,26,32};
		boolean[] spiral = indexFrom1ArrayToSpiral(array, GraphUtils.getFaceCount(60));
		final boolean isIsolatedPentagons = true;
		final int result = Windup.windup(spiral, isIsolatedPentagons, new boolean[spiral.length][spiral.length]);
		Assert.assertEquals(0, result);
	}
	
	private void testAdjacencyMatrixRow(int rowNumber, boolean[] row, int[] expectedMatches) {
		int matchIdx = 0;
		for (int i = 0; i < row.length; i++) {
			if (matchIdx < expectedMatches.length && expectedMatches[matchIdx] == i) {
				Assert.assertTrue("Error on row " + rowNumber + " expected match " + i, row[i]);
				matchIdx++;
			} else {
				Assert.assertFalse("Error on row " + rowNumber + " didn't expect match " + i, row[i]);
			}
		}
	}
}

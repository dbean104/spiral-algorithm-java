package com.dbean104.spiral.impl;

import static com.dbean104.spiral.impl.GraphUtils.boolToHexOrPent;
import static com.dbean104.spiral.impl.GraphUtils.pentTo1;

public class Windup {

	/**
	 * Attempts to wind up an input spiral into a fullerene dual (face) adjacency matrix.
	 * 
	 * It returns a value <i>p</i> if the spiral shorts or is discovered to be open-ended after <i>p</i> pentagons have been added.
	 * 
	 * Otherwise returns 0.
	 * 
	 * @param spiral
	 * @param m
	 * @param isolatedPentagonIsomersOnly
	 * @return
	 */
	public static int windup(boolean[] spiral, int m, boolean isolatedPentagonIsomersOnly, boolean[][] dualAdjacenyMatrix) {
		if (dualAdjacenyMatrix.length != m | dualAdjacenyMatrix[0].length != m)
			throw new IllegalStateException("Adjancency matrix must have dimensions equal to nuclearity");
		final int[][] afi = new int[spiral.length][6]; // adjacent faces identifier 
		final int[] afc = new int[spiral.length]; // adjacent faces counter
		int j = 0;
		// associate the first two faces with each other
		afi[0][0] = 1;
		afi[1][0] = 0;
		// and add one adjacent face to the count
		afc[0] = 1;
		afc[1] = 1;
		int e = 1;
		// keep track of the number of pentagons
		int p = pentTo1(spiral[0]) + pentTo1(spiral[1]);
		for (int face = 2; face < m-1; face++) {
			p += pentTo1(spiral[face]);
			int prevFace = face-1;
			boolean connectedPreviousFaces = false;
			prevFace: while (!connectedPreviousFaces) {
				if (violatesAdjPents(isolatedPentagonIsomersOnly, spiral, prevFace, face) || hasAllNeighbours(face, spiral, afc)) {
					return p;
				}
				afi[prevFace][afc[prevFace]] = face;
				afi[face][afc[face]] = prevFace;
				afc[prevFace]++;
				afc[face]++;
				if (hasAllNeighbours(prevFace, spiral, afc)) {
					// this has closed up prevFace - connect this face to the new prevFace
					int l = prevFace - 1;
					for (prevFace = l; prevFace > j; prevFace--) {
						if (needsMoreNeighbours(prevFace, spiral, afc))
							continue prevFace;
					}
					return p;
				} else {
					connectedPreviousFaces = true;
				}
			}
			boolean connectedPreviousSpiral = false;
			findSpiralStart: while (!connectedPreviousSpiral) {
				if (violatesAdjPents(isolatedPentagonIsomersOnly, spiral, prevFace, face) && hasAllNeighbours(face, spiral, afc)) {
					return p;
				}
				// connect face to the first open face j in the preceding spiral
				afi[j][afc[j]] = face;
				afi[face][afc[face]] = j;
				afc[j]++;
				afc[face]++;
				if (hasAllNeighbours(j, spiral, afc)) {
					int l = j+1;
					for (j = l; j < face; j++) {
						if (needsMoreNeighbours(j, spiral, afc)) {
							continue findSpiralStart;
						}
					}
					return p;
				} else {
					connectedPreviousSpiral = true;
				}
			}
			// Use Euler's theorem to streamline the search. f is a lower bound on the # of additional faces required for closure
			int h = face - p;
			e = e+afc[face];
			int v = 3+2*p+3*h-e;
			int f = (v+1)/2+1;
			if (f > m-face)
				return p;
		}
		assert(!spiral[m-1] && p==12 || spiral[m-1] && p==11);
		p = 12;
		afc[m-1] = 1;
		for (int face = j; face < m; face++) {
			if (needsMoreThanOneMoreNeighbour(face, spiral, afc)) {
				return p;
			}
			if (hasAllNeighbours(face, spiral, afc) ) {
				continue;
			}
			if (hasAllNeighbours(m-1, spiral, afc)) {
				return p;
			}
			if (violatesAdjPents(isolatedPentagonIsomersOnly, spiral, face, m-1)) {
				return p;
			}
			afi[face][afc[face]] = m-1;
			afi[m-1][afc[m-1]] = face;
			afc[face]++;
			afc[m-1]++;
		}
		if (needsMoreNeighbours(m-1, spiral, afc)) {
			return p;
		}
		// all good, so return value will now be zero
		for (int i = 0; i < m; i++) {
			for (int n  = 0; n < boolToHexOrPent(spiral[i]); n++) {
				dualAdjacenyMatrix[i][afi[i][n]] = true;
			}
		}
		
		return 0;
		
	}
	
	private static boolean violatesAdjPents(boolean ipr, boolean[] spiral, int prevFace, int face) {
		return ipr && spiral[prevFace] && spiral[face];
	}
	
	private static boolean hasAllNeighbours(int face, boolean[] spiral, int[] afc) {
		return afc[face] >= boolToHexOrPent(spiral[face]);
	}
	
	private static boolean needsMoreNeighbours(int face, boolean[] spiral, int[] afc) {
		return !hasAllNeighbours(face, spiral, afc);
	}
	
	private static boolean needsMoreThanOneMoreNeighbour(int face, boolean[] spiral, int[] afc) {
		return afc[face]+1 < boolToHexOrPent(spiral[face]);
	}
	
}

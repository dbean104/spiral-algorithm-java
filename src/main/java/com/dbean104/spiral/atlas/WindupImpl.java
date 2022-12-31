package com.dbean104.spiral.atlas;

import static com.dbean104.spiral.util.GraphUtils.PENTAGON_COUNT;
import static com.dbean104.spiral.util.GraphUtils.boolToHexOrPent;
import static com.dbean104.spiral.util.GraphUtils.pentTo1;

import com.dbean104.spiral.Winder;

/**
 * A Java implementation of the FORTRAN WINDUP subroutine for the spiral algorithm in "An Atlas of Fullerenes"
 * @author david
 *
 */
public class WindupImpl implements Winder {
	
	private static final Winder INSTANCE = new WindupImpl();
	
	private WindupImpl() { /* Make singleton */ }
	
	public static Winder getInstance() {
		return INSTANCE;
	}
	
	@Override
	public int windup(boolean[] spiral, boolean isolatedPentagonIsomersOnly, boolean[][] dualAdjacencyMatrix) {
		final int m = spiral.length; // the number of faces in the fullerene
		if (dualAdjacencyMatrix.length != m || dualAdjacencyMatrix[0].length != m)
			throw new IllegalStateException("Adjacency matrix must have dimensions equal to nuclearity");
		final int[][] afi = new int[spiral.length][]; // adjacent faces identifier
		for (int i = 0; i < spiral.length; i++) {
			afi[i] = new int[boolToHexOrPent(spiral[i])];
		}
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
				if (violatesAdjPents(isolatedPentagonIsomersOnly, spiral, prevFace, face) ||
						hasAllNeighbours(prevFace, spiral, afc) || hasAllNeighbours(face, spiral, afc)) {
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
				if (violatesAdjPents(isolatedPentagonIsomersOnly, spiral, prevFace, face) || hasAllNeighbours(face, spiral, afc)) {
					return p;
				}

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
		assert(!spiral[m-1] && p == PENTAGON_COUNT || spiral[m-1] && p == PENTAGON_COUNT - 1);
		p = PENTAGON_COUNT;
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
		// fullerene is now known to be a valid spiral, so we populate the adjacency matrix
		for (int i = 0; i < m; i++) {
			for (int n = 0; n < boolToHexOrPent(spiral[i]); n++) {
				dualAdjacencyMatrix[i][afi[i][n]] = true;
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

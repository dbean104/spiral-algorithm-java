package com.dbean104.spiral.impl;

import java.util.Arrays;
import java.util.function.BiFunction;

public class Unwind {

	public static UnwindResult unwind(boolean[] spiral, boolean[][] dualAdjacencyMatrix) {
		int totalFaces = spiral.length;
		if (dualAdjacencyMatrix.length != totalFaces && dualAdjacencyMatrix[0].length != totalFaces) {
			throw new IllegalArgumentException("Inconsistent dimensions between spiral and adjacency matrix");
		}
		final BiFunction<Integer, Integer, Boolean> adj = (i1, i2) -> dualAdjacencyMatrix[i1][i2];
		final int[][] facePermutations = getPermutationArray(totalFaces);
		int s0 = 0;

		face1: for (int if1 = 0; if1 < totalFaces; if1++) {
			final int[] p = new int[totalFaces];
			p[0] = if1;
			int flag1 = updateFlag(spiral, p, 0, 0);
			if (flag1 == -1) {
				continue face1;
			}
			
			face2: for (int if2 = 0; if2 < totalFaces; if2++) {
				if (!adj.apply(if1, if2)) {
					continue face2;
				}
				p[1] = if2;
				int flag2 = updateFlag(spiral, p, 1, flag1);
				if (flag2 == -1) {
					continue face2;
				}

				face3: for (int if3 = 0; if3 < totalFaces; if3++) {
					if (!adj.apply(if1,if3) || !adj.apply(if2,if3)) {
						continue face3;
					}
					
					if (s0 == 0) {
						assert(if1 == 0 && if2 == 1 && if3 == 2);
						// this is the original spiral at this point
						// so store the identity operation...
						s0 = 1;
						updateSymmetry(facePermutations, s0, null);
						// ... and move on 
						continue face3;
					}
					
					p[2] = if3;
					int flag3 = updateFlag(spiral, p, 2, flag2);
					if (flag3 == -1) {
						continue face3;
					}
					final int[] r = new int[totalFaces];
					r[p[0]] = 2;
					r[p[1]] = 2;
					r[p[2]] = 2;
					int i = 0;
					level6 : for (int j = 3; j < totalFaces; j++) {
						while (r[p[i]] == GraphUtils.boolToHexOrPent(spiral[p[i]])) {
							i++;
							if (i == j-1) {
								// spiral will not wind as no open faces
								continue face3;								
							}
						}
						int firstOpenFace = p[i];
						int lastOpenFace = p[j-1];
						level5: for (int ij = 0; ij < totalFaces; ij++) {
							if ((!adj.apply(ij, firstOpenFace) || !adj.apply(ij, lastOpenFace))
									|| r[ij] > 0) {
								continue level5;
							}
							p[j] = ij;
							flag3 = updateFlag(spiral, p, j, flag3);
							if (flag3 == -1) {
								continue face3;
							}
							for (int k = 0; k < j; k++) {
								if (adj.apply(p[j],p[k])) {
									r[p[j]]++;
									r[p[k]]++;
								}
							}
							continue level6;
						}
						// if we get here then we haven't found the next face for our spiral, so find a new starting point
						continue face3;
					}
					if (flag3 == 0) {
						// the new spiral p is equivalent to our starting spiral
						s0++;
						updateSymmetry(facePermutations, s0, p);
					} else {
						// the new spiral p has a lower lexicographical value than our starting spiral
						return null;
					}
				}
			}
		}
		
		// Now determine the point group and NMR of the good spiral
		
		final int order = s0;
		final int nuclearity = GraphUtils.getNuclearity(totalFaces);
		final int totalEdges = nuclearity * 3 / 2;
		int[][] v = new int[3][nuclearity];
		int[][] e = new int[2][totalEdges];
		int n = 0;
		int l = 0;
		for (int k = 1; k < totalFaces; k++) {
			for (int j = 0; j < k; j++) {
				if (adj.apply(k, j)) {
					for (int i = 0; i < j; i++) {
						if (adj.apply(i, j) && adj.apply(i, k)) {
							v[0][n] = i;
							v[1][n] = j;
							v[2][n] = k;
							n++;
						}
					}
					e[0][l] = j;
					e[1][l] = k;
					l++;
				}
			}
		}
		
		assert(n == nuclearity && l == totalEdges);
		
		int[][] vertexPermutations = getPermutationArray(nuclearity);
		int[][] edgePermutations = getPermutationArray(totalEdges);
		
		for (int orderIdx = 0; orderIdx < order; orderIdx++) {
			j_loop: for (int j = 0; j < n; j++) {
				int j1 = facePermutations[v[0][j]][orderIdx];
				int j2 = facePermutations[v[1][j]][orderIdx];
				int j3 = facePermutations[v[2][j]][orderIdx];
				int i1 = Math.min(j1, Math.min(j2, j3));
				int i3 = Math.max(j1, Math.max(j2, j3));
				int i2 = j1 + j2 + j3 - i1 - i3;
				for (int i = 0; i < n; i++) {
					if (v[0][i] == i1 && v[1][i] == i2 && v[2][i] == i3) {
						vertexPermutations[j][orderIdx] = i;
						continue j_loop;
					}
				}
			}
			j_loop: for (int j = 0; j < l; j++) {
				int j1 = facePermutations[e[0][j]][orderIdx];
				int j2 = facePermutations[e[1][j]][orderIdx];
				int i1 = Math.min(j1, j2);
				int i2 = j1 + j2 - i1;
				for (int i = 0; i < l; i++) {
					if (e[0][i] == i1 && e[1][i] == i2) {
						edgePermutations[j][orderIdx] = i;
						continue j_loop;
					}
				}
			}
		}
		
		// Count vertex orbits with each site group order
		final int[] mv = populateSymmetryCounts(vertexPermutations, order);
		
		// Count edge orbits with each site group order
		final int[] me = populateSymmetryCounts(edgePermutations, order);
		
		// Count face orbits with each site group order
		final int[] mf = populateSymmetryCounts(facePermutations, order);
		
		// Count ALL special point orbits with each site group order
		final int[] ms = new int[12];
		for (int k = 0; k < 12; k++) {
			ms[k] = mv[k] + me[k] + mf[k];
		}
		
		final int[] nmr = new int[6];
		int j = 0;
		for (int k = nmr.length - 1; k >= 0; k--) {
			if (mv[k] != 0) {
				nmr[j] = mv[k];
				j++;
				nmr[j] = order / (k+1);
				j++;
			}
		}
		String group = PointGroup.getGroup(order, ms);
		return new UnwindResult(group);
	}
	
	private static void updateSymmetry(int[][] fp, int s0, int[] updateArray) {
		for (int i = 0; i < fp.length; i++) {
			fp[i][s0-1] = updateArray != null ? updateArray[i] : i;
		}
	}
	
	private static int updateFlag(boolean[] spiral, int[] p, int index, int flagValue) {
		if (flagValue == 0 && spiral[p[index]] != spiral[index]) {
			if (!spiral[p[index]]) {
				// the new spiral has a hexagon in this position and therefore, assuming all previous positions
				// have been checked, this spiral has a higher lexicographical id than the original
				return -1;
			} else {
				// the spiral is different but has a lower lexicographical id than the original, so flag it
				return index + 1;
			}
		} else {
			// either the flag is already non-zero or this part of the spiral is identical,
			// so just return the original value
			return flagValue;
		}
	}
	
	private static int[] populateSymmetryCounts(int[][] permutations, int order) {
		final int[] countArray = new int[12];
		for (int j = 0; j < permutations.length; j++) {
			if (permutations[j][0] != -1) {
				permutations[j][0] = -1;
				int k = 1;
				for (int orderIdx = 1; orderIdx < order; orderIdx++ ) {
					int i = permutations[j][orderIdx];
					if (permutations[i][0] != -1) {
						permutations[i][0] = -1;
						k++;
					}
				}
				k = order / k;
				countArray[k-1]++;
			}
		}
		return countArray;
	}
	
	private static int[][] getPermutationArray(int elementCount) {
		final int[][] a = new int[elementCount][120];
		for (int i = 0; i < a.length; i++) {
			Arrays.fill(a[i], -1);
		}
		return a;
	}

}

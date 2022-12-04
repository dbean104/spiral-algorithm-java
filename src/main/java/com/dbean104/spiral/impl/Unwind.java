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
		final int[][] fp = new int[totalFaces][120];
		int s0 = -1;

		final int[] p = new int[totalFaces];
		final int[] r = new int[totalFaces];
		level10: for (int if1 = 0; if1 < totalFaces; if1++) {
			p[0] = if1;
			int flag1 = 0;
			if (spiral[p[0]] != spiral[0]) {
				if (!spiral[p[0]]) {
					continue level10;
				}
				flag1 = 1;
			}
			level9: for (int if2 = 0; if2 < totalFaces; if2++) {
				if (!adj.apply(if1, if2)) {
					continue level9;
				}
				p[1] = if2;
				int flag2 = flag1;
				if (flag2 == 0 && spiral[p[1]] != spiral[1]) {
					if (!spiral[p[1]]) {
						continue level9;
					}
					flag2 = 2;
				}
				level8: for (int if3 = 0; if3 < totalFaces; if3++) {
					if (!adj.apply(if1,if3) || !adj.apply(if2,if3)) {
						continue level8;
					}
					
					if (s0 == -1) {
						s0 = 0;
						for (int i = 0; i < totalFaces; i++) {
							fp[i][s0] = i;
						}
						continue level8;
					}
					
					p[2] = if3;
					int flag3 = flag2;
					if (flag3 == 0 && spiral[p[2]] != spiral[2]) {
						if (!spiral[p[2]]) {
							continue level8;
						}
						flag3 = 3;
					}
					Arrays.fill(r, 0); // TODO : Check if this needs to be called
					r[p[0]] = 2;
					r[p[1]] = 2;
					r[p[2]] = 2;
					int i = 0;
					for (int j = 3; j < totalFaces; j++) {
						while (r[p[i]] == GraphUtils.boolToHexOrPent(spiral[p[i]])) {
							i++;
							if (i == j-2)
								continue level8;
						}
						int firstOpenFace = p[i];
						int lastOpenFace = p[j-1];
						level5: for (int ij = 0; ij < totalFaces; ij++) {
							if (!adj.apply(ij, firstOpenFace) || !adj.apply(ij,lastOpenFace)
									|| r[ij] > 0) {
								continue level5;
							}
							p[j] = ij;
							if (flag3 == 0 && spiral[p[j]] != spiral[j]) {
								if (!spiral[p[j]]) {
									continue level8;
								}
								flag3 = j;
							}
							for (int k = 0; k < j-2; k++) {
								if (adj.apply(p[j],p[k])) {
									r[p[j]]++;
									r[p[k]]++;
								}
							}
						}
					}
					if (flag3 == 0) {
						s0++;
						for (int k = 0; k < totalFaces; k++) {
							fp[k][s0] = p[k];
						}
					} else {
						return null;
					}
				}
			}
		}
		final int order = s0 + 1;
		// TODO : Determine point group and NMR
		return new UnwindResult(order);
	}
}

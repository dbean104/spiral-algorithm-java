package com.dbean104.spiral.impl;

import java.util.Arrays;

public class Unwind {

	public static int unwind(boolean[] spiral, boolean[][] dualAdjacencyMatrix) {
		int totalFaces = spiral.length;
		if (dualAdjacencyMatrix.length != totalFaces && dualAdjacencyMatrix[0].length != totalFaces) {
			throw new IllegalArgumentException("Inconsistent dimensions between spiral and adjacency matrix");
		}
		int s0 = 0;
		int[][] fp = new int[totalFaces][120];
		int[] p = new int[totalFaces];
		int[] r = new int[totalFaces];
		level10: for (int i1 = 0; i1 < totalFaces; i1++) {
			p[0] = i1;
			int flag1 = 0;
			if (spiral[p[0]] != spiral[0]) {
				if (!spiral[p[0]]) {
					continue level10;
				}
				flag1 = 1;
			}
			level9: for (int i2 = 0; i2 < totalFaces; i2++) {
				if (!dualAdjacencyMatrix[i1][i2]) {
					continue level9;
				}
				p[1] = i2;
				int flag2 = flag1;
				if (flag2 == 0 && spiral[p[1]] != spiral[1]) {
					if (!spiral[p[1]]) {
						continue level9;
					}
					flag2 = 2;
				}
				level8: for (int i3 = 0; i3 < totalFaces; i3++) {
					if (!dualAdjacencyMatrix[i1][i3] || !dualAdjacencyMatrix[i2][i3]) {
						continue level8;
					}
					if (s0 == 0) {
						s0 = 1;
						for (int i = 0; i < totalFaces; i++) {
							fp[i][s0] = i;
						}
						continue level8;
					}
					p[2] = i3;
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
					level6: for (int j = 3; j < totalFaces; j++) {
						while (r[p[i]] == GraphUtils.boolToHexOrPent(spiral[p[i]])) {
							i++;
							if (i == j-1)
								continue level8;
						}
					}
				}
			}
		}
		return 0;
	}
}

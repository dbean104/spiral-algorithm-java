package com.dbean104.spiral.impl;


import static com.dbean104.spiral.impl.GraphUtils.boolToHexOrPent;
import static com.dbean104.spiral.impl.GraphUtils.pentTo1;
import static com.dbean104.spiral.impl.Windup.windup;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbean104.spiral.SpiralAlgorithm;

/**
 * The default implementation of the spiral algorithm code
 * @author david
 *
 */
public class SpiralAlgorithmImpl implements SpiralAlgorithm {
	
	private static final Logger LOGGER = LogManager.getLogger(SpiralAlgorithmImpl.class);
	

	@Override
	public void generateIsomers(int nuclearity, boolean isolatedPentagonIsomersOnly) {
		GraphUtils.verifyNuclearity(nuclearity);
		if (nuclearity > 194)
			throw new IllegalArgumentException("Nuclearity must be less than 194");
		LOGGER.info("Running with nuclearity={} and isolatedPentagonOnly={}",nuclearity,isolatedPentagonIsomersOnly);
		
		int l = 0;
		int totalFaces = nuclearity/2 + 2;
		final int jpr = isolatedPentagonIsomersOnly ? 2 : 1;
		
		addPentagon(new int[12], 0, 0, jpr, totalFaces);
	/*	
		level1: for (int j1 = 0; j1 < m-11*jpr; j1++) {
			level2: for (int j2 = j1+jpr; j2 < m-10*jpr; j2++) {
				level3: for (int j3 = j2+jpr; j3 < m-9*jpr; j3++) {
					level4: for (int j4 = j3+jpr; j4 < m-8*jpr; j4++) {
						level5: for (int j5 = j4+jpr; j5 < m-7*jpr; j5++) {
							level6: for (int j6 = j5+jpr; j6 < m-6*jpr; j6++) {
								level7: for (int j7 = j6+jpr; j7 < m-5*jpr; j7++) {
									level8: for (int j8 = j7+jpr; j8 < m-4*jpr; j8++) {
										level9: for (int j9 = j8+jpr; j9 < m-3*jpr; j9++) {
											level10: for (int j10 = j9+jpr; j10 < m-2*jpr; j10++) {
												level11: for (int j11 = j10+jpr; j11 < m-jpr; j11++) {
													for (int j12 = j11+jpr; j12 < m; j12++) {
														
														if (windup == 12) continue;
														if (windup == 11) continue level11;
														if (windup == 10) continue level10;
														if (windup == 9) continue level9;
														if (windup == 8) continue level8;
														if (windup == 7) continue level7;
														if (windup == 6) continue level6;
														if (windup == 5) continue level5;
														if (windup == 4) continue level4;
														if (windup == 3) continue level3;
														if (windup == 2) continue level2;
														if (windup == 1) continue level1;
													}
													
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Found " + l + " isomers");
*/
	}
	
	private int addPentagon(int[] pentagonPositions, int pentagon, int face, int jpr, int totalFaces) {
		if (pentagon == 12) {
			// call windup
			final boolean[] s = new boolean[totalFaces];
			for (int i : pentagonPositions) {
				s[i] = true;
			}
			// call windup
			boolean[][] adjacencyMatrix = new boolean[totalFaces][totalFaces];
			int windup = windup(s, jpr == 2, adjacencyMatrix);
			if (windup == 0) {
				int unwind = Unwind.unwind(s, adjacencyMatrix);
				if (unwind != 13)
					System.out.println("Found one!" + Arrays.toString(s)); // TODO : Do something
				return 11; // loop round again looking for more
			} else {
				return windup - 1;
			}
		} else {
			int stuckPentagon = pentagon;
			int j = face;
			pentagonPositions[pentagon] = j;
			while (stuckPentagon==pentagon && j < totalFaces-(11-pentagon)*jpr) {
				stuckPentagon = addPentagon(pentagonPositions, pentagon+1, j+jpr, jpr, totalFaces);
				pentagonPositions[pentagon] = ++j;
			}
			return pentagon - 1;
		}
		
	}
	
	

	
	


}

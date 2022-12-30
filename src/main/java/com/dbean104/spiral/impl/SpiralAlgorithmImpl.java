package com.dbean104.spiral.impl;

import static com.dbean104.spiral.impl.GraphUtils.getNuclearity;
import static com.dbean104.spiral.impl.Windup.windup;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dbean104.spiral.FullereneIsomer;
import com.dbean104.spiral.SpiralAlgorithm;

/**
 * The default implementation of the spiral algorithm code
 * @author david
 *
 */
public class SpiralAlgorithmImpl implements SpiralAlgorithm {
	
	private static final Logger LOGGER = LogManager.getLogger(SpiralAlgorithmImpl.class);
	

	@Override
	public SortedSet<FullereneIsomer> generateIsomers(int nuclearity, boolean isolatedPentagonIsomersOnly) {
		GraphUtils.verifyNuclearity(nuclearity);
		if (nuclearity > 194)
			throw new IllegalArgumentException("Nuclearity must be less than 194");
		LOGGER.info("Running with nuclearity={} and isolatedPentagonOnly={}",nuclearity,isolatedPentagonIsomersOnly);
		
		final SortedSet<FullereneIsomer> result = new TreeSet<>();
		
		int totalFaces = GraphUtils.getFaceCount(nuclearity);
		final int jpr = isolatedPentagonIsomersOnly ? 2 : 1;
		
		final long startTime = System.currentTimeMillis();
		addPentagon(new int[12], 0, 0, jpr, totalFaces, result);
		final long endTime = System.currentTimeMillis();
		LOGGER.info("Completed calculation for nuclearity={} and isolatedPentagonOnly={}. Time taken={} ms. Isomers found={}", nuclearity, isolatedPentagonIsomersOnly, endTime-startTime, result.size());

		return result;
	}
	
	private int addPentagon(int[] pentagonPositions, int pentagon, int face, int jpr, int totalFaces, SortedSet<FullereneIsomer> found) {
		if (pentagon == 12) {
			// test the spiral we have to see if it is an eligible fullerene
			final boolean[] s = translateSpiral(pentagonPositions, totalFaces);

			// call windup to determine if the candidate spiral does close into a fullerene
			// and populate the adjacency matrix if it does
			boolean[][] adjacencyMatrix = new boolean[totalFaces][totalFaces];
			int windup = windup(s, jpr == 2, adjacencyMatrix);
			
			if (windup == 0) {
				// the spiral is a fullerene
				// now call unwind to determine if this spiral is the lexicographical lowest for this isomer
				final UnwindResult unwind = Unwind.unwind(s, adjacencyMatrix);
				if (unwind != null) {
					// this copy is necessary because this array will get changed once this method returns
					int[] pentagonPositionsCopy = Arrays.copyOf(pentagonPositions, pentagonPositions.length);
					found.add(new FullereneIsomerImpl(getNuclearity(totalFaces), pentagonPositionsCopy, unwind.getPointGroup(), unwind.getNMR()));
				}
				return 11; // loop round again looking for more
			} else {
				// the spiral does not wind so return the pentagon where it fails
				return windup - 1;
			}
		} else {
			int stuckPentagon = pentagon;
			int j = face;
			pentagonPositions[pentagon] = j;
			while (stuckPentagon==pentagon && j < totalFaces-(11-pentagon)*jpr) {
				stuckPentagon = addPentagon(pentagonPositions, pentagon+1, j+jpr, jpr, totalFaces, found);
				pentagonPositions[pentagon] = ++j;
			}
			return pentagon - 1;
		}
		
	}
	
	/**
	 * Translates an integer array of pentagon positions into a boolean array indicating whether each face is a hexagon (<code>false</code> or a pentagon (<code>true</code>
	 * @param pentagonPositions an integer array of pentagon positions
	 * @param totalFaces the number of faces in the fullerene
	 * @return a boolean array representing pentagon positions
	 */
	private static boolean[] translateSpiral(int[] pentagonPositions, int totalFaces) {
		final boolean[] s = new boolean[totalFaces];
		for (int i : pentagonPositions) {
			s[i] = true;
		}
		return s;
	}
	
}

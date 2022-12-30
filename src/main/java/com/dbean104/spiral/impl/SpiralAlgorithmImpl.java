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
		
		addPentagon(new int[12], 0, 0, jpr, totalFaces, result);

		return result;
	}
	
	private int addPentagon(int[] pentagonPositions, int pentagon, int face, int jpr, int totalFaces, SortedSet<FullereneIsomer> found) {
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
				final UnwindResult unwind = Unwind.unwind(s, adjacencyMatrix);
				if (unwind != null) {
					int[] pentagonPositionsCopy = Arrays.copyOf(pentagonPositions, pentagonPositions.length);
					found.add(new FullereneIsomerImpl(getNuclearity(totalFaces), pentagonPositionsCopy, unwind.getPointGroup()));
				}
				return 11; // loop round again looking for more
			} else {
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
	
}

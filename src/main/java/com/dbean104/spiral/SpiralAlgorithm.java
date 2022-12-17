package com.dbean104.spiral;

import java.util.SortedSet;

/**
 * Main interface for calling the the spiral algorithm
 * @author david
 *
 */
public interface SpiralAlgorithm {
	
	/**
	 * Generates all fullerene isomers for the required nuclearity
	 * 
	 * @param nuclearity an even number less than or equal to 194
	 * @param isolatedPentagonIsomersOnly <code>true</code> if only 
	 * 	fullerenes with isolated pentagons are required
	 * @return All isomers matching the input parameters
	 */
	SortedSet<FullereneIsomer> generateIsomers(int nuclearity, 
			boolean isolatedPentagonIsomersOnly);

}

package com.dbean104.spiral;

/**
 * Main interface for calling the the spiral algorithm
 * @author david
 *
 */
public interface SpiralAlgorithm {
	// TODO : Add methods
	
	/**
	 * Generates all fullerene isomers for the required nuclearity
	 * @param nuclearity an even number less than or equal to 194
	 * @param isolatedPentagonIsomersOnly true if only fullerenes with isolated pentagons are require
	 * TODO : Determine return type
	 */
	void generateIsomers(int nuclearity, boolean isolatedPentagonIsomersOnly);

}

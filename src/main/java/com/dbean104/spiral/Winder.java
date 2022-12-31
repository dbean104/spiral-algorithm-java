package com.dbean104.spiral;

/**
 * Object to test candidate fullerene isomer spirals 'wind up' into a fullerene
 * @author david
 *
 */
public interface Winder {

	/**
	 * Attempts to wind up an input spiral into a fullerene dual (face) adjacency matrix.
	 * 
	 * It returns a value <i>p</i> if the spiral shorts or is discovered to be open-ended after <i>p</i> pentagons have been added.
	 * 
	 * Otherwise returns 0.
	 * 
	 * @param spiral A <code>boolean</code> array representing the test fullerene. The values should be <code>true</code> to represent pentagons,
	 * 		and <code>false</code> to represent hexagons at each index.
	 * @param isolatedPentagonIsomersOnly This should be <code>true</code> if only isolated pentagon isomers are required. The windup mechanism will then stop if adjacent pentagons are found.
	 * @param dualAdjacencyMatrix an adjacency matrix which will be populated if the fullerene provided is valid
	 * @return a value greater than zero if the spiral fails, or zero if the spiral represents a valid fullerene
	 */
	int windup(boolean[] spiral, boolean isolatedPentagonIsomersOnly, boolean[][] dualAdjacencyMatrix);
}

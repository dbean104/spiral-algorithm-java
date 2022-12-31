package com.dbean104.spiral.util;

/**
 * Class containing utility methods and constants
 * @author david
 *
 */
public class GraphUtils {
	
	public static final int HEXAGON_EDGES = 6;
	public static final int PENTAGON_EDGES = 5;
	
	public static final int MIN_FULLERENE_SIZE = 20;
	public static final int PENTAGON_COUNT = 12;

	private GraphUtils() { /* Avoid instantiation */ }
	
	/**
	 * Returns 1 if boolean is true
	 * @param isPentagon
	 * @return <code>1</code> if the supplied boolean is <code>true</code>, and <code>0</code> otherwise
	 */
	public static int pentTo1(boolean isPentagon) {
		return isPentagon ? 1 : 0;
	}
	
	/**
	 * Returns 5 if true (a pentagon) and 6 if false (a hexagon)
	 * @param isPentagon
	 * @return
	 */
	public static int boolToHexOrPent(boolean isPentagon) {
		return isPentagon ? PENTAGON_EDGES : HEXAGON_EDGES;
	}
	
	/**
	 * Applies Euler's theorem to get faces for a fullerene with a given number of atoms
	 * @param atoms the number of atoms
	 * @return the number of faces
	 */
	public static int getFaceCount(int atoms) {
		verifyNuclearity(atoms);
		return PENTAGON_COUNT + (atoms / 2) - 10;
	}
	
	/**
	 * Applies Euler's theorem to get the nuclearity of a fullerence with a given number of faces
	 * @param faceCount the number of faces
	 * @return the nuclearity
	 */
	public static int getNuclearity(int faceCount) {
		if (faceCount < PENTAGON_COUNT)
			throw new IllegalArgumentException("A fullerence must have > " + PENTAGON_COUNT + " faces"); 
		return 2 * faceCount - 4;
	}
	
	/**
	 * Verifies the nuclearity of a fullerene is valid
	 * @param nuclearity the nuclearity (number of carbon atoms)
	 * @throws IllegalArgumentException if validity checks fail
	 */
	public static void verifyNuclearity(int nuclearity) {
		if (nuclearity < MIN_FULLERENE_SIZE || nuclearity % 2 == 1)
			throw new IllegalArgumentException("A fullerene must have > " + MIN_FULLERENE_SIZE + " atoms and and even number of atoms");
	}
}

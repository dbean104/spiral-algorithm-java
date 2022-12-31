package com.dbean104.spiral.output;

import java.util.SortedSet;

import com.dbean104.spiral.FullereneIsomer;

/**
 * Interface for writing the isomers calculated by the spiral algorithm
 * @author david
 *
 */
public interface IsomerOutputWriter {

	/**
	 * <p>Writes the isomers provided for a given nuclearity</p>
	 * 
	 * <p>Implementing classes may chose not to use the <code>nuclearity</code> or <code>isIsolatedPentagon</code> parameters.
	 * @param nuclearity the nuclearity of the isomer
	 * @param isIsolatedPentagon whether the isomers are exclusively with isolated pentagons
	 * @param isomers a sorted collection of the isomers calculated
	 */
	void dumpResult(int nuclearity, boolean isIsolatedPentagon, SortedSet<FullereneIsomer> isomers);
}

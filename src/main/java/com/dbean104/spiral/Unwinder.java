package com.dbean104.spiral;

/**
 * Object to test candidate fullerene isomers by 'unwinding' the candidate spiral
 * @author david
 *
 */
public interface Unwinder {

	/**
	 * <p>Unwinds a fullerene dual adjacency matrix into each of its constituent spirals
	 * and checks that none has a lexicographically smaller code than the input spiral</p>
	 * 
	 * <p>If this test is passed, the idealized point group and NMR signature of the fullerene are calculated</p>
	 * 
	 * @param spiral A <code>boolean</code> array representing the test fullerene. The values should be <code>true</code> to represent pentagons,
	 * 		and <code>false</code> to represent hexagons at each index.
	 * @param dualAdjacencyMatrix an adjacency matrix where value <i>i</i>,<i>j</i> is <code>true</code> iff face <i>i</i> and face <i>j</i> are adjacent
	 *  in the fullerene provided
	 * @return an {@link UnwindResult} if this spiral provided has the smallest lexicographical code, or <code>null</code> otherwise 
	 */
	UnwindResult unwind(boolean[] spiral, boolean[][] dualAdjacencyMatrix);
	
}

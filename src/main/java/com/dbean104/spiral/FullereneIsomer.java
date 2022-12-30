package com.dbean104.spiral;

/**
 * <p>Interface for an object representing a fullerene isomer</p>
 * 
 * <p>This extends {@link Comparable} so implementing classes must define an natural ordering of isomers</p>
 * @author david
 *
 */
public interface FullereneIsomer extends Comparable<FullereneIsomer> {

	int getNuclearity();

	int[] getRingSpiral();

	String getPointGroup();
	
	int[] getNMR();
	
}

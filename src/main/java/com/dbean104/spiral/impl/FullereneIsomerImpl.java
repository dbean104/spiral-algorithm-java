package com.dbean104.spiral.impl;

import com.dbean104.spiral.FullereneIsomer;

/**
 * Standard implementation of the {@link FullereneIsomer} interface
 * @author david
 *
 */
public class FullereneIsomerImpl implements FullereneIsomer {

	private final int nuclearity;
	private final int[] ringSpiral;
	private final String pointGroup;
	private final int[] nmr;
	
	public FullereneIsomerImpl(int nuclearity, int[] ringSpiral, String pointGroup, int[] nmr) {
		this.nuclearity = nuclearity;
		this.ringSpiral = ringSpiral;
		this.pointGroup = pointGroup;
		this.nmr = nmr;
	}

	@Override
	public int getNuclearity() {
		return nuclearity;
	}

	@Override
	public int[] getRingSpiral() {
		return ringSpiral;
	}

	@Override
	public String getPointGroup() {
		return pointGroup;
	}
	
	@Override
	public int[] getNMR() {
		return nmr;
	}

	@Override
	public int compareTo(FullereneIsomer o) {
		final int nuclearityDiff = this.nuclearity - o.getNuclearity(); 
		if (nuclearityDiff != 0)
			return nuclearityDiff;
		final int[] otherRingSpiral = o.getRingSpiral();
		for (int i = 0; i < ringSpiral.length; i++) {
			if (ringSpiral[i] != otherRingSpiral[i]) {
				return ringSpiral[i] - otherRingSpiral[i];
			}
		}
		return 0;
	}
}

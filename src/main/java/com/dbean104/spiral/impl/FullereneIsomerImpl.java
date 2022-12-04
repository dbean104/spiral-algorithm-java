package com.dbean104.spiral.impl;

import com.dbean104.spiral.FullereneIsomer;

public class FullereneIsomerImpl implements FullereneIsomer {

	private final int nuclearity;
	private final int[] ringSpiral;
	private final int pointGroupOrder;
	
	public FullereneIsomerImpl(int nuclearity, int[] ringSpiral, int pointGroupOrder) {
		this.nuclearity = nuclearity;
		this.ringSpiral = ringSpiral;
		this.pointGroupOrder = pointGroupOrder;
	}

	public int getNuclearity() {
		return nuclearity;
	}

	public int[] getRingSpiral() {
		return ringSpiral;
	}

	public int getPointGroupOrder() {
		return pointGroupOrder;
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

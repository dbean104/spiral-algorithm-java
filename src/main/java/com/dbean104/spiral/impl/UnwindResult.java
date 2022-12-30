package com.dbean104.spiral.impl;

/**
 * An object to return the result of a successful call to the {@link Unwind#unwind(boolean[], boolean[][]) function
 * @author david
 *
 */
public class UnwindResult {

	private final String pointGroup;
	private final int[] nmr;
	
	UnwindResult(String pointGroup, int[] nmr) {
		this.pointGroup = pointGroup;
		this.nmr = nmr;
	}
	
	String getPointGroup() {
		return pointGroup;
	}
	
	int[] getNMR() {
		return nmr;
	}

	@Override
	public String toString() {
		return "UnwindResult [pointGroup=" + pointGroup + "]";
	}
}

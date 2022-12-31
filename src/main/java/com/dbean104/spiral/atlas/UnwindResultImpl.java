package com.dbean104.spiral.atlas;

import com.dbean104.spiral.UnwindResult;

/**
 * An object to return the result of a successful call to the {@link Unwind#unwind(boolean[], boolean[][]) function
 * @author david
 *
 */
public class UnwindResultImpl implements UnwindResult {

	private final String pointGroup;
	private final int[] nmr;
	
	UnwindResultImpl(String pointGroup, int[] nmr) {
		this.pointGroup = pointGroup;
		this.nmr = nmr;
	}
	
	public String getPointGroup() {
		return pointGroup;
	}
	
	public int[] getNMR() {
		return nmr;
	}

	@Override
	public String toString() {
		return "UnwindResult [pointGroup=" + pointGroup + "]";
	}
}

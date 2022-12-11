package com.dbean104.spiral.impl;

public class UnwindResult {

	private final String pointGroup;
	
	UnwindResult(String pointGroup) {
		this.pointGroup = pointGroup;
	}
	
	String getPointGroup() {
		return pointGroup;
	}

	@Override
	public String toString() {
		return "UnwindResult [pointGroup=" + pointGroup + "]";
	}
}

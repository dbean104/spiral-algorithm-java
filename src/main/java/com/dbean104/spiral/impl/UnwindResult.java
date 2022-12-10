package com.dbean104.spiral.impl;

public class UnwindResult {

	private final int pointGroupOrder;
	
	UnwindResult(int pointGroupOrder) {
		this.pointGroupOrder = pointGroupOrder;
	}
	
	int getPointGroupOrder() {
		return pointGroupOrder;
	}

	@Override
	public String toString() {
		return "UnwindResult [pointGroupOrder=" + pointGroupOrder + "]";
	}
}

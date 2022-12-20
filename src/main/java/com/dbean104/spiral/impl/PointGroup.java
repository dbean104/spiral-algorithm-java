package com.dbean104.spiral.impl;

import java.util.Arrays;

public class PointGroup {

	/**
	 * Calculates the point group
	 * @param order the point group order
	 * @param ms an array of special point orbits with site group order <i>i</i>
	 * @return the point group as a <code>String</code>
	 */
	public static String getGroup(int order, int[] ms) {
		final String group;
		switch (order) {
			case 1:
				group = "C1";
				break;
			case 2:
				group = resolveOrder2(ms);
				break;
			case 3:
				group = "C3";
				break;
			case 4:
				group = resolveOrder4(ms);
				break;
			case 6:
				group = resolveOrder6(ms);
				break;
			case 8:
				group = resolveOrder8(ms);
				break;
			case 10:
				group = "D5";
				break;
			case 12:
				group = resolveOrder12(ms);
				break;
			case 20:
				group = resolveOrder20(ms);
				break;
			case 24:
				group = resolveOrder24(ms);
				break;
			case 60:
				group = "I";
				break;
			case 120:
				group = "Ih";
				break;
			default:
				throw unresolved(order, ms);
		}
		return group;
	}
	
	private static IllegalArgumentException unresolved(int order, int[] ms) {
		return new IllegalArgumentException("Unable to resolve point group with order = " + order +" and ms = " + Arrays.toString(ms));
	}
	
	private static String resolveOrder2(int[] ms) {
		final String group;
		if (ms[1] == 0) {
			group  = "Ci";
		} else if (ms[1] == 2) {
			group = "C2";
		} else if (ms[1] > 2) {
			group = "Cs";
		} else {
			throw unresolved(2, ms);
		}
		return group;
	}
	
	private static String resolveOrder4(int[] ms) {
		String group = null;
		if (ms[3] == 0) {
			if (ms[1] == 1) {
				group = "S4";
			} else if (ms[1] == 3) {
				group = "D2";
			} else if (ms[1] > 3) {
				group = "C2h";
			}
		} else if (ms[3] == 2) {
			group = "C2v";
		}
		if (group == null)
			throw unresolved(4, ms);
		return group;
	}
	
	private static String resolveOrder6(int[] ms) {
		String group = null;
		if (ms[5] == 0)  {
			if (ms[1] == 0) {
				group = "S6";
			} else if (ms[1] == 2) {
				group = "D3";
			} else if (ms[1] > 2) {
				group = "C3h";
			}
		} else if (ms[5] == 2) {
			group = "C3v";
		}
		if (group == null)
			throw unresolved(6, ms);
		return group;
	}
	
	private static String resolveOrder8(int[] ms) {
		final String group;
		if (ms[3] == 1) {
			group =  "D2d";
		} else if (ms[3] == 3) {
			group = "D2h";
		} else {
			throw unresolved(8, ms);
		}
		return group;
	}
	
	private static String resolveOrder12(int[] ms) {
		String group = null;
		if (ms[5] == 0) {
			group = "T";
		} else if (ms[5] == 1) {
			if (ms[3] == 0) {
				if (ms[1] == 2) {
					group = "D6";
				} else if (ms[1] > 2) {
					group = "D3d";
				}
			} else if (ms[3] == 2) {
				group = "D3h";
			}
		}
		if (group == null)
			throw unresolved(12, ms);
		return group;
	}
	
	private static String resolveOrder20(int[] ms) {
		final String group;
		if (ms[3] == 0) {
			group = "D5d";
		} else if (ms[3] == 2) {
			group = "D5h";
		} else {
			throw unresolved(20, ms);
		}
		return group;
	}
	
	private static String resolveOrder24(int[] ms) {
		String group = null;
		if (ms[11] == 0) {
			if (ms[5] == 0) {
				group = "Th";
			} else if (ms[5] == 2) {
				group = "Td";
			}
		} else if (ms[11] == 1) {
			if (ms[3] == 0) {
				group  = "D6d";
			} else if (ms[3] == 2) {
				group =  "D6h";
			}
		}
		if (group == null)
			throw unresolved(24, ms);
		return group;
	}
		
}

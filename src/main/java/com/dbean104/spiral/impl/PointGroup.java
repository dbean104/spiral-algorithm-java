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
		String group = null;
		if (order == 1) {
			group = "C1";
		} else if (order == 2) {
			if (ms[1] == 0) {
				group  = "Ci";
			} else if (ms[1] == 2) {
				group = "C2";
			} else if (ms[1] > 2) {
				group = "Cs";
			}
		} else if (order == 3) {
			group = "C3";
		} else if (order == 4) {
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
		} else if (order == 6) {
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
		} else if (order == 8) {
			if (ms[3] == 1) {
				group =  "D2d";
			} else if (ms[3] == 3) {
				group = "D2h";
			}
		} else if (order == 10) {
			group = "D5";
		} else if (order == 12) {
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
		} else if (order == 20) {
			if (ms[3] == 0) {
				group = "D5d";
			} else if (ms[3] == 2) {
				group = "D5h";
			}
		} else if (order == 24) {
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
		} else if (order == 60) {
			group = "I";
		} else if (order == 120) {
			group = "Ih";
		}
		
		if (group == null) {
			throw new IllegalArgumentException("Unable to resolve point group with order = " + order +" and ms = " + Arrays.toString(ms));
		}
		
		return group;
	}
}

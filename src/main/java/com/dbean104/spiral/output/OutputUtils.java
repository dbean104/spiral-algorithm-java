package com.dbean104.spiral.output;

import java.util.SortedSet;

import com.dbean104.spiral.FullereneIsomer;

public class OutputUtils {
	
	private static final String OUTPUT_TEMPLATE = "%s\t%s\t%s";

	public static void dumpResult(int nuclearity, SortedSet<FullereneIsomer> isomers) {
		int i = 1;
		for (FullereneIsomer isomer : isomers) {
			dumpFullereneResult(nuclearity, i, isomer);
			i++;
		}
	}
	
	private static void dumpFullereneResult(int nuclearity, int isomerNumber, FullereneIsomer isomer) {
		final String output = String.format(OUTPUT_TEMPLATE, String.format("%d:%d", nuclearity, isomerNumber), formatRingSpiral(isomer.getRingSpiral()), isomer.getPointGroup());
		System.out.println(output);
	}
	
	private static String formatRingSpiral(int[] s) {
		final StringBuilder sb = new StringBuilder();
		for (int i : s) {
			if (i < 9)
				sb.append(" ");
			sb.append(i + 1).append(" ");
		}
		return sb.toString();
	}
}

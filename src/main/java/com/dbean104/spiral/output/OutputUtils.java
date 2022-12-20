package com.dbean104.spiral.output;

import java.util.SortedSet;

import com.dbean104.spiral.FullereneIsomer;

public class OutputUtils {
	
	private static final String OUTPUT_TEMPLATE = "%s\t%s\t%s";

	public static void dumpResult(int nuclearity, boolean isIsolatedPentagon, SortedSet<FullereneIsomer> isomers) {
		final StringBuilder header = new StringBuilder();
		if (isIsolatedPentagon) {
			header.append("ISOLATED-PENTAGON");
		} else {
			header.append("GENERAL FULLERENE");
		}
		header.append(" ISOMERS OF C").append(nuclearity).append(":");
		System.out.println(header.toString());
		printSeparator();
		int i = 1;
		for (FullereneIsomer isomer : isomers) {
			dumpFullereneResult(nuclearity, i, isomer);
			i++;
		}
		printSeparator();
	}
	
	private static void dumpFullereneResult(int nuclearity, int isomerNumber, FullereneIsomer isomer) {
		final String output = String.format(OUTPUT_TEMPLATE, String.format("%d:%d", nuclearity, isomerNumber), isomer.getPointGroup(), formatRingSpiral(isomer.getRingSpiral()));
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
	
	private static void printSeparator() {
		final StringBuilder separator = new StringBuilder();
		for (int i = 0; i < 77; i++) {
			separator.append("-");
		}
		System.out.println(separator.toString());
	}
}

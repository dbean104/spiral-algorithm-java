package com.dbean104.spiral.output.atlas;

import java.io.PrintStream;
import java.util.SortedSet;

import com.dbean104.spiral.FullereneIsomer;
import com.dbean104.spiral.output.IsomerOutputWriter;

/**
 * Implementation of {@link IsomerOutputWriter} that writes the output in the same format as the "An Atlas of Fullerenes" spiral algorithm
 * @author david
 *
 */
public class IsomerOutputWriterImpl implements IsomerOutputWriter {
	
	private static final String OUTPUT_TEMPLATE = "%s %s %s  %s";
	
	private final PrintStream ps;
	
	/**
	 * <p>Default constructor requiring a {@link PrintStream}</p>
	 * 
	 * <p>The calling class is responsible for the lifecycle of this stream, including closing it</p>
	 * 
	 * @param outputStream a {@link PrintStream}
	 */
	public IsomerOutputWriterImpl(PrintStream outputStream) {
		this.ps = outputStream;
	}

	@Override
	public void dumpResult(int nuclearity, boolean isIsolatedPentagon, SortedSet<FullereneIsomer> isomers) {
		final StringBuilder header = new StringBuilder();
		if (isIsolatedPentagon) {
			header.append("ISOLATED-PENTAGON");
		} else {
			header.append("GENERAL FULLERENE");
		}
		header.append(" ISOMERS OF C").append(nuclearity).append(":");
		ps.println(header.toString());
		printSeparator();
		int i = 1;
		for (FullereneIsomer isomer : isomers) {
			dumpFullereneResult(i, isomer);
			i++;
		}
		printSeparator();
	}
	
	private void dumpFullereneResult(int isomerNumber, FullereneIsomer isomer) {
		final String output = String.format(OUTPUT_TEMPLATE, String.format("%8d", isomerNumber), String.format("%3s", isomer.getPointGroup()), formatRingSpiral(isomer.getRingSpiral()), formatNMR(isomer.getNMR()));
		ps.println(output);
	}
	
	private static String formatRingSpiral(int[] s) {
		final StringBuilder sb = new StringBuilder();
		for (int i : s) {
			sb.append(String.format("%3d", i+1));
		}
		return sb.toString();
	}
	
	private static String formatNMR(int[] nmr) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nmr.length; i += 2) {
			if (nmr[i] != 0) {
				if (i > 0) {
					sb.append(',');
				}
				sb.append(String.format("%3d", nmr[i])).append(" x").append(String.format("%3d",nmr[i+1]));
			}
		}
		return sb.toString();
	}
	
	private void printSeparator() {
		final StringBuilder separator = new StringBuilder();
		for (int i = 0; i < 77; i++) {
			separator.append("-");
		}
		ps.println(separator.toString());
	}
}

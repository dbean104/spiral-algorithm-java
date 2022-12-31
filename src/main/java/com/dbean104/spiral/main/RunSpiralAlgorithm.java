package com.dbean104.spiral.main;

import java.io.IOException;
import java.io.PrintStream;
import java.util.SortedSet;

import com.dbean104.spiral.FullereneIsomer;
import com.dbean104.spiral.SpiralAlgorithm;
import com.dbean104.spiral.atlas.SpiralAlgorithmImpl;
import com.dbean104.spiral.output.IsomerOutputWriter;
import com.dbean104.spiral.output.atlas.IsomerOutputWriterImpl;

/**
 * Class containing a main method for running the spiral algorithm
 * @author david
 *
 */
public class RunSpiralAlgorithm {

	public static void main(String[] args) {
		final int nuclearity;
		final boolean isIsolatedPentagons;
		final String outputFile;
		if (args.length < 1) {
			printUsage();
			System.exit(1);
		}
		try {
			nuclearity = Integer.parseInt(args[0]);
			isIsolatedPentagons = args.length > 1 ? Boolean.parseBoolean(args[1]) : false;
			outputFile = args.length > 2 ? args[2] : null;
		} catch (Exception e) {
			printUsage();
			throw new IllegalArgumentException();
		}
		
		final SpiralAlgorithm spa = SpiralAlgorithmImpl.getDefaultInstance();
		
		final SortedSet<FullereneIsomer> isomers = spa.generateIsomers(nuclearity, isIsolatedPentagons);
		
		try (PrintStream ps = outputFile == null ? System.out : new PrintStream(outputFile)) {
			final IsomerOutputWriter iow = new IsomerOutputWriterImpl(ps);
			iow.dumpResult(nuclearity, isIsolatedPentagons, isomers);
		} catch (IOException ioe) {
			throw new IllegalStateException("Unable to write output", ioe);
		}
		
		System.exit(0);
	}
	
	private static void printUsage() {
		System.out.println("Arguments: nuclearity [isIsolatedPentagons] [outputFile]");
		System.out.println("\tisIsolatedPentagons: true|false (default: false), outputFile: (default: System.out)");
	}
}

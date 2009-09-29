package aco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class Space {

	private static Random random = new Random();

	/**
	 * Initialize vector space
	 *
	 * @param vectors Number of vectors
	 * @param variables Number of variables
	 * @param value Value to initialize space with
	 * @return Initialized vector space
	 */
	public static double[][] initialize(int vectors, int variables, double value) {

		double[][] space = new double[vectors][variables];

		// Iterate space
		for(int i = 0; i < vectors; i++) {
			for(int j = 0; j < variables; j++) {

				// Initialize with value
				space[i][j] = value;
			}
		}

		return space;
	}

	public static double[][] initialize(int vectors, int variables) {

		double[][] space = new double[vectors][variables];

		// Iterate space
		for(int i = 0; i < vectors; i++) {
			for(int j = 0; j < variables; j++) {

				// Initialize with value
				space[i][j] = random.nextDouble();
			}
		}

		return space;
	}

	public static void update(double[][] space, double[] variables, int vector) {

		// Update variable in space
		for (int variable = 0; variable < variables.length; variable++) {

			space[vector][variable] = variables[variable];
		}
	}

	public static void write(double[][] space, String file) {

		try {

			FileWriter fstream = new FileWriter(file);

	        BufferedWriter out = new BufferedWriter(fstream);

	        // Iterate vectors
	        for(int i = 0; i < space.length; i++) {

	        	// Write row
	        	for(int j = 0; j < space[0].length; j++) {

	        		out.write(Double.toString(space[i][j]) + " ");
	        	}

	        	out.write("\r\n");
	        }

	        out.close();

		} catch (Exception e) {

			System.out.println(e.toString());
		}
	}
}

package aco;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Pheromone {

	public static double[] initialize(int size, double value) {

		double[] pheromone = new double[size];

		for (int i = 0; i < size; i++) {

			pheromone[i] = value;
		}

		return pheromone;
	}

	/**
	 * Calculates the total sum of pheromone.
	 *
	 * @param pheromone Array of double representing pheromone
	 * @return The sum of pheromone
	 */
	public static double sum(double[] pheromone) {

		double sum = 0.0;

		for (double value : pheromone) {
			sum += value;
		}

		return sum;
	}

	public static void update(double[] pheromone, double[] accumulator, double evaporation) {

		for (int i = 0; i < pheromone.length; i++)
			pheromone[i] = (1.0 - evaporation) * pheromone[i] + accumulator[i];
	}

	public static void write(double[] pheromone, String file) {

		try {

			FileWriter fstream = new FileWriter(file);

	        BufferedWriter out = new BufferedWriter(fstream);

	        for(int i = 0; i < pheromone.length; i++) {

	        	out.write(Double.toString(pheromone[i]) + "\r\n");
	        }

	        out.close();

		} catch (Exception e) {

			System.out.println(e.toString());
		}
	}
}

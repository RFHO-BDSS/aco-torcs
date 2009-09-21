package aco;

import java.util.Random;

public class Evolution {

	private double crossover;

	private Random random;

	public Evolution(double crossover) {

		this.crossover = crossover;

		this.random = new Random(System.currentTimeMillis());
	}

	public double[] getChild(double population[][], int variables, int parents) {

		boolean tabu[] = new boolean[parents];

		for(int i = 0; i < parents; i++) {
			tabu[i] = false;
		}

		// Child
		double child[] = new double[variables];

		// Get a random parent
		int parent = random.nextInt(parents);

		// Add to tabu array
		tabu[parent] = true;

		// Crossover
		for(int i = 0; i < variables; i++) {

			child[i] = population[parent][i];


		}

		// Mutate
		for(int i = 0; i < variables; i++) {

		}

		return null;
	}

}

package aco;

import java.util.Random;

public class Colony {

	private int ants;
	private int variables;
	private double evaporation;
	private double alfa;
	private double beta;

	private Random random;

	/**
	 * Search space matrix.
	 *
	 * Variables * Ants
	 */
	private double space[][];

	/**
	 * Function value vector.
	 */
	private double function[];

	/**
	 * Pheromone state vector.
	 */
	private double pheromone[];

	public Colony(int ants, int variables, double evaporation, double alfa, double beta) {

		this.ants = ants;
		this.variables = variables;
		this.evaporation = evaporation;
		this.alfa = alfa;
		this.beta = beta;

		this.random = new Random(System.currentTimeMillis());

		initialize();
	}

	private void initialize() {

		space = new double[ants][variables];
		function = new double[ants];
		pheromone = new double[ants];

		// Initialize search space with random values in interval [0.0 1.0]
		for(int i = 0; i < ants; i++) {
			for(int j = 0; j < variables; j++) {
				space[i][j] = random.nextDouble();
			}
		}

		// Initialize pheromone vector
		for(int i = 0; i < ants; i++) {

		}
	}

	private void evaporate() {


	}

}

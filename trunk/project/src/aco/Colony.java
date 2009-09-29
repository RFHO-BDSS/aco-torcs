package aco;

import functions.*;

public class Colony {

	private int ants;
	private int vectors;
	private int variables;
	private int cycles;
	private int n;
	private double evaporation;
	private double gain;
	private double radius;
	private double decrease;
	private double min;
	private double max;
	private double start;

	private double highest;
	private double[] fittest;


	/**
	 * Vector space.
	 */
	private double[][] space;

	/**
	 * Vector fitness value vector.
	 */
	private double[] fitness;

	/**
	 * Pheromone state vector.
	 */
	private double[] pheromone;

	/**
	 * Roulette wheel;
	 */
	private double[][] wheel;

	private IFunction function;

	public void setFunction(IFunction function) {

		this.function = function;

		this.variables = function.getDimension();
		this.min = function.getMin();
		this.max = function.getMax();

		initialize();
	}

	/**
	 * Gets the highest fitness measure.
	 *
	 * @return
	 */
	public double getHighestFitness() {

		return this.highest;
	}

	/**
	 * Gets the fittest solution.
	 *
	 * @return
	 */
	public double[] getFittestSolution() {

		return this.fittest;
	}

	/**
	 * Gets vector space.
	 *
	 * @return
	 */
	public double[][] getSpace() {

		return this.space;
	}

	/**
	 * Sets vector space.
	 *
	 * @param space
	 */
	public void setSpace(double[][] space) {

		this.space = space;
	}

	public Colony(int ants, int vectors, int cycles, double start, double evaporation, double gain, double radius, double decrease) {

		this.ants = ants;
		this.vectors = vectors;
		this.evaporation = evaporation;
		this.gain = gain;
		this.radius = radius;
		this.decrease = decrease;
		this.cycles = cycles;
		this.n  = 0;
		this.highest = Double.MIN_VALUE;
		this.start = start;
	}

	public Colony(int ants, int vectors, int variables, int cycles, double start, double evaporation, double gain, double radius, double decrease, double min, double max) {

		this.ants = ants;
		this.vectors = vectors;
		this.variables = variables;
		this.evaporation = evaporation;
		this.gain = gain;
		this.radius = radius;
		this.decrease = decrease;
		this.min = min;
		this.max = max;
		this.cycles = cycles;
		this.n = 0;
		this.highest = Double.MIN_VALUE;
		this.start = start;
	}

	public double[] optimize() {

		while(n < cycles) {

			n++;
			step();
		}

		Space.write(space, "space.txt");
		Roulette.write(wheel, "wheel.txt");

		return fittest;
	}

	private void initialize() {

		space = Space.initialize(vectors, variables, start);
		//space = Space.initialize(vectors, variables);
		pheromone = Pheromone.initialize(vectors, 10.0);
		wheel = Roulette.build(pheromone);

		fitness = new double[vectors];

		for(int i = 0; i < vectors; i++)
			fitness[i] = Double.MIN_VALUE;

		System.out.println("Initialized with...");
		System.out.println("Ants: " + Integer.toString(ants));
		System.out.println("Vectors: " + Integer.toString(vectors));
		System.out.println("Start point: " + Double.toString(start));
	}

	private void step() {

		// An ant's selected vector
		int vector;

		// Ant's solution
		double[] solution = new double[variables];

		// Fitness of an ant's solution
		double antFitness;

		// Pheromone accumulator
		double[] accumulator = Pheromone.initialize(vectors, 0.0);

		// Iterate population of ants
		for(int ant = 0; ant < ants; ant++) {

			// Ant selects vector to search in respect of pheromone intensity
			vector = Roulette.select(wheel);

			// Ant performs local search around vector point
			solution = Ant.search(space[vector], radius, min, max);

			// Evaluate fitness of solution
			antFitness = function.fitness(solution);

			// Limit
			antFitness = Math.min(antFitness, Double.MAX_VALUE);
			antFitness = Math.max(antFitness, Double.MIN_VALUE);

			// Found fitter solution
			if (antFitness > highest) {

				// Update
				highest = antFitness;
				fittest = solution;

				System.out.println("Highest fitness: " + Double.toString(highest));
			}

			// Ant's solution is fitter than current vector
			if (antFitness > fitness[vector]) {

				// Deposit pheromone in accumulator
				Ant.pheromonize(vector, accumulator, antFitness, gain);

				// Update selected vector - in space - with the fitter solution
				Space.update(space, solution, vector);

				// Update vector fitness
				fitness[vector] = antFitness;

				//Space.write(space, "fittest_space.txt");
				//Pheromone.write(pheromone, "fittest_pheromone.txt");
				//Roulette.write(wheel, "fittest_wheel.txt");
			}
		}

		// Linear decrease of search radius
		radius *= decrease;

		// Update pheromone trails
		Pheromone.update(pheromone, accumulator, evaporation);

		// Update roulette wheel
		Roulette.update(wheel, pheromone);

	}
}

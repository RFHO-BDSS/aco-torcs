

import java.util.Random;

public class Evolution {

	private double pCrossover;
	private double degreeOfNonLinearity;

	private Random random;

	public Evolution(double pCrossover) {

		this.pCrossover = pCrossover;

		this.random = new Random(System.currentTimeMillis());
	}

	public double[][] evolve(double population[][], int individuals, int variables) {

		// Recombined population
		double recombined[][] = new double[individuals][variables];

		for (int j = 0; (j + 1) < individuals; j += 2) {

			int r = variables;

			// Crossover occurs
			if (random.nextDouble() < pCrossover) {

				// Crossover from r
				r = random.nextInt(variables);
			}

			// Construct two individuals
			for (int k = 0; k < variables; k++)

				// Preserve genetic material
				if (k < r) {

					recombined[j][k] = population[j][k];
					recombined[j + 1][k] = population[j + 1][k];

				// Exchange genetic material
				} else {

					recombined[j][k] = population[j + 1][k];
					recombined[j + 1][k] = population[j][k];
				}
		}

		// Mutate
		for (int i = 0; i < variables; i++) {

		}

		return recombined;
	}

}

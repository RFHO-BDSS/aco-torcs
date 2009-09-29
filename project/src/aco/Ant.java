package aco;

import java.util.Random;
import java.lang.Math;

public class Ant {

	private static Random random = new Random(System.currentTimeMillis());

	public static double[] search(double[] vector, double radius, double min, double max) {

		// Clone vector
		double[] clone = vector.clone();

		// Direction of local search
		int direction;

		// Step size of variable
		double step;

		// Iterate all variables in vector
		for(int i = 0; i < vector.length; i++) {

			// Randomize direction
			direction = (int)Math.pow(-1.0, (double)random.nextInt(2)+1);

			// Step size
			step = (double) direction * radius * random.nextDouble();

			// Make change to variable in clone vector
			clone[i] += step;

			//  Limit
			clone[i] = Math.min(clone[i], max);
			clone[i] = Math.max(clone[i], min);
		}

		return clone;
	}

	public static void pheromonize(int vector, double[] accumulator, double fitness, double gain) {

		accumulator[vector] += gain * fitness;
	}
}

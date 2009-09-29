package aco;

import java.io.*;
import java.util.Random;

public class Roulette {

	public static final int ERROR = -1;

	private static Random random = new Random(System.currentTimeMillis());

	public static double[][] build(double values[]) {

		double[][] wheel = new double[values.length][2];

		update(wheel, values);

		return wheel;
	}

	public static void update(double[][] wheel, double values[]) {

		double last = 0.0;

		double sum = 0.0;

		// Calculate sum of values
		for(double d : values) {
			sum += d;
		}

		// Iterate all values
		for(int i = 0; i < values.length; i++) {

			// Calculate ratio
			double ratio = values[i] / sum;

			wheel[i][0] = last;
			wheel[i][1] = wheel[i][0] + ratio;

			last = wheel[i][1];
		}
	}

	public static int select(double[][] wheel) {

		// Get random double
		double r = random.nextDouble();

		// Find selected item
		for (int i = 0; i < wheel.length; i++) {

			// Check constrains
			if (wheel[i][0] <= r && r < wheel[i][1]) {
				return i;
			}
		}

		return ERROR;
	}

	public static void write(double[][] wheel, String file) {

		try {

			FileWriter fstream = new FileWriter(file);

	        BufferedWriter out = new BufferedWriter(fstream);

	        for(int i = 0; i < wheel.length; i++) {

	        	out.write(Double.toString(wheel[i][0]) + " " + Double.toString(wheel[i][1]) + "\r\n");
	        }

	        out.close();

		} catch (Exception e) {

			System.out.println(e.toString());
		}
	}
}

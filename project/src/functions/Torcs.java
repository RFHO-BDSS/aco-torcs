package functions;

import evaluator.*;

public class Torcs implements IFunction {

	private ServerCommunication server;

	private int time;
	private int dimension;

	private double highest;

	private double[] best;

	public int getDimension() { return this.dimension; }

	public double getMin() { return 0.0; }

	public double getMax() { return 1.0; }

	public Torcs(int time) {

		this.server = new ServerCommunication("localhost", 3001);
		this.dimension = server.getParamNumber();
		this.server.setFitnessFunction((FitnessFunction) new TorcsFitness());

		this.time = time;
		this.highest = Double.MIN_VALUE;
	}

	public double fitness(double[] variables) {

		Double fitness = null;

		try {

			fitness = (Double) server.launchSimulation(variables, time);

			if (fitness > highest) {
				best = variables;
			}

		} catch (TimeOverException toe) {

			//This exception is raised when simulation time is over
			//It is an alternative to the use of server.getRemainingTime()
			try {

				server.saveBest(best);
				server.close();

			} catch (Exception e) {

				System.out.println(e.toString());
				server.close();
			}

			System.exit(-1);

		} catch (Exception e) {

			System.out.println(e.toString());
			server.close();
		}

		return fitness.doubleValue();
	}
}

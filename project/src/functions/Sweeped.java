package functions;

public class Sweeped implements IFunction{

	public int getDimension() { return 2; }

	public double getMin() { return -1.0; }

	public double getMax() { return 1.0; }

	public double fitness(double[] variables) {

		return Math.pow(variables[0], 3) + 3.0 * Math.pow(variables[1], 2);
	}
}

package functions;

public class Tilted implements IFunction {

	public int getDimension() { return 2; }

	public double getMin() { return -1.0; }

	public double getMax() { return 1.0; }

	public double fitness(double[] variables) {

		return 2.0 * variables[0] + variables[1] + 3.0;
	}
}

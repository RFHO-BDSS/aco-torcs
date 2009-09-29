package functions;

public interface IFunction {

	public int getDimension();

	public double getMin();

	public double getMax();

	public double fitness(double[] variables);
}

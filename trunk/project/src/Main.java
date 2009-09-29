import aco.Colony;
import functions.*;
import wox.serial.Easy;

public class Main {

	private static final int NUMBER_OF_ANTS = 8;
	private static final int NUMBER_OF_VECTORS = 4;
	//private static final int DIMENSION = 2;
	private static final int MAX_CYCLES = 50;

	private static final double START_POINT = 0.5;
	private static final double PHEROMONE_EVAPORATION = 0.5;
	private static final double PHEROMONE_GAIN = 0.001;
	private static final double RADIUS = 0.25;
	private static final double RADIUS_DECREASE = 0.99;
	//private static final double MAX_VALUE = 1.0;
	//private static final double MIN_VALUE = 0.0;

	public static void main(String[] args) {

		IFunction function = (IFunction) new Torcs(600);

		//System.out.println(Integer.toString(function.getDimension()));

		//Colony colony = new Colony(NUMBER_OF_ANTS, NUMBER_OF_VECTORS, DIMENSION, MAX_CYCLES, START_POINT, PHEROMONE_GAIN, RADIUS, RADIUS_DECREASE, MIN_VALUE, MAX_VALUE);

		Colony colony = new Colony(NUMBER_OF_ANTS, NUMBER_OF_VECTORS, MAX_CYCLES, START_POINT, PHEROMONE_EVAPORATION, PHEROMONE_GAIN, RADIUS, RADIUS_DECREASE);

		colony.setFunction(function);

		colony.optimize();

		Easy.save(colony.getFittestSolution(), "solution.xml");

		System.out.println("Finished!");

	}
}

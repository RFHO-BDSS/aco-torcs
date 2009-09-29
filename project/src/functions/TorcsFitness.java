package functions;

import evaluator.FitnessFunction;
import java.lang.Math;

public class TorcsFitness implements FitnessFunction{

	public Object getFitness(double bestlap, double topspeed, double distraced, double damage) {

		System.out.println(Double.toString(bestlap) + " " + Double.toString(topspeed));

		return new Double(topspeed);
	}

}

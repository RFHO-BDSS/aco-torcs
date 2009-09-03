package hillClimber;

import evaluator.FitnessFunction;

public class Fitness implements FitnessFunction{

	// you are free to use any fitness scheme you want, however keep in mind that damage will always
	// be returned as zero, since we are running TORCS with the -nodamage flag
	public Object getFitness(double bestlap, double topspeed, double distraced, double damage) {
		return new Double(distraced);
	}

}

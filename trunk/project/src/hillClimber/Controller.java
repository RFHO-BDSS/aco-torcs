package hillClimber;

import java.util.Random;
import evaluator.ServerCommunication;
import evaluator.TimeOverException;

import wox.serial.Easy;

public class Controller {

	// controllers take the form of a double array, which have
	// parameters in the range (0..1)
	private double[] parameters;
	private double fitness = Double.MIN_VALUE;
	
	private transient Random random;
	
	static private double pMutation; // probability of mutating (set in constructor)
	static private final double mutation=0.1; // how much to mutate
	
	public Controller(int numParameters) {
		pMutation = 5/numParameters;
		parameters = new double[numParameters];
		random = new Random(System.currentTimeMillis());
	}
	
	// Write to XML
	public void save(String fileName) {
		Easy.save (this, fileName);
	}

	// Load from XML
	public void load(String fileName) {
		// since we can't replace "this", we'll just copy the values across
		Controller loaded = (Controller)Easy.load(fileName);
		setParameters(loaded.getParameters());
	}
	
	// randomly initialise parameters
	public void initiliase() {
		for (int i=0; i<parameters.length; i++)
			parameters[i] = random.nextDouble();
	}
	
	// set parameters
	public void setParameters(double[] p) {
		if (parameters == null)
			parameters = new double[0];
		if (p.length != parameters.length) {
			System.out.println("Warning: redefining length of parameters, pMutation also updating to match.");
			parameters = new double[p.length];
			pMutation = 1/p.length;
		}
		for (int i=0; i<parameters.length; i++)
			parameters[i] = p[i];	
	}
	
	// get parameters
	public double[] getParameters() {
		if (parameters == null) {
			return null;
		}
		double[] p = new double[parameters.length];
		for (int i=0; i<parameters.length; i++)
			p[i] = parameters[i];
		return p;
	}
	
	// set fitness
	public void setFitness(double fitness) {
		this.fitness = fitness;	
	}
	
	// get fitness, return null if we think it's never been set
	public double getFitness() {
		return fitness;
	}
	
	
	// output as String
	public String toString() {
	
		String currentValues="";
		for (int i=0; i<parameters.length ; i++)
			currentValues = currentValues + "\n " + parameters[i];
		return ("Parameters:" + currentValues + "\n"
			+ "Fitness:\n " + fitness);
	}
	
	// apply mutation
	public void mutate() {
	
		for (int i=0; i<parameters.length; i++)
		if (random.nextDouble() < pMutation) {
			// mutate
			parameters[i] += mutation * (random.nextDouble() - 0.5);
			
			// enforce boundaries for the parameter (0..1)
			parameters[i] = Math.max(0, Math.min(1, parameters[i]));
		}
	}
	
	// used when playing
	public static void main(String[] args) throws Exception {
		
		// connect to server
		System.out.println("Starting...");
		ServerCommunication server = new ServerCommunication("localhost",3001);
		
		// set the fitness function we are using
		server.setFitnessFunction(new Fitness());
		
		// get numParameterseters from server
		int numParameters = server.getParamNumber();	
		
		Controller current = new Controller(numParameters);
		
		// if given a starting xml, use that as our current
		if (args.length > 0) {
			// set 'this' to have the details from the loaded .xml file
			current.load(args[0]);
		}
		// otherwise randomly initialise
		else {
			current.initiliase();
		}
		
		int simulationTime = 7200; // 3 minutes - chosen arbitrarily
		
		/* 
		// THIS WOULD KEEP LOOPING THROUGH, RESTARTING EVERY TIME simulationTime is reached
		while (simulationTime < server.getRemainingTime()) {
			try {
				Double f = (Double) server.launchSimulation(current.getParameters(), simulationTime);
				System.out.println("Fitness: " + f.doubleValue());
			} catch (TimeOverException e) {
				//This exception is raised when simulation time is over
				//It is an alternative to the use of server.getRemainingTime()
				System.exit(-1);
			}
		}*/
		
		try {
			Double f = (Double) server.launchSimulation(current.getParameters(), simulationTime);
			System.out.println("Fitness: " + f.doubleValue());
		} catch (TimeOverException e) {
			//This exception is raised when simulation time is over
			//It is an alternative to the use of server.getRemainingTime()
			System.exit(-1);
		}
		
		server.close();
	}
}
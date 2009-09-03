package hillClimber;

import java.util.Random;
import evaluator.ServerCommunication;
import evaluator.TimeOverException;

import wox.serial.Easy;

public class Learner {
	
	// this is a flag to say whether or not this is the first run ever.
	// i.e. if it is, then the current has not been evaluated and 
	// therefore currentFitness does not reflects its evaluation.
	private boolean firstRun = true;
	
	// these two are transient (i.e. not serialised)
	private transient ServerCommunication server;	
	
	private int numParameters;
	private Random random = new Random(System.currentTimeMillis());
	static final int simulationTime=600; // 40 simulation time steps == 1 second, (i.e. 600 == 15 seconds)
	
	// our controller
	private Controller current;
	
	// construct a fresh HillClimber
	public Learner(ServerCommunication server, int numParameters){
	
		firstRun = true;
		
		this.numParameters = numParameters;
		this.server = server;
		
		//Random Initialization of current
		current = new Controller(numParameters);
		current.initiliase();				
		
		System.out.println("New HillClimber will begin from random initialisation.");			
	}
	
	// construct a HillClimber by loading in the current from a .XML file
	public Learner(ServerCommunication server, int numParameters, String fileName){
	
		firstRun = false;
		
		this.numParameters = numParameters;
		this.server = server;
		
		// load in details of current from XML
		current = (Controller)Easy.load(fileName);
		
		if (current.getParameters() == null) {
			System.out.println("The HillClimberController saved as " + fileName + " contains a null parameter array.");
			System.exit(-2);
		}
		if (numParameters != current.getParameters().length) {
			System.out.println("The parameter length specified by the server (" + numParameters
				+ ") conflicts with the HillClimberController saved as " + fileName + ".");
			System.exit(-2);
		}
		
		System.out.println("New HillClimberLearner will begin from individual stored as " + fileName + ".");
	}

	// run generations
	public double[] run(int numGenerations) throws Exception{
	
		boolean timeover = server.getRemainingTime() < (simulationTime * numGenerations); 
		
		Controller challenger;
		
		/* Our generation is only one evaluation, and some very simple work.
		   If your code requires some very expensive work, you may need to check
		   the remaining time at different stages to see if you can continue */
		for(int g=0; g<numGenerations && !timeover; g++){
			
			challenger = new Controller(numParameters);
			
			if (firstRun) {
				// if it is our first run, we don't make a challenger, we just evaluate current
				challenger = current;
				firstRun = false;
			}
			else {
				// copy current to make a challenger
				challenger.setParameters(current.getParameters());
					
				// and now we apply some mutation
				challenger.mutate();
			}
			
			// Evaluation			
			Double f = null;
			try {
				f = (Double) server.launchSimulation(challenger.getParameters(), simulationTime);
			} catch (TimeOverException e) {
				//This exception is raised when simulation time is over
				//It is an alternative to the use of server.getRemainingTime()
				System.exit(-1);
			}
			challenger.setFitness(f.doubleValue());
			
			System.out.print("#Generation " + g 
				+ " - current: " + (current.getFitness()==Double.MIN_VALUE?"not set":current.getFitness()) 
				+ " - challenger: " + challenger.getFitness());
			
			// Replacement
			if (challenger.getFitness() > current.getFitness()) {
				current = challenger;
				System.out.println(" <replacing>");
			}
			else {
				System.out.println();
			}
			
			// the best could be also be saved at each generation, but we won't
			//server.saveBest(current);
					
			timeover= (simulationTime > server.getRemainingTime());
		}
		
		if (timeover)
			System.out.println("Time Over!");
		
		// Send the best to the Server to keep playing
		server.saveBest(current.getParameters());
		
		// Write the best to XML
		current.save ("Best-HillClimber.xml");
		
		// print out info for the saved controller
		System.out.println(current);
		
		return current.getParameters();
	}
	
	public static void main(String[] args) throws Exception {
	
		// connect to server
		System.out.println("Starting...");
		ServerCommunication server = new ServerCommunication("localhost",3001);
		
		// set the fitness function we are using
		server.setFitnessFunction(new Fitness());
		
		// get numParameterseters from server
		int numParameters = server.getParamNumber();	
		
		Learner hcl;
		
		// if given a starting xml, use that as our current, otherwise random
		if (args.length > 0)
			hcl = new Learner(server,numParameters, args[0]);
		else 
			hcl = new Learner(server,numParameters);
		
		// run for 100 generations
		hcl.run(100);
		
		server.close();
	
	}
}

import wox.serial.Easy;
import evaluator.ServerCommunication;
import evaluator.TimeOverException;

public class Play {

	public static void main(String[] args) {

		//if(args.length > 0) {

			System.out.println("Lets play...");

			double[] solution = (double[]) Easy.load("solution_1.xml");

			ServerCommunication server = new ServerCommunication("localhost",3001);

			try {
				Double f = (Double) server.launchSimulation(solution, 20000);
				System.out.println("Fitness: " + f.doubleValue());
			} catch (TimeOverException e) {
				//This exception is raised when simulation time is over
				//It is an alternative to the use of server.getRemainingTime()
				System.exit(-1);
			} catch (Exception e) { }
		}
	//}
}

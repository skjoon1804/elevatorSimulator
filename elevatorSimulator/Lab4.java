//  Copyright © 2016 Oh Jun Kwon. All rights reserved.

public class Lab4 {
	
	// main function
	public static void main(String[] args) {
		// create an ElevatorSimulation object
		ElevatorSimulation simulation = new ElevatorSimulation();
		// Initialize the simulation by calling the start function
		simulation.start();
		// Print the statistics of the whole simulation when the simulation is over
		simulation.printBuildingState();
	}
}
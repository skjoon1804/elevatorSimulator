//  Copyright © 2016 Oh Jun Kwon. All rights reserved.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ElevatorSimulation {
	
	private int totalSimulationTime;
	private int simulationSecondRate;
	private BuildingManager manager;
	private ArrayList<ArrayList<PassengerArrival>> arrivals;
	private Elevator[] elevators;
	private Thread[] threads;
	
	// constructor for all fields in ElevatorSimulation and initialize them to default values; 
	//buildingManger, PassengerArrival, Elevator, and Thread
	public ElevatorSimulation() {
		manager = new BuildingManager();
		arrivals = new ArrayList<ArrayList<PassengerArrival>>();
		elevators = new Elevator[] {new Elevator(0, manager), new Elevator(1, manager), new Elevator(2, manager), new Elevator(3, manager), new Elevator(4, manager)};
		threads = new Thread[] {new Thread(elevators[0]), new Thread(elevators[1]), new Thread(elevators[2]), new Thread(elevators[3]), new Thread(elevators[4])};
	}
	
	// main loop of the game where the input from text file is read, the elevator is run by threads, passengers get on and off
	// until either the thread is interrupted or time runs out
	public void start() {
		readConfig();
		System.out.println("------------------------------");
		
		// elevator thread initiated
		for (int i = 0; i < 5; ++i) {
			threads[i].start();
		}

		try {
			// run the simulation until the threads are interrupted or designated time runs out
			while (SimClock.getTime() <= totalSimulationTime && !Thread.interrupted()) {
				int floorNum = 0;
				for (ArrayList<PassengerArrival> eachFloor: arrivals) {
					for (PassengerArrival eachArrival: eachFloor) {
						// take action if the expected time arrival of passengers at each floor matches the current time
						if (eachArrival.getExpectedTimeOfArrival() == SimClock.getTime()) {
							// since passengers are expected to arrive for every time period, reset their expected time of arrival
							eachArrival.setExpectedTimeOfArrival(SimClock.getTime() + eachArrival.getTimePeriod());
							System.out.printf("TIME %d | PASSENGER ARRIVAL | floor %d : %d passensers want to go to floor %d\n", SimClock.getTime(), floorNum, eachArrival.getNumPassengers(), eachArrival.getDestinationFloor());
							BuildingFloor tempFloor = manager.getFloors(floorNum);
							tempFloor.setTotalDestinationRequests(eachArrival.getDestinationFloor(), tempFloor.getPassengerRequests(eachArrival.getDestinationFloor()) + eachArrival.getNumPassengers());

							tempFloor.setPassengerRequests(eachArrival.getDestinationFloor(), tempFloor.getPassengerRequests(eachArrival.getDestinationFloor()) + eachArrival.getNumPassengers());
							manager.setBuildingFloor(floorNum, tempFloor);
						}
					}
					++floorNum;
				}
				// simulation time is ticked according to the millisecond that was set in the text file input read
				Thread.sleep(simulationSecondRate);
				SimClock.tick();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Simulation Ends, printing stats:");
		}
		
		for (int i=0; i < 5; i++){
			threads[i].interrupt();
		}
	}
	
	// function for printing the result of whole simulation when the simulation is over
	public void printBuildingState() {
		// print the status of simulation for each elevators
		System.out.println("            Total Loaded             Total Unloaded");
		for (int i = 0; i < 5; ++i) {
			System.out.printf("Elevator %d:       %d                        %d\n", i, elevators[i].getTotalLoadedPassengers(), elevators[i].getTotalUnloadedPassengers());
		}
		System.out.println("\n");
		// print the status of simulation for each floors
		System.out.println("          Total desination request | Total arrived passengers");
		for (int i =0; i<5; ++i){

			int destination=0;
			int arrived=0;
			
			for (int j=0; j<5; j++){
				destination += manager.getFloors(i).getTotalDestinationRequests(j);
				arrived += manager.getFloors(i).getArrivedPassengers(j);
			}
			System.out.printf("Floor %d:             %d                     %d\n", i, destination, arrived);

		}
	}
	
	// function for reading meaningful data and parsing them from the text file
	public void readConfig() {
		Scanner inFile = null;
		try {
			// call the text file
			inFile = new Scanner(new File("ElevatorConfig.txt"));
			// first line of the text file is designated for totalSimulationTime
			totalSimulationTime = Integer.parseInt(inFile.nextLine());
			System.out.printf("totalSimulationTime: %d\n", totalSimulationTime);
			// second line of the text file is designated for simulationSecondRate
			simulationSecondRate = Integer.parseInt(inFile.nextLine());
			System.out.printf("simulationSecondRate: %d\n", simulationSecondRate);
			// for every line after is designated for information for respective floor
			for (int i = 0; i < 5; ++i) {
				String line = inFile.nextLine();
				String[] floorInfo = line.split(";");
				arrivals.add(new ArrayList<PassengerArrival>());
				for (int j = 0; j < floorInfo.length; ++j) {
					System.out.printf("In floor #%d: arrival #%d: %s\n", i, j, floorInfo[j]);
					// parse the text in the file and store in the PassengerArrival so it can be used for later
					String[] arrivalInfo = floorInfo[j].split(" ");
					arrivals.get(i).add(new PassengerArrival(Integer.parseInt(arrivalInfo[0]), Integer.parseInt(arrivalInfo[1]), Integer.parseInt(arrivalInfo[2]), Integer.parseInt(arrivalInfo[2])));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			inFile.close();
		}
	}
}
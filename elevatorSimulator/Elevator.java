//  Copyright © 2016 Oh Jun Kwon. All rights reserved.

import java.util.ArrayList;

public class Elevator implements Runnable {
	
	private int elevatorID;
	private int currentFloor;
	private int numPassengers;
	private int totalLoadedPassengers;
	private int totalUnloadedPassengers;
	private ArrayList<ElevatorEvent> moveQueue;
	private int[] passengerDestinations;
	private BuildingManager manager;
	// constructor for all fields in Elevator and initialize them to default values,
	// which some might be dependent on the input and others might have a set value already (ex. 0);
	// elevatorID, current floor level, number of passengers, total number of loaded passengers, total number of unloaded passengers,
	// Arraylist movequeue, passenger destination array, and manager object
	public Elevator(int elevatorID, BuildingManager manager) {
		this.elevatorID = elevatorID;
		currentFloor = 0;
		numPassengers = 0;
		totalLoadedPassengers = 0;
		totalUnloadedPassengers = 0;
		moveQueue = new ArrayList<ElevatorEvent>();
		passengerDestinations = new int[] {0, 0, 0, 0, 0};
		this.manager = manager;
	}
	// main function where the elevators are looped until the thread is interrupted
	public void run() {
		while (!Thread.interrupted()) {
			// elevator is in the IDLE state
			if (checkStates() == -1) {
				int waitingFloor = manager.setElevatorToWaitingFloor(elevatorID);
				// look for floors with passengers waiting to get on the elevator
				if (waitingFloor != -1) {
					System.out.printf("TIME %d | PICKUP START | idle elevator %d goes to floor %d from floor %d\n", SimClock.getTime(), elevatorID, waitingFloor, currentFloor);
					// elevator event is created with the respective passenger information waiting at each floor
					ElevatorEvent event = new ElevatorEvent(waitingFloor, 5 * Math.abs(waitingFloor - currentFloor) + SimClock.getTime());
					moveQueue.add(event);
				}
			// elevator is in the PICKUP state, on the way to pick up passengers
			} else if (checkStates() == 0) {
				if (moveQueue.get(0).getExpectedArrival() == SimClock.getTime()){
					// elevator will head to floor where passengers are waiting, then the floor information will be removed from the moveQueue
					currentFloor = moveQueue.get(0).getDestination();
					moveQueue.remove(0);
					// elevator searches for passengers in the upward direction of floors
					for (int upperFloor = currentFloor + 1; upperFloor < 5; ++upperFloor) {
						// elevator event is created with the respective passenger information waiting at each floor
						if (manager.getFloors(currentFloor).getPassengerRequests(upperFloor) != 0) {
							ElevatorEvent event = new ElevatorEvent(upperFloor, 5 * Math.abs(upperFloor - currentFloor) + SimClock.getTime());
							moveQueue.add(event);
							// passengers get on the elevator
							numPassengers += manager.getFloors(currentFloor).getPassengerRequests(upperFloor);
							totalLoadedPassengers += manager.getFloors(currentFloor).getPassengerRequests(upperFloor);
							passengerDestinations[upperFloor] = manager.getFloors(currentFloor).getPassengerRequests(upperFloor);
							// when all passengers are on, passenger request from that floor is reset
							manager.getFloors(currentFloor).setPassengerRequests(upperFloor, 0);
							System.out.printf("TIME %d | PICKED UP | elevator %d picks up %d passengers from floor %d to floor %d\n", SimClock.getTime(), elevatorID, passengerDestinations[upperFloor], currentFloor, upperFloor);
						}
					}
					// elevator searches for passengers in the downward direction of floors
					for (int lowerFloor = currentFloor - 1; lowerFloor >= 0; --lowerFloor) {
						// elevator event is created with the respective passenger information waiting at each floor
						if (manager.getFloors(currentFloor).getPassengerRequests(lowerFloor) != 0) {
							ElevatorEvent event = new ElevatorEvent(lowerFloor, 5 * Math.abs(lowerFloor - currentFloor) + SimClock.getTime());
							moveQueue.add(event);
							// passengers get on the elevator
							numPassengers += manager.getFloors(currentFloor).getPassengerRequests(lowerFloor);
							totalLoadedPassengers += manager.getFloors(currentFloor).getPassengerRequests(lowerFloor);
							passengerDestinations[lowerFloor] = manager.getFloors(currentFloor).getPassengerRequests(lowerFloor);
							// when all passengers are on, passenger request from that floor is reset
							manager.getFloors(currentFloor).setPassengerRequests(lowerFloor, 0);
							System.out.printf("TIME %d | PICKED UP | elevator %d picks up %d passengers from floor %d to floor %d\n", SimClock.getTime(), elevatorID, passengerDestinations[lowerFloor], currentFloor, lowerFloor);
						}
					}
					// after searching through all the floors, the information is cleared and reset
					manager.clearApproachingElevator(currentFloor);
				}
			// elevator is in the DROPOFF state, on the way to drop off passengers
			} else if (checkStates() == 1) {
				if (moveQueue.get(0).getExpectedArrival() == SimClock.getTime()) {
					// destination floor is determined from the array of actions that the elevator has to take
					int destinationFloor = moveQueue.get(0).getDestination();
					System.out.printf("TIME %d | DROP OFF | elevator %d drops %d passengers from floor %d at floor %d\n", SimClock.getTime(), elevatorID, passengerDestinations[destinationFloor], currentFloor, destinationFloor);
					// passengers get off the elvator
					numPassengers -= passengerDestinations[destinationFloor];
					totalUnloadedPassengers += passengerDestinations[destinationFloor];
					manager.getFloors(destinationFloor).setArrivedPassengers(currentFloor, passengerDestinations[destinationFloor]);
					passengerDestinations[destinationFloor] = 0;
					// when all passengers are off, destination request for that floor is reset
					currentFloor = destinationFloor;
					moveQueue.remove(0);
				}
			}
		}
	}
	
	private int checkStates() {
		// return -1 if elevators are given no action to do and there are no passengers on the elevator
		// basically its state is IDLE
		if (moveQueue.isEmpty() && numPassengers == 0) {
			return -1;
		} 
		// return 0 if elevators have some actions to do but there are no passengers on the elevator
		// basically its state is on the way to pickup passengers
		else if (!moveQueue.isEmpty() && numPassengers == 0) {
			return 0;
		} 
		// return 1 if elevators have some actions to do, or there are some passengers on the elevator
		// basically its state is on the way to dropoff passengers
		else {
			return 1;
		} // IDLE -> -1; PICKUP -> 0; DROPOFF -> 1;
	}
	// getter for Total number of loaded passengers 
	public int getTotalLoadedPassengers() {
		return totalLoadedPassengers;
	}
	// getter for total number of unloaded passengers
	public int getTotalUnloadedPassengers() {
		return totalUnloadedPassengers;
	}
}
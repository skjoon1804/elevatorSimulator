//  Copyright © 2016 Oh Jun Kwon. All rights reserved.

public class BuildingFloor {
	
	private int[] totalDestinationRequests;
	private int[] totalArrivedPassengers;
	
	private int[] arrivedPassengers;
	private int[] passengerRequests;
	private int approachingElevator;

	
	// constructor for all fields in BuildingFloor and initialize them to default values; 
	// totalDestinationRequests, arrivedPassengers, passengerRequests, approachingElevator
	public BuildingFloor() {
		totalDestinationRequests = new int[] {0, 0, 0, 0, 0};
		arrivedPassengers = new int[] {0, 0, 0, 0, 0};
		passengerRequests = new int[] {0, 0, 0, 0, 0};
		totalArrivedPassengers = new int[] {0,0,0,0,0};
		approachingElevator = -1;
	}

	// getter from TotalDestinationRequests array for according floor
	public int getTotalDestinationRequests(int floorNum) {
		return totalDestinationRequests[floorNum];
	}
	// setter for totalDestinationRequests array for according floor
	public void setTotalDestinationRequests(int floorNum, int passengerNum) {
		totalDestinationRequests[floorNum] = passengerNum;
	}
	// getter from ArrivedPassengers array for according floor
	public int getArrivedPassengers(int floorNum) {
		return arrivedPassengers[floorNum];
	}
	// setter for ArrivedPassengers array for according floor
	public void setArrivedPassengers(int floorNum, int passengerNum) {
		arrivedPassengers[floorNum] = passengerNum;
	}
	// getter for PassengerRequests array for according floor
	public int getPassengerRequests(int floorNum) {
		return passengerRequests[floorNum];
	}
	// setter for PassengerRequests array for according floor
	public void setPassengerRequests(int floorNum, int passengerNum) {
		passengerRequests[floorNum] = passengerNum;
	}
	// getter for approachingElevator and return its elevatorID
	public int getapproachingElevator() {
		return approachingElevator;
	}
	// setter for approachingElevator that would be assigned to do the task
	public void setapproachingElevator(int approachingElevator) {
		this.approachingElevator = approachingElevator;
	}
	// if the elevator is idle, search for floors that have passenger requests
	// if the elevator would have task to do, return true, otherwise return false
	public boolean hasArrivedPassengers() {
		if (approachingElevator == -1) {
			for (int i = 0; i < 5; ++i) {
				if (passengerRequests[i] != 0) {
					return true;
				}
			}
		}
		return false;
	}
}
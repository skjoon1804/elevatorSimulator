//  Copyright © 2016 Oh Jun Kwon. All rights reserved.

public class BuildingManager {
	
	private static BuildingFloor[] floors;

	// constructor for all fields in BuildingManager and initialize them to default values;
	// BuildingFloor
	public BuildingManager() {
		floors = new BuildingFloor[] {new BuildingFloor(), new BuildingFloor(), new BuildingFloor(), new BuildingFloor(), new BuildingFloor()};
	}
	// getter from BuildingFloor array for according floor
	public synchronized BuildingFloor getFloors(int floorNum) {
		return floors[floorNum];
	}
	// setter for BuildingFloor array for according floor 
	public synchronized void setBuildingFloor(int floorNum, BuildingFloor floor) {
		floors[floorNum] = floor;
	}
	// search all floors and look for floors that have any passengers waiting to get on the elevator
	// return the floor number, if not return -1
	private synchronized int getWaitingFloor() {
		for (int i = 0; i < 5; ++i) {
			if (floors[i].hasArrivedPassengers()) {
				return i;
			}
		}
		return -1;
	}
	// for each floors that passengers are waiting to get on the elevator
	// assign an elevator to head to that floor
	public synchronized int setElevatorToWaitingFloor(int elevatorID) {
		int waitingFloor = getWaitingFloor();
		if (waitingFloor != -1) {
			floors[waitingFloor].setapproachingElevator(elevatorID);
		}
		return waitingFloor;
	}
	// assign approachingElevator value to -1 which indicates that
	// the elevator is in idle state right now
	public synchronized void clearApproachingElevator(int floorNum) {
		floors[floorNum].setapproachingElevator(-1);
	}
}
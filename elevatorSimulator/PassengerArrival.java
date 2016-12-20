//  Copyright © 2016 Oh Jun Kwon. All rights reserved.

public class PassengerArrival {
	
	private int numPassengers;
	private int destinationFloor;
	private int timePeriod;
	private int expectedTimeOfArrival;
	// constructor for all fields in the PassengerArrival and initialize them to default values that were input;
	// numPassengers, destinationFloor, timePeriod, expectedTimeOfArrival
	public PassengerArrival(int numPassengers, int destinationFloor, int timePeriod, int expectedTimeOfArrival) {
		this.numPassengers = numPassengers;
		this.destinationFloor = destinationFloor;
		this.timePeriod = timePeriod;
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}
	// getter for number of passengers
	public int getNumPassengers() {
		return numPassengers;
	}
	// setter for number of passengers according to the input
	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}
	// getter for destination floor level
	public int getDestinationFloor() {
		return destinationFloor;
	}
	// setter for destination floor level according to the input
	public void setDestinationFloor(int destinationFloor) {
		this.destinationFloor = destinationFloor;
	}
	// getter for number of time period
	public int getTimePeriod() {
		return timePeriod;
	}
	// setter for number of time period according to the input
	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
	}
	// getter for the expected time of arrival
	public int getExpectedTimeOfArrival() {
		return expectedTimeOfArrival;
	}
	// setter for the expected time of arrival according to the input
	public void setExpectedTimeOfArrival(int expectedTimeOfArrival) {
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}	
}
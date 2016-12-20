//  Copyright © 2016 Oh Jun Kwon. All rights reserved.

public class ElevatorEvent {
	
	private int destination;
	private int expectedArrival;
	// constructor for all fields in the ElevatorEvent and initialize them to default values that were input;
	// destination level, expected arrival time
	public ElevatorEvent(int destination, int expectedArrival) {
		this.destination = destination;
		this.expectedArrival = expectedArrival;
	}
	// getter for destination level
	public int getDestination() {
		return destination;
	}
	// setter for destination level according to the input
	public void setDestination(int destination) {
		this.destination = destination;
	}
	// getter for the expected time of arrival
	public int getExpectedArrival() {
		return expectedArrival;
	}
	// setter for the expected time of arrival according to the input
	public void setExpectedArrival(int expectedArrival) {
		this.expectedArrival = expectedArrival;
	}
	
}
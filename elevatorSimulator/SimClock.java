//  Copyright © 2016 Oh Jun Kwon. All rights reserved.

public class SimClock {
	
	private static int time;
	// constructor for all fields in the SimClock, and initialize time value to zero
	public SimClock() {
		time = 0;
	}
	// increment time value by one every time this function is called
	public static void tick() {
		++time;
	}
	// getter for the value time
	public static int getTime() {
		return time;
	}
}
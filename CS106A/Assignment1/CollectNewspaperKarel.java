/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	public void run() {
		findNewspaper();
		takeNewspaper();
		returnToStartingPoint();
	}

//Before findNewsPaper is performed Karel is looking east. Karel needs to walk front until reaches
//	the wall then turn right, move one step forward and turn left to look east again.
private void findNewspaper() {
	while(frontIsClear()) {
		move();
	  }
	turnRight();
	move();
	turnLeft();
	}
//Karel is looking right at the newspaper. Just moving one step forward, taking the beeper and 
//turning around is needed.
private void takeNewspaper() {
	move();
	pickBeeper();
	turnAround();
}
//Karel is looking west, needs to walk until gets to the wall, then turn right to be looking north
//one step forward and turn right to be looking east just as he started.
private void returnToStartingPoint() {
	while(frontIsClear()) {
		move();
	}
		turnRight();
		move();
		turnRight();
	}
}

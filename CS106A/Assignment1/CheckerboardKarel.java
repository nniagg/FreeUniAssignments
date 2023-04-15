
/*
 * File: CheckerboardKarel.java
 * ----------------------------
/*
 * 
 */
import stanford.karel.*;

//On the very fist cell beeper must be placed. Then Karel fills the first raw, returns to the starting
//point and checks if north is clear
public class CheckerboardKarel extends SuperKarel {
	public void run() {
		fillFirstRaw();
		returnToStartingPoint();
		while (frontIsClear()) {
			stepUp();
			fillSecondRaw();
			returnToStartingPoint();
			if (frontIsClear()) {
				stepUp();
				fillFirstRaw();
				returnToStartingPoint();
			}
		}

	}

//Karel is on 1x1 cell looking east, puts beeper on cell 1x1. 
//After this while front is clear Karel should repeat these actions:
//1. check if front is clear and if it is Karel just moves one step forward.
//2. Then again Karel checks his front cell and if it is clear Karel places beeper.
	private void fillFirstRaw() {
		putBeeper();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}
	}

//After filling the raw Karel is looking east, therefore needs to turn around and move while
//front is clear. Then Karel turns right to be looking north cause he will need to check north
//cell for his future actions.
	private void returnToStartingPoint() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnRight();
	}

//On the second raw 1x2 cell does not need beeper. Therefore, if front is clear karel has to move
//and then while front is clear repeat these actions:
//1.move and put beeper
//2.then again check if front is clear and if it is just move
	private void fillSecondRaw() {
		while (frontIsClear()) {
			move();
			putBeeper();
			if (frontIsClear()) {
				move();
			}
		}
	}

//StepUp is meant to set Karel in a right position to start filling raws.
	private void stepUp() {
		move();
		turnRight();
	}
}

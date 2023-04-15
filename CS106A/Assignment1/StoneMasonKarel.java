/*
 * File: StoneMasonKarel.java
 * --------------------------
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	//first column in any possible world needs to be filled.
	public void run() {
		fillTheColumn();
		descend();
		goToNextColumn();
		while(frontIsClear()) {
			fillTheColumn();
			descend();
			goToNextColumn();
		}
		fillTheColumn();
		descend();
	}
//before Karel starts filling the column he is looking east, needs to go upwards so must turn left.
//columns might not be completely empty so Karel must check if beeper is present or not and shall 
//not put the second beeper if one is present. The last cell in the column separately needs to
//be checked and filled if needed.
private void fillTheColumn() {
	turnLeft();
	while(frontIsClear()) {
		if(beepersPresent()) {
			move();
		} else {
			putBeeper();
			move();
		}
	}
	if(noBeepersPresent()) {
		putBeeper();
	}
    }
//until descend is performed Karel is looking north. He needs to turn around, walk while front is
//clear and then turn left to be looking east just like he was in his starting position.
private void descend() {
	turnAround();
	while(frontIsClear()) {
		move();
	}
		turnLeft();
}
//It is known that after a column there is either a wall or after three empty cells another column
//so after filling a column if front is clear after 3 empty cells there will be another column.
private void goToNextColumn() {
	if(frontIsClear()) {
     for(int i = 0; i < 4; i++){
		move();
	}
	
}

}
}
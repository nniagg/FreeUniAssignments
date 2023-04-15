
import stanford.karel.SuperKarel;

public class MidpointFindingKarel extends SuperKarel {
    public void run() {
        if (frontIsClear()) {
        fillTheRow();
        turnAround();
        move();
        //Here Karel must check if the world is 2x2 or not. If it's 2x2 world Karel just has to pick one
        //beeper out of two. If the world is bigger than 2x2 then Karel takes one beeper from the left edge,
        //one from the right edge and goes on this way until only one beeper is present in the world.
        if(frontIsClear()) {
        	turnAround();
        	move();
            pickBeepersOnEdge();
            turnAround();
        if(frontIsClear()) {
        	move();
        	while (beepersPresent()) {
                pickLastBeepers();
            }
            move();
            }
        }else {
        	pickBeeper();
        	turnAround();
        	move();
        }
        }else {
        	putBeeper();
        }
        }
      
//First, Karel fills the whole first row with beepers
    private void fillTheRow() {
        putBeeper();
        while (frontIsClear()) {
            move();
            putBeeper();
        }
    }
    
 //Secondly, Karel picks last beepers on the edges. Karel must take into account special conditions
 //(e.x- 1x1 or 2x2 world)
    private void pickBeepersOnEdge() {
        while (frontIsClear()) {
            move();
        }
        pickBeeper();
        turnAround();
        while (frontIsClear()) {
            move();
        }
        pickBeeper();
    }
//Lastly, Karel picks all beepers one by one from the right and left sides.    
    private void pickLastBeepers() {

        while (beepersPresent()) {
            move();
        }
        turnAround();
        move();
        move();
        if (beepersPresent()) {
            turnAround();
            move();
            pickBeeper();
        }
        else move();
        turnAround();
        move();

    }

}

/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		// user enters the natural number
		int X = readInt(" Enter a number: ");
		// counter is to count how many times while loop ran
		int counter = 0;
		// if entered number is even we divide it by 2, else multiply by 3 and add 1.
		while (X != 1) {
			if (X % 2 == 0) {
				println(X + "is even, so I take half:" + X / 2);
				X = X / 2;
			} else {
				println(X + "is odd, so I make 3X+1: " + (X * 3 + 1));
				X = (X * 3) + 1;
			}
			counter++;
		}
		println("The process took " + counter + " steps to reach 1.");

	}
}

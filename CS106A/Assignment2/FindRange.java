
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	public void run() {
		println("This program finds the largest and smallest numbers.")
		int minNumber = Integer.MAX_VALUE; // -2147483648 = -(2^31)
		int maxNumber = Integer.MIN_VALUE; // 2147483647 = (2^31-1)

		while (true) {
			int value = readInt();
			if (value == SENTINEL)
				break;
			// If input is less than currently saved minNumber, update minNumber
			if (value < minNumber) {
				minNumber = value;
			}
			// If input is bigger than currently saved maxNumber, update maxNumber

			if (value > maxNumber) {
				maxNumber = value;
			}

		}
		// Print result
		// if minNumber == 2147483647 , this means that the only value user entered was
		// SENTINEL.
		if (minNumber == 2147483647) {
			println(" No value was entered.");
		} else {
			println("Max Number = " + maxNumber);
			println("Min Number = " + minNumber);

		}
	}

	private static final int SENTINEL = 0;
}

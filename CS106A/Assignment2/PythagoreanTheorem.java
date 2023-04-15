
/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Pythagorean theorem");
		// int a is for the first catheter
		int a = readInt("Enter a: ");
		// int b is for the second catheter
		int b = readInt("Enter b: ");
		// the square of the length of the hypothenuse equals the sum of the squares of
		// the lengths of the other two sides
		double result = Math.sqrt((a * a) + (b * b));
		println("The Hypothenuse is " + result);
	}
}

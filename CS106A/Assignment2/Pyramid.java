
/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {
		// from 1 to number BRICKS_IN_BASE pyramid floors should be built
		for (int i = 1; i <= BRICKS_IN_BASE; i++) {
			// k is the number of bricks on each floor
			for (int k = 0; k <= BRICKS_IN_BASE - i; k++) {
				// X starting coordinate for each brick is canvas size minus BRICKS_IN_BASE
				// multiplied by the
				// number of bricks plus BRICK_WIDTH multiplied by the number which identifies
				// after how many
				// bricks the exact brick comes in row
				int X = ((getWidth() - (BRICK_WIDTH * (BRICKS_IN_BASE - i - 1))) / 2 + (BRICK_WIDTH * k));
				int Y = getHeight() - (BRICK_HEIGHT * i);
				GRect brick = new GRect(X, Y, BRICK_WIDTH, BRICK_HEIGHT);
				add(brick);

			}
		}

	}
}


/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {
	public void run() {

	private static final int RECT_WIDTH = 180;
	private static final int RECT_HEIGHT = 70;

	public void run() {
		addRects();
		addLines();
		addLabels();

	}

	// one by one adding 4 rectangles of the same width and height
	// divided height of the canvas by 6 and width by 2 for better orientation
	private void addRects() {
		GRect rect1 = new GRect((getWidth() / 2) - (RECT_WIDTH / 2), ((getHeight() / 6)), RECT_WIDTH, RECT_HEIGHT);
		add(rect1);
		GRect rect2 = new GRect((getWidth() / 2) - (RECT_WIDTH / 2), ((getHeight() / 6) * 3), RECT_WIDTH, RECT_HEIGHT);
		add(rect2);
		GRect rect3 = new GRect(((getWidth() / 2) - (RECT_WIDTH * 3 / 2) - 20), ((getHeight() / 6) * 3), RECT_WIDTH,
				RECT_HEIGHT);
		add(rect3);
		GRect rect4 = new GRect(((getWidth() / 2) + (RECT_WIDTH * 1 / 2) + 20), ((getHeight() / 6) * 3), RECT_WIDTH,
				RECT_HEIGHT);
		add(rect4);
	}

	// all the 3 lines have the same starting x coordinate and they end on their
	// rectangles middle points

	private void addLines() {
		GLine line1 = new GLine((getWidth() / 2), (getHeight() / 6) + RECT_HEIGHT, (getWidth() / 2),
				(getHeight() / 6) * 3);
		add(line1);
		GLine line2 = new GLine((getWidth() / 2), (getHeight() / 6) + RECT_HEIGHT, (getWidth() / 2) + 20 + RECT_WIDTH,
				(getHeight() / 6) * 3);
		add(line2);
		GLine line3 = new GLine((getWidth() / 2), (getHeight() / 6) + RECT_HEIGHT, (getWidth() / 2) - 20 - RECT_WIDTH,
				(getHeight() / 6) * 3);
		add(line3);

	}

	private void addLabels() {

		GLabel label1 = new GLabel("Program");
		label1.setFont("Serif-BOLD-22");
		double X = label1.getWidth();
		double Y = label1.getAscent();
		label1.setLocation((getWidth() / 2) - (RECT_WIDTH / 2) + (RECT_WIDTH - X) / 2,
				((getHeight() / 6)) + ((RECT_HEIGHT + Y) / 2));
		add(label1);

		GLabel label2 = new GLabel("ConsoleProgram");
		label2.setFont("Serif-BOLD-22");
		double Z = label2.getWidth();
		double L = label2.getAscent();
		label2.setLocation((getWidth() / 2) - (RECT_WIDTH / 2) + (RECT_WIDTH - Z) / 2,
				((getHeight() / 6) * 3) + ((RECT_HEIGHT + L) / 2));
		add(label2);

		GLabel label3 = new GLabel("GraphicsProgram");
		label3.setFont("Serif-BOLD-22");
		double M = label3.getWidth();
		double N = label3.getAscent();
		label3.setLocation(((getWidth() / 2) - (RECT_WIDTH * 3 / 2) - 20) + (RECT_WIDTH - M) / 2,
				((getHeight() / 6) * 3) + ((RECT_HEIGHT + N) / 2));
		add(label3);

		GLabel label4 = new GLabel("DialogProgram");
		label4.setFont("Serif-BOLD-22");
		double K = label4.getWidth();
		double F = label4.getAscent();
		label4.setLocation(((getWidth() / 2) + (RECT_WIDTH * 1 / 2) + 20) + (RECT_WIDTH - K) / 2,
				((getHeight() / 6) * 3) + ((RECT_HEIGHT + F) / 2));
		add(label4);
	}
}

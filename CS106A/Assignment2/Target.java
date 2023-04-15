
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	public void run() {
		addBigOval();
		addMediumOval();
		addSmallOval();
	}

	private void addBigOval() {
		// diameter of the big circle is 144 pixels
		GOval bigOval = new GOval((getWidth() - 144) / 2, (getHeight() - 144) / 2, 144, 144);
		bigOval.setColor(Color.RED);
		bigOval.setFilled(true);
		bigOval.setFillColor(Color.RED);
		add(bigOval);
	}

	private void addMediumOval() {
		// diameter is 94 pixels
		GOval mediumOval = new GOval((getWidth() - 94) / 2, (getHeight() - 94) / 2, 94, 94);
		mediumOval.setColor(Color.WHITE);
		mediumOval.setFilled(true);
		mediumOval.setFillColor(Color.WHITE);
		add(mediumOval);
	}

	private void addSmallOval() {
		// diameter is 44 pixels
		GOval smallOval = new GOval((getWidth() - 44) / 2, (getHeight() - 44) / 2, 44, 44);
		smallOval.setColor(Color.RED);
		smallOval.setFilled(true);
		smallOval.setFillColor(Color.RED);
		add(smallOval);
	}

}

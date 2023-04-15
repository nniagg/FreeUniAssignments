
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import com.sun.prism.paint.Color;
import acm.util.RandomGenerator;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private static final int DELAY = 8;
	/** Runs the Breakout program. */

	private GRect paddle = new GRect(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT,
			PADDLE_WIDTH, PADDLE_HEIGHT);
	private GOval ball = new GOval(2 * BALL_RADIUS, 2 * BALL_RADIUS);
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	private int brokenBricks = 0;

	public void init() {
		setSize(WIDTH, HEIGHT);
		addMouseListeners();
	}

	public void run() {
		gameInitialization();
		gamePlay();
	}

	private void gamePlay() {
		addMouseListeners();
		for (int i = 0; i < NTURNS; i++) {
			singleTry();
		}
		if (brokenBricks != 100) {
			remove(ball);
			GLabel lost = new GLabel(" Try again! :^) ");
			lost.setLocation((WIDTH - lost.getWidth()) / 2 - 40, ((HEIGHT / 2)) - (lost.getAscent() / 2));

			lost.setColor(java.awt.Color.BLACK);
			lost.setFont("Serif-BOLD-25");
			add(lost);
		}
	}

	// Creates bricks

	private void gameInitialization() {
		for (int k = 1; k <= NBRICK_ROWS; k++) {
			for (int i = 1; i <= NBRICKS_PER_ROW; i++) {
				double X = (WIDTH - (NBRICKS_PER_ROW * BRICK_WIDTH) - ((NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2
						+ ((BRICK_WIDTH + BRICK_SEP) * (i - 1));
				int Y = (BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * (k - 1));
				if (k <= 2) {
					GRect brick = new GRect(X, Y, BRICK_WIDTH, BRICK_HEIGHT);
					brick.setColor(java.awt.Color.RED);
					brick.setFilled(true);
					brick.setFillColor(java.awt.Color.RED);
					add(brick);
				}
				if (k >= 3 & k <= 4) {
					GRect brick = new GRect(X, Y, BRICK_WIDTH, BRICK_HEIGHT);
					brick.setColor(java.awt.Color.ORANGE);
					brick.setFilled(true);
					brick.setFillColor(java.awt.Color.ORANGE);
					add(brick);
				}
				if (k >= 5 & k <= 6) {
					GRect brick = new GRect(X, Y, BRICK_WIDTH, BRICK_HEIGHT);
					brick.setColor(java.awt.Color.YELLOW);
					brick.setFilled(true);
					brick.setFillColor(java.awt.Color.YELLOW);
					add(brick);
				}
				if (k >= 7 & k <= 8) {
					GRect brick = new GRect(X, Y, BRICK_WIDTH, BRICK_HEIGHT);
					brick.setColor(java.awt.Color.GREEN);
					brick.setFilled(true);
					brick.setFillColor(java.awt.Color.GREEN);
					add(brick);
				}
				if (k >= 9 & k <= 10) {
					GRect brick = new GRect(X, Y, BRICK_WIDTH, BRICK_HEIGHT);
					brick.setColor(java.awt.Color.CYAN);
					brick.setFilled(true);
					brick.setFillColor(java.awt.Color.CYAN);
					add(brick);
				}
			}
		}
		GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
		brickCounter.setLocation(0, 0);
		add(brickCounter);
	}

	public void mouseMoved(MouseEvent e) {
		// Java runs this when mouse is clicked
		paddle.setColor(java.awt.Color.BLACK);
		paddle.setFilled(true);
		paddle.setFillColor(java.awt.Color.BLACK);
		if (e.getX() >= 0 & e.getX() <= WIDTH - PADDLE_WIDTH) {
			paddle.setLocation(e.getX(), HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			add(paddle);
		}
	}

	private void ballCreation() {
		ball.setFilled(true);
		ball.setFillColor(java.awt.Color.BLACK);
		ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS);
		add(ball);
	}

	// describes conditions in which ball direction must be changed
	private void ballDirection() {
		vy = 3.0;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5))
			vx = -vx;
		while (ball.getY() <= HEIGHT - (4 * BALL_RADIUS)) {
			ball.move(vx, vy);
			pause(DELAY);
			breakBricks();
			if (getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + (2 * BALL_RADIUS)) == paddle) {
				bounceClip.play();
				vy = -3.0;
			}
			if (ball.getY() <= 0) {
				vy = 3.0;
			}
			if (ball.getX() <= 0) {
				vx = -vx;
			}
			if (ball.getX() >= WIDTH - (2 * BALL_RADIUS)) {
				vx = -vx;
			}
		}
	}

	// this method describes breaking bricks and changing ball direction after
	// breaking
	private void breakBricks() {
		int brokenBricks = 0;
		if (getElementAt(ball.getX() + BALL_RADIUS, ball.getY() - 1) != null
				&& getElementAt(ball.getX() + BALL_RADIUS, ball.getY() - 1) != paddle) {
			bounceClip.play();
			remove(getElementAt(ball.getX() + BALL_RADIUS, ball.getY() - 1));
			brokenBricks++;
			GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
			add(brickCounter);
			vy = Math.abs(vy);

		} else {
			if (getElementAt(ball.getX() + (1.72 * BALL_RADIUS), ball.getY() + (0.28 * BALL_RADIUS)) != null
					& getElementAt(ball.getX() + (1.72 * BALL_RADIUS), ball.getY() + (0.28 * BALL_RADIUS)) != paddle) {
				bounceClip.play();
				remove(getElementAt(ball.getX() + (1.72 * BALL_RADIUS), ball.getY() + (0.28 * BALL_RADIUS)));
				brokenBricks++;
				GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
				add(brickCounter);
				vx = -Math.abs(vx);
				vy = Math.abs(vy);

			} else {
				if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + BALL_RADIUS - 1) != null
						& getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + BALL_RADIUS - 1) != paddle) {
					bounceClip.play();
					remove(getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + BALL_RADIUS - 1));
					brokenBricks++;
					GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
					add(brickCounter);
					vx = -Math.abs(vx);
				} else {
					if (getElementAt(ball.getX() + (1.72 * BALL_RADIUS), ball.getY() + (1.72 * BALL_RADIUS)) != null
							& getElementAt(ball.getX() + (1.78 * BALL_RADIUS),
									ball.getY() + (1.72 * BALL_RADIUS)) != paddle) {
						bounceClip.play();
						remove(getElementAt(ball.getX() + (1.72 * BALL_RADIUS), ball.getY() + (1.72 * BALL_RADIUS)));
						brokenBricks++;
						GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
						add(brickCounter);
						vx = -Math.abs(vx);
						vy = -Math.abs(vy);
					} else {
						if (getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + (2 * BALL_RADIUS) + 1) != null
								& getElementAt(ball.getX() + BALL_RADIUS,
										ball.getY() + (2 * BALL_RADIUS) + 1) != paddle) {
							bounceClip.play();
							remove(getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + (2 * BALL_RADIUS) + 1));
							brokenBricks++;
							GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
							add(brickCounter);
							vy = -Math.abs(vy);
						} else {
							if (getElementAt(ball.getX() + (0.28 * BALL_RADIUS),
									ball.getY() + (1.72 * BALL_RADIUS)) != null
									& getElementAt(ball.getX() + (0.28 * BALL_RADIUS),
											ball.getY() + (1.72 * BALL_RADIUS)) != paddle) {
								bounceClip.play();
								remove(getElementAt(ball.getX() + (0.28 * BALL_RADIUS),
										ball.getY() + (1.72 * BALL_RADIUS)));
								brokenBricks++;
								GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
								add(brickCounter);
								vx = Math.abs(vx);
								vy = -Math.abs(vy);
							} else {
								if (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS - 1) != null
										& getElementAt(ball.getX(), ball.getY() + BALL_RADIUS - 1) != paddle) {
									bounceClip.play();
									remove(getElementAt(ball.getX(), ball.getY() + BALL_RADIUS - 1));
									brokenBricks++;
									GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
									add(brickCounter);
									vx = Math.abs(vx);
								} else {
									if (getElementAt(ball.getX() + (0.28 * BALL_RADIUS),
											ball.getY() + (0.28 * BALL_RADIUS)) != null
											& getElementAt(ball.getX() + (0.28 * BALL_RADIUS),
													ball.getY() + (0.28 * BALL_RADIUS)) != paddle) {
										bounceClip.play();
										remove(getElementAt(ball.getX() + (0.28 * BALL_RADIUS),
												ball.getY() + (0.28 * BALL_RADIUS)));
										brokenBricks++;
										GLabel brickCounter = new GLabel(" Number of broken bricks : " + brokenBricks);
										add(brickCounter);
										vx = Math.abs(vx);
										vy = Math.abs(vy);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void singleTry() {
		ballCreation();
		waitForClick();
		ballDirection();
		if (ball.getX() == 0) {
			while (ball.getY() != (HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT - (2 * BALL_RADIUS))
					|| ball.getX() != WIDTH) {
				vy = -3.0;
				vx = rgen.nextDouble(1.0, 3.0);
				if (rgen.nextBoolean(0.5)) {
					vx = -vx;
				}
				pause(DELAY);
				ball.move(vx, vy);
			}
		}
		if (ball.getX() == WIDTH) {
			while (ball.getY() != (HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT - (2 * BALL_RADIUS)) || ball.getX() != 0) {
				vy = -3.0;
				vx = rgen.nextDouble(1.0, 3.0);
				if (rgen.nextBoolean(0.5)) {
					vx = -vx;
				}
				pause(DELAY);
				ball.move(vx, vy);
			}
		}
		if (ball.getY() == 0) {
			while (ball.getY() != (HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT - (2 * BALL_RADIUS)) || ball.getX() != 0) {
				vy = 3.0;
				vx = rgen.nextDouble(1.0, 3.0);
				if (rgen.nextBoolean(0.5)) {
					vx = -vx;
				}
				pause(DELAY);
				ball.move(vx, vy);
			}
		}
		remove(ball);
	}
}
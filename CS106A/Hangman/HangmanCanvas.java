/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	private static final int scaffoldX = 40;
	private static final int scaffoldY = 30;

	/** Resets the display so that only the scaffold appears */

	public void reset() {
		GLine scaffold = new GLine(scaffoldX, scaffoldY, scaffoldX, scaffoldY + SCAFFOLD_HEIGHT);
		add(scaffold);
		GLine beam = new GLine(scaffoldX, scaffoldY, scaffoldX + BEAM_LENGTH, scaffoldY);
		add(beam);
		GLine rope = new GLine(scaffoldX + BEAM_LENGTH, scaffoldY, scaffoldX + BEAM_LENGTH, scaffoldY + ROPE_LENGTH);
		add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens. If guess is wrong adds the letter
	 * to the list of incorrect guesses that appears at the bottom of the window.
	 */
	// white box is to hide past GLabel and rewrite new on it (very bad solution :^) )
	public void displayWord(String word, String wrongLetters) {
		GLabel displayWord = new GLabel(word);
		displayWord.setFont("Serif-BOLD-26");
		displayWord.setLocation(getWidth() / 4, 420);
		GLabel wrongTries = new GLabel(wrongLetters);
		wrongTries.setFont("Serif-BOLD-22");
		wrongTries.setLocation(getWidth() / 4, 460);
		GRect wordBox = new GRect(20, 400, 400, 200);
		wordBox.setFilled(true);
		wordBox.setColor(Color.WHITE);
		add(wordBox);
		add(displayWord);
		add(wrongTries);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold.
	 */
	public void noteIncorrectGuess(int wrongGuesses) {
		if (wrongGuesses == 1) {
			GOval head = new GOval(scaffoldX + BEAM_LENGTH - HEAD_RADIUS, scaffoldY + ROPE_LENGTH, 2 * HEAD_RADIUS,
					2 * HEAD_RADIUS);
			add(head);
		}
		if(wrongGuesses == 2) {
			GLine body = new GLine(scaffoldX + BEAM_LENGTH, scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS,
					scaffoldX + BEAM_LENGTH, scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
			add(body);
		}
		if(wrongGuesses == 3) {
			GLine leftUpperHand = new GLine(scaffoldX + BEAM_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
					scaffoldX + BEAM_LENGTH - UPPER_ARM_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
			add(leftUpperHand);
			GLine leftLowerHand = new GLine(scaffoldX + BEAM_LENGTH - UPPER_ARM_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
					scaffoldX + BEAM_LENGTH - UPPER_ARM_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
			add(leftLowerHand);
		}
		if(wrongGuesses == 4) {
			GLine rightUpperHand = new GLine(scaffoldX + BEAM_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
					scaffoldX + BEAM_LENGTH + UPPER_ARM_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
			add(rightUpperHand);
			GLine rightLowerHand = new GLine(scaffoldX + BEAM_LENGTH + UPPER_ARM_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
					scaffoldX + BEAM_LENGTH + UPPER_ARM_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
			add(rightLowerHand);
		}
		if(wrongGuesses == 5) {
			GLine leftHip = new GLine(scaffoldX + BEAM_LENGTH, scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
					scaffoldX + BEAM_LENGTH - HIP_WIDTH, scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
			add(leftHip);
			GLine leftLeg = new GLine(scaffoldX + BEAM_LENGTH - HIP_WIDTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, scaffoldX + BEAM_LENGTH - HIP_WIDTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			add(leftLeg);
		}
		if(wrongGuesses == 6) {
			GLine rightHip = new GLine(scaffoldX + BEAM_LENGTH, scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
					scaffoldX + BEAM_LENGTH + HIP_WIDTH, scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
			add(rightHip);
			GLine rightLeg = new GLine(scaffoldX + BEAM_LENGTH + HIP_WIDTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, scaffoldX + BEAM_LENGTH + HIP_WIDTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			add(rightLeg);
		}
		if(wrongGuesses == 7) {
			GLine leftFoot = new GLine(scaffoldX + BEAM_LENGTH - HIP_WIDTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
					scaffoldX + BEAM_LENGTH - HIP_WIDTH - FOOT_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			add(leftFoot);
		}
		if(wrongGuesses == 8) {
			GLine rightFoot = new GLine(scaffoldX + BEAM_LENGTH + HIP_WIDTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
					scaffoldX + BEAM_LENGTH + HIP_WIDTH + FOOT_LENGTH,
					scaffoldY + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			add(rightFoot);
		}

	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}

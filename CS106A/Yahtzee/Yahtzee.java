
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		// scoresTable is created to control whether the category was already chosen or
		// not. Every value equals to -1 and if chosen the value changes into 0 or more.
		for (int[] row : scoresTable) {
			Arrays.fill(row, -1);
		}
		for (int i = 0; i < 13; i++) {
			for (int p = 1; p <= nPlayers; p++) {
				singleTry(dice, p);
			}
		}
		for (int p = 1; p <= nPlayers; p++) {
			countScores(p);
		}
	}

	public void singleTry(int[] dice, int p) {
		display.waitForPlayerToClickRoll(p);
		firstRoll();
		// second roll
		rollSelected(dice, p);
		// third roll
		rollSelected(dice, p);
		selectCategory(dice, p);
	}

	private void rollSelected(int[] dice, int p) {
		display.waitForPlayerToSelectDice();
		display.waitForPlayerToClickRoll(p);
		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i) == true) {
				dice[i] = rgen.nextInt(1, 6);
			}
		}
		display.displayDice(dice);
	}

	// below are the methods to check if dices match with categories
	private boolean yahtzee(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[0] == dice[i]) {
				x++;
			}
		}
		if (x == 5) {
			display.updateScorecard(category, p, 50);
			scoresTable[p - 1][category - 1] = 50;
			return true;
		} else {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean threeOfaKind(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				if (dice[i] == dice[j]) {
					x++;
					if (x >= 3) {
						int score = dice[0] + dice[1] + dice[2] + dice[3] + dice[4];
						display.updateScorecard(category, p, score);
						scoresTable[p - 1][category - 1] = score;

						return true;
					}
				}
			}
			x = 0;
		}
		if (x < 3) {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean fourOfaKind(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				if (dice[i] == dice[j]) {
					x++;
					if (x >= 4) {
						int score = dice[0] + dice[1] + dice[2] + dice[3] + dice[4];
						display.updateScorecard(category, p, score);
						scoresTable[p - 1][category - 1] = score;

						return true;
					}
				}
			}
			x = 0;
		}
		if (x < 4) {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean fullHouse(int[] dice, int category, int p) {
		int x = 0;
		int m = 0;
		int y = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[0] == dice[i]) {
				x++;
			}
			if (dice[0] != dice[i]) {
				m = i;
			}
		}
		if (x == 2 || x == 3) {
			for (int i = 0; i < N_DICE; i++) {
				if (dice[m] == dice[i]) {
					y++;
				}
			}
		}
		if ((x == 2 && y == 3) || (x == 3 && y == 2)) {
			display.updateScorecard(category, p, fullHouseScore);
			scoresTable[p - 1][category - 1] = fullHouseScore;
			return true;
		} else {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean ones(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == 1) {
				x++;
			}
		}
		if (x >= 1) {
			display.updateScorecard(category, p, x);
			scoresTable[p - 1][category - 1] = x;
			return true;
		}
		if (x < 1) {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean twos(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == 2) {
				x++;
			}
		}
		if (x >= 1) {
			display.updateScorecard(category, p, x * 2);
			scoresTable[p - 1][category - 1] = x * 2;
			return true;
		}
		if (x < 1) {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean threes(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == 3) {
				x++;
			}
		}
		if (x >= 1) {
			display.updateScorecard(category, p, x * 3);
			scoresTable[p - 1][category - 1] = x * 3;
			return true;
		}
		if (x < 1) {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean fours(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == 4) {
				x++;
			}
		}
		if (x >= 1) {
			display.updateScorecard(category, p, x * 4);
			scoresTable[p - 1][category - 1] = x * 4;
			return true;
		}
		if (x < 1) {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean fives(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == 5) {
				x++;
			}
		}
		if (x >= 1) {
			display.updateScorecard(category, p, x * 5);
			scoresTable[p - 1][category - 1] = x * 5;
			return true;
		}
		if (x < 1) {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean sixes(int[] dice, int category, int p) {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == 6) {
				x++;
			}
		}
		if (x >= 1) {
			display.updateScorecard(category, p, x * 6);
			scoresTable[p - 1][category - 1] = x * 6;
			return true;
		}
		if (x < 1) {
			display.updateScorecard(category, p, 0);
			scoresTable[p - 1][category - 1] = 0;
		}
		return false;
	}

	private boolean chance(int[] dice, int category, int p) {
		int x = dice[0] + dice[1] + dice[2] + dice[3] + dice[4];
		if (x > 1) {
			display.updateScorecard(category, p, x);
			scoresTable[p - 1][category - 1] = x;
			return true;
		}
		return false;
	}

	private boolean smallStraight(int[] dice, int category, int p) {
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				for (int k = 0; k < N_DICE; k++) {
					for (int l = 0; l < N_DICE; l++) {
						if ((dice[i] == dice[j] + 1) && (dice[i] == dice[k] + 2) && (dice[i] == dice[l] + 3)) {
							display.updateScorecard(category, p, smallStaightScore);
							scoresTable[p - 1][category - 1] = smallStaightScore;
							return true;
						}
					}
				}
			}
		}
		display.updateScorecard(category, p, 0);
		scoresTable[p - 1][category - 1] = 0;
		return false;
	}

	private boolean largeStraight(int[] dice, int category, int p) {
		for (int i = 0; i < N_DICE; i++) {
			for (int j = 0; j < N_DICE; j++) {
				for (int k = 0; k < N_DICE; k++) {
					for (int l = 0; l < N_DICE; l++) {
						for (int m = 0; m < N_DICE; m++) {
							if ((dice[i] == dice[j] + 1) && (dice[i] == dice[k] + 2) && (dice[i] == dice[l] + 3)
									&& (dice[i] == dice[m] + 4)) {
								display.updateScorecard(category, p, largeStraightScore);
								scoresTable[p - 1][category - 1] = largeStraightScore;
								return true;
							}
						}
					}
				}
			}
		}
		display.updateScorecard(category, p, 0);
		scoresTable[p - 1][category - 1] = 0;
		return false;
	}

//first roll rolls every dice
	private void firstRoll() {
		for (int i = 0; i < N_DICE; i++) {
			dice[i] = rgen.nextInt(1, 6);
			display.displayDice(dice);
		}
	}

	private void selectCategory(int[] dice, int p) {
		int category = display.waitForPlayerToSelectCategory();
		// if player chooses the category which already was chosen program allows him to
		// chose new category
		if (scoresTable[p - 1][category - 1] > -1) {
			while (scoresTable[p - 1][category - 1] > -1) {
				category = display.waitForPlayerToSelectCategory();
			}
		}
		if (category == YAHTZEE) {
			yahtzee(dice, category, p);
		}
		if (category == THREE_OF_A_KIND) {
			threeOfaKind(dice, category, p);
		}
		if (category == FOUR_OF_A_KIND) {
			fourOfaKind(dice, category, p);
		}
		if (category == FULL_HOUSE) {
			fullHouse(dice, category, p);
		}
		if (category == ONES) {
			ones(dice, category, p);
		}
		if (category == TWOS) {
			twos(dice, category, p);
		}
		if (category == THREES) {
			threes(dice, category, p);
		}
		if (category == FOURS) {
			fours(dice, category, p);
		}
		if (category == FIVES) {
			fives(dice, category, p);
		}
		if (category == SIXES) {
			sixes(dice, category, p);
		}
		if (category == CHANCE) {
			chance(dice, category, p);
		}
		if (category == SMALL_STRAIGHT) {
			smallStraight(dice, category, p);
		}
		if (category == LARGE_STRAIGHT) {
			largeStraight(dice, category, p);
		}
	}

//players are numbered from 1 to 5 but scoresTable changes from 0 to 4 (p-1)
	private void countScores(int p) {
		int x = scoresTable[p - 1][0] + scoresTable[p - 1][1] + scoresTable[p - 1][2] + scoresTable[p - 1][3]
				+ scoresTable[p - 1][4] + scoresTable[p - 1][5];
		display.updateScorecard(UPPER_SCORE, p, x);
		if (x > 63) {
			display.updateScorecard(UPPER_BONUS, p, 35);
			int z = scoresTable[p - 1][8] + scoresTable[p - 1][9] + scoresTable[p - 1][10] + scoresTable[p - 1][11]
					+ scoresTable[p - 1][12] + scoresTable[p - 1][13] + scoresTable[p - 1][14];
			display.updateScorecard(LOWER_SCORE, p, z);
			int y = x + 35 + z;
			display.updateScorecard(TOTAL, p, y);
		}
		if (x <= 63) {
			display.updateScorecard(UPPER_BONUS, p, 0);
			int z = scoresTable[p - 1][8] + scoresTable[p - 1][9] + scoresTable[p - 1][10] + scoresTable[p - 1][11]
					+ scoresTable[p - 1][12] + scoresTable[p - 1][13] + scoresTable[p - 1][14];
			display.updateScorecard(LOWER_SCORE, p, z);
			int y = x + z;
			display.updateScorecard(TOTAL, p, y);
		}
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice = new int[N_DICE];
	private int largeStraightScore = 40;
	private int smallStaightScore = 30;
	private int fullHouseScore = 25;
	private int[][] scoresTable = new int[MAX_PLAYERS][TOTAL];
}

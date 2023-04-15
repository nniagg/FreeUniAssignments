
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.io.BufferedReader;

public class Hangman extends ConsoleProgram {
	HangmanCanvas canvas = new HangmanCanvas();

	public void init() {
		add(canvas);
		canvas.reset();
	}

	private int guesses = 8;
	private String currentWord = new String("");
	private String currentWord1 = new String("");
	private int dashes;
	private String wrongLetters = new String("");
	private int wrongGuesses = 0;
	private RandomGenerator rand = new RandomGenerator();

	public void run() {
		HangmanLexicon word = new HangmanLexicon();
		int numWord = rand.nextInt(0, word.getWordCount());
		word.getWord(numWord);
		String word1 = new String(word.getWord(numWord));
		word1.length();
		dashes = word1.length();
		for (int i = 0; i < word1.length(); i++) {
			currentWord += "-";
			canvas.displayWord(currentWord, wrongLetters);
		}
		println(currentWord);
		println("You have " + guesses + " guesses left.");
		while (guesses > 0 && dashes > 0) {
			singleTry(word1, guesses);
			if (currentWord1 == currentWord) {
				println("Your guess was right.");
				println("The word now looks like this: " + currentWord);
				println("You have " + guesses + " guesses left.");
				currentWord1 = "";
				canvas.displayWord(currentWord, wrongLetters);
			} else {
				guesses--;
				println("Your guess was wrong.");
				println("Your have " + guesses + " guesses left.");
			}
			if (dashes == 0) {
				break;
			}
		}
		// game stops if all the letters are guessed or no guesses left
		if (dashes == 0) {
			println("You won!");
		}
		if (guesses == 0) {
			println("You lose");
		}
	}

	//user enters single char, if not program asks to enter only one letter
	private char readChar(String prompt) {
		while (true) {
			String res = readLine(prompt);
			if (res.length() == 1) {
				return res.charAt(0);
			}
			println("Please enter exactly 1 letter!");
		}
	}

	//if user enters char in lower case program turns it into upper case letter
	private void singleTry(String word1, int guesses) {
		char c = readChar("Your guess: ");
		c = Character.toUpperCase(c);
		boolean found = false;
		for (int i = 0; i < word1.length(); i++) {
			if (word1.charAt(i) == c) {
				dashes--;
				currentWord = currentWord.substring(0, i) + c + currentWord.substring(i + 1);
				currentWord1 = currentWord;
				found = true;
			} 
		} 
		// if user enters wrong letter
		if(!found) {
			wrongGuesses ++;
			wrongLetters += " " + c;
			canvas.displayWord(currentWord, wrongLetters);
			canvas.noteIncorrectGuess(wrongGuesses);
		}
	}
}
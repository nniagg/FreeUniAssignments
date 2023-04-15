/*
 * File: ConsecutiveHeads.cpp
 * --------------------------
 * Name: [TODO: enter name here]
 * Section: [TODO: enter section leader here]
 * This file is the starter project for the Consecutive Heads problem.
 * [TODO: rewrite the documentation]
 */

#include <iostream>    
#include <cstdlib>
#include <stdio.h>
#include <stdlib.h>
#include<time.h>
using namespace std;

int singleFlip() {
	//Uses current time as seed for random generator
	srand(time(NULL));
	// 0 for heads, 1 for the tails
	int n = rand() % 2;
	return n;
}

int countFlips() {
	int headsCounter = 0;
	int flips = 0;
	while (true) {
		int n = singleFlip();
		flips++;
		if (n == 0) {
			cout << "heads" << endl;
			headsCounter++;
			if (headsCounter == 3) {
				break;
			}
		}
		else {
			cout << "tails" << endl;
			headsCounter = 0;
		}
	}
	cout << "It took " << flips << " flips to get 3 consecutive heads." << endl;
	return flips;
}

int main() {
	countFlips();
	return 0;
}

/*
 * File: Combinations.cpp
 * ----------------------
 * Name: [TODO: enter name here]
 * Section: [TODO: enter section leader here]
 * This file is the starter project for the Combinations problem.
 * [TODO: rewrite the documentation]
 */

#include <iostream>
//#include "console.h"
using namespace std;

int c(int n, int k) {
	if (n == 0 || k == 0 || k == n) {
		return 1;
	}
	return c(n - 1, k - 1) + c(n - 1, k);
}

int main() {
	cout << c(3, 2) << endl;
    return 0;
}

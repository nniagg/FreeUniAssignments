/*
 * File: NumericConversions.cpp
 * ---------------------------
 * Name: [TODO: enter name here]
 * Section: [TODO: enter section leader here]
 * This file is the starter project for the Numeric Conversions problem.
 * [TODO: rewrite the documentation]
 */

#include <iostream>
#include <string>
//#include "strlib.h"
//#include "console.h"

using namespace std;

/* Function prototypes */

string intToString(int n) {
	string answ;
	if (n >= 0 && n <= 9) {
		char c = n + '0';
		answ = string() + c;
		return answ;
	}
	int k = n % 10;
	char c = k + '0';
	answ = string() + c;
	return intToString((int)(n / 10)) + answ;
}

int stringToInt(string str) {
	if (str.length() < 1) {
		return 0;
	}
	if (str.length() > 1 && str[0] == '-') {
		return 0 - stringToInt(str.substr(1, str.length()-1));
	}
	if (str.length() == 1 && str[0] == '0') {
		return 0;
	}
	int k = str[str.length() - 1] - '0';
	//Copy str.length()-1 characters of str starting from position 0 multiply by 10 and plus k
	return 10 * stringToInt(str.substr(0, str.length() - 1)) + k;
}

/* Main program */

int main() {
	cout << intToString(1975) << endl;
	cout << stringToInt("-3") << endl;
	cout << stringToInt("122874305") << endl;
	cout << stringToInt("0") << endl;
	cout << stringToInt("-0") << endl;
	cout << stringToInt("-3475") << endl;
    return 0;
}

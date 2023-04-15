/*
* File: FleschKincaid.cpp
* ----------------------
* Name: [TODO: enter name here]
* Section: [TODO: enter section leader here]
* This file is the starter project for the Flesch-Kincaid problem.
* [TODO: rewrite the documentation]
*/

#include <iostream>
#include <fstream>
#include "console.h"
#include "simpio.h"
#include "filelib.h"
#include "tokenscanner.h"
using namespace std;

double const c0 = -15.59;
double const c1 = 0.39;
double const c2 = 11.8;

//method counts the number of sentences in text
int sentencesInLine(string line) {
	int count = 0;
	TokenScanner scanner(line);
	while (scanner.hasMoreTokens()) {
		string tk = scanner.nextToken();
		if (tk.length() == 1) {
			//three options for punctuating the end of a sentence(.?!)
			if (tk[0] == '.' || tk[0] == '?' || tk[0] == '!') {
				count++;
			}
		}
	}
	return count;
}

//struct to count words and syllables
struct WordsAndSyllables {
	int words;
	int syllables;
};

//method counts the number of syllables in a word
int syllablesInSingleWord(string word) {
	int count = 0;
	int lastVowelIndex = -2;
	//a,e,i,o,u and sometimes y are vowels
	for (int i = 0; i < word.length(); i++) {
		if (word[i] == 'a' || word[i] == 'i' || word[i] == 'o' || word[i] == 'u' || word[i] == 'y' || word[i] == 'A' || word[i] == 'I' || word[i] == 'O' || word[i] == 'U' || word[i] == 'Y') {
			if ((i - 1) != lastVowelIndex) {
				count++;
			}
			lastVowelIndex = i;
		}
		//e should not be counted when it's the last letter in a word
		if ((word[i] == 'e' || word[i] == 'E') && (i != word.length() - 1)) {
			if ((i - 1) != lastVowelIndex) {
				count++;
			}
			lastVowelIndex = i;
		}
	}
	//every word has at least 1 vowel
	if (count == 0) {
		count++;
	}
	return count;
}

//method counts the number of words
WordsAndSyllables wordsAndSyllablesInLine(string line) {
	//struct to count words and syllables
	WordsAndSyllables counter;
	counter.words = 0;
	counter.syllables = 0;
	TokenScanner scanner(line);
	scanner.addWordCharacters("'-,");
	scanner.ignoreWhitespace();
	while (scanner.hasMoreTokens()) {
		string tk = scanner.nextToken();
		if ((tk[0] >= 'a' && tk[0] <= 'z') || (tk[0] >= 'A' && tk[0] <= 'Z')) {
			counter.words++;
			counter.syllables += syllablesInSingleWord(tk);
		}
	}

	return counter;
}

int main() {
	ifstream input;
	string file;
	string prompt = "Enter filename: ";
	string filename = promptUserForFile(input, prompt);

	int sentences = 0;
	int words = 0;
	int syllables = 0;

	string fileline;
	while (getline(input, fileline)) {
		int currSentences = sentencesInLine(fileline);
		WordsAndSyllables current = wordsAndSyllablesInLine(fileline);
		sentences += currSentences;
		words += current.words;
		syllables += current.syllables;
	}

	if (words == 0) {
		words = 1;
	}
	if (sentences == 0) {
		sentences = 1;
	}

	double grade = c0 + c1*(words / (double)sentences) + c2*(syllables / (double)words);
	cout << grade << endl;

	input.close();
	return 0;
}

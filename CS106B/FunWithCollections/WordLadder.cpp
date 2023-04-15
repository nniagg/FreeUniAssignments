#include <iostream>
#include "console.h"
#include "queue.h"
#include "simpio.h"
#include "vector.h"
#include "foreach.h"
#include "lexicon.h"
#include "set.h"
using namespace std;

//takes single word as an argument and finds all its neighbour words which differ with one symbol from the argument
Vector<string> modifiedWord(string word, Lexicon &english){
    Vector<string> neighbourWords;
    for(int i = 0; i < word.length(); i++){
        for(char c = 'a'; c <= 'z'; c++){
            string modified = word.substr(0, i) + c;
            if(i < word.length()-1){
                modified += word.substr(i+1);
            }
            if(modified != word && english.contains(modified)){
                neighbourWords.add(modified);
            }
        }
    }
    return neighbourWords;
}

// BFS 
Vector<string> shortestLadder(string start, string end){
	Lexicon english("EnglishWords.dat");
    //set allTheWords keeps all the words we have reached so far in case not to return to the words we have already "visited"
    Set<string> allTheWords;
    Queue<Vector<string> > paths;
    Vector<string> firstWord;
    firstWord.add(start);
    allTheWords.add(start);
    paths.enqueue(firstWord);
    while(!paths.isEmpty()){
        //remove and take single path from vector, if path contains ending word we return the path, else find all the posible next words in this path and continue searching for shortest path
        Vector<string> singlePath = paths.dequeue();
        string lastWord = singlePath.get(singlePath.size()-1);
        if(lastWord == end){
            return singlePath;
        }else{
            Vector<string> newWords = modifiedWord(lastWord, english);
            for(int i = 0; i < newWords.size(); i++){
                string word = newWords.get(i);
                if(allTheWords.contains(word)){
                    continue;
                }
                Vector<string> newPath = singlePath;
                newPath.add(newWords.get(i));
                paths.enqueue(newPath);
                allTheWords.add(word);
            }
        }
    }
	Vector<string> empty;
	return empty;
}
int main() {
    string start = getLine("Enter starting word: ");
    string end = getLine("Enter ending word: ");
    Vector<string> ladder = shortestLadder(start, end);
	if(ladder.size() == 0) {
		cout << "No ladder found." << endl;
	} else {
		for(int i = 0; i < ladder.size() - 1; i++){
			cout << ladder.get(i) << " -> ";
		}
		cout << ladder.get(ladder.size() - 1) << endl;
	}
	
    return 0;
}
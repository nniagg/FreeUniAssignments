
#include <iostream>
#include "console.h"
#include <fstream>
#include "simpio.h"
#include "random.h"
#include "hashmap.h"
using namespace std;

//method analyzes text and keeps data of k length strings and chars that follow
bool analyzeText(HashMap<string, Vector<char> > &map, ifstream& stream, int k){
    char c = 0;
    string lastKChars = "";
	int counter = 0;
    while(stream.get(c)){
		counter++;
        if(lastKChars.length() <= k-1){
            lastKChars += c;
        }else{
			Vector<char> tempVector = map[lastKChars];
            tempVector.add(c);
			map[lastKChars] = tempVector;

            string temp = lastKChars.substr(lastKChars.length()-k+1);
            lastKChars = temp + c;
        }
    }
	if(counter < k){
		return false;
	}
	return true;
}

//generates new text using hashmap
string generateText(HashMap<string, Vector<char> > &map, int k){
	int vectorSize = 0;
	string text = "";
	foreach(string s in map){
		if(map[s].size() > vectorSize) {
			vectorSize = map[s].size();
			text = s;
		}
	}
	
	for(int i = k; i <= 2000; i++){
		string chars = text.substr(text.length() - k);
		Vector<char> current = map[chars];
		if(current.size() == 0) return text;
		int randIndex = randomInteger(0, current.size() - 1);
		char randChar = current.get(randIndex);
		text += randChar;
	}
	
	return text;
}

int main() {
	ifstream input;
	int k;
    string filename;
    while(true){
        filename = getLine("Enter filename: ");
        input.open(filename.c_str());
        if(input.is_open()){
            break;
        }
        input.clear();
        cout << "File does not exist. Try again. " << endl;
    }
    while(true){
        k = getInteger("Enter the Markov order from 1 to 10: ");
        if(k >= 1 && k <= 10){
            break;
        }
        cout << "Try again." << endl;
    }
	cout << "Processing file..." << endl;
	HashMap<string, Vector<char> > map;
	if(analyzeText(map, input, k)){
		string text = generateText(map, k);
		cout << text << endl;
	} else {
		cout << "Not valid text" << endl;
	}

    return 0;
}
#include <bits/stdc++.h>

using namespace std;

/*
 * Complete the 'pangrams' function below.
 *
 * The function is expected to return a STRING.
 * The function accepts STRING s as parameter.
 */

void recursion(string s, char letter, int &counter){
    if(letter > 'z') return;
    for(int i = 0; i < s.length(); i++){
        char upper = toupper(letter);
        if(s[i] == letter || s[i] == upper){
            counter++;
            break;
        }
    }
    letter++;
    recursion(s, letter, counter);
}
string pangrams(string s) {
    int counter = 0;
    recursion(s, 'a', counter);
    if(counter == 26)return "pangram";
    return "not pangram";
}

int main()
{
    ofstream fout(getenv("OUTPUT_PATH"));

    string s;
    getline(cin, s);

    string result = pangrams(s);

    fout << result << "\n";

    fout.close();

    return 0;
}


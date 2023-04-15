#include <bits/stdc++.h>
#include <iterator>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);

/*
 * Complete the 'alternate' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts STRING s as parameter.
 */
void allPairChars(set<char>&diffChars, vector<vector<char>>&pairChars){
    set<char>::iterator itr;
    set<char>::iterator itr2;
    for (itr = diffChars.begin(); itr != diffChars.end(); itr++) {
        vector<char>twoChars;
        twoChars.push_back(*itr);
        for(itr2 = diffChars.begin(); itr2 != diffChars.end(); itr2++){
            vector<char> copy = twoChars;
            copy.push_back(*itr2);
            if(copy[0]!=copy[1])pairChars.push_back(copy);
        }
    }
}
void max(string &s, vector<vector<char>>&pairChars, int &maxL){
    for(int i = 0; i < pairChars.size(); i++){
        string dupl="";
        for(int k = 0; k < s.size(); k++){
            if(s[k] == pairChars[i][0] || s[k] == pairChars[i][1])dupl+=s[k];
        }
        if(dupl.size()>maxL){
            int counter = 0;
            for(int i = 0; i < dupl.size()-1; i++){
                if(dupl[i]==dupl[i+1])counter++;
            }
            if(counter==0)maxL=dupl.size();
        } 
    }
}
int alternate(string s) {
    if(s.length()<2)return 0;
    if(s.length()==2)return 2;
    set<char> diffChars;
    for(int i = 0; i < s.length();  i++){
        diffChars.insert(s[i]);
    }
    vector<vector<char>> pairChars;
    allPairChars(diffChars, pairChars);
    
    int maxL=0;
    max(s, pairChars, maxL);
    return maxL;
}

int main()
{
    ofstream fout(getenv("OUTPUT_PATH"));

    string l_temp;
    getline(cin, l_temp);

    int l = stoi(ltrim(rtrim(l_temp)));

    string s;
    getline(cin, s);

    int result = alternate(s);

    fout << result << "\n";

    fout.close();

    return 0;
}

string ltrim(const string &str) {
    string s(str);

    s.erase(
        s.begin(),
        find_if(s.begin(), s.end(), not1(ptr_fun<int, int>(isspace)))
    );

    return s;
}

string rtrim(const string &str) {
    string s(str);

    s.erase(
        find_if(s.rbegin(), s.rend(), not1(ptr_fun<int, int>(isspace))).base(),
        s.end()
    );

    return s;
}


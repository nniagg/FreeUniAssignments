#include <bits/stdc++.h>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);
vector<string> split(const string &);

/*
 * Complete the 'countSort' function below.
 *
 * The function accepts 2D_STRING_ARRAY arr as parameter.
 */

#include <iostream>
#include <cmath>
#include <map>
#include <vector>
using namespace std;
int main (){
    vector <pair <string, int > > v[100];
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        int x;
        string s;
        cin >> x >> s;
        v[x].push_back(make_pair(s, i));
    }
    for (int x = 0; x < 100; x++) 
        for (int i = 0; i < v[x].size(); i++)
            if (v[x][i].second < n / 2)
                cout << "- ";
            else
                cout << v[x][i].first << " ";

    
    
    
}

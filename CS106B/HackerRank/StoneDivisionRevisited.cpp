#include <iostream>
#include <cmath>
#include <map>
using namespace std;
long long solve(long long n, map <long long, long long> &mymap, long long* s, int m) {
	if (mymap[n] == -1)
		return 0;
	if (mymap[n])
		return mymap[n];
	long long ans = 0;
	for (int i = 0; i < m; i++)
		if (n % s[i] == 0 && n > s[i])
			mymap[n] = max(mymap[n], solve(s[i], mymap, s, m) * (n / s[i]) + 1);
	//cout << n << " " << mymap[n] << endl;
	if (mymap[n] == 0) {
		mymap[n] = -1;
		return 0;
	}
	return mymap[n];
		
}
int main (){
	int q;
	cin >> q;
	while (q--) {
		long long n;
		int m;
		cin >> n >> m;
		long long s[m];
		for (int i = 0; i < m; i++)
			cin >> s[i];
		map <long long, long long> mymap;
		cout << solve(n, mymap, s, m) << endl;
	}
	
	
	
}
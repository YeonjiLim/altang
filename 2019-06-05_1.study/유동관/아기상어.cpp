#include<iostream>
#include<queue>
#include<vector>
#include<tuple>
using namespace std;
int dy[] = { -1,1,0,0 };
int dx[] = { 0,0,-1,1 };

bool bfs(vector<vector<int>> &a, int l, int r) {
	int n = a.size();
	bool ans = false;
	vector<vector<bool>> c(n, vector<bool>(n, false));
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (!c[i][j]) {
				c[i][j] = true;
				queue<pair<int, int>>q;
				queue<pair<int, int>>q2;
				q.push(make_pair(i, j));
				q2.push(make_pair(i, j));
				int sum = a[i][j];
				while (!q.empty()) {
					int y, x;
					tie(y, x) = q.front();
					q.pop();
					for (int k = 0; k < 4; k++) {
						int ny = y + dy[k];
						int nx = x + dx[k];
						if (0 <= ny && ny < n && 0 <= nx && nx < n) {
							if (!c[ny][nx]) {
								int diff = a[ny][nx] - a[y][x];
								if (diff < 0)diff = -diff;
								if (l <= diff && diff <= r) {
									c[ny][nx] = true;
									ans = true;
									q.push(make_pair(ny, nx));
									q2.push(make_pair(ny, nx));
									sum += a[ny][nx];
								}
							}
						}
					}
				}
				int val = sum / q2.size();
				while (!q2.empty()) {
					int y, x;
					tie(y, x) = q2.front();
					q2.pop();
					a[y][x] = val;
				}
			}
		}
	}

	return ans;
}
int main() {
	int n, l, r;
	cin >> n >> l >> r;
	vector<vector<int>>a(n, vector<int>(n, 0));
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> a[i][j];
		}
	}
	int ans = 0;
	while (true) {
		if (bfs(a, l, r)) {
			ans++;
		}
		else {
			break;
		}
	}
	cout << ans << endl;

	return 0;
}
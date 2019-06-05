#include<iostream>
#include<vector>
#include<tuple>
#include<algorithm>
#include<queue>
using namespace std;
int dy[] = { -1,1,0,0 };
int dx[] = { 0,0,-1,1 };
tuple<int, int, int>bfs(vector<vector<int>> &a, int y, int x, int size) {
	int n = a.size();
	vector<vector<int>>d(n, vector<int>(n, -1));
	vector<tuple<int, int, int>>ans;
	queue<pair<int, int>>q;
	q.push(make_pair(y, x));
	d[y][x] = 0;
	while (!q.empty()) {
		int y, x;
		tie(y, x) = q.front();
		q.pop();
		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];
			if (0 <= ny && ny < n && 0 <= nx && nx < n) {
				if (d[ny][nx] == -1) {
					bool ok = false;
					bool eat = false;
					if (a[ny][nx] == 0) {
						ok = true;
					}
					else if (a[ny][nx] == size) {
						ok = true;
					}
					else if (a[ny][nx] < size) {
						ok = eat = true;
					}
					if (ok) {
						q.push(make_pair(ny, nx));
						d[ny][nx] = d[y][x] + 1;
						if (eat) {
							ans.push_back(make_tuple(d[ny][nx],ny,nx));
						}
					}
				}
			}
		}
	}
	if (ans.empty()) {
		return make_tuple(-1, -1, -1);
	}
	else {
		sort(ans.begin(), ans.end());
		return ans[0];
	}
}
int main() {
	int n;
	cin >> n;
	vector<vector<int>> a(n, vector<int>(n, 0));
	int y, x;
	int size = 2;
	int exp = 0;
	int ans = 0;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> a[i][j];
			if (a[i][j] == 9) {
				tie(y, x) = make_pair(i, j);
				a[i][j] = 0;
			}
		}
	}
	while (true) {
		int ny, nx, dist;
		tie(dist, ny, nx) = bfs(a, y, x, size);
		if (dist == -1)break;
		ans += dist;
		a[ny][nx] = 0;
		exp++;
		if (size == exp) {
			size++;
			exp = 0;
		}
		tie(y, x) = make_pair(ny, nx);
	}
	cout << ans << endl;

	return 0;
}
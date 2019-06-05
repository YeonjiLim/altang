import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static int n;
	static xy shark;
	static List<xy> list;
	static int[][] dis;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static int res;
	static int[][] map;
	static int eat;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		list = new ArrayList();
		map = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				map[i][j] = sc.nextInt();
				if(map[i][j] == 9) {
					shark = new xy(i, j,2);
				}else if(map[i][j] !=0) {
					list.add(new xy(i, j, map[i][j]));
				}
			}
		}
		eat=0;
		res=0;
		sol(0);
		
		System.out.println(res);
		
	}
	static void makedis(int d,List<xy> list) {
		List<xy> newlist =  new ArrayList();
		for(xy tmp : list) {
			int x = tmp.x;
			int y = tmp.y;
			newlist.add(tmp);
			for (int i = 0; i < 4; i++) {
				int tx = x+dx[i];
				int ty = y+dy[i];
				if(tx>=0 && tx<n && ty>=0 && ty<n && map[tx][ty] <= shark.v && !list.contains(new xy(tx, ty, 0)) && !newlist.contains(new xy(tx, ty, 0))) {
					dis[tx][ty] = d;
					newlist.add(new xy(tx, ty, 0));
				}
			}
		}
		if(newlist.size() == list.size()) {
			return;
		}
		makedis(d+1,newlist);
	}
	static void sol(int time) {
		dis = new int[n][n];
		List<xy> l = new ArrayList();
		l.add(new xy(shark.x, shark.y, shark.v));
		makedis(1,l);
		
		
		
		xy go = null;
		int d = Integer.MAX_VALUE;
		
		for(xy tmp : list) {
			if(shark.v <= tmp.v) continue;
			int x = tmp.x;
			int y = tmp.y;
			if(dis[x][y] == 0) continue;
			if(dis[x][y] <=d) {
				if(go == null) {
					go = new xy(x, y, map[x][y]);
					d= dis[x][y];
				}
				else {
					 if( dis[x][y] < d) {
						go = new xy(x, y, map[x][y]);
						d= dis[x][y];

					}
					 else if(dis[x][y] == d) {
						if(x < go.x) {
							go = new xy(x, y, map[x][y]);
							d= dis[x][y];

						}
						else if(x == go.x) {
							if(y < go.y) {
								go = new xy(x, y, map[x][y]);
								d= dis[x][y];

							}
						}
					}
					
				}
			}
		}
		if(go == null) {
			res = time;
			return;
		}
		map[shark.x][shark.y] = 0;
		map[go.x][go.y] = 9;
		eat++;
		if(eat == shark.v) {
			shark.v++;
			eat=0;
		}
		shark.x = go.x;
		shark.y = go.y;
//		System.out.println(go);
//		System.out.println(list);
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				System.out.print(dis[i][j]+" ");
//			}System.out.println();
//		}
//		System.out.println();
		
		list.remove(go);
		sol(time+dis[go.x][go.y]);
	}
	
	static class xy {
		int x;
		int y;
		int v;
		public xy(int x, int y, int v) {
			super();
			this.x = x;
			this.y = y;
			this.v = v;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			xy other = (xy) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "xy [x=" + x + ", y=" + y + ", v=" + v + "]";
		}
		
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	
	// 상, 좌, 우 , 하
	public int[] moveR = {-1, 0, 0, 1};
	public int[] moveC = {0, -1, 1, 0};
	
	public int N;
	public int[][] map;
	
	public Position shark;
	public boolean[][] visited;
	public int sharkSize;
	public int time;
	
	public void eatFish() {
		PriorityQueue<Position> sharkPos = new PriorityQueue<Position>();
		Queue<Position> sharkList = new LinkedList<Position>();
		visited = new boolean[N][N];
		sharkPos.add(shark);
		visited[shark.row][shark.col] = true;
		map[shark.row][shark.col] = 0;
		int eatFish = 0;
		time++;
		while(!sharkPos.isEmpty()) {
			int size = sharkPos.size();
			//현재 큐 사이즈만큼 돌리기
			boolean eat = false;
			for(int q = 0; q < size; q++) {
				Position curr = sharkPos.poll();
				for(int d = 0; d < 4; d++) {
					int nxtR = curr.row + moveR[d];
					int nxtC = curr.col + moveC[d];
					if(nxtR >= 0 && nxtR < N && nxtC >= 0 && nxtC < N && !visited[nxtR][nxtC]) {
						if(map[nxtR][nxtC] != 0 && map[nxtR][nxtC] < sharkSize) {
							visited[nxtR][nxtC] = true;
							sharkList.add(new Position(nxtR, nxtC, time));
							eat = true;
						}
						if(map[nxtR][nxtC] == 0 || map[nxtR][nxtC] == sharkSize) {
							visited[nxtR][nxtC] = true;
							sharkList.add(new Position(nxtR, nxtC, time));
						}
					}
				}
			}
			if(eat) {
				while(!sharkList.isEmpty()) {
					Position tmp = sharkList.poll();
					if(map[tmp.row][tmp.col] != 0 && map[tmp.row][tmp.col] < sharkSize) {						
						sharkPos.add(tmp);
					}
				}
				
				shark = sharkPos.poll();
				sharkPos.clear();
				sharkPos.add(shark);
				
				visited = new boolean[N][N];
				visited[shark.row][shark.col] = true;
				map[shark.row][shark.col] = 0;
				
				eatFish++;
				if(eatFish == sharkSize) {
					sharkSize++;
					eatFish = 0;
				}
			} else {
				while(!sharkList.isEmpty()) {
					Position tmp = sharkList.poll();
					sharkPos.add(tmp);
				}
			}
			time++;
		}
		
		System.out.println(shark.time);
	}
	
	public void solve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String strN = br.readLine();
		N = Integer.parseInt(strN);
		map = new int[N][N];
		sharkSize = 2;
		time = 0;
		for(int r = 0; r < N; r++) {
			String[] str = br.readLine().split(" ");
			for(int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(str[c]);
			}
		}
		
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				if(map[r][c] == 9) {
					shark = new Position(r, c, 0);
					break;
				}
			}
			if(shark != null) {
				break;
			}
		}
		
		eatFish();
	}
	
	class Position implements Comparable<Position> {
		int row;
		int col;
		int time;
		public Position(int row, int col, int time) {
			this.row = row;
			this.col = col;
			this.time = time;
		}
		
		@Override
		public int compareTo(Position p) {
			if(this.row < p.row) {
				return -1;
			} else if(this.row > p.row) {
				return 1;
			} else {
				if(this.col < p.col) {
					return -1;
				} else if(this.col > p.col) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Main m16236 = new Main();
		m16236.solve();
	}
}

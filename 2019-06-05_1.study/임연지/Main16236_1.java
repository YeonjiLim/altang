import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// 안되는 코드 혹시나해서 올려봅니다

public class Main16236 {
	
	// 상, 좌, 우 , 하
	public int[] moveR = {-1, 0, 0, 1};
	public int[] moveC = {0, -1, 1, 0};
	
	public int N;
	public int[][] space;
	
	public Position shark;
	public int sharkSize;
	
	public int eatCount;
	
	public boolean visited[][];
	
	
	public void eatFish() {
		ArrayList<Position> fishList = new ArrayList<>();
		fishList.add(shark);
		visited = new boolean[N][N];
		visited[shark.r][shark.c] = true;
		space[shark.r][shark.c] = 0;
		int time = 1;
		int pos = 1;
		while(fishList.size() > 0) {
			int size = fishList.size();
			int startR = shark.r-pos < 0 ? 0 : shark.r-pos;
			int endR = shark.r+pos >= N ? N-1 : shark.r+pos;
			boolean eat = false;
			// 리스트에 있는 원소들이 상어기준으로 이번에 찾을 거리 위치에 있는지 확인하려고 만든 반복문
			for(int rr = startR; rr <= endR; rr++) {
				// 리스트 0번째부터 돌면서 찾을것 - 이거때문에 큐를 못쓰는중(pq쓰면 될거같은데 다 날릴까요?ㅠㅠㅋㅋ)
				for(int q = 0; q < size; q++) {
					Position curr = fishList.get(q);
					// 상 좌 우 하 순서대로 찾기
					for(int d = 0; d < 4; d++) {
						int nxtR = curr.r + moveR[d];
						int nxtC = curr.c + moveC[d];
						// space 범위 안인지 확인 + 리스트 원소가(상,하,좌,우로 ) 이동 시 현재 찾는 행과 같은 행에 있는지 + 방문 안한곳인지
						if(nxtR >= 0 && nxtR < N && nxtC >= 0 && nxtC < N && rr == nxtR && !visited[nxtR][nxtC]) {
							// 상어현재크기랑 같거나 0이면 갈 수 있는 곳이므로 리스트에 추가
							if(space[nxtR][nxtC] == sharkSize || space[nxtR][nxtC] == 0) {
								visited[nxtR][nxtC] = true;
								fishList.add(new Position(nxtR, nxtC, time));
							}
							// 상어현재크기보다 작은데 0이 아니면 먹을 수 있는 물고기
							if(space[nxtR][nxtC] != 0 && space[nxtR][nxtC] < sharkSize) {
								shark = new Position(nxtR, nxtC, time); // 상어 위치 갱신
								fishList = new ArrayList<>();
								fishList.add(shark); // 상어 현재 위치에서 다시 가야해서 새로 할당
								visited = new boolean[N][N];
								visited[shark.r][shark.c] = true; // 새로운 상어위치
								space[shark.r][shark.c] = 0;
								eat = true; // 물고기 먹었음
								eatCount++; // 먹을 물고기 수 증가
								if(eatCount == sharkSize) { // 먹은물고기수 = 상어크기면 상어크기 증가
									sharkSize++;
									eatCount = 0;
								}
								pos = 0; // 상어로부터 거리 1부터 다시시작하게 하려고
//								for(int rrr = 0; rrr < N; rrr++) {
//									for(int ccc = 0; ccc < N; ccc++) {
//										System.out.print(space[rrr][ccc]);
//									}
//									System.out.println();
//								}
//								System.out.println("상어크기:" + sharkSize);
							}
						}
						// 물고기 먹었으면 거기부터 새로 시작할거니까 break걸어서 빠져나가기
						if(eat) {
							break;
						}
					}
					if(eat) {
						break;
					}
				}
				if(eat) {
					break;
				}
			}
			// 한번 루프돌면 상어로부터의 거리는 1증가
			pos++;
			// 아무것도 안먹었으면 이전에 있었던 리스트 개수들 삭제(큐대신 리스트를 써서 직접삭제..)
			if(!eat) {				
				for(int q = 0; q < size; q++) {
					fishList.remove(0);
				}
			}
			// 상어 이동하는 중
			time++;
		}
		
		System.out.println(shark.t);
	}
	
	public void solve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String strN = br.readLine();
		N = Integer.parseInt(strN);
		space = new int[N][N];
		sharkSize = 2;
		eatCount = 0;
		for(int row = 0; row < N; row++) {
			String[] str = br.readLine().split(" ");
			for(int col = 0; col < N; col++) {
				space[row][col] = Integer.parseInt(str[col]);
			}
		}
		
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				if(space[row][col] == 9) {
					shark = new Position(row, col, 0);
				}			
			}
			if(shark != null) {
				break;
			}
		}
		
		eatFish();
	}
	
	class Position {
		int r;
		int c;
		int t;
		public Position(int r, int c, int t) {
			this.r = r;
			this.c = c;
			this.t = t;
		}
	}
	
	public static void main(String[] args) throws IOException {
		Main16236 m16236 = new Main16236();
		m16236.solve();
	}
}

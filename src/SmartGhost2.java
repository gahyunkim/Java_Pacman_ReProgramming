import java.awt.*;
import java.util.Random;

public class SmartGhost2 extends Rectangle{
	
	// 랜덤모드 <-> 스마트모드
	private int random = 0;
	private int smart = 1;
	private int find_path = 2;	// 방향 찾기 모드
	
	private int state = smart;
	
	private int right = 0;
	private int left = 1;
	private int up = 2;
	private int down = 3;
	
	private int direction = -1;
	
	public Random randomMaker;
	
	private int time = 0;
	
	private int changingTime = 60 * 5;
	
	private int speed = 1;
	
	private int lastDirection = -1;
	
	private int imageIndex = 0;
	
	public SmartGhost2(int x, int y) {
		randomMaker = new Random();  //랜덤 클래스를 사용하는 객체
		setBounds(x, y, 32, 32);
		direction = randomMaker.nextInt(4);  //1과 4사이의 난수를 생성해서 상하좌우를 결정함
	}
	
	public void tick() {
		// 랜덤으로 방향 생성하기 -> 일반 유령과 같은 동작
		imageIndex = 0;
		// 랜덤모드
		if(state == random) {
			if(direction == right) {  //right=0
				if(canMove(x + speed, y)) {
					x=x+speed;
					imageIndex=0;
					lastDirection=right;
				}
				else {
					direction = randomMaker.nextInt(4);  //벽에 막혀 못이동 하면 다시 난수를 생성받는다
				}
			}
			else if(direction == left) {  //left=1
				if(canMove(x - speed, y)) {
					x=x-speed;
					imageIndex=1;
					lastDirection=left;
				}
				else {
					direction = randomMaker.nextInt(4);
				}
			}
			else if(direction == up) {
				if(canMove(x, y - speed)) {
					y=y-speed;
					imageIndex=2;
					lastDirection=up;
				}
				else {
					direction = randomMaker.nextInt(4);
				}
			}
			else if(direction == down) {
				if(canMove(x, y + speed)) {
					y=y+speed;
					imageIndex=3;
					lastDirection=down;
				}
				else {
					direction = randomMaker.nextInt(4);
				}
			}
			
			time++;
			
			// 5초마다 모드 변경
			if(time == changingTime) {
				state = smart;
				time = 0;
			}
		}
		
		// 스마트모드
		else if(state == smart) {
			// follow the player! == 형광색 유령이 움직이는 경우
			imageIndex = 1;  //원래는 파란색의 기본 모습임
			boolean move = false;
			
			if(x < Game.pacman.x) {  //현재 팩맨이 있는 x의 위치보다 유령의 x가 작다면
				if(canMove(x + speed, y)) {
					x=x+speed;
					imageIndex=4;   //오른쪽으로 이동해야 하기 때문에 형광색 눈이 오른쪽으로 가있는 이미지
					move = true;    //이동하고 있기 때문에 true로 바뀜
					lastDirection = right;
				}
			}
			
			if(x > Game.pacman2.x) {  //만약 팩맨의 현재 x위치 보다 유령의 x위치가 크다면 왼쪽으로 이동해야함
				if(canMove(x - speed, y)) {
					x=x-speed;
					imageIndex=5;
					move = true;
					lastDirection = left;
				}
			}
			
			if(y < Game.pacman2.y) {  //아래로 이동
				if(canMove(x, y + speed)) {
					y=y+speed;
					imageIndex=7;
					move = true;
					lastDirection = down;
				}
			}
			
			if(y > Game.pacman2.y) {  //위로 이동
				if(canMove(x, y - speed)) {
					y=y-speed;
					imageIndex=6;
					move = true;
					lastDirection = up;
				}
			}
			
			if(x == Game.pacman2.x && y == Game.pacman2.y) { // 팩맨을 찾음
				move = true;
			}
			
			if(!move) {  //이동을 할 수 없다면 
				state = find_path;
			}
			
			time++;
			
			// 5초마다 모드 변경
			if(time == changingTime) {
				state = random;
				time = 0;
			}
		}
		
		// smart인 상황에서 방향 찾기
		else if(state == find_path) {  //이동을 할 수 없는 경우에 길을 찾는 방법
			imageIndex = 4;
			if(lastDirection == right) { //만약 마지막 방향이 오른쪽이었다면
				if(y < Game.pacman2.y) {  //오른쪽 빼고 나머지의 길을 찾으면 됨
					if(canMove(x, y + speed)) {
						y=y+speed;
						imageIndex=7;
						state = smart;
						lastDirection = up;
					}							
				}
				else {
					if(canMove(x, y - speed)) {
						y=y-speed;
						imageIndex=6;
						state = smart;
						lastDirection = down;
					}
				}
				if(canMove(x + speed, y)) {
					x=x+speed;
					imageIndex=4;
					lastDirection = right;
				}
				else {
					lastDirection=randomMaker.nextInt(4);
				}
			}
			else if(lastDirection == left) {
				if(y < Game.pacman2.y) {
					if(canMove(x, y + speed)) {
						y=y+speed;
						imageIndex=7;
						state = smart;
						lastDirection = down;
					}							
				}
				else {
					if(canMove(x, y - speed)) {
						y=y-speed;
						imageIndex=6;
						state = smart;
						lastDirection = up;
						
					}
				}
				if(canMove(x - speed, y)) {
					x=x-speed;
					imageIndex=5;
					lastDirection = left;
				}
				else {
					lastDirection=randomMaker.nextInt(4);
				}
			}
			else if(lastDirection == up) {
				if(x < Game.pacman2.x) {
					if(canMove(x + speed, y)) {
						x=x+speed;
						imageIndex=4;
						state = smart;
						lastDirection = right;
					}							
				}
				else {
					if(canMove(x - speed, y)) {
						x=x-speed;
						imageIndex=5;
						state = smart;
						lastDirection = left;
					}
				}
				if(canMove(x, y - speed)) {
					y=y-speed;
					imageIndex=6;
					lastDirection = up;
				}
				else {
					lastDirection=randomMaker.nextInt(4);
				}
			}
			else if(lastDirection == down) {
				if(x < Game.pacman2.x) {
					if(canMove(x + speed, y)) {
						x=x+speed;
						imageIndex=4;
						state = smart;
						lastDirection = right;
					}							
				}
				else {
					if(canMove(x - speed, y)) {
						x=x-speed;
						imageIndex=5;
						state = smart;
						lastDirection = left;
					}
				}
				if(canMove(x, y + speed)) {
					y=y+speed;
					imageIndex=7;
					lastDirection = down;
				}	
				else {
					lastDirection=randomMaker.nextInt(4);
				}
			}
			
			time++;
			
			// 5초마다 모드 변경
			if(time == changingTime) {
				state = random;
				time = 0;
			}
		}
	}
	
	// 이동가능여부판단
	private boolean canMove(int next_x, int next_y) {
		
		Rectangle bounds2 = new Rectangle(next_x, next_y, width, height);
		Map2 map2 = Game.map2;
		
		for(int i=0;i<map2.tiles2.length;i++) {
			for(int j=0;j<map2.tiles2[0].length;j++) {
				if(map2.tiles2[i][j]!=null) {
					if(bounds2.intersects(map2.tiles2[i][j])) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public void render(Graphics g) {
		g.drawImage(Character.smartGhost[imageIndex], x, y, width, height, null);
	}
}

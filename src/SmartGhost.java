import java.awt.*;
import java.util.Random;

public class SmartGhost extends Rectangle{
	
	private int right = 0;
	private int left = 1;
	private int up = 2;
	private int down = 3;
	
	private int direction = -1;
	
	public Random randomMaker;

	private int speed = 1;
	
	private int imageIndex = 0;
	
	public SmartGhost(int x, int y) {
		randomMaker = new Random();  //랜덤 클래스를 사용하는 객체
		setBounds(x, y, 32, 32);
		direction = randomMaker.nextInt(4);  //1과 4사이의 난수를 생성해서 상하좌우를 결정함
	}
	
	public void tick() { // 랜덤으로 방향을 생성해서 만약 움직일 수 있다면 이동, 움직일 수 없다면 다시 방향을 생성함
		if(direction==right) {
			if(canMove(x+speed, y)) {   //이동할 수 있는지의 여부 판단 메소드를 이용
				x=x+speed;
				imageIndex=0;
			}
			else
				direction = randomMaker.nextInt(4);
		}
		else if(direction==left) {
			if(canMove(x-speed, y)) {
				x=x-speed;
				imageIndex=1;
			}
			else
				direction = randomMaker.nextInt(4);
		}
		else if(direction==up) {
			if(canMove(x, y-speed)) {
				y=y-speed;
				imageIndex=2;
			}
			else 
				direction = randomMaker.nextInt(4);
		}
		else if(direction==down) {
			if(canMove(x, y+speed)) {
				y=y+speed;
				imageIndex=3;
			}
			else
				direction = randomMaker.nextInt(4);
		}
}	
	// 이동가능여부판단
	private boolean canMove(int next_x, int next_y) {
		
		Rectangle bounds = new Rectangle(next_x, next_y, width, height);
		Map map = Game.map;
		
		for(int i=0;i<map.tiles.length;i++) {
			for(int j=0;j<map.tiles[0].length;j++) {
				if(map.tiles[i][j]!=null) {
					if(bounds.intersects(map.tiles[i][j])) {
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

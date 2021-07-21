import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ghost2 extends Rectangle{
	
	private int right = 0;
	private int left = 1;
	private int up = 2;
	private int down = 3;
	private int direction = -1;
	
	private int imageIndex=0;
	
	public Random randomDirection;
	
	private int speed = 1;
	
	public Ghost2(int x, int y) {
		randomDirection = new Random();
		setBounds(x, y, 32, 32);
		direction = randomDirection.nextInt(4);
	}
	
	public void tick() { // 랜덤으로 방향을 생성해서 만약 움직일 수 있다면 이동, 움직일 수 없다면 다시 방향을 생성함
			if(direction==right) {
				if(canMove(x+speed, y)) {   //이동할 수 있는지의 여부 판단 메소드를 이용
					x=x+speed;
					imageIndex=0;
				}
				else
					direction = randomDirection.nextInt(4);
			}
			else if(direction==left) {
				if(canMove(x-speed, y)) {
					x=x-speed;
					imageIndex=1;
				}
				else
					direction = randomDirection.nextInt(4);
			}
			else if(direction==up) {
				if(canMove(x, y-speed)) {
					y=y-speed;
					imageIndex=2;
				}
				else
					direction = randomDirection.nextInt(4);
			}
			else if(direction==down) {
				if(canMove(x, y+speed)) {
					y=y+speed;
					imageIndex=3;
				}
				else
					direction = randomDirection.nextInt(4);
			}
	}
	
	// 이동가능여부판단
	private boolean canMove(int next_x, int next_y) {
		
		Rectangle bounds = new Rectangle(next_x, next_y, width, height);
		Map2 map2 = Game.map2;
		
		for(int i=0;i<map2.tiles2.length;i++) {
			for(int j=0;j<map2.tiles2[0].length;j++) {
				if(map2.tiles2[i][j]!=null) {  //tiles의 자리가 비어있지 않을때
					if(bounds.intersects(map2.tiles2[i][j])) {   //새롭게 만들어진 bounds가 타일과 겹친다면 false를 반환함 
						return false;  //즉, 벽이나 장애물이 있기 때문에 움직이지 못하는 것을 반환하는 것
					}
				}
			}
		}
		
		return true;  //아니라면 이동할 수 있는 것
	}
	
	public void render(Graphics g) {  //render는 되돌리다라는 뜻으로 render를 호출하면 Graphics의 모습 그대로 만들어지는 것
		g.drawImage(Character.ghost[imageIndex], x, y, null);  //이미지를 그려서 위치시키는 것과 동일함
	}
}

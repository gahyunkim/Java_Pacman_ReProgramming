import java.awt.*;
import java.util.Random;

public class SmartGhost2 extends Rectangle{
	
	// ������� <-> ����Ʈ���
	private int random = 0;
	private int smart = 1;
	private int find_path = 2;	// ���� ã�� ���
	
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
		randomMaker = new Random();  //���� Ŭ������ ����ϴ� ��ü
		setBounds(x, y, 32, 32);
		direction = randomMaker.nextInt(4);  //1�� 4������ ������ �����ؼ� �����¿츦 ������
	}
	
	public void tick() {
		// �������� ���� �����ϱ� -> �Ϲ� ���ɰ� ���� ����
		imageIndex = 0;
		// �������
		if(state == random) {
			if(direction == right) {  //right=0
				if(canMove(x + speed, y)) {
					x=x+speed;
					imageIndex=0;
					lastDirection=right;
				}
				else {
					direction = randomMaker.nextInt(4);  //���� ���� ���̵� �ϸ� �ٽ� ������ �����޴´�
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
			
			// 5�ʸ��� ��� ����
			if(time == changingTime) {
				state = smart;
				time = 0;
			}
		}
		
		// ����Ʈ���
		else if(state == smart) {
			// follow the player! == ������ ������ �����̴� ���
			imageIndex = 1;  //������ �Ķ����� �⺻ �����
			boolean move = false;
			
			if(x < Game.pacman.x) {  //���� �Ѹ��� �ִ� x�� ��ġ���� ������ x�� �۴ٸ�
				if(canMove(x + speed, y)) {
					x=x+speed;
					imageIndex=4;   //���������� �̵��ؾ� �ϱ� ������ ������ ���� ���������� ���ִ� �̹���
					move = true;    //�̵��ϰ� �ֱ� ������ true�� �ٲ�
					lastDirection = right;
				}
			}
			
			if(x > Game.pacman2.x) {  //���� �Ѹ��� ���� x��ġ ���� ������ x��ġ�� ũ�ٸ� �������� �̵��ؾ���
				if(canMove(x - speed, y)) {
					x=x-speed;
					imageIndex=5;
					move = true;
					lastDirection = left;
				}
			}
			
			if(y < Game.pacman2.y) {  //�Ʒ��� �̵�
				if(canMove(x, y + speed)) {
					y=y+speed;
					imageIndex=7;
					move = true;
					lastDirection = down;
				}
			}
			
			if(y > Game.pacman2.y) {  //���� �̵�
				if(canMove(x, y - speed)) {
					y=y-speed;
					imageIndex=6;
					move = true;
					lastDirection = up;
				}
			}
			
			if(x == Game.pacman2.x && y == Game.pacman2.y) { // �Ѹ��� ã��
				move = true;
			}
			
			if(!move) {  //�̵��� �� �� ���ٸ� 
				state = find_path;
			}
			
			time++;
			
			// 5�ʸ��� ��� ����
			if(time == changingTime) {
				state = random;
				time = 0;
			}
		}
		
		// smart�� ��Ȳ���� ���� ã��
		else if(state == find_path) {  //�̵��� �� �� ���� ��쿡 ���� ã�� ���
			imageIndex = 4;
			if(lastDirection == right) { //���� ������ ������ �������̾��ٸ�
				if(y < Game.pacman2.y) {  //������ ���� �������� ���� ã���� ��
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
			
			// 5�ʸ��� ��� ����
			if(time == changingTime) {
				state = random;
				time = 0;
			}
		}
	}
	
	// �̵����ɿ����Ǵ�
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

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
	
	public void tick() { // �������� ������ �����ؼ� ���� ������ �� �ִٸ� �̵�, ������ �� ���ٸ� �ٽ� ������ ������
			if(direction==right) {
				if(canMove(x+speed, y)) {   //�̵��� �� �ִ����� ���� �Ǵ� �޼ҵ带 �̿�
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
	
	// �̵����ɿ����Ǵ�
	private boolean canMove(int next_x, int next_y) {
		
		Rectangle bounds = new Rectangle(next_x, next_y, width, height);
		Map2 map2 = Game.map2;
		
		for(int i=0;i<map2.tiles2.length;i++) {
			for(int j=0;j<map2.tiles2[0].length;j++) {
				if(map2.tiles2[i][j]!=null) {  //tiles�� �ڸ��� ������� ������
					if(bounds.intersects(map2.tiles2[i][j])) {   //���Ӱ� ������� bounds�� Ÿ�ϰ� ��ģ�ٸ� false�� ��ȯ�� 
						return false;  //��, ���̳� ��ֹ��� �ֱ� ������ �������� ���ϴ� ���� ��ȯ�ϴ� ��
					}
				}
			}
		}
		
		return true;  //�ƴ϶�� �̵��� �� �ִ� ��
	}
	
	public void render(Graphics g) {  //render�� �ǵ����ٶ�� ������ render�� ȣ���ϸ� Graphics�� ��� �״�� ��������� ��
		g.drawImage(Character.ghost[imageIndex], x, y, null);  //�̹����� �׷��� ��ġ��Ű�� �Ͱ� ������
	}
}

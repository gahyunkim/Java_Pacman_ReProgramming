import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman extends Rectangle{ // ����� ������ ������ ���� �ϱ� ���� extends Rectangle

	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	private int speed = 2;
	private int imageIndex = 0;
	private int time=0;
	private int changingTime=60*2;
	
	Score curScore;   
	
	public Pacman(int x, int y) {
		curScore=Game.score;
		curScore.score=0;
		setBounds(x, y, 30, 30);
	}

	private boolean canMove(int next_x, int next_y) { // Ghost�� ���� �Լ� ���� ������ �������� ����
		
		Rectangle bounds = new Rectangle(next_x, next_y, width, height);
		Map map = Game.map;
		
		for(int i=0;i<map.tiles.length;i++){           //tiles
			for(int j=0;j<map.tiles[0].length;j++){
				if(map.tiles[i][j]!=null){   //���� tiles�� ��Ұ� ��ĭ�� �ƴ϶�� 
					if(bounds.intersects(map.tiles[i][j])) {  //������� bounds�� ���� tiles�� ��ģ�ٸ� false�� ��ȯ��
						return false;
					}
				}
			}
		}
		
		return true; //bounds�� ��ġ�� �ʴ´ٸ� true�� ��ȯ��
	}
		
	public void tick() { // �� �������� ������ ������ �̹����� �ٲ�
		if(right&&canMove(x+speed, y)){
			x=x+speed;
			imageIndex = 0;
		}
		if(left&&canMove(x-speed, y)) {
			x=x-speed;
			imageIndex = 1;
		}
		if(up&&canMove(x, y-speed)) {
			y=y-speed;
			imageIndex = 2;
		}
		if(down&&canMove(x, y+speed)) {
			y=y+speed;
			imageIndex = 3;
		}
		
		Map map = Game.map;
				
		if(Game.map.redSeeds.size()==0) {
			//����1������ 8���� ���� ������ �Ծ����.
			Game.STATE = Game.GAME2;
		}
		
		for(int i=0;i<Game.map.seeds.size();i++){ // �Ѹǰ� seed�� ��ġ�� seed�� �����
			if(this.intersects(map.seeds.get(i))) {  //get�� ����Ʈ���� � ��ü�� �������µ� ���Ǵ� �޼ҵ�
				curScore.score+=10;     //seeds�� ��ĥ������ score�� 10�� �����Ѵ�.
				map.seeds.remove(i);    //������ seed�� �������. remove(i)
				break;
			}			
		}
		
		for(int i=0;i<Game.map.redSeeds.size();i++) { //������ ���Ѱ� �Ѹ��� ��ġ�� ���ھ 20�� �ö�
			if(this.intersects(map.redSeeds.get(i))) {
				curScore.score+=20;
				map.redSeeds.remove(i);
			}
		}
		
		for(int i=0;i<Game.map.Green.size();i++) {
			//�ʷ�Ÿ�Ͽ� ���� ���
			Green temp = Game.map.Green.get(0);
			Green temp2 = Game.map.Green.get(1);
			if(temp.intersects(this)) {
				this.setBounds(temp2);
			}
		}
		

		for(int i=0;i<Game.map.Orange.size();i++) {
			//������ Ÿ�Ͽ� ���� ���
			Orange temp = Game.map.Orange.get(0);
			if(temp.intersects(this)) {
				Game.STATE = Game.GAME2;
			}
		}
			
		for(int i=0;i<Game.map.Ghosts.size();i++){ 
			// lose
			// �Ϲ� ���ɿ��� ����
			Ghost temp = Game.map.Ghosts.get(i);
			if(temp.intersects(this)) {   //������ Rectangle2D ���� ������ �����ϴ����� �����ϴ� intersects
				Game.STATE=Game.START;    //�ϴ� ���۵Ǿ��ִ� ���¿��� �ϱ� ������ STATE = START��
				curScore.insertScore();
				Game.DEAD = true;
				return;
			}
		}
		for(int i=0;i<Game.map.SmartGhosts.size();i++){
			// lose
			// �ȶ��� ���ɿ��� ����
			SmartGhost temp=Game.map.SmartGhosts.get(i);
			if(temp.intersects(this)){
				Game.STATE=Game.START;
				curScore.insertScore();
				Game.DEAD = true;
				return;
			}
		}
		
		if(Game.map.seeds.size()<=130||curScore.score>=550) {
			//�� ���� ������ 50�� �̻� ������ �ӵ��� 3���� �����ش�.
			//�Ǵ� ������ �Ծ ������ 550�� �̻��� �Ǹ� �ӵ��� 3���� �����ش�.
			//��1�̶� ���̵��� ��1���� ���� ��
			speed=3;
		}		
	}
	
	public void render(Graphics g){  //�Ѹ��� ����� imageIndex�� ���� �ٲ�� x,y��ġ�� ĳ���Ͱ� ��ġ�ϰԵȴ�.
		g.drawImage(Character.pacman[imageIndex], x, y, width, height, null);  //�̹����� ���� render�ϴ� ��
	}
}

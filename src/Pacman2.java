import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman2 extends Rectangle{ // ����� ������ ������ ���� �ϱ� ���� extends Rectangle
	
	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	private int speed = 2;
	private int imageIndex = 0;
	
	Score2 curScore;   
	
	public Pacman2(int x, int y) {
		curScore=Game.score2;
		curScore.score2=0;
		setBounds(x, y, 30, 30);
	}
	
	private boolean canMove(int next_x, int next_y) { // Ghost�� ���� �Լ� ���� ������ �������� ����
		
		Rectangle bounds2 = new Rectangle(next_x, next_y, width, height);
		Map2 map2 = Game.map2;
		
		for(int i=0;i<map2.tiles2.length;i++){           //tiles
			for(int j=0;j<map2.tiles2[0].length;j++){
				if(map2.tiles2[i][j]!=null){   //���� tiles�� ��Ұ� ��ĭ�� �ƴ϶�� 
					if(bounds2.intersects(map2.tiles2[i][j])) {  //������� bounds�� ���� tiles�� ��ģ�ٸ� false�� ��ȯ��
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
		
		Map2 map2 = Game.map2;
		
		for(int i=0;i<map2.seeds2.size();i++){ // �Ѹǰ� seed�� ��ġ�� seed�� �����
			if(this.intersects(map2.seeds2.get(i))) {  //get�� ����Ʈ���� � ��ü�� �������µ� ���Ǵ� �޼ҵ�
				curScore.score2+=10;     //seeds�� ��ĥ������ score�� 10�� �����Ѵ�.
				map2.seeds2.remove(i);    //������ seed�� �������. remove(i)
				break;
			}
		
		}
		
		if(map2.seeds2.size()==0) {
			// win
			//seeds�� �� ����
			Game.STATE=Game.END;  //���� STATE�� -1�̰� START�� 1�̾��µ� ���� �������� �����ν� �̱�� ������ ��������
			Game.WIN=true;
		}
		
		if(Game.map2.redSeeds2.size()==0) {
			//win
			//������ seeds�� �� �Դ� ��� -> �̱�� ��츦 �������� ������.(����1������ 8�� ����2������ 9���� �Ծ����)
			Game.STATE=Game.END; 
			Game.WIN=true;
		}
		
		for(int i=0;i<map2.redSeeds2.size();i++) {
			if(this.intersects(map2.redSeeds2.get(i))) {
				curScore.score2+=20;
				map2.redSeeds2.remove(i);
			}
		}
		
		for(int i=0;i<Game.map2.Green2.size();i++) {
			//�ʷ�Ÿ�Ͽ� ���� ���
			Green temp = Game.map2.Green2.get(0);
			Green temp2 = Game.map2.Green2.get(1);
			if(temp.intersects(this)) {
				this.setBounds(temp2);
			}
		}
		
		for(int i=0;i<Game.map2.Orange2.size();i++) {
			//win
			//������Ÿ�Ͽ� ���� ���
			Orange temp = Game.map2.Orange2.get(0);
			if(temp.intersects(this)) {
				Game.STATE=Game.END;
				Game.WIN=true;
			}
		}
				
		for(int i=0;i<Game.map2.Ghosts2.size();i++){ 
			// lose
			// �Ϲ� ���ɿ��� ����
			Ghost2 temp = Game.map2.Ghosts2.get(i);
			if(temp.intersects(this)) {   //������ Rectangle2D ���� ������ �����ϴ����� �����ϴ� intersects
				Game.STATE=Game.START;    //�ϴ� ���۵Ǿ��ִ� ���¿��� �ϱ� ������ STATE = START��
				Game.DEAD2 = true;
				curScore.insertScore();
				return;
				

			}
		}
		for(int i=0;i<Game.map2.SmartGhosts2.size();i++){
			// lose
			// �ȶ��� ���ɿ��� ����
			SmartGhost2 temp=Game.map2.SmartGhosts2.get(i);
			if(temp.intersects(this)){
				Game.STATE=Game.START;
				Game.DEAD2 = true;
				curScore.insertScore();
				return;
			}
		}
		
		if(map2.seeds2.size()<=110||curScore.score2>=650) {
			//�� ���� ������ 70�� �̻� ������ �Ѹ��� �ӵ��� 3���� �ٲ��ش�.
			//�� ���� ������ �Ծ� 650�� �̻��� �Ǹ� �ӵ��� 3�� �ȴ�.
			//��1���� �ٸ��� ���̵��� ���� ������
			speed=3;
		}
		
	}
	
	public void render(Graphics g){  //�Ѹ��� ����� imageIndex�� ���� �ٲ�� x,y��ġ�� ĳ���Ͱ� ��ġ�ϰԵȴ�.
		g.drawImage(Character.pacman[imageIndex], x, y, width, height, null);  //�̹����� ���� render�ϴ� ��
	}
}

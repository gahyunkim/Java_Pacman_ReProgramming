import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile extends Rectangle{
		
	public Tile(int x, int y) {
		setBounds(x, y, 32, 32);   //x,y��ġ�� �ִ� Ÿ���� 32,32ũ���� �簢���� �����.
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(33, 0, 127));
		g.fillRect(x, y, width, height);    //x,y��ġ�� �ִ� 40,25¥�� �簢���� ���� ĥ�Ѵ�. setColor�� ������
	}
	
}

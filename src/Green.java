import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Green extends Rectangle {
	
	public Green(int x, int y) {
		setBounds(x+1, y+1, 30, 30);  //�ʷϻ� �̵�Ÿ���� ��ġ��Ŵ
	}
	
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);   
		
	}

}

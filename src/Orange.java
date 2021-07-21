import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Orange extends Rectangle {
	
	public Orange(int x, int y) {
		setBounds(x, y, 32, 32);  //�ʷϻ� �̵�Ÿ���� ��ġ��Ŵ
	}
	
	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);   
		
	}

}

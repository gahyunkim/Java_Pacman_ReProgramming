import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class RedSeed extends Rectangle{
		
	public RedSeed(int x, int y) {
		setBounds(x+12, y+12, 8, 8);  //�߾ӿ� ��ġ��Ű�� ���ؼ� x+12,y+12�� ��ġ�� ũ�Ⱑ 8*8�� ���带 ��ġ��Ŵ
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		
	}

}

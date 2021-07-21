import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class RedSeed extends Rectangle{
		
	public RedSeed(int x, int y) {
		setBounds(x+12, y+12, 8, 8);  //중앙에 위치시키기 위해서 x+12,y+12인 위치에 크기가 8*8인 씨드를 위치시킴
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		
	}

}

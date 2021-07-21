import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile extends Rectangle{
		
	public Tile(int x, int y) {
		setBounds(x, y, 32, 32);   //x,y위치에 있는 타일의 32,32크기인 사각형을 만든다.
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(33, 0, 127));
		g.fillRect(x, y, width, height);    //x,y위치에 있는 40,25짜리 사각형의 색을 칠한다. setColor의 색으로
	}
	
}

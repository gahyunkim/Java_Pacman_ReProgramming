import java.awt.image.BufferedImage;  //�̹��� ������ ������ �޸𸮰� ���� �ʿ��ѵ� �ʿ��� ���� �޸� �ڿ��� Ȯ������ ���� ���� ����
//�׷� ��Ȳ�� ����ؼ� ���۸� �̿��ؼ� �̿밡���� �޸𸮸� ����ؼ� �̸� �̹����� ó���صε��� ��
import java.io.IOException;

import javax.imageio.ImageIO;

public class Appearance { // res������ �ִ� appearance ������ �о��
	
	private BufferedImage appearance;
	
	public Appearance(String path) {  
		try {  //���ܸ� �����ϴ� �κ�
			appearance = ImageIO.read(getClass().getResource(path));  //�ڹ��� ����ӽ��� Ŭ���� ������ ã�� ���, ���α׷��� ����������� Ŭ���� ������ ã���� �����  
		} catch (IOException e) {  //try���� ������ ���ܸ� ó���ϴ� �κ�
			e.printStackTrace();
		}	
	}
	
	public BufferedImage getAppearance(int x, int y) {
		return appearance.getSubimage(x, y, 32, 32);   //getSubimage = BufferdImage�� ���� ���� �̹����� ��ġ�� ���� �߶󳻴� ����
	}
	
}

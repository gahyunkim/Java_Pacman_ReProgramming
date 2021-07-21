import java.awt.image.BufferedImage;  //이미지 파일을 얻으면 메모리가 많이 필요한데 필요한 때에 메모리 자원을 확보하지 못할 수도 있음
//그런 상황을 대비해서 버퍼를 이용해서 이용가능한 메모리를 사용해서 미리 이미지를 처리해두도록 함
import java.io.IOException;

import javax.imageio.ImageIO;

public class Appearance { // res폴더에 있는 appearance 파일을 읽어옴
	
	private BufferedImage appearance;
	
	public Appearance(String path) {  
		try {  //예외를 감지하는 부분
			appearance = ImageIO.read(getClass().getResource(path));  //자바의 가상머신이 클래스 파일을 찾는 경로, 프로그램의 실행과정에서 클래스 파일을 찾을때 사용함  
		} catch (IOException e) {  //try에서 감지한 예외를 처리하는 부분
			e.printStackTrace();
		}	
	}
	
	public BufferedImage getAppearance(int x, int y) {
		return appearance.getSubimage(x, y, 32, 32);   //getSubimage = BufferdImage로 부터 받은 이미지를 위치에 따라 잘라내는 역할
	}
	
}

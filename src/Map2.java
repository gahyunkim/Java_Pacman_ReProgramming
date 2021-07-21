import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Map2 {
	public int width; // 20
	public int height; //20
	
	public Tile[][] tiles2;
	
	public List<Seed> seeds2;
	public List<Ghost2> Ghosts2;
	public List<SmartGhost2> SmartGhosts2;
	public List<Green> Green2;
	public List<Orange> Orange2;
	public List<RedSeed> redSeeds2;
	
	private int location=30;
	
	public Map2(String path) { // map(png)이 저장되어 있는 경로를 생성자로 받음
		try {
			seeds2=new ArrayList<>();
			Ghosts2=new ArrayList<>();
			SmartGhosts2=new ArrayList<>();
			Green2=new ArrayList<>();
			Orange2=new ArrayList<>();
			redSeeds2 = new ArrayList<>();
			
			BufferedImage map2 = ImageIO.read(getClass().getResource(path));
			this.width=map2.getWidth();
			this.height=map2.getHeight();
			
			int[] pixels2 = new int[width*height]; // 포토샵으로 그려놓은 맵
			// pixels 배열에 map.png의 픽셀의 색을 일렬화(세로방향으로) 해서 저장함
			map2.getRGB(0, 0, width, height, pixels2, 0, width); // 
			
			tiles2 =new Tile[width][height]; // GUI에 구현될 맵

			for(int i=0;i<this.width;i++) {
				for(int j=0;j<this.height;j++) {
					int val = pixels2[i+(j*this.width)];
					if(val==0xFF000A7C) {  //위치에 있는 색이 벽색이라면 
						// 벽
						tiles2[i][j]=new Tile(i*32+location, j*32+location);
					}
					else if(val==0xFFFFD800) {
						// 팩맨
						Game.pacman2.x=i*32+location;
						Game.pacman2.y=j*32+location;
					}
					else if(val==0xFFFF0000) {
						// 일반 유령
						Ghosts2.add(new Ghost2(i*32+location, j*32+location));
					}
					else if(val==0xFF00FFFF) {
						// 똑똑한 유령
						SmartGhosts2.add(new SmartGhost2(i*32+location, j*32+location));
					}
					else if(val==0xFF33CF10) {
						//초록색 이동 타일
						Green2.add(new Green(i*32+location, j*32+location));
					}
					else if(val==0xFFF5AB0D) {
						//주황색 골 타일
						Orange2.add(new Orange(i*32+location,j*32+location));
					}
					else if(val==0xFFFF00FC) {
						//핑크색 타일에 빨간색 씨앗이 생김 (9개)
						redSeeds2.add(new RedSeed(i*32+location,j*32+location));
					}
					else{
						// 씨앗
						seeds2.add(new Seed(i*32+location, j*32+location));
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void tick() { // map에서 유령들의 상태를 바꾸고 랜더링 시킴
		for(int i=0;i<Ghosts2.size();i++) {
			Ghosts2.get(i).tick();  //유령의 방향에 따른 이미지 변화
		}
		for(int i=0;i<SmartGhosts2.size();i++) {
			SmartGhosts2.get(i).tick();   //스마트유령인지 랜덤유령인지의 상태 변화와 더불어 이미지 변화
		}
	}
	
	public void render(Graphics g) { // seeds와 tile은 상태가 바뀔 필요가 없으므로 tick함수가 존재하지 않음
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				if(tiles2[i][j]!=null)    //tile의 요소중에서 비어있지 않은 공간이 있다면
					tiles2[i][j].render(g);   //render를 통해서 상자를 만들어라
			}
		}
		
		for(int i=0;i<Orange2.size();i++) {
			Orange2.get(i).render(g);  
		}
		for(int i=0;i<Green2.size();i++) {
			Green2.get(i).render(g); 
		}
		for(int i=0;i<seeds2.size();i++) {
			seeds2.get(i).render(g);  //seeds를 render하여 사각형의 씨앗을 그린다
		}
		
		for(int i=0;i<redSeeds2.size();i++) {
			//빨간색 씨앗, 먹으면 20점씩 올라감 + 다먹으면 win
			redSeeds2.get(i).render(g);  //redSeeds를 render하여 빨간색 사각형의 씨앗을 그린다
		}
		
		for(int i=0;i<Ghosts2.size();i++) {
			Ghosts2.get(i).render(g);  //render하여 유령을 그려라
		}
		for(int i=0;i<SmartGhosts2.size();i++) {
			SmartGhosts2.get(i).render(g);
		}
	}
}

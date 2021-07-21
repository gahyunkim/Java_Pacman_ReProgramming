import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Map {
	public int width; // 20
	public int height; //20
	
	public Tile[][] tiles;
	
	public List<Seed> seeds;
	public List<RedSeed> redSeeds;
	public List<Ghost> Ghosts;
	public List<SmartGhost> SmartGhosts;
	public List<Green> Green;
	public List<Orange> Orange;
	
	private int location=30;
	
	public Map(String path) { // map(png)이 저장되어 있는 경로를 생성자로 받음
		try {
			seeds=new ArrayList<>();
			Ghosts=new ArrayList<>();
			SmartGhosts=new ArrayList<>();
			Green=new ArrayList<>();
			Orange=new ArrayList<>();
			redSeeds = new ArrayList<>();
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			this.width=map.getWidth();
			this.height=map.getHeight();
			
			int[] pixels = new int[width*height]; // 포토샵으로 그려놓은 맵
			// pixels 배열에 map.png의 픽셀의 색을 일렬화(세로방향으로) 해서 저장함
			map.getRGB(0, 0, width, height, pixels, 0, width); // 
			
			tiles =new Tile[width][height]; // GUI에 구현될 맵

			for(int i=0;i<width;i++) {
				for(int j=0;j<height;j++) {
					int val = pixels[i+(j*width)];
					if(val==0xFF000A7C) {  //위치에 있는 색이 벽색이라면 
						// 벽
						tiles[i][j]=new Tile(i*32+location, j*32+location);
					}
					else if(val==0xFFFFD800) {
						// 팩맨
						Game.pacman.x=i*32+location;
						Game.pacman.y=j*32+location;
					}
					else if(val==0xFFFF0000) {
						// 일반 유령
						Ghosts.add(new Ghost(i*32+location, j*32+location));
					}
					else if(val==0xFF00FFFF) {
						// 똑똑한 유령
						SmartGhosts.add(new SmartGhost(i*32+location, j*32+location));
					}
					else if(val==0xFF33CF10) {
						//초록색 이동 타일
						Green.add(new Green(i*32+location, j*32+location));
					}
					else if(val==0xFFF5AB0D) {
						//주황색 골 타일
						Orange.add(new Orange(i*32+location,j*32+location));
					}
					else if(val==0xFFFF00FC) {
						//핑크색 타일에 빨간색 씨앗이 생김 (8개)
						redSeeds.add(new RedSeed(i*32+location,j*32+location));
					}
					else{
						// 씨앗
						seeds.add(new Seed(i*32+location, j*32+location));
						
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void tick() { // map에서 유령들의 상태를 바꾸고 랜더링 시킴
		for(int i=0;i<Ghosts.size();i++) {
			Ghosts.get(i).tick();  //유령의 방향에 따른 이미지 변화
		}
		for(int i=0;i<SmartGhosts.size();i++) {
			SmartGhosts.get(i).tick();   //스마트유령인지 랜덤유령인지의 상태 변화와 더불어 이미지 변화
		}
	}
	
	public void render(Graphics g) { // seeds와 tile은 상태가 바뀔 필요가 없으므로 tick함수가 존재하지 않음
		
		for(int i=0;i<Orange.size();i++) {
			Orange.get(i).render(g);  
		}
		for(int i=0;i<Green.size();i++) {
			Green.get(i).render(g);  
		}
		
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				if(tiles[i][j]!=null)    //tile의 요소중에서 비어있지 않은 공간이 있다면
					tiles[i][j].render(g);   //render를 통해서 상자를 만들어라
			}
		}
		
		for(int i=0;i<seeds.size();i++) {
			seeds.get(i).render(g);  //seeds를 render하여 사각형의 씨앗을 그린다
		}
		
		for(int i=0;i<redSeeds.size();i++) {
			//빨간색 씨앗, 먹으면 20점씩 올라감+ 다먹으면 다음 레벨로 이동
			redSeeds.get(i).render(g); //redSeeds를 render하여 빨간색 사각형의 씨앗을 그린다
		}
		
		for(int i=0;i<Ghosts.size();i++) {
			//빨간색 씨앗, 먹으면 20점씩 올라감
			Ghosts.get(i).render(g);  //render하여 유령을 그려라
		}
		for(int i=0;i<SmartGhosts.size();i++) {
			SmartGhosts.get(i).render(g);
		}
	}
}

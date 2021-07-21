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
	
	public Map(String path) { // map(png)�� ����Ǿ� �ִ� ��θ� �����ڷ� ����
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
			
			int[] pixels = new int[width*height]; // ���伥���� �׷����� ��
			// pixels �迭�� map.png�� �ȼ��� ���� �Ϸ�ȭ(���ι�������) �ؼ� ������
			map.getRGB(0, 0, width, height, pixels, 0, width); // 
			
			tiles =new Tile[width][height]; // GUI�� ������ ��

			for(int i=0;i<width;i++) {
				for(int j=0;j<height;j++) {
					int val = pixels[i+(j*width)];
					if(val==0xFF000A7C) {  //��ġ�� �ִ� ���� �����̶�� 
						// ��
						tiles[i][j]=new Tile(i*32+location, j*32+location);
					}
					else if(val==0xFFFFD800) {
						// �Ѹ�
						Game.pacman.x=i*32+location;
						Game.pacman.y=j*32+location;
					}
					else if(val==0xFFFF0000) {
						// �Ϲ� ����
						Ghosts.add(new Ghost(i*32+location, j*32+location));
					}
					else if(val==0xFF00FFFF) {
						// �ȶ��� ����
						SmartGhosts.add(new SmartGhost(i*32+location, j*32+location));
					}
					else if(val==0xFF33CF10) {
						//�ʷϻ� �̵� Ÿ��
						Green.add(new Green(i*32+location, j*32+location));
					}
					else if(val==0xFFF5AB0D) {
						//��Ȳ�� �� Ÿ��
						Orange.add(new Orange(i*32+location,j*32+location));
					}
					else if(val==0xFFFF00FC) {
						//��ũ�� Ÿ�Ͽ� ������ ������ ���� (8��)
						redSeeds.add(new RedSeed(i*32+location,j*32+location));
					}
					else{
						// ����
						seeds.add(new Seed(i*32+location, j*32+location));
						
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void tick() { // map���� ���ɵ��� ���¸� �ٲٰ� ������ ��Ŵ
		for(int i=0;i<Ghosts.size();i++) {
			Ghosts.get(i).tick();  //������ ���⿡ ���� �̹��� ��ȭ
		}
		for(int i=0;i<SmartGhosts.size();i++) {
			SmartGhosts.get(i).tick();   //����Ʈ�������� �������������� ���� ��ȭ�� ���Ҿ� �̹��� ��ȭ
		}
	}
	
	public void render(Graphics g) { // seeds�� tile�� ���°� �ٲ� �ʿ䰡 �����Ƿ� tick�Լ��� �������� ����
		
		for(int i=0;i<Orange.size();i++) {
			Orange.get(i).render(g);  
		}
		for(int i=0;i<Green.size();i++) {
			Green.get(i).render(g);  
		}
		
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				if(tiles[i][j]!=null)    //tile�� ����߿��� ������� ���� ������ �ִٸ�
					tiles[i][j].render(g);   //render�� ���ؼ� ���ڸ� ������
			}
		}
		
		for(int i=0;i<seeds.size();i++) {
			seeds.get(i).render(g);  //seeds�� render�Ͽ� �簢���� ������ �׸���
		}
		
		for(int i=0;i<redSeeds.size();i++) {
			//������ ����, ������ 20���� �ö�+ �ٸ����� ���� ������ �̵�
			redSeeds.get(i).render(g); //redSeeds�� render�Ͽ� ������ �簢���� ������ �׸���
		}
		
		for(int i=0;i<Ghosts.size();i++) {
			//������ ����, ������ 20���� �ö�
			Ghosts.get(i).render(g);  //render�Ͽ� ������ �׷���
		}
		for(int i=0;i<SmartGhosts.size();i++) {
			SmartGhosts.get(i).render(g);
		}
	}
}

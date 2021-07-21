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
	
	public Map2(String path) { // map(png)�� ����Ǿ� �ִ� ��θ� �����ڷ� ����
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
			
			int[] pixels2 = new int[width*height]; // ���伥���� �׷����� ��
			// pixels �迭�� map.png�� �ȼ��� ���� �Ϸ�ȭ(���ι�������) �ؼ� ������
			map2.getRGB(0, 0, width, height, pixels2, 0, width); // 
			
			tiles2 =new Tile[width][height]; // GUI�� ������ ��

			for(int i=0;i<this.width;i++) {
				for(int j=0;j<this.height;j++) {
					int val = pixels2[i+(j*this.width)];
					if(val==0xFF000A7C) {  //��ġ�� �ִ� ���� �����̶�� 
						// ��
						tiles2[i][j]=new Tile(i*32+location, j*32+location);
					}
					else if(val==0xFFFFD800) {
						// �Ѹ�
						Game.pacman2.x=i*32+location;
						Game.pacman2.y=j*32+location;
					}
					else if(val==0xFFFF0000) {
						// �Ϲ� ����
						Ghosts2.add(new Ghost2(i*32+location, j*32+location));
					}
					else if(val==0xFF00FFFF) {
						// �ȶ��� ����
						SmartGhosts2.add(new SmartGhost2(i*32+location, j*32+location));
					}
					else if(val==0xFF33CF10) {
						//�ʷϻ� �̵� Ÿ��
						Green2.add(new Green(i*32+location, j*32+location));
					}
					else if(val==0xFFF5AB0D) {
						//��Ȳ�� �� Ÿ��
						Orange2.add(new Orange(i*32+location,j*32+location));
					}
					else if(val==0xFFFF00FC) {
						//��ũ�� Ÿ�Ͽ� ������ ������ ���� (9��)
						redSeeds2.add(new RedSeed(i*32+location,j*32+location));
					}
					else{
						// ����
						seeds2.add(new Seed(i*32+location, j*32+location));
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void tick() { // map���� ���ɵ��� ���¸� �ٲٰ� ������ ��Ŵ
		for(int i=0;i<Ghosts2.size();i++) {
			Ghosts2.get(i).tick();  //������ ���⿡ ���� �̹��� ��ȭ
		}
		for(int i=0;i<SmartGhosts2.size();i++) {
			SmartGhosts2.get(i).tick();   //����Ʈ�������� �������������� ���� ��ȭ�� ���Ҿ� �̹��� ��ȭ
		}
	}
	
	public void render(Graphics g) { // seeds�� tile�� ���°� �ٲ� �ʿ䰡 �����Ƿ� tick�Լ��� �������� ����
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				if(tiles2[i][j]!=null)    //tile�� ����߿��� ������� ���� ������ �ִٸ�
					tiles2[i][j].render(g);   //render�� ���ؼ� ���ڸ� ������
			}
		}
		
		for(int i=0;i<Orange2.size();i++) {
			Orange2.get(i).render(g);  
		}
		for(int i=0;i<Green2.size();i++) {
			Green2.get(i).render(g); 
		}
		for(int i=0;i<seeds2.size();i++) {
			seeds2.get(i).render(g);  //seeds�� render�Ͽ� �簢���� ������ �׸���
		}
		
		for(int i=0;i<redSeeds2.size();i++) {
			//������ ����, ������ 20���� �ö� + �ٸ����� win
			redSeeds2.get(i).render(g);  //redSeeds�� render�Ͽ� ������ �簢���� ������ �׸���
		}
		
		for(int i=0;i<Ghosts2.size();i++) {
			Ghosts2.get(i).render(g);  //render�Ͽ� ������ �׷���
		}
		for(int i=0;i<SmartGhosts2.size();i++) {
			SmartGhosts2.get(i).render(g);
		}
	}
}

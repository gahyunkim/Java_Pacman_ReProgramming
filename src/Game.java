import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener{  //Runnable �������̽��� ����ϸ� �����带 ������ �� �ִ� + Runnable�� �����ϸ� �ش� Ŭ������ Ȯ���Ű�� ���� ���� ������

	private boolean isRunning = false;
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 780;
	public static final String TITLE = "Pacman";
		
	private Thread thread;  //������� �ϳ��� pc�ȿ��� �������� �۾��� ���ÿ� �� �� �ֵ��� �ϴ� �� ȿ������ ���α׷� �ۼ��� ����
	
	public static Pacman pacman;
	public static Pacman2 pacman2;
	public static Map map;
	public static Map2 map2;
	public static Appearance appearance;
	public static Score score;
	public static Score2 score2;
	
	//GUI�� ���� �̹����� 
	private Image background=new ImageIcon(Game.class.getResource("/title/title.png")).getImage();
	private Image red = new ImageIcon(Game.class.getResource("/Image/red.png")).getImage();
	private Image blue = new ImageIcon(Game.class.getResource("/Image/blue.png")).getImage();
	private Image smart = new ImageIcon(Game.class.getResource("/Image/smart.png")).getImage();
	private Image pacmanImage = new ImageIcon(Game.class.getResource("/Image/pacmanImage.png")).getImage();
	
	// ���
	public static final int START = 0;
	public static final int GAME = 1;
	public static final int GAME2 = 2;
	public static final int END = 3;
	public static boolean DEAD = false;
	public static boolean DEAD2 = false;
	public static boolean WIN =false;
	public static int STATE = -1;
	
	public static boolean isEnter = false;
	//���Ḧ ���� �����̽� ��
	public boolean isSpaceBar = false;
	
	private int time = 0;
	private int targetFrames =  35;
	private boolean showText = true;
	
	/********************************************************************************************************/
	
	public Game() {  //������ ����
		Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);  //������ ���ο� ���θ� ���� ����
		setPreferredSize(dimension);  //Dimension�� �޾Ƽ� ���� ���� ���� ���� ���� �� ũ�⸦ �����ϴ� ��
		setMinimumSize(dimension);    //Dimension�� �޾Ƽ� �ּ� ����� ����
		setMaximumSize(dimension);    //�ִ� ������ ����
		addKeyListener(this);     //����Ű ����� ���� �̺�Ʈ ������
		
		STATE=START; //������ ���۵Ǿ�� �ϱ� ������ STATE�� START�� ��ȭ
		WIN=false;
		DEAD=false;
		
		score = new Score("res\\scoreboard\\scoreboard.txt");  //score�� text���Ͽ� ����Ǿ� ����
		score2 = new Score2("res\\scoreboard\\scoreboard2.txt");
		pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2); // ����� �� �߾ӿ� ��ġ
		pacman2 = new Pacman2(Game.WIDTH/2, Game.HEIGHT/2);
		map=new Map("/map/Map1.png");
		map2=new Map2("/map/map2.png");
		appearance = new Appearance("/appearance/appearance.png");
		
		new Character();
	}

	/********************************************************************************************************/	
	
	public synchronized void start() {  //���� �����͸� ����ϰ� �ִ� �ش� �����带 �����ϰ� ������ ��������� �����Ϳ� ������ �������� ���� ���� Thread-safe�� �ϱ� ����
		if(isRunning)  //������ ����ǰ� �ִٸ� ��ȯ�ض�
			return;
		isRunning = true;  //������ ����ǰ� ���� �ʴٸ� thread�� ����ǵ��� �ض�
		thread = new Thread(this);
		thread.start();  //�����带 �����ϴ� �޼ҵ�
	}
	
	/********************************************************************************************************/

	public synchronized void stop() {
		if(!isRunning)  //������ ����ǰ� ���� �ʴٸ� 
			return;
		isRunning = false;
		try {
			thread.join();  //�ش� �����尡 ����� �� ���� ��ٷȴٰ� �������� �Ѿ + �ٸ� �����带 ���߰� �� �����带 ������ ����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************/

	private void render() { // tick���� ��ȭ��Ų Ŭ������ ������ ������
		BufferStrategy bs = getBufferStrategy(); // ȭ���� �������ų� �����Ÿ��� ������ �����ϱ� ����
		if(bs==null) { 
			createBufferStrategy(5); // ���۸� 5�� Ȱ�� -> �׸��� �׸��� �� ���� ȭ�鿡 ǥ���ϴ� ���� ���� ������ ������
			return;
		}		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		if(STATE==GAME) {
			pacman.render(g);
			map.render(g);
			score.render(g);
			DEAD=false;
			
			g.setColor(Color.white);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			g.drawString("SCORE", 30, 705);
			
			//���� 1������ ������ Ʃ�丮�� ǥ�ø� ����
			g.setColor(Color.red);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
			g.drawString("Level1",250,700);
			g.setColor(Color.white);
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
			g.drawString("Use the arrow keys to move Pac-Man", 250, 720);
			g.drawString("The green box moves Pac-Man to another green box.", 250, 740);
			g.drawString("To move on to the next stage, you have to go to the yellow box", 250, 760);
			
		}
		
		else if(STATE==GAME2) {
			pacman2.render(g);
			map2.render(g);
			score2.render(g);
			DEAD2=false;
			WIN=false;

			g.setColor(Color.white);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			g.drawString("SCORE", 30, 705);
			
			//���� 2������ ������ Ʃ�丮�� ǥ�ø� ����
			g.setColor(Color.red);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
			g.drawString("Level2",250,700);
			g.setColor(Color.white);
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
			g.drawString("Use the arrow keys to move Pac-Man", 250, 720);
			g.drawString("The green box moves Pac-Man to another green box.", 250, 740);
			g.drawString("If you go to the yellow box, you win. Or eat all the seeds,you win.", 250, 760);
			
		}

		else if(STATE==START) { // �ٽ� �����ؾ� �ϰų� �����ϴ� ��Ȳ

			int menu_width = 600;
			int menu_height = 400;
			int xx = Game.WIDTH/2 - menu_width/2;
			int yy = Game.HEIGHT/2 - menu_height/2;
			g.drawImage(background, 165,50, 390,150, null);
			g.drawImage(red, 60, 670, 64, 64, null);
			g.drawImage(blue, 229, 670, 64,64, null);
			g.drawImage(pacmanImage,398 ,670, 64,64, null);
			g.drawImage(smart,  567, 670,64, 64, null);
			g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.6f));
			g.fillRect(xx, yy+50, menu_width, menu_height);			
		
			g.setColor(Color.white);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			
			
			if(showText) { // ����ȭ��
				if(DEAD){	// �׾�����
					g.setColor(Color.white);
					g.drawString("LEVEL 1", xx+250, yy+140);
					g.setColor(Color.black);
					g.drawString("YOU ARE DEAD", xx+200, yy+190);
					g.drawString("Your Score is "+score.score, xx+190, yy+240);  //���� (,)��ġ�� ���ڸ� �׸��� ��
					g.drawString("Your Rank is "+score.rank, xx+200, yy+290);
					g.drawString("Press enter to restart the game", xx+122, yy+340);

				}
				else if(DEAD2) {
					g.setColor(Color.white);
					g.drawString("LEVEL 2",  xx+250, yy+140);
					g.setColor(Color.black);
					g.drawString("YOU ARE DEAD",xx+200, yy+190);
					g.drawString("Your Score is "+score2.score2, xx+190, yy+240);  //���� (,)��ġ�� ���ڸ� �׸��� ��
					g.drawString("Your Rank is "+score2.rank2, xx+200, yy+290);
					g.drawString("Press enter to restart the game", xx+122, yy+340);
					
				}
				else
					g.drawString("Press enter to start the game", xx+125, yy+250);
			}
		}
		
		//�������� �̰��� ���� GUI�� ���� �ٸ��� ���ֱ� ���� ����� ��. 
		else if(STATE==END) {
			int menu_width = 600;
			int menu_height = 400;
			int xx = Game.WIDTH/2 - menu_width/2;
			int yy = Game.HEIGHT/2 - menu_height/2;
			g.drawImage(background, 165,50, 390,150, null);
			g.drawImage(red, 60, 670, 64, 64, null);
			g.drawImage(blue, 229, 670, 64,64, null);
			g.drawImage(pacmanImage,398 ,670, 64,64, null);
			g.drawImage(smart,  567, 670,64, 64, null);
			g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.8f));
			g.fillRect(xx, yy+50, menu_width, menu_height);			
		
			g.setColor(Color.black);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			
			if(showText) {
				if(WIN) {
					g.setColor(Color.red);
					g.drawString("!!! YOU WIN !!!", xx+215, yy+200);
					g.setColor(Color.black);
					g.drawString("Press SpaceBar to End the game", xx+100, yy+250);
				}
			}
		}
		
		g.dispose(); // ȭ�鿡 �ִ� �׷����� ���ɷ� �ٲٱ� ���� ���� �׷��� ����
		bs.show();
	}
	
	/********************************************************************************************************/

	private void tick() { // ���� ��� Ŭ������ �ִ� tick �Լ��� �� Ŭ������ ���¸� ��ȭ��Ŵ
		if(STATE==GAME) {  //������ ���۵ȴٸ� 
			pacman.tick();  //�Ѹǰ� map�� tick�� �����Ѵ� + �Ѹ��� �����϶����� �׸� �ٲ�� seed�Դ� ����
			map.tick();     //���ɵ��� ���º�ȭ ����
		}
		else if(STATE==GAME2) {
			pacman2.tick();  
			map2.tick(); 
		}
		else if(STATE == START) {  //�ٽ� �����ؾ� �ϴ� ��Ȳ�̶��
			time++;
			if(time==targetFrames) {
				time=0;
				if(showText) {
					showText=false;
				}
				else
					showText=true;
			} // �����ϰų� �׾��� �� ������ �����Ÿ����� ��
			
			if(isEnter) { // ���͸� �������� ���� ���� ������
				isEnter=false;
				pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2);
				map=new Map("/map/Map1.png");
				appearance = new Appearance("/appearance/appearance.png");
				STATE=GAME;
				pacman.tick();
				map.tick();
				render();

				if(DEAD) { //DEAD�϶��� ��, ����1���� �׾��� �� �ٽ� ���� 1�� ���ư� �� �ֵ��� �ϱ� ����(����κ�)
					isEnter=false;
					pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2);
					map=new Map("/map/Map1.png");
					appearance = new Appearance("/appearance/appearance.png");
					STATE=GAME;
					pacman.tick();
					map.tick();
					render();
					return;
				}
				
				if(DEAD2) { //DEAD2�϶��� ��, ����2���� �׾��� �� �ٽ� ���� 2�� ���ư� �� �ֵ��� �ϱ� ����(����κ�)
					isEnter=false;
					pacman2 = new Pacman2(Game.WIDTH/2, Game.HEIGHT/2);
					map2=new Map2("/map/map2.png");
					appearance = new Appearance("/appearance/appearance.png");
					STATE=GAME2;
					pacman2.tick();
					map2.tick();
					render();
					return;
				}
			}
		}
		
		else if(STATE==END) {  //END�� ���� �������� ������ �׿� �´� ��Ȳ�� ǥ�����ֱ� ���� �������
			time++;
			if(time==targetFrames) {
				time=0;
				if(showText) {
					showText=false;
				}
				else
					showText=true;
			} // �����ϰų� �׾��� �� ������ �����Ÿ����� ��
			
			//END�� ��Ȳ���� �����̽��ٸ� ������ ������ �����ϵ��� �ϴ� key
			if(isSpaceBar) {
				isSpaceBar = false;
			}
		}
	}
	
	/********************************************************************************************************/
	
	public static void main(String[] args) {   //���� �޼ҵ��� ���� + ��ü ����
		
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle(Game.TITLE);
		frame.add(game);
		frame.setResizable(false);  //�ٽ� ����� �ٲ� �� ������ ����
		frame.pack();   //��ġ�� pack���� ����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
				
		frame.setVisible(true);
		game.start();
	}
	
	/***************************************** ������ ���� �Լ� / fps���� ************************************************/

	public void run() {
		
		requestFocus();  //key�� ����� �ǰ� �ִ��� Ȯ���ϱ� ���� �뵵
		int fps = 0; // fps�� �׻� 100�� �ǵ��� ��  1/100�ʿ� �ѹ��� ȭ�鿡 ���ο� �׷����� ��쵵�� ��
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 100.0;
		double delta = 0;
		double ns = 1000000000 / targetTick;
		
		while(isRunning) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime)/ns);
			lastTime = now;
			
			while(delta >=1) { // render�� tick���� �� ���� �θ��� ������ synchronized�� ���� ����
				tick();
				render(); 
				fps++; 
				delta--;
			}
			
			if(System.currentTimeMillis()-timer>=1000) { // 1�ʺ��� ũ��
				fps=0;
				timer = timer+1000;
			}
			
		}
		stop();
	}

	/****************************************************** Ű���� �Է� ���� *******************************************************/

	public void keyPressed(KeyEvent e) {
		if(STATE == GAME) {
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				pacman.right=true;
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
				pacman.left=true;
			if(e.getKeyCode()==KeyEvent.VK_UP)
				pacman.up=true;
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				pacman.down=true;
		}
		else if(STATE==GAME2) {
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				pacman2.right=true;
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
				pacman2.left=true;
			if(e.getKeyCode()==KeyEvent.VK_UP)
				pacman2.up=true;
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				pacman2.down=true;
		}
		
		//���͸� ������ ������ ������ �� �ֵ��� ��
		else if(STATE==START) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				isEnter = true;
			}
		}
		//�����̽� �ٸ� ������ ������ �����ϵ��� ��
		else if(STATE==END) {
			if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				isSpaceBar = true;
				System.exit(0);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			pacman.right=false;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			pacman.left=false;
		if(e.getKeyCode()==KeyEvent.VK_UP)
			pacman.up=false;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
			pacman.down=false;
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			pacman2.right=false;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			pacman2.left=false;
		if(e.getKeyCode()==KeyEvent.VK_UP)
			pacman2.up=false;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
			pacman2.down=false;
	}

	public void keyTyped(KeyEvent e) {  //�߻�޼ҵ� ����
		
	}

}

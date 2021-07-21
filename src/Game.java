import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener{  //Runnable 인터페이스를 사용하면 스레드를 생성할 수 있다 + Runnable을 구현하면 해달 클래스를 확장시키는 것이 더욱 편리해짐

	private boolean isRunning = false;
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 780;
	public static final String TITLE = "Pacman";
		
	private Thread thread;  //스레드는 하나의 pc안에서 여러개의 작업을 동시에 할 수 있도록 하는 것 효률적인 프로그램 작성을 도움
	
	public static Pacman pacman;
	public static Pacman2 pacman2;
	public static Map map;
	public static Map2 map2;
	public static Appearance appearance;
	public static Score score;
	public static Score2 score2;
	
	//GUI를 위한 이미지들 
	private Image background=new ImageIcon(Game.class.getResource("/title/title.png")).getImage();
	private Image red = new ImageIcon(Game.class.getResource("/Image/red.png")).getImage();
	private Image blue = new ImageIcon(Game.class.getResource("/Image/blue.png")).getImage();
	private Image smart = new ImageIcon(Game.class.getResource("/Image/smart.png")).getImage();
	private Image pacmanImage = new ImageIcon(Game.class.getResource("/Image/pacmanImage.png")).getImage();
	
	// 모드
	public static final int START = 0;
	public static final int GAME = 1;
	public static final int GAME2 = 2;
	public static final int END = 3;
	public static boolean DEAD = false;
	public static boolean DEAD2 = false;
	public static boolean WIN =false;
	public static int STATE = -1;
	
	public static boolean isEnter = false;
	//종료를 위한 스페이스 바
	public boolean isSpaceBar = false;
	
	private int time = 0;
	private int targetFrames =  35;
	private boolean showText = true;
	
	/********************************************************************************************************/
	
	public Game() {  //생성자 생성
		Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);  //게임의 가로와 세로를 얻어내는 과정
		setPreferredSize(dimension);  //Dimension을 받아서 가로 세로 길이 값을 저장 및 크기를 지정하는 것
		setMinimumSize(dimension);    //Dimension을 받아서 최소 사이즈를 설정
		setMaximumSize(dimension);    //최대 사이즈 설정
		addKeyListener(this);     //방향키 사용을 위한 이벤트 리스너
		
		STATE=START; //게임이 시작되어야 하기 때문에 STATE가 START로 변화
		WIN=false;
		DEAD=false;
		
		score = new Score("res\\scoreboard\\scoreboard.txt");  //score는 text파일에 저장되어 있음
		score2 = new Score2("res\\scoreboard\\scoreboard2.txt");
		pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2); // 모니터 정 중앙에 배치
		pacman2 = new Pacman2(Game.WIDTH/2, Game.HEIGHT/2);
		map=new Map("/map/Map1.png");
		map2=new Map2("/map/map2.png");
		appearance = new Appearance("/appearance/appearance.png");
		
		new Character();
	}

	/********************************************************************************************************/	
	
	public synchronized void start() {  //현재 데이터를 사용하고 있는 해당 스레드를 제외하고 나머지 스레드들은 데이터에 접근할 수없도록 막는 개념 Thread-safe를 하기 위함
		if(isRunning)  //게임이 실행되고 있다면 반환해라
			return;
		isRunning = true;  //게임이 실행되고 있지 않다면 thread가 실행되도록 해라
		thread = new Thread(this);
		thread.start();  //스레드를 실행하는 메소드
	}
	
	/********************************************************************************************************/

	public synchronized void stop() {
		if(!isRunning)  //게임이 실행되고 있지 않다면 
			return;
		isRunning = false;
		try {
			thread.join();  //해당 스레드가 종료될 때 까지 기다렸다가 다음으로 넘어감 + 다른 스레드를 멈추고 이 스레드를 끝까지 실행
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************/

	private void render() { // tick에서 변화시킨 클래스를 실제로 보여줌
		BufferStrategy bs = getBufferStrategy(); // 화면이 찢어지거나 깜빡거리는 현상을 방지하기 위함
		if(bs==null) { 
			createBufferStrategy(5); // 버퍼를 5개 활용 -> 그림을 그리는 것 보다 화면에 표출하는 것이 빠른 현상을 방지함
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
			
			//레벨 1에서의 간단한 튜토리얼 표시를 위함
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
			
			//레벨 2에서의 간단한 튜토리얼 표시를 위함
			g.setColor(Color.red);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
			g.drawString("Level2",250,700);
			g.setColor(Color.white);
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
			g.drawString("Use the arrow keys to move Pac-Man", 250, 720);
			g.drawString("The green box moves Pac-Man to another green box.", 250, 740);
			g.drawString("If you go to the yellow box, you win. Or eat all the seeds,you win.", 250, 760);
			
		}

		else if(STATE==START) { // 다시 시작해야 하거나 시작하는 상황

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
			
			
			if(showText) { // 시작화면
				if(DEAD){	// 죽었을때
					g.setColor(Color.white);
					g.drawString("LEVEL 1", xx+250, yy+140);
					g.setColor(Color.black);
					g.drawString("YOU ARE DEAD", xx+200, yy+190);
					g.drawString("Your Score is "+score.score, xx+190, yy+240);  //뒤의 (,)위치에 글자를 그리는 것
					g.drawString("Your Rank is "+score.rank, xx+200, yy+290);
					g.drawString("Press enter to restart the game", xx+122, yy+340);

				}
				else if(DEAD2) {
					g.setColor(Color.white);
					g.drawString("LEVEL 2",  xx+250, yy+140);
					g.setColor(Color.black);
					g.drawString("YOU ARE DEAD",xx+200, yy+190);
					g.drawString("Your Score is "+score2.score2, xx+190, yy+240);  //뒤의 (,)위치에 글자를 그리는 것
					g.drawString("Your Rank is "+score2.rank2, xx+200, yy+290);
					g.drawString("Press enter to restart the game", xx+122, yy+340);
					
				}
				else
					g.drawString("Press enter to start the game", xx+125, yy+250);
			}
		}
		
		//마지막에 이겼을 때의 GUI를 조금 다르게 해주기 위해 만들어 줌. 
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
		
		g.dispose(); // 화면에 있던 그래픽을 새걸로 바꾸기 위해 예전 그래픽 지움
		bs.show();
	}
	
	/********************************************************************************************************/

	private void tick() { // 거의 모든 클래스에 있는 tick 함수는 각 클래스의 상태를 변화시킴
		if(STATE==GAME) {  //게임이 시작된다면 
			pacman.tick();  //팩맨과 map이 tick을 시작한다 + 팩맨은 움직일때마다 그림 바뀌고 seed먹는 과정
			map.tick();     //유령들의 상태변화 시작
		}
		else if(STATE==GAME2) {
			pacman2.tick();  
			map2.tick(); 
		}
		else if(STATE == START) {  //다시 시작해야 하는 상황이라면
			time++;
			if(time==targetFrames) {
				time=0;
				if(showText) {
					showText=false;
				}
				else
					showText=true;
			} // 시작하거나 죽었을 때 문구를 깜빡거리도록 함
			
			if(isEnter) { // 엔터를 눌렀으면 게임 새로 시작함
				isEnter=false;
				pacman = new Pacman(Game.WIDTH/2, Game.HEIGHT/2);
				map=new Map("/map/Map1.png");
				appearance = new Appearance("/appearance/appearance.png");
				STATE=GAME;
				pacman.tick();
				map.tick();
				render();

				if(DEAD) { //DEAD일때는 즉, 레벨1에서 죽었을 떄 다시 레벨 1로 돌아갈 수 있도록 하기 위함(연결부분)
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
				
				if(DEAD2) { //DEAD2일때는 즉, 레벨2에서 죽었을 떄 다시 레벨 2로 돌아갈 수 있도록 하기 위함(연결부분)
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
		
		else if(STATE==END) {  //END를 새로 만들어줬기 떄문에 그에 맞는 상황을 표시해주기 위해 만들어줌
			time++;
			if(time==targetFrames) {
				time=0;
				if(showText) {
					showText=false;
				}
				else
					showText=true;
			} // 시작하거나 죽었을 때 문구를 깜빡거리도록 함
			
			//END의 상황에서 스페이스바를 누르면 게임을 종료하도록 하는 key
			if(isSpaceBar) {
				isSpaceBar = false;
			}
		}
	}
	
	/********************************************************************************************************/
	
	public static void main(String[] args) {   //메인 메소드의 실행 + 객체 생성
		
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle(Game.TITLE);
		frame.add(game);
		frame.setResizable(false);  //다시 사이즈를 바꿀 수 없도록 설정
		frame.pack();   //위치를 pack으로 정렬
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
				
		frame.setVisible(true);
		game.start();
	}
	
	/***************************************** 스레드 실행 함수 / fps관리 ************************************************/

	public void run() {
		
		requestFocus();  //key의 사용이 되고 있는지 확인하기 위한 용도
		int fps = 0; // fps가 항상 100이 되도록 즉  1/100초에 한번씩 화면에 새로운 그래픽을 띄우도록 함
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 100.0;
		double delta = 0;
		double ns = 1000000000 / targetTick;
		
		while(isRunning) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime)/ns);
			lastTime = now;
			
			while(delta >=1) { // render를 tick보다 더 많이 부르지 않으면 synchronized가 되지 않음
				tick();
				render(); 
				fps++; 
				delta--;
			}
			
			if(System.currentTimeMillis()-timer>=1000) { // 1초보다 크면
				fps=0;
				timer = timer+1000;
			}
			
		}
		stop();
	}

	/****************************************************** 키보드 입력 관리 *******************************************************/

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
		
		//엔터를 누르면 게임을 시작할 수 있도록 함
		else if(STATE==START) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				isEnter = true;
			}
		}
		//스페이스 바를 누르면 게임을 종료하도록 함
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

	public void keyTyped(KeyEvent e) {  //추상메소드 구현
		
	}

}

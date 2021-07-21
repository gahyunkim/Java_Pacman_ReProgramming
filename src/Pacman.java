import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman extends Rectangle{ // 사이즈나 포지션 관리를 쉽게 하기 위해 extends Rectangle

	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	private int speed = 2;
	private int imageIndex = 0;
	private int time=0;
	private int changingTime=60*2;
	
	Score curScore;   
	
	public Pacman(int x, int y) {
		curScore=Game.score;
		curScore.score=0;
		setBounds(x, y, 30, 30);
	}

	private boolean canMove(int next_x, int next_y) { // Ghost와 같은 함수 벽이 있으면 움직이지 못함
		
		Rectangle bounds = new Rectangle(next_x, next_y, width, height);
		Map map = Game.map;
		
		for(int i=0;i<map.tiles.length;i++){           //tiles
			for(int j=0;j<map.tiles[0].length;j++){
				if(map.tiles[i][j]!=null){   //만약 tiles의 요소가 빈칸이 아니라면 
					if(bounds.intersects(map.tiles[i][j])) {  //만들어진 bounds가 맵의 tiles와 겹친다면 false를 반환함
						return false;
					}
				}
			}
		}
		
		return true; //bounds가 겹치지 않는다면 true를 반환함
	}
		
	public void tick() { // 각 방향으로 움직일 때마다 이미지를 바꿈
		if(right&&canMove(x+speed, y)){
			x=x+speed;
			imageIndex = 0;
		}
		if(left&&canMove(x-speed, y)) {
			x=x-speed;
			imageIndex = 1;
		}
		if(up&&canMove(x, y-speed)) {
			y=y-speed;
			imageIndex = 2;
		}
		if(down&&canMove(x, y+speed)) {
			y=y+speed;
			imageIndex = 3;
		}
		
		Map map = Game.map;
				
		if(Game.map.redSeeds.size()==0) {
			//레벨1에서는 8개의 빨간 씨앗을 먹어야함.
			Game.STATE = Game.GAME2;
		}
		
		for(int i=0;i<Game.map.seeds.size();i++){ // 팩맨과 seed가 겹치면 seed는 사라짐
			if(this.intersects(map.seeds.get(i))) {  //get은 리스트에서 어떤 객체를 가져오는데 사용되는 메소드
				curScore.score+=10;     //seeds와 겹칠때마다 score는 10씩 증가한다.
				map.seeds.remove(i);    //겹쳐진 seed는 사라진다. remove(i)
				break;
			}			
		}
		
		for(int i=0;i<Game.map.redSeeds.size();i++) { //빨간색 씨앗과 팩맨이 겹치면 스코어가 20씩 올라감
			if(this.intersects(map.redSeeds.get(i))) {
				curScore.score+=20;
				map.redSeeds.remove(i);
			}
		}
		
		for(int i=0;i<Game.map.Green.size();i++) {
			//초록타일에 가는 경우
			Green temp = Game.map.Green.get(0);
			Green temp2 = Game.map.Green.get(1);
			if(temp.intersects(this)) {
				this.setBounds(temp2);
			}
		}
		

		for(int i=0;i<Game.map.Orange.size();i++) {
			//오렌지 타일에 가는 경우
			Orange temp = Game.map.Orange.get(0);
			if(temp.intersects(this)) {
				Game.STATE = Game.GAME2;
			}
		}
			
		for(int i=0;i<Game.map.Ghosts.size();i++){ 
			// lose
			// 일반 유령에게 잡힘
			Ghost temp = Game.map.Ghosts.get(i);
			if(temp.intersects(this)) {   //지정된 Rectangle2D 내부 영역과 교차하는지를 판정하는 intersects
				Game.STATE=Game.START;    //일단 시작되어있는 상태여야 하기 때문에 STATE = START임
				curScore.insertScore();
				Game.DEAD = true;
				return;
			}
		}
		for(int i=0;i<Game.map.SmartGhosts.size();i++){
			// lose
			// 똑똑한 유령에게 잡힘
			SmartGhost temp=Game.map.SmartGhosts.get(i);
			if(temp.intersects(this)){
				Game.STATE=Game.START;
				curScore.insertScore();
				Game.DEAD = true;
				return;
			}
		}
		
		if(Game.map.seeds.size()<=130||curScore.score>=550) {
			//맵 위의 씨앗을 50개 이상 먹으면 속도를 3으로 높여준다.
			//또는 씨앗을 먹어서 점수가 550점 이상이 되면 속도를 3으로 높여준다.
			//맵1이라서 난이도를 맵1보다 낮게 함
			speed=3;
		}		
	}
	
	public void render(Graphics g){  //팩맨의 모양이 imageIndex에 따라 바뀌고 x,y위치에 캐릭터가 위치하게된다.
		g.drawImage(Character.pacman[imageIndex], x, y, width, height, null);  //이미지에 따라 render하는 것
	}
}

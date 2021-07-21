import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman2 extends Rectangle{ // 사이즈나 포지션 관리를 쉽게 하기 위해 extends Rectangle
	
	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	private int speed = 2;
	private int imageIndex = 0;
	
	Score2 curScore;   
	
	public Pacman2(int x, int y) {
		curScore=Game.score2;
		curScore.score2=0;
		setBounds(x, y, 30, 30);
	}
	
	private boolean canMove(int next_x, int next_y) { // Ghost와 같은 함수 벽이 있으면 움직이지 못함
		
		Rectangle bounds2 = new Rectangle(next_x, next_y, width, height);
		Map2 map2 = Game.map2;
		
		for(int i=0;i<map2.tiles2.length;i++){           //tiles
			for(int j=0;j<map2.tiles2[0].length;j++){
				if(map2.tiles2[i][j]!=null){   //만약 tiles의 요소가 빈칸이 아니라면 
					if(bounds2.intersects(map2.tiles2[i][j])) {  //만들어진 bounds가 맵의 tiles와 겹친다면 false를 반환함
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
		
		Map2 map2 = Game.map2;
		
		for(int i=0;i<map2.seeds2.size();i++){ // 팩맨과 seed가 겹치면 seed는 사라짐
			if(this.intersects(map2.seeds2.get(i))) {  //get은 리스트에서 어떤 객체를 가져오는데 사용되는 메소드
				curScore.score2+=10;     //seeds와 겹칠때마다 score는 10씩 증가한다.
				map2.seeds2.remove(i);    //겹쳐진 seed는 사라진다. remove(i)
				break;
			}
		
		}
		
		if(map2.seeds2.size()==0) {
			// win
			//seeds를 다 먹음
			Game.STATE=Game.END;  //원래 STATE는 -1이고 START는 1이었는데 둘이 같아지게 함으로써 이기는 것으로 마무리함
			Game.WIN=true;
		}
		
		if(Game.map2.redSeeds2.size()==0) {
			//win
			//빨간색 seeds를 다 먹는 경우 -> 이기는 경우를 세가지로 나눠줌.(레벨1에서는 8개 레벨2에서는 9개를 먹어야함)
			Game.STATE=Game.END; 
			Game.WIN=true;
		}
		
		for(int i=0;i<map2.redSeeds2.size();i++) {
			if(this.intersects(map2.redSeeds2.get(i))) {
				curScore.score2+=20;
				map2.redSeeds2.remove(i);
			}
		}
		
		for(int i=0;i<Game.map2.Green2.size();i++) {
			//초록타일에 가는 경우
			Green temp = Game.map2.Green2.get(0);
			Green temp2 = Game.map2.Green2.get(1);
			if(temp.intersects(this)) {
				this.setBounds(temp2);
			}
		}
		
		for(int i=0;i<Game.map2.Orange2.size();i++) {
			//win
			//오렌지타일에 가는 경우
			Orange temp = Game.map2.Orange2.get(0);
			if(temp.intersects(this)) {
				Game.STATE=Game.END;
				Game.WIN=true;
			}
		}
				
		for(int i=0;i<Game.map2.Ghosts2.size();i++){ 
			// lose
			// 일반 유령에게 잡힘
			Ghost2 temp = Game.map2.Ghosts2.get(i);
			if(temp.intersects(this)) {   //지정된 Rectangle2D 내부 영역과 교차하는지를 판정하는 intersects
				Game.STATE=Game.START;    //일단 시작되어있는 상태여야 하기 때문에 STATE = START임
				Game.DEAD2 = true;
				curScore.insertScore();
				return;
				

			}
		}
		for(int i=0;i<Game.map2.SmartGhosts2.size();i++){
			// lose
			// 똑똑한 유령에게 잡힘
			SmartGhost2 temp=Game.map2.SmartGhosts2.get(i);
			if(temp.intersects(this)){
				Game.STATE=Game.START;
				Game.DEAD2 = true;
				curScore.insertScore();
				return;
			}
		}
		
		if(map2.seeds2.size()<=110||curScore.score2>=650) {
			//맵 위의 씨앗을 70개 이상 먹으면 팩맨의 속도를 3으로 바꿔준다.
			//맵 위의 씨앗을 먹어 650점 이상이 되면 속도가 3이 된다.
			//맵1과는 다르게 난이도를 조금 높여줌
			speed=3;
		}
		
	}
	
	public void render(Graphics g){  //팩맨의 모양이 imageIndex에 따라 바뀌고 x,y위치에 캐릭터가 위치하게된다.
		g.drawImage(Character.pacman[imageIndex], x, y, width, height, null);  //이미지에 따라 render하는 것
	}
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;  //Buffer는 데이터를 한곳에서 다른 한곳으로 전송하는 동안 일시적으로 데이터를 보관하는 임시 메모리 영역+입출력 속도 향상을 위해 사용
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Score {
	
	String path;  
	File scoreboard;  //File 클래스를 통해서 파일과 디렉터리를 다룰 수있음
	private ArrayList<Integer> scores;  //ArrayList를 이용한 리스트 생성(배열 생성)
	public int score;
	private int temp;
	public int total;
	public int rank;
	
	Score(String path){
		this.path=path;
		scores = new ArrayList<>();
		score=0;
		scoreboard = new File(path);  //path로 파일의 경로를 받았으므로 scoreboard의 경로를 얻을 수 있음
		try { // 이전 점수 모두 scores라는 ArrayList에 저장
			Scanner scan = new Scanner(scoreboard);
			while(scan.hasNextInt()) {   //입력값이 들어올때 까지 while문 내부가 실행되지 않다가 입력값이 들어와서 scan.hasNextInt()가 true를 리턴한 후에 while문 내부가 실행됨
				scores.add(scan.nextInt());
			}
			scan.close();
		} catch (FileNotFoundException e) {   //입출력예외처리하는것 IOException, 존재하지 않는 파일을 입력한경우에 예외를 처리함
			e.printStackTrace();  //예외 발생 당시의 호출 객체에? 있었던 메서드의 정보와 예외 메시지를 화면에 출력한다.
		}	
	}
	
	public void insertScore() {
		scores.add(this.score); // player가 새로 받은 점수를 추가하여 내림차순 정렬
		Collections.sort(scores);  //기본적으로 오름차순 정렬이 가능하고 Collections.reverseOrder(),Collections.reverse(list)해도 내림차순과 동일
		total = scores.size();  //입력된 값들은 크기가 정해지지 않은 scores에 저장되고 total이 입력된 개수이기 때문에 size가 되는 것
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));  //버퍼를 이용한 출력 + System.out.print와 동일하게 사용가능한 함수 + 많은 양의 출력이 필요한 경우에 사용
			PrintWriter pw = new PrintWriter(bw);
			for(int i=total-1;i>=0;i--) {
				temp=scores.get(i);
				pw.printf("%d\n", temp);
				if(score==temp)   //score와 얻은 score가 같다면 등수는 전체에서 i만큼 빠지는 것
					rank=total-i; 
			}
			pw.close();  //close를 해줌으로써 발생할 수 있는 문제들을 예방함. 메모리를 사용했기 때문에 close해줌
			bw.close();
		} catch (IOException e) {  //오류가 생기는지 안생기는 지를 확인하고 처리함
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics g) {
		if(Game.STATE==Game.GAME) {
			g.setColor(Color.BLACK);
			g.fillRect(30, 860, 150, 70);
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 60));
			g.drawString(String.valueOf(score), 30, 755);  //valueOf 함수를 사용해서 score가 정수형이지만 이 값을 문자열로 변환함
		}
		
	}
}

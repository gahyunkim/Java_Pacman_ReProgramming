import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;  //Buffer�� �����͸� �Ѱ����� �ٸ� �Ѱ����� �����ϴ� ���� �Ͻ������� �����͸� �����ϴ� �ӽ� �޸� ����+����� �ӵ� ����� ���� ���
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
	File scoreboard;  //File Ŭ������ ���ؼ� ���ϰ� ���͸��� �ٷ� ������
	private ArrayList<Integer> scores;  //ArrayList�� �̿��� ����Ʈ ����(�迭 ����)
	public int score;
	private int temp;
	public int total;
	public int rank;
	
	Score(String path){
		this.path=path;
		scores = new ArrayList<>();
		score=0;
		scoreboard = new File(path);  //path�� ������ ��θ� �޾����Ƿ� scoreboard�� ��θ� ���� �� ����
		try { // ���� ���� ��� scores��� ArrayList�� ����
			Scanner scan = new Scanner(scoreboard);
			while(scan.hasNextInt()) {   //�Է°��� ���ö� ���� while�� ���ΰ� ������� �ʴٰ� �Է°��� ���ͼ� scan.hasNextInt()�� true�� ������ �Ŀ� while�� ���ΰ� �����
				scores.add(scan.nextInt());
			}
			scan.close();
		} catch (FileNotFoundException e) {   //����¿���ó���ϴ°� IOException, �������� �ʴ� ������ �Է��Ѱ�쿡 ���ܸ� ó����
			e.printStackTrace();  //���� �߻� ����� ȣ�� ��ü��? �־��� �޼����� ������ ���� �޽����� ȭ�鿡 ����Ѵ�.
		}	
	}
	
	public void insertScore() {
		scores.add(this.score); // player�� ���� ���� ������ �߰��Ͽ� �������� ����
		Collections.sort(scores);  //�⺻������ �������� ������ �����ϰ� Collections.reverseOrder(),Collections.reverse(list)�ص� ���������� ����
		total = scores.size();  //�Էµ� ������ ũ�Ⱑ �������� ���� scores�� ����ǰ� total�� �Էµ� �����̱� ������ size�� �Ǵ� ��
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));  //���۸� �̿��� ��� + System.out.print�� �����ϰ� ��밡���� �Լ� + ���� ���� ����� �ʿ��� ��쿡 ���
			PrintWriter pw = new PrintWriter(bw);
			for(int i=total-1;i>=0;i--) {
				temp=scores.get(i);
				pw.printf("%d\n", temp);
				if(score==temp)   //score�� ���� score�� ���ٸ� ����� ��ü���� i��ŭ ������ ��
					rank=total-i; 
			}
			pw.close();  //close�� �������ν� �߻��� �� �ִ� �������� ������. �޸𸮸� ����߱� ������ close����
			bw.close();
		} catch (IOException e) {  //������ ������� �Ȼ���� ���� Ȯ���ϰ� ó����
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics g) {
		if(Game.STATE==Game.GAME) {
			g.setColor(Color.BLACK);
			g.fillRect(30, 860, 150, 70);
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 60));
			g.drawString(String.valueOf(score), 30, 755);  //valueOf �Լ��� ����ؼ� score�� ������������ �� ���� ���ڿ��� ��ȯ��
		}
		
	}
}

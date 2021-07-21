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

public class Score2 {
	
	String path2;  
	File scoreboard2;  //File Ŭ������ ���ؼ� ���ϰ� ���͸��� �ٷ� ������
	private ArrayList<Integer> scores2;  //ArrayList�� �̿��� ����Ʈ ����(�迭 ����)
	public int score2;
	private int temp;
	public int total;
	public int rank2;
	
	Score2(String path2){
		this.path2=path2;
		scores2 = new ArrayList<>();
		score2=0;
		scoreboard2 = new File(path2);  //path�� ������ ��θ� �޾����Ƿ� scoreboard�� ��θ� ���� �� ����
		try { // ���� ���� ��� scores��� ArrayList�� ����
			Scanner scan = new Scanner(scoreboard2);
			while(scan.hasNextInt()) {   //�Է°��� ���ö� ���� while�� ���ΰ� ������� �ʴٰ� �Է°��� ���ͼ� scan.hasNextInt()�� true�� ������ �Ŀ� while�� ���ΰ� �����
				scores2.add(scan.nextInt());
			}
			scan.close();
		} catch (FileNotFoundException e) {   //����¿���ó���ϴ°� IOException, �������� �ʴ� ������ �Է��Ѱ�쿡 ���ܸ� ó����
			e.printStackTrace();  //���� �߻� ����� ȣ�� ��ü��? �־��� �޼����� ������ ���� �޽����� ȭ�鿡 ����Ѵ�.
		}	
	}
	
	public void insertScore() {
		scores2.add(this.score2); // player�� ���� ���� ������ �߰��Ͽ� �������� ����
		Collections.sort(scores2);  //�⺻������ �������� ������ �����ϰ� Collections.reverseOrder(),Collections.reverse(list)�ص� ���������� ����
		total = scores2.size();  //�Էµ� ������ ũ�Ⱑ �������� ���� scores�� ����ǰ� total�� �Էµ� �����̱� ������ size�� �Ǵ� ��
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path2));  //���۸� �̿��� ��� + System.out.print�� �����ϰ� ��밡���� �Լ� + ���� ���� ����� �ʿ��� ��쿡 ���
			PrintWriter pw = new PrintWriter(bw);
			for(int i=total-1;i>=0;i--) {
				temp=scores2.get(i);
				pw.printf("%d\n", temp);
				if(score2==temp)   //score�� ���� score�� ���ٸ� ����� ��ü���� i��ŭ ������ ��
					rank2=total-i; 
			}
			pw.close();  //close�� �������ν� �߻��� �� �ִ� �������� ������. �޸𸮸� ����߱� ������ close����
			bw.close();
		} catch (IOException e) {  //������ ������� �Ȼ���� ���� Ȯ���ϰ� ó����
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics g) {
		if(Game.STATE==Game.GAME2) {
			g.setColor(Color.BLACK);
			g.fillRect(30, 860, 150, 70);
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 60));
			g.drawString(String.valueOf(this.score2), 30, 755);  //valueOf �Լ��� ����ؼ� score�� ������������ �� ���� ���ڿ��� ��ȯ��
		}
		
	}
}

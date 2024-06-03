import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Haksa2 extends JFrame {
	JMenuBar mb=null; //변수 호출
	JMenu m1=null; //학생
	JMenuItem mi1=null; //학생관리
	
	JMenu m2=null; //도서
	JMenuItem mi2=null; //대출현황
	
	JPanel panel=null;
	
	
	public Haksa2() { //생성자 생성
		this.setTitle("학사관리");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mb=new JMenuBar(); //맨꼭대기 메뉴
		m1=new JMenu("학생관리");
		mi1=new JMenuItem("학생정보");  //제일 아래쪽의 메뉴
		m2=new JMenu("도서관리");
		mi2=new JMenuItem("대출정보"
				+ "");
		
		mi1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); // 모든컴포넌트삭제
				panel.revalidate(); // 다시활성화
				panel.repaint(); // 다시그림.paintComponent() 호출.
				panel.add(new Student()); // 학생정보패널을 생성.추가.
				panel.setLayout(null); // 레이아웃은 사용 안함	
				
			}});//이벤트 처리-메뉴바의 학생 관리를 누르면 콘솔창에 기록
		
		mi2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll(); // 모든컴포넌트삭제
				panel.revalidate(); // 다시활성화
				panel.repaint(); // 다시그림. paintComponent() 호출.
				panel.add(new BookRent()); // 대출현환패널을 생성.추가.
				panel.setLayout(null); // 레이아웃은 사용 안함	
				
			}});
		
		m1.add(mi1); //학생 메뉴에 학생관리 메뉴 아이템 추가
		mb.add(m1); //메뉴바에 학생 메뉴 추가
		
		m2.add(mi2); //도서 메뉴에 대출현황 메뉴아이템 추가
		mb.add(m2); //메뉴바에 도서 메뉴 추가
		
		this.setJMenuBar(mb); //프레임에 메뉴바를 설정
		
		panel=new JPanel(); //panel 생성
		this.add(panel); //컨테이너 안만들고 생성_해도 되고 아니어도 됨
		//(패널은 눈에 안보임,기본은 보더 레이아웃)
		
		this.setSize(800,600);
		this.setVisible(true);;
	}

	public static void main(String[] args) {
		new Haksa2(); //생성자 호출
	}

}

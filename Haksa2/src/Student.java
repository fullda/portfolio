import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class Student extends JPanel{
	JLabel lblID=null;
	JTextField tfID=null;	
	JLabel lblName=null;
	JTextField tfName=null;
	JLabel lblDepartment=null;
	JTextField tfDepartment=null;
	
	DefaultTableModel model=null; // 테이블에 들어가는 데이터
	JTable table=null;	
	
	JButton btnInsert=null;
	JButton btnSelect=null;
	JButton btnUpdate=null;
	JButton btnDelete=null;	
	
	JButton btnSearch=null;
	
	Connection con=null;  // 연결객체
	Statement stmt=null;  // sql생성.실행 객체
	ResultSet rs = null;  // select결과를 fetch하는 객체
	String sql=null; // sql문자열
	
	
	
	public Student() {		
		setLayout(new FlowLayout());
		
		lblID=new JLabel("학번");
		add(lblID);
		tfID=new JTextField(13);
		add(tfID);
		
		this.btnSearch=new JButton("search");
		this.btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver"); //oracle driver 로드
					// oracle 연결 enterprise는 xe 대신 orcl 
					con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sqld","1234");
					// statement객체 생성.
					stmt=con.createStatement();
					sql="select * from student where id='"+tfID.getText()+"'";
					rs=stmt.executeQuery(sql); // select한  결과를 rs가 가리킴
					//목록 초기화
					model.setRowCount(0);//행의수를 0으로 설정
					//FETCH. 한 행씩 읽어오기
					while(rs.next()) {
						String[] row=new String[3];
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("department");
						model.addRow(row);
					}	
				}catch(Exception e1) {
					e1.printStackTrace();
				}finally {
					try {
						if(rs!=null) {rs.close();}
						if(stmt!=null) {stmt.close();}
						if(con!=null) {con.close();}
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}				
			}});
		add(btnSearch);		
		
		this.lblName=new JLabel("이름");
		add(this.lblName);
		this.tfName=new JTextField(20);
		add(tfName);
		
		this.lblDepartment=new JLabel("학과");
		add(this.lblDepartment);
		this.tfDepartment=new JTextField(20);
		add(this.tfDepartment);
		
		String[] colName= {"학번","이름","학과"}; // 컬럼명
		this.model=new DefaultTableModel(colName,0);
		this.table=new JTable(model); //테이블과 model 바인딩
		this.table.setPreferredScrollableViewportSize(new Dimension(250,200));
		
		this.table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//클릭한 컴포넌트 구하기
				table=(JTable)e.getComponent();
				//모델 구하기
				model=(DefaultTableModel)table.getModel();
				//선택한 행의 행index,열index로 컬럼값 구하기
				String id=(String)model.getValueAt(table.getSelectedRow(),0); //id
				tfID.setText(id);
				String name=(String)model.getValueAt(table.getSelectedRow(),1); //name
				tfName.setText(name);
				String department=(String)model.getValueAt(table.getSelectedRow(),2);//department
				tfDepartment.setText(department);
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			});
		
		add(new JScrollPane(this.table));//scroll추가
		
		this.btnInsert=new JButton("등록");
		this.btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");//oracle driver 로드
					// oracle xe연결. enterprise는 xe대신 orcl
					con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sqld","1234");
					// statement객체 생성.
					stmt=con.createStatement();
					sql="insert into student(id,name,department) values('"+tfID.getText()+"','"+tfName.getText()+"','"+tfDepartment.getText()+"')";
					stmt.executeUpdate(sql);					
				}catch(Exception e1) {
					e1.printStackTrace();
				}finally {
					try {
						if(rs!=null) {rs.close();}
						if(stmt!=null) {stmt.close();}
						if(con!=null) {con.close();}
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
				
				JOptionPane.showMessageDialog(null, "등록되었습니다");
				
				
			}});
		add(this.btnInsert);
		this.btnSelect=new JButton("목록");
		this.btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");//oracle driver 로드
					// oracle xe연결. enterprise는 xe대신 orcl
					con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sqld","1234");
					// statement객체 생성.
					stmt=con.createStatement();
					sql="select * from student";
					rs=stmt.executeQuery(sql); // select한 결과를 rs가 가리킴	
					//목록초기화
					model.setRowCount(0); //model의 행의 수를 0로 변경
									
					//fetch. 한행씩 읽어오기
					while(rs.next()) {
//						System.out.println(rs.getString("id")+"\t"+rs.getString("name")+"\t"+rs.getString("department"));
						String[] row=new String[3];
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("department");
						model.addRow(row);
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}finally {
					try {
						if(rs!=null) {rs.close();}
						if(stmt!=null) {stmt.close();}
						if(con!=null) {con.close();}
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
			}});
		add(this.btnSelect);
		this.btnUpdate=new JButton("수정");
		this.btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");//oracle driver 로드
					// oracle xe연결. enterprise는 xe대신 orcl
					con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sqld","1234");
					// statement객체 생성.
					stmt=con.createStatement();
					sql="update student set name='"+tfName.getText()+"', department='"+tfDepartment.getText()+"' where id='"+tfID.getText()+"'";
					System.out.println(sql);
					stmt.executeUpdate(sql);					
				}catch(Exception e1) {
					e1.printStackTrace();
				}finally {
					try {
						if(rs!=null) {rs.close();}
						if(stmt!=null) {stmt.close();}
						if(con!=null) {con.close();}
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}});
		
		add(this.btnUpdate);
		this.btnDelete=new JButton("삭제");
		this.btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result=JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?","알림",JOptionPane.YES_NO_OPTION);
				System.out.println(result);
				if(result==JOptionPane.YES_OPTION) {
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");//oracle driver 로드
						// oracle xe연결. enterprise는 xe대신 orcl
						con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sqld","1234");
						// statement객체 생성.
						stmt=con.createStatement();
						sql="delete from student where id='"+tfID.getText()+"'";
						System.out.println(sql);
						stmt.executeUpdate(sql);					
					}catch(Exception e1) {
						e1.printStackTrace();
					}finally {
						try {
							if(rs!=null) {rs.close();}
							if(stmt!=null) {stmt.close();}
							if(con!=null) {con.close();}
						}catch(Exception e2) {
							e2.printStackTrace();
						}
					}
				}
				
			}});
		add(this.btnDelete);		
		
		this.setSize(280, 500);
		this.setVisible(true);		
	}
}


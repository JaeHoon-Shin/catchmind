package sist.ss.catch1;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CatchLayOut extends JFrame implements ActionListener{
	private JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9,jp10;
	private JPanel userImgBox[]=new JPanel[4];
	private JPanel user[]=new JPanel[4];
	private JButton jbt,clear,red,blue,black,green,ready;
	private JTextArea jta;
	private JTextField jtf;
	private JLabel []userId=new JLabel[4];
	private JLabel []userCnt=new JLabel[4];
	private JLabel []userImg= new JLabel[4];
	private ImageIcon img;
	private Socket socket;
	private ObjectInputStream ois=null;
	private ObjectOutputStream oos=null;
	private String ip;
	private String color="black";
	private int port;
	private String id;
	private static int cnt=0;
	private Point point;
	private Pan pan;
	private Vector<Point>vGrim=new Vector<Point>();
	CatchThread c;
	private boolean turn;
	public void intitLabel() {
		
		for(int i=0;i<userId.length;i++) {
			userImg[i]=new JLabel(new ImageIcon("C:\\Img\\"+i+".jpg"));
			userId[i]=new JLabel(); 
		}
		jp3.add(userImg[0]);
		jp4.add(userImg[1]);
		jp9.add(userImg[2]);
		jp10.add(userImg[3]);
		jp3.add(userId[0]);
		jp4.add(userId[1]);
		jp9.add(userId[2]);
		jp10.add(userId[3]);


	}
	public void initFrame() {
		Dimension d=new Dimension(300,300);
		jp1=new JPanel(null);
		jp2=new JPanel(new GridLayout(2,1));
		jp3=new JPanel();
		jp4=new JPanel();
		jp5=new JPanel(null);
		jp6=new JPanel(new BorderLayout());
		jp7=new JPanel();
		jp8=new JPanel(new GridLayout(2,1));
		jp9=new JPanel();
		jp10=new JPanel();
		for(int i=0;i<userImgBox.length;i++) {
			userImgBox[i]=new JPanel();
		}
		

		jp2.add(jp3);
		jp2.add(jp4);
		jp2.setBounds(0, 0, 300, 1000);
		jp1.add("West",jp2);
		jp5.add(pan=new Pan());
		
		pan.setBounds(0, 0, 900, 700);
		pan.setBackground(Color.WHITE);
		jp5.setBounds(300,0,900,1000);
		jp6.setBounds(0,700, 900, 260);
		jp6.add(new JScrollPane(jta=new JTextArea(5,30),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		jta.setEditable(false);
		jp7.add("West",jtf=new JTextField(20));
		jp7.add(jbt=new JButton("입력"));
		jp7.add(black = new JButton("검정"));
		jp7.add(red = new JButton("빨강"));
		jp7.add(blue = new JButton("파랑"));
		jp7.add(green = new JButton("초록"));
		jp7.add(clear = new JButton("지우기"));
		jp7.add(ready = new JButton("시작"));
		jbt.addActionListener(this);
		black.addActionListener(this);
		red.addActionListener(this);
		blue.addActionListener(this);
		green.addActionListener(this);
		clear.addActionListener(this);
		ready.addActionListener(this);		
		jp6.add("South",jp7);
		jp5.add(jp6);
		jp1.add(jp5);
		jp8.setBounds(1200, 0, 300, 1000);
		

		jp8.add(jp9);
		jp8.add(jp10);
		jp1.add(jp8);
		jtf.addActionListener(this);
		this.add(jp1);
	}
	//채팅 및 정답확인
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		if(!turn) {
		if(obj==jtf) {
			if(jtf.getText().length()==0) {
				JOptionPane.showMessageDialog(this, "메세지를 입력하세요.");
				return;
			}
			else { 
			try {
				oos.writeObject("[메세지]"+id+"#"+jtf.getText());
				if(jtf.getText().equals(c.getQuestion())) {
					cnt++;
					oos.writeObject("[정답]");
				}
				jtf.setText("");
				jtf.requestFocus();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
			}
		}
		if(obj==jbt) {
			if(jtf.getText().length()==0) {
				JOptionPane.showMessageDialog(this, "메세지를 입력하세요.");
				return;
			}
			else { 
			try {
				oos.writeObject("[메세지]"+id+"#"+jtf.getText());
				if(jtf.getText().equals(c.getQuestion())) {
					cnt++;
					oos.writeObject("[정답]");
				}
				jtf.setText("");
				jtf.requestFocus();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			}
		}
		}
		//그림판의 펜의 색
		if(turn) {
		if(obj==black) {
			try {
				oos.writeObject("[색깔]#black");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(obj==red) {
			try {
				oos.writeObject("[색깔]#red");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(obj==blue) {
			try {
				oos.writeObject("[색깔]#blue");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(obj==green) {
			try {
				oos.writeObject("[색깔]#green");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(obj==clear) {
			try {
				oos.writeObject("[지우기]");
			} catch (Exception e2) {
				e2.printStackTrace();
				}
			}
	
		}

		if(obj==ready) {
			System.out.println("ready");
			try {
				oos.writeObject("[READY]");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			ready.setEnabled(false);
			}
	}

	public  CatchLayOut(String id) {
		this.id=id;
		initFrame();
		connect();
		intitLabel();
		try {
			oos.writeObject("[ID]#"+id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				try {
					oos.writeObject("[메세지]"+id+"#나가셨습니다!");
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}		
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(10, 10, 1520, 1000);
		this.setVisible(true);
		}
	
	public class Pan extends Canvas  {
		
		public Pan()  {
			
				this.addMouseListener(new MouseAdapter() {	

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					if(turn) { //turn 이 true 일때 마우스가 클릭 된다 즉 false상태에서는 마우스 클릭이 안된다. 출제자일때문 turn 은 true가된다.
					vGrim.add(null);
					vGrim.add(e.getPoint());
					point=e.getPoint();
					try {
						oos.writeObject(vGrim);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					}
				}	
			});
				this.addMouseMotionListener(new MouseMotionAdapter() {

				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					if(turn) {	//turn 이 true 일때 마우스가 클릭 된다 즉 false상태에서는 마우스 클릭이 안된다. 출제자일때문 turn 은 true가된다.
					vGrim.add(e.getPoint());
					repaint();
					try {
						oos.writeObject(vGrim);
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					
				}
				}
			});	
		}
		//그림판
		@Override
		public void paint(Graphics g) {	
			//g.setColor(Color.BLACK);
			if(color.equals("black")) {
				g.setColor(Color.black);
			}
			else if(color.equals("red")) {
				g.setColor(Color.red);	
			}		
			else if(color.equals("blue")) {
				g.setColor(Color.blue);
			}
			else if(color.equals("green")) {
				g.setColor(Color.green);
			}
				for(int i=1;i<vGrim.size();i++) {
					if(vGrim.get(i-1)==null) { //저장된 백터의 0번지의 값이null일경우 excption이 난다 그걸 방지
						continue;
					}
					if(vGrim.get(i)==null) { //저장된 백터의 값이null일경우 excption이 난다 그걸 방지
						continue;
					}
						g.drawLine((int)vGrim.get(i-1).getX(),(int)vGrim.get(i-1).getY(),
								(int)vGrim.get(i).getX(),(int)vGrim.get(i).getY());	
					}
				
					
		}

		@Override
		public void update(Graphics g) {
			paint(g);
		}
		
	}
	//스레드 컨텍
	public void connect() {
		try {
			socket=new Socket("127.0.0.1",5000);
			oos=new ObjectOutputStream(socket.getOutputStream());
			ois=new ObjectInputStream(socket.getInputStream());
			c=new CatchThread(this);
			c.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public JButton getReady() {
		return ready;
	}
	public void setReady(JButton ready) {
		this.ready = ready;
	}
	public JLabel[] getuserId() {
		return userId;
	}
	public void setuserId(JLabel[] userId) {
		this.userId = userId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public boolean isTurn() {
		return turn;
	}
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	public Vector<Point> getvGrim() {
		return vGrim;
	}

	public void setvGrim(Vector<Point> vGrim) {
		this.vGrim = vGrim;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}


	public Pan getPan() {
		return pan;
	}

	public void setPan(Pan pan) {
		this.pan = pan;
	}
	public JButton getJbt() {
		return jbt;
	}

	public void setJbt(JButton jbt) {
		this.jbt = jbt;
	}

	public JTextArea getJta() {
		return jta;
	}

	public void setJta(JTextArea jta) {
		this.jta = jta;
	}

	public JTextField getJtf() {
		return jtf;
	}

	public void setJtf(JTextField jtf) {
		this.jtf = jtf;
	}


	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public JLabel[] getUserCnt() {
		return userCnt;
	}
	public void setUserCnt(JLabel[] userCnt) {
		this.userCnt = userCnt;
	}
}
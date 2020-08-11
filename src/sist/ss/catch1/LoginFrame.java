/*package sist.com.catch1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import test.Brick;

public class LoginFrame extends JFrame implements ActionListener{
	
	private JPanel jp;
	private JButton jbtn;
	private JLabel jId,jPw;
	private JTextField jtf1,jtf2;
	private String id,pw;
	private boolean log;	

	public void initLogin() {
		jp = new JPanel(null);
		jId=new JLabel("ID    :");
		jPw=new JLabel("PW :");
		jtf1=new JTextField();
		jtf2=new JTextField();
		jbtn=new JButton("L");
		jId.setBounds(10, 10, 50, 20);
		jPw.setBounds(10, 30, 50, 20);
		jtf1.setBounds(50, 10, 100, 20);
		jtf2.setBounds(50, 30, 100, 20);
		jbtn.setBounds(150, 10, 50, 30);
		jp.add(jId);
		jp.add(jPw);
		jp.add(jtf1);
		jp.add(jtf2);
		jp.add(jbtn);
		jbtn.addActionListener(this);
		this.add(jp);	
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o.equals(jbtn)) {
			id=jtf1.getText();
			pw=jtf2.getText();
			log=true;
			dispose();
			layOut(log);
		}
	}
	
	public void layOut(boolean log) {
		if(log) {
			CatchLayOut co=new CatchLayOut(id.trim());	
			}
		
	}
	public LoginFrame()  {
		initLogin();
		
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
			
		});
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int)(d.getWidth()/3),(int)(d.getHeight()/3), 230, 100);
		
		this.setVisible(true);
		
	}


	
	public static void main(String[] args) {
		new LoginFrame();
	}
	
}
*/
package sist.ss.catch1;



import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements ActionListener{
	private JPanel jp1;
	private JLabel idLabel,pwLabel;
	private JTextField idField;
	private JPasswordField pwField;
	private JButton loginButton;
	private String id,pw;
	private boolean log;	
	
	private ImageIcon img=new ImageIcon("c:\\img\\CatchLogin.png");

	public LoginFrame()  {
		super("캐치마인드 로그인");
		initFrame();
	
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(d.width / 3+100,d.height / 2 - 350, 400, 705);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initFrame() {
		
		jp1=new JPanel(null) {

			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(img.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
			
		};
		idLabel=new JLabel("ID");
		pwLabel=new JLabel("PW");
		idField=new JTextField();
		pwField=new JPasswordField();
		loginButton= new JButton("Login");
		
		idLabel.setBounds(80, 500, 80, 30);
		pwLabel.setBounds(75, 531, 80, 30);
		idField.setBounds(100, 500, 200, 30);
		pwField.setBounds(100, 531, 200, 30);
		loginButton.setBounds(100, 570, 200, 50);
		
		jp1.add(idLabel);
		jp1.add(pwLabel);
		jp1.add(idField);
		jp1.add(pwField);
		jp1.add(loginButton);
		this.add(jp1);
		loginButton.addActionListener(this);
	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o=e.getSource();
		if(o.equals(loginButton)) {
			id=idField.getText();
			pw=String.valueOf(pwField.getPassword());
			log=true;
			dispose();
			layOut(log);
		}
	}
	public void layOut(boolean log) {
		if(log) {
			CatchLayOut co=new CatchLayOut(id.trim());	
			}
	}
	
	public JPanel getJp1() {
		return jp1;
	}

	public void setJp1(JPanel jp1) {
		this.jp1 = jp1;
	}

	public JLabel getIdLabel() {
		return idLabel;
	}

	public void setIdLabel(JLabel idLabel) {
		this.idLabel = idLabel;
	}

	public JLabel getPwLabel() {
		return pwLabel;
	}

	public void setPwLabel(JLabel pwLabel) {
		this.pwLabel = pwLabel;
	}

	public JTextField getIdField() {
		return idField;
	}

	public void setIdField(JTextField idField) {
		this.idField = idField;
	}

	public JPasswordField getPwField() {
		return pwField;
	}

	public void setPwField(JPasswordField pwField) {
		this.pwField = pwField;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(JButton loginButton) {
		this.loginButton = loginButton;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public static void main(String[] args) {
		new LoginFrame();
	}
	
}

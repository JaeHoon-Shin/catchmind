package sist.ss.catch1;

import java.awt.Color;
import java.awt.Label;
import java.awt.Point;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class CatchThread extends Thread{
	private CatchLayOut catchLayOut;
	String []q;
	String question;
	String []idd=new String[4];
	int []cnt=new int[4];

	public String[] getIdd() {
		return idd;
	}
	public void setIdd(String[] idd) {
		this.idd = idd;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public CatchThread(CatchLayOut catchLayOut) {
		super();
		this.catchLayOut = catchLayOut;
	}
	public CatchThread() {
		super();
	}
	
	public String[] getQ() {
		return q;
	}
	public void setQ(String[] q) {
		this.q = q;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	
	public void run() {
		// TODO Auto-generated method stub
		boolean isStop=false;
		try {
			while(!isStop) {
				Object o=catchLayOut.getOis().readObject();
				if(o instanceof Vector) {
					Vector v=(Vector)o;
					catchLayOut.setvGrim(v);
					catchLayOut.getPan().repaint();
				}
				if(o instanceof ArrayList) {
					int i=0;
					for(User u : (ArrayList<User>)o) {
						idd[i++]=u.getId();	
					}
					for(int j=0;j<idd.length;j++) {
						catchLayOut.getuserId()[j].setText("ID: "+idd[j]);
					}
				}
				if(o instanceof String) {
					String obj=(String)o;
					if(obj.startsWith("[����]")) {
						q=obj.split("#");
						question=q[1];
						catchLayOut.setColor("black");
						
					}
					if(obj.startsWith("[������]")){
						catchLayOut.setTurn(true);

						JOptionPane j=new JOptionPane();
						catchLayOut.getJta().append("-------------------------�������Դϴ�-------------------------\n");
						catchLayOut.getJta().append("------------[���� :"+question+"]------------\n");
						j.showMessageDialog(null, "������ �Դϴ� �׸��� �׷��ּ���. [���� :"+question+"]");
						catchLayOut.setColor("black");
					}
					if(obj.startsWith("[�׸�]")) {
						catchLayOut.setTurn(false);		
					}
					if(obj.startsWith("[�����]")) {				
						catchLayOut.getPan().getGraphics().clearRect(0, 0, 1000, 1000);
						catchLayOut.setvGrim(new Vector<Point>());
					}
					if(obj.startsWith("[����]")) {
						String []color=obj.split("#");
						System.out.println(obj);
						if(color[1].equals("black")) {
							catchLayOut.setColor(color[1]);
							catchLayOut.setvGrim(new Vector<Point>());
						}
						if(color[1].equals("red")) {	
							catchLayOut.setColor(color[1]);
							catchLayOut.setvGrim(new Vector<Point>());

						}
						if(color[1].equals("blue")) {
							catchLayOut.setColor(color[1]);
							catchLayOut.setvGrim(new Vector<Point>());

						}
						if(color[1].equals("green")) {	
							catchLayOut.setColor(color[1]);
							catchLayOut.setvGrim(new Vector<Point>());
	
						}
					}
					
					if(obj.startsWith("[�޼���]")){
						String idd=obj.substring(obj.indexOf("]")+1,obj.indexOf("#"));
						String[]msg=(obj.split("#"));
					if(msg[1].equals("exit")) {
							catchLayOut.getJta().append(idd+"�� ����"+msg[1]+System.getProperty("line.separator"));
							catchLayOut.getJta().setCaretPosition(catchLayOut.getJta().getDocument().getLength());
					}
					
					else {
					catchLayOut.getJta().append(idd+":"+msg[1]+System.getProperty("line.separator"));
					catchLayOut.getJta().setCaretPosition(catchLayOut.getJta().getDocument().getLength());
					if(!(question.equals(null))&&msg[1].equals(question)) {	
						JOptionPane j=new JOptionPane();
						j.showMessageDialog(null, idd+"���� ����!");
						}
					}
					}
					if(obj.startsWith("[��]")) {
						JOptionPane j=new JOptionPane();
						j.showMessageDialog(null, "������ �������ϴ�.");
						}
					
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
	
}

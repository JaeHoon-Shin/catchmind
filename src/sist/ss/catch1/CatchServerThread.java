package sist.ss.catch1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class CatchServerThread extends Thread {

	CatchServer catchServer;
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	String uid="";

	public CatchServerThread(CatchServer catchServer) {
		super();
		this.catchServer = catchServer;
		socket = catchServer.getSocket();
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void brodCast(String message) {
		for (CatchServerThread s : catchServer.getList()) {
			s.sendMessage(message);
		}
	}

	public void sendMessage(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void brodGrim(Vector grim) {
		for (CatchServerThread s : catchServer.getList()) {
			s.sendGrim(grim);
		}
	}

	public void sendGrim(Vector grim) {
		try {
			oos.writeObject(grim);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Question(String q) {
		for (CatchServerThread s : catchServer.getList()) {
			s.sendQuestion(q);
		}
	}

	public void sendQuestion(String q) {
		try {
			oos.writeObject("[����]#" + q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clean(String clean) {
		for (CatchServerThread s : catchServer.getList()) {
			s.sendClean(clean);
		}
	}

	public void sendClean(String clean) {
		try {
			oos.writeObject(clean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public synchronized void brodUser() {
		ArrayList<User>user=new ArrayList<User>();
		for (CatchServerThread s: catchServer.getList()) {
			String id=s.getUid();
			user.add(new User(id));
			}
		for (CatchServerThread s: catchServer.getList()) {
			s.sendUser(user);
			}
	}
	public void sendUser(ArrayList<User> user) {
		try {
			oos.writeObject(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean isStop = false;
		try {	
			String message = null;
			String[] msg = null;
			while (!isStop) {
				Object o = ois.readObject();
				String[] qq = catchServer.getQuestion();
				if (o instanceof Vector) {
					Vector grim = (Vector) o;
					brodGrim(grim);
				}
				
				if (o instanceof String) {
					
					if(((String)o).startsWith("[ID]")) {
						String[] i = ((String) o).split("#");
						uid = i[1];
						brodUser();
					}
					if (((String) o).startsWith("[�޼���]")) {
						message = (String) o;;
						brodCast(message);
					}
					if (((String) o).startsWith("[�����]")) {// [�����]�� ������ ȭ���� �����ش�.
						clean("[�����]");// ȭ���� ����� �޼ҵ�
					}
					if(((String)o).startsWith("[����]")){
						String color=(String)o;
						brodCast(color);		
					}
					/////!!!!!!!!!!!!!!!!!!!!!!!!!!!!�߿�!!!!!!!!!!!!!!!!!!!!!!!!!!!!/////////
					if (((String) o).startsWith("[����]")) {

						clean("[�����]");
						catchServer.setTurnThread(catchServer.getList().get(catchServer.getTurn()));// �����ڸ� �ҷ��´�
						catchServer.getTurnThread().getOos().writeObject("[�׸�]");// ���������� [�׸�]�� ������ turn�� false���ȴ�.
						catchServer.setqNumber(catchServer.getqNumber() + 1); // ������ �迭�ȿ��ִµ� ���� ������ �ε��� ���� +1�� ���ش�.���� ����
																				// ���!
						if (catchServer.getqNumber() < catchServer.getQuestion().length) {// �� ������ ��ȣ�� �����迭�� ũ�⺸�� ������ �ؿ�
																							// ���� ȣ��
							Question(qq[catchServer.getqNumber()]);// �����迭���� ���������� �� Ŭ���̾�Ʈ�� �ѷ��ش�.
							catchServer.setTurn(catchServer.getTurn() + 1);// �������ʸ� �ҷ��´�
							if (catchServer.getTurn() == catchServer.getList().size()) {// �������ʾ����� ���»���� �������̸� �ٽ�
																						// ù��°������� ����
								catchServer.setTurn(0);
							}
							catchServer.setTurnThread(catchServer.getList().get(catchServer.getTurn()));
							catchServer.getTurnThread().getOos().writeObject("[����]#" + qq[catchServer.getqNumber()]);// ������������
																														// ������
																														// ���
							catchServer.getTurnThread().getOos().writeObject("[������]");

						} else if (catchServer.getQuestion().length == catchServer.getqNumber())// ������ �� ������ ��ü������ �ѷ��ش�

							brodCast("[��]");
					}
			if(((String) o).startsWith("[READY]")) {
					catchServer.setReady(catchServer.getReady()+1);
					if(catchServer.getReady()==catchServer.getGameUser() ) {				
						catchServer.setTurnThread(catchServer.getList().get(CatchServer.turn));
						ObjectOutputStream toos=catchServer.getTurnThread().getOos();
						toos.writeObject("[������]");
						}
					}
				}
			}
			catchServer.getList().remove(this);

		} catch (Exception e) {
			e.printStackTrace();
			catchServer.getList().remove(this);
		}
	}


public Socket getSocket() {
	return socket;
}

public void setSocket(Socket socket) {
	this.socket = socket;
}

public ObjectOutputStream getOos() {
	return oos;
}

public void setOos(ObjectOutputStream oos) {
	this.oos = oos;
}

public ObjectInputStream getOis() {
	return ois;
}

public void setOis(ObjectInputStream ois) {
	this.ois = ois;
}


public String getUid() {
	return uid;
}


public void setUid(String uid) {
	this.uid = uid;
}
}

/*package sist.com.catch1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class CatchServerThread extends Thread {

	CatchServer catchServer;
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	String uid="";
	int ucnt=0;
	
	
	public CatchServerThread(CatchServer catchServer) {
		super();
		this.catchServer = catchServer;
		socket = catchServer.getSocket();
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void brodCast(String message) {
		for (CatchServerThread s : catchServer.getList()) {
			s.sendMessage(message);
		}
	}

	public void sendMessage(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void brodGrim(Vector grim) {
		for (CatchServerThread s : catchServer.getList()) {
			s.sendGrim(grim);
		}
	}

	public void sendGrim(Vector grim) {
		try {
			oos.writeObject(grim);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Question(String q) {
		for (CatchServerThread s : catchServer.getList()) {
			s.sendQuestion(q);
		}
	}

	public void sendQuestion(String q) {
		try {
			oos.writeObject("[����]#" + q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clean(String clean) {
		for (CatchServerThread s : catchServer.getList()) {
			s.sendClean(clean);
		}
	}

	public void sendClean(String clean) {
		try {
			oos.writeObject(clean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public synchronized  void brodUser() {
		ArrayList user=new ArrayList<User>();
		System.out.println(catchServer.getList().size()+"��");
		for (CatchServerThread s: catchServer.getList()) {
			String id=s.getUid();
			int cnt=s.getUcnt();
			user.add(new User(id,cnt));
			}
		for (CatchServerThread s: catchServer.getList()) {
			s.sendUser(user);
			}
		System.out.println(user);
		//catchServer.state++;
	}
	public void sendUser(ArrayList<User> user) {
		try {
			oos.writeObject(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean isStop = false;
		try {

			String message = null;
			String[] msg = null;
			//brodUser();
			while (!isStop) {
				Object o = ois.readObject();
				String[] qq = catchServer.getQuestion();
				
				

				if(catchServer.getList().size()>4 && catchServer.state>=3 ) {				
					
					catchServer.setTurnThread(catchServer.getList().get(CatchServer.turn));
					    
						ObjectOutputStream toos=catchServer.getTurnThread().getOos();
						toos.writeObject("[������]");
						
						break;
						}
				
				
				if (o instanceof Vector) {
					Vector grim = (Vector) o;
					brodGrim(grim);
				}
				if (o instanceof String) {
					
					
					if(((String)o).startsWith("[ID]")) {
						System.out.println(o+"Check");
						String[] i = ((String) o).split("#");
						uid = i[1];
						ucnt = Integer.parseInt(i[2]);
						brodUser();
					}
					if (((String) o).startsWith("[�޼���]")) {
						message = (String) o;;
						brodCast(message);
					}
					if (((String) o).startsWith("[�����]")) {// [�����]�� ������ ȭ���� �����ش�.
						clean("[�����]");// ȭ���� ����� �޼ҵ�
					}
					if(((String)o).startsWith("[����]")){
						String color=(String)o;
						brodCast(color);
						
						
					}
					/////!!!!!!!!!!!!!!!!!!!!!!!!!!!!�߿�!!!!!!!!!!!!!!!!!!!!!!!!!!!!/////////
					if (((String) o).startsWith("[����]")) {

						clean("[�����]");
						// brodCast("���� ������ ���߼̽��ϴ�.");
						catchServer.setTurnThread(catchServer.getList().get(catchServer.getTurn()));// �����ڸ� �ҷ��´�
						catchServer.getTurnThread().getOos().writeObject("[�׸�]");// ���������� [�׸�]�� ������ turn�� false���ȴ�.
						catchServer.setqNumber(catchServer.getqNumber() + 1); // ������ �迭�ȿ��ִµ� ���� ������ �ε��� ���� +1�� ���ش�.���� ����
																				// ���!
						if (catchServer.getqNumber() < catchServer.getQuestion().length) {// �� ������ ��ȣ�� �����迭�� ũ�⺸�� ������ �ؿ�
																							// ���� ȣ��
							Question(qq[catchServer.getqNumber()]);// �����迭���� ���������� �����´�.
							catchServer.setTurn(catchServer.getTurn() + 1);// �������ʸ� �ҷ��´�
							if (catchServer.getTurn() == catchServer.getList().size()) {// �������ʾ����� ���»���� �������̸� �ٽ�
																						// ù��°������� ����
								catchServer.setTurn(0);
							}
							catchServer.setTurnThread(catchServer.getList().get(catchServer.getTurn()));
							catchServer.getTurnThread().getOos().writeObject("[����]#" + qq[catchServer.getqNumber()]);// ������������
																														// ������
																														// ���
							catchServer.getTurnThread().getOos().writeObject("[������]");

						} else if (catchServer.getQuestion().length == catchServer.getqNumber())// ������ �� ������ ��ü������ �ѷ��ش�

							brodCast("[��]");
					}

				}

			}
			
			catchServer.getList().remove(this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			brodUser();
			catchServer.getList().remove(this);
		}
	}


public Socket getSocket() {
	return socket;
}

public void setSocket(Socket socket) {
	this.socket = socket;
}

public ObjectOutputStream getOos() {
	return oos;
}

public void setOos(ObjectOutputStream oos) {
	this.oos = oos;
}

public ObjectInputStream getOis() {
	return ois;
}

public void setOis(ObjectInputStream ois) {
	this.ois = ois;
}


public String getUid() {
	return uid;
}


public void setUid(String uid) {
	this.uid = uid;
}


public int getUcnt() {
	return ucnt;
}


public void setUcnt(int ucnt) {
	this.ucnt = ucnt;
}
}*/

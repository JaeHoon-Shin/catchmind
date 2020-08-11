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
			oos.writeObject("[문제]#" + q);
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
					if (((String) o).startsWith("[메세지]")) {
						message = (String) o;;
						brodCast(message);
					}
					if (((String) o).startsWith("[지우기]")) {// [지우기]가 들어오면 화면을 지워준다.
						clean("[지우기]");// 화면을 지우는 메소드
					}
					if(((String)o).startsWith("[색깔]")){
						String color=(String)o;
						brodCast(color);		
					}
					/////!!!!!!!!!!!!!!!!!!!!!!!!!!!!중요!!!!!!!!!!!!!!!!!!!!!!!!!!!!/////////
					if (((String) o).startsWith("[정답]")) {

						clean("[지우기]");
						catchServer.setTurnThread(catchServer.getList().get(catchServer.getTurn()));// 출제자를 불러온다
						catchServer.getTurnThread().getOos().writeObject("[그만]");// 출제자한테 [그만]을 보내면 turn이 false가된다.
						catchServer.setqNumber(catchServer.getqNumber() + 1); // 문제가 배열안에있는데 현재 문제의 인덱스 값의 +1을 해준다.다음 문제
																				// 출력!
						if (catchServer.getqNumber() < catchServer.getQuestion().length) {// 현 문제의 번호가 문제배열의 크기보다 작을때 밑에
																							// 식이 호출
							Question(qq[catchServer.getqNumber()]);// 문제배열에서 다음문제를 각 클라이언트에 뿌려준다.
							catchServer.setTurn(catchServer.getTurn() + 1);// 다음차례를 불러온다
							if (catchServer.getTurn() == catchServer.getList().size()) {// 다음차례없으면 들어온사람의 마지막이면 다시
																						// 첫번째사람한테 간다
								catchServer.setTurn(0);
							}
							catchServer.setTurnThread(catchServer.getList().get(catchServer.getTurn()));
							catchServer.getTurnThread().getOos().writeObject("[문제]#" + qq[catchServer.getqNumber()]);// 다음차례한테
																														// 문제를
																														// 출력
							catchServer.getTurnThread().getOos().writeObject("[출제자]");

						} else if (catchServer.getQuestion().length == catchServer.getqNumber())// 문제가 다 끝나면 전체적으로 뿌려준다

							brodCast("[끝]");
					}
			if(((String) o).startsWith("[READY]")) {
					catchServer.setReady(catchServer.getReady()+1);
					if(catchServer.getReady()==catchServer.getGameUser() ) {				
						catchServer.setTurnThread(catchServer.getList().get(CatchServer.turn));
						ObjectOutputStream toos=catchServer.getTurnThread().getOos();
						toos.writeObject("[출제자]");
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
			oos.writeObject("[문제]#" + q);
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
		System.out.println(catchServer.getList().size()+"명");
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
						toos.writeObject("[출제자]");
						
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
					if (((String) o).startsWith("[메세지]")) {
						message = (String) o;;
						brodCast(message);
					}
					if (((String) o).startsWith("[지우기]")) {// [지우기]가 들어오면 화면을 지워준다.
						clean("[지우기]");// 화면을 지우는 메소드
					}
					if(((String)o).startsWith("[색깔]")){
						String color=(String)o;
						brodCast(color);
						
						
					}
					/////!!!!!!!!!!!!!!!!!!!!!!!!!!!!중요!!!!!!!!!!!!!!!!!!!!!!!!!!!!/////////
					if (((String) o).startsWith("[정답]")) {

						clean("[지우기]");
						// brodCast("님이 정답을 맞추셨습니다.");
						catchServer.setTurnThread(catchServer.getList().get(catchServer.getTurn()));// 출제자를 불러온다
						catchServer.getTurnThread().getOos().writeObject("[그만]");// 출제자한테 [그만]을 보내면 turn이 false가된다.
						catchServer.setqNumber(catchServer.getqNumber() + 1); // 문제가 배열안에있는데 현재 문제의 인덱스 값의 +1을 해준다.다음 문제
																				// 출력!
						if (catchServer.getqNumber() < catchServer.getQuestion().length) {// 현 문제의 번호가 문제배열의 크기보다 작을때 밑에
																							// 식이 호출
							Question(qq[catchServer.getqNumber()]);// 문제배열에서 다음문제를 가져온다.
							catchServer.setTurn(catchServer.getTurn() + 1);// 다음차례를 불러온다
							if (catchServer.getTurn() == catchServer.getList().size()) {// 다음차례없으면 들어온사람의 마지막이면 다시
																						// 첫번째사람한테 간다
								catchServer.setTurn(0);
							}
							catchServer.setTurnThread(catchServer.getList().get(catchServer.getTurn()));
							catchServer.getTurnThread().getOos().writeObject("[문제]#" + qq[catchServer.getqNumber()]);// 다음차례한테
																														// 문제를
																														// 출력
							catchServer.getTurnThread().getOos().writeObject("[출제자]");

						} else if (catchServer.getQuestion().length == catchServer.getqNumber())// 문제가 다 끝나면 전체적으로 뿌려준다

							brodCast("[끝]");
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

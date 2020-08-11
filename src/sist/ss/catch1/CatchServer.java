package sist.ss.catch1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.TRANSACTION_UNAVAILABLE;

public class CatchServer {

	ArrayList<CatchServerThread> list =new ArrayList<CatchServerThread>();
	Socket socket;
	private int qNumber;
	private String []question = {"귓속말","퇴학","피고인","중고생","초등학교","크라잉넛","전사자","진심","공고문","카카오나무","워크샵","해운대"};
	static int turn=0;
	private ObjectOutputStream oos=null;
	private ObjectInputStream ois = null;
	private final static int PORT=5000;
	private CatchServerThread turnThread;
	private final int gameUser=4;
	int ready=0;


	public CatchServer() {
		System.out.println("Server Ready");
		boolean isStop=false;
		
		try {
			ServerSocket serverSocket=new ServerSocket(PORT);
			while(!isStop) {
				if(list.size()<gameUser) {
					
				socket=serverSocket.accept();
				CatchServerThread Thread=new CatchServerThread(this);
				ObjectOutputStream oos=Thread.getOos();
				ObjectInputStream ois = Thread.getOis();
				oos.writeObject("[문제]#"+question[qNumber]);
				list.add(Thread);
				Thread.start();		
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new CatchServer();
	}
	public int getGameUser() {
		return gameUser;
	}


	public int getReady() {
		return ready;
	}

	public void setReady(int ready) {
		this.ready = ready;
	}
	public CatchServerThread getTurnThread() {
		return turnThread;
	}

	public void setTurnThread(CatchServerThread turnThread) {
		this.turnThread = turnThread;
	}

	/*public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}*/

	public int getqNumber() {
		return qNumber;
	}

	public void setqNumber(int qNumber) {
		this.qNumber = qNumber;
	}
	public String[] getQuestion() {
		return question;
	}
	public void setQuestion(String[] question) {
		this.question = question;
	}

	public static int getTurn() {
		return turn;
	}

	public static void setTurn(int turn) {
		CatchServer.turn = turn;
	}

	public ArrayList<CatchServerThread> getList() {
		return list;
	}

	public void setList(ArrayList<CatchServerThread> list) {
		this.list = list;
	}
	public Socket getSocket() {
		return socket;
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
	
}

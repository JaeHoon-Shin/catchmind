package sist.ss.catch1;

import java.io.Serializable;

public class User implements Serializable{

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id;
	}
	public User(String id) {
		super();
		this.id = id;
	}
	
	
}

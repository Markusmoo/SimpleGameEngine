package ca.tonsaker.simplegameengine.test;

import java.io.Serializable;

public class DataTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6854135823097087830L;
	
	public int x;
	public int y;
	public int currentClient;
	
	public DataTest(int x, int y, int clientID) {
		this.x = x;
		this.y = y;
		this.currentClient = clientID;
	}

}

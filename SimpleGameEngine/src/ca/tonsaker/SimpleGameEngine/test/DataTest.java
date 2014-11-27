package ca.tonsaker.SimpleGameEngine.test;

import java.io.Serializable;

public class DataTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6854135823097087830L;
	
	public int x;
	public int y;
	public String string;
	
	public DataTest(int x, int y, String string) {
		this.x = x;
		this.y = y;
		this.string = string;
	}

}

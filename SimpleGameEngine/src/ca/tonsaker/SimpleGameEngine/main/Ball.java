package ca.tonsaker.SimpleGameEngine.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;

public class Ball extends Rectangle implements EngineFrame{
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	public int direction;
	public int speed;
	
	public Ball(int x, int y, int width, int height, int dir, int spd){
		super(x,y,width,height);
		direction = dir;
		speed = spd;
	}
	
	@Override
	public void init() {}

	@Override
	public void update(){
		switch(direction){
			case(UP):
				this.setLocation(x, y-speed);
				break;
			case(RIGHT):
				this.setLocation(x+speed, y);
				break;
			case(DOWN):
				this.setLocation(x, y+speed);
				break;
			case(LEFT):
				this.setLocation(x-speed, y);
				break;
		}
	}
	
	@Override
	public void draw(Graphics g){
		g.drawOval(x, y, width, height);
	}
}

package ca.tonsaker.SimpleGameEngine.engine.graphics;

import java.awt.Point;
import java.awt.Polygon;

public class Triangle extends Polygon{
	
	int x1;
	int y1;
	
	int x2;
	int y2;
	
	int x3;
	int y3;
	
	public Triangle(int x1, int y1, int x2, int y2, int x3, int y3){
		super(new int[]{x1,x2,x3}, new int[]{y1,y2,y3}, 3);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
	}
	
	public Triangle(Point p1, Point p2, Point p3){
		super(new int[]{p1.x,p2.x,p3.x}, new int[]{p1.y,p2.y,p3.y}, 3);
		this.x1 = p1.x;
		this.y1 = p1.y;
		this.x2 = p2.x;
		this.y2 = p2.y;
		this.x3 = p3.x;
		this.y3 = p3.y;
	}
}

package ca.tonsaker.SimpleGameEngine.engine.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import moo.Moo;

import org.imgscalr.Scalr;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay.DebugInfo;


//TODO Add missing texture, scales accordingly. update paint methods to graphics2D, fix moveTo, implement missing methods
public class Sprite implements EngineFrame{
	
	private class MoveInfo{

		private double targetX, targetY;
		private double deltaX, deltaY;
		private double direction;
		private double speed;
		private boolean ai;
		
		public MoveInfo(double targetX, double targetY, double speed){
			this.targetX = targetX;
			this.targetY = targetY;
			this.speed = speed;
			this.deltaX = targetX - x;
			this.deltaY = targetY - y;
			calculateDirection();
			ai = false;
		}
		
		public double getTargetX() {
			return targetX;
		}

		public double getTargetY() {
			return targetY;
		}

		public double getDirection() {
			return direction;
		}

		public double getSpeed() {
			return speed;
		}

		public boolean isAi() {
			return ai;
		}

		private void calculateDirection(){
			this.direction = Math.atan2(deltaX, deltaY);
		}
	}
	
	public final int NORTH = 90;
	public final int EAST = 0;
	public final int SOUTH = 270;
	public final int WEST = 190;
	
	double x, y;
	int width, height;
	BufferedImage spriteImage;
	boolean spriteMoving = false;
	
	Queue<MoveInfo> moveTo = new LinkedList<MoveInfo>(); //Create a moveTo Class
	
	public Sprite(int x, int y, int width, int height, String file){
		this.setPosition(x, y);
		this.setImage(file);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public Sprite(int x, int y, String file){
		this.setPosition(x, y);
		this.setImage(file);
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
	}
	
	public Sprite(int x, int y, BufferedImage img){
		this.setPosition(x, y);
		this.setImage(img);
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
	}
	
	public Sprite(String file){
		this.setPosition(0, 0);
		this.setImage(file);
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
	}
	
	public Sprite(BufferedImage img){
		this.setPosition(0, 0);
		this.setImage(img);
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
	}

	@Override
	public void init() {
		debra = new DebugInfo("",Color.red,6); 
		DebugOverlay.addDebug(debra);//TODO DEBUG
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(spriteImage, (int) x, (int) y, null);
	}

	@Override
	public void update() {
		moveToUpdate();
	}
	
	DebugInfo debra; //TODO DEBUG

	private MoveInfo moveInfo;
	
	//TODO Change string puller accordingly to moveToAI
	private void moveToUpdate(){
		if(!spriteMoving && !moveTo.isEmpty()){
			if(!moveTo.peek().isAi()){
				moveInfo = moveTo.poll();
				spriteMoving = true;
			}
		}else if(spriteMoving){
			if((int) x != (int) moveInfo.getTargetX() && (int) y != moveInfo.getTargetY()){
				double nextY = y + (moveInfo.getSpeed() * Math.cos(moveInfo.getDirection()));
				double nextX = x + (moveInfo.getSpeed() * Math.sin(moveInfo.getDirection()));
				System.out.println(x+" "+moveInfo.getTargetX()+" "+moveInfo.getSpeed() * Math.sin(moveInfo.getDirection()));
				setPosition(nextX, nextY);
			}else{
				spriteMoving = false;
			}
		}
	}
	
	public void moveTo(int x, int y, float speed){
		moveTo.add(new MoveInfo(x,y,speed));
	}
	
	public void moveTo(Point point, float speed){
		moveTo(point.x,point.y,speed);
	}
	
	public void moveTo(Point[] points, float speed){
		for(Point p : points){
			moveTo(p.x,p.y,speed);
		}
	}
	
	public void moveToAI(int x, int y, Rectangle[] avoidBox){
		//TODO Method stub
	}
	
	/**
	 * Move a certain amount of pixels in 360 degrees.
	 * 
	 * @param degree X <= 360 Degrees
	 * @param speed Amount of pixels.
	 */
	public void move(int degree, float speed){
		y += speed * Math.cos(Math.toRadians(degree));
		x += speed * Math.sin(Math.toRadians(degree));
	}
	
	public void move(double radian, float speed){
		y += speed * Math.cos(radian);
		x += speed * Math.sin(radian);
	}
	
	public void moveAmount(int x, int y){
		this.x+=x;
		this.y+=y;
	}
	
	/**
	 * 
	 * 
	 * @param widthPercent
	 * @param heightPercent
	 */
	public void scale(double widthPercent, double heightPercent){
		this.width*=widthPercent;
		this.height*=heightPercent;
		spriteImage = Scalr.resize(spriteImage, Scalr.Method.BALANCED, this.width, this.height);
	}
	
	/**
	 * 
	 * 
	 * @param width
	 * @param height
	 */
	public void resize(int width, int height){
		this.width = width;
		this.height = height;
		spriteImage = Scalr.resize(spriteImage, Scalr.Method.BALANCED, width, height);
	}
	
	public Point getPosition(){
		return new Point((int) x, (int) y);
	}
	
	public int getX(){
		return (int) x;
	}
	
	public int getY(){
		return (int) y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public BufferedImage getImage(){
		return spriteImage;
	}
	
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Point point){
		setPosition(point.x,point.y);
	}
	
	public void setX(int x){
		setPosition((int) x, (int) this.y);
	}
	
	public void setY(int y){
		setPosition((int) this.x, (int) y);
	}
	
	public void setWidth(int width){
		this.resize(width, this.height);
	}

	public void setHeight(int height){
		this.resize(this.width, height);
	}
	
	public void setImage(String filePath){
		try {
			this.spriteImage = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setImage(BufferedImage image){
		this.spriteImage = image;
	}
	
	public String toString(){
		return "Sprite(X:"+x+" Y:"+y+")";
	}
}

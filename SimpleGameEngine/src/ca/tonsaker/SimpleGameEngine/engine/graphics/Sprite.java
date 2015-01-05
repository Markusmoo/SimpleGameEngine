package ca.tonsaker.SimpleGameEngine.engine.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;

//TODO JavaDocs, touch up
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

		public void calculateDirection(){
			this.deltaX = targetX - x;
			this.deltaY = targetY - y;
			this.direction = Math.atan2(deltaX, deltaY);
		}
	}
	
	public final int NORTH = 90;
	public final int EAST = 0;
	public final int SOUTH = 270;
	public final int WEST = 190;
	
	public double x, y;
	protected int width, height;
	protected BufferedImage spriteImage;
	protected boolean spriteMoving = false;
	
	protected Queue<MoveInfo> moveTo = new LinkedList<MoveInfo>(); //Create a moveTo Class
	
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
		
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(spriteImage, (int) x, (int) y, null);
	}

	@Override
	public void update() {
		moveToUpdate();
	}
	
	public Point getNextMove(){
		Point next = new Point();
		if(moveInfo != null){
			next.setLocation(moveInfo.getTargetX(), moveInfo.getTargetY());
			return next;
		}else{
			return null;
		}
	}
	
	public Point[] getQueuedMoves(){
		Point[] points = new Point[moveTo.size()];
		int idx = 0;
		for(MoveInfo m : moveTo){
			points[idx] = new Point();
			points[idx].setLocation(m.getTargetX(), m.getTargetY());
			idx++;
		}
		return points;
	}
	
	protected MoveInfo moveInfo;
	
	protected void moveToUpdate(){
		if(!spriteMoving && !moveTo.isEmpty()){
			if(!moveTo.peek().isAi()){
				moveInfo = moveTo.poll();
				spriteMoving = true;
			}
		}else if(spriteMoving && moveInfo != null){
			if(Math.round(x) != Math.round(moveInfo.getTargetX()) || Math.round(y) != Math.round(moveInfo.getTargetY())){	
				moveInfo.calculateDirection();
				double nextY = y + (moveInfo.getSpeed() * Math.cos(moveInfo.getDirection()));
				double nextX = x + (moveInfo.getSpeed() * Math.sin(moveInfo.getDirection()));
				setPosition(nextX, nextY);
			}else{
				this.setPosition(moveInfo.getTargetX(), moveInfo.getTargetY());
				spriteMoving = false;
				moveInfo = null;
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

package ca.tonsaker.simplegameengine.engine.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

//TODO JavaDocs, animations/sprite sheet
public class Sprite{

	private class MoveInfo{

		private double targetX, targetY;
		private double deltaX, deltaY;
		private double direction;
		private double speed;
		private Point point;
		
		public MoveInfo(double targetX, double targetY, double speed){
			point = new Point();
			point.setLocation(targetX, targetY);
			this.targetX = targetX;
			this.targetY = targetY;
			this.speed = speed;
			calculateDirection();
		}
		
		public Point getLocation(){
			return point;
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
	
	public double speedMod = 1.0;
	
	public double x, y;
	protected int width, height;
	protected BufferedImage spriteImage;
	protected boolean spriteMoving = false;
	
	protected Rectangle spriteBounds;
	
	protected Queue<MoveInfo> moveTo = new LinkedList<MoveInfo>();
	
	public Sprite(int x, int y, int width, int height, String file){
		spriteBounds = new Rectangle(x,y,width,height);
		this.setPosition(x, y);
		this.setImage(file);
		this.resize(width, height);
	}
	
	public Sprite(int x, int y, int width, int height, BufferedImage preClickedImage) {
		spriteBounds = new Rectangle(x,y,width,height);
		this.setPosition(x, y);
		this.setImage(preClickedImage);
		this.resize(width, height);
	}
	
	public Sprite(int x, int y, String file){
		spriteBounds = new Rectangle(x,y,0,0);
		this.setPosition(x, y);
		this.setImage(file);
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
	}
	
	public Sprite(int x, int y, BufferedImage img){
		spriteBounds = new Rectangle(x,y,0,0);
		this.setPosition(x, y);
		this.setImage(img);
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
	}
	
	public Sprite(String file){
		spriteBounds = new Rectangle();
		this.setPosition(0, 0);
		this.setImage(file);
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
	}
	
	public Sprite(BufferedImage img){
		spriteBounds = new Rectangle();
		this.setPosition(0, 0);
		this.setImage(img);
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
	}
	
	public Sprite(){
		spriteBounds = new Rectangle();
		this.setPosition(0, 0);
		this.setImage(new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB));
		this.setWidth(spriteImage.getWidth());
		this.setHeight(spriteImage.getHeight());
		
	}

	public void render(Graphics2D g) {
		g.drawImage(spriteImage, (int) x, (int) y, null);
	}

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
			points[idx] = m.getLocation();
			idx++;
		}
		return points;
	}
	
	public boolean removeQueuedMove(Point p){
		for(MoveInfo m : moveTo){
			if(p.equals(m.getLocation())){
				
				return true;
			}
		}
		return false;
	}
	
	protected MoveInfo moveInfo;
	protected double directionMoving;
	
	protected void moveToUpdate(){
		if(!spriteMoving && !moveTo.isEmpty()){
			moveInfo = moveTo.poll();
			spriteMoving = true;
		}else if(spriteMoving && moveInfo != null){
			if(Math.round(x) != Math.round(moveInfo.getTargetX()) || Math.round(y) != Math.round(moveInfo.getTargetY())){	
				moveInfo.calculateDirection();
				double nextX = x + ((moveInfo.getSpeed()*speedMod) * Math.sin(moveInfo.getDirection()));
				double nextY = y + ((moveInfo.getSpeed()*speedMod) * Math.cos(moveInfo.getDirection()));
				if( (nextX-x > 0 && nextX > moveInfo.getTargetX()) || (nextX-x < 0 && nextX < moveInfo.getTargetX()) ){
					nextX = moveInfo.getTargetX();
				}
				if( (nextY-y > 0 && nextY > moveInfo.getTargetY()) || (nextY-y < 0 && nextY < moveInfo.getTargetY()) ){
					nextY = moveInfo.getTargetY();
				}
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
		y += (speed*speedMod) * Math.cos(Math.toRadians(degree));
		x += (speed*speedMod) * Math.sin(Math.toRadians(degree));
		spriteBounds.setLocation((int) x, (int) y);
	}
	
	public void move(double radian, float speed){
		y += (speed*speedMod) * Math.cos(radian);
		x += (speed*speedMod) * Math.sin(radian);
		spriteBounds.setLocation((int) x, (int) y);
	}
	
	public void moveAmount(int x, int y){
		this.x+=(x*speedMod);
		this.y+=(y*speedMod);
		spriteBounds.setLocation((int) x, (int) y);
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
		spriteImage = Scalr.resize(spriteImage, Scalr.Mode.FIT_EXACT, this.width, this.height);
		spriteBounds.setBounds((int) x, (int) y, (int) width, (int) height);
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
		spriteImage = Scalr.resize(spriteImage, Scalr.Mode.FIT_EXACT, width, height);
		spriteBounds.setBounds((int) x, (int) y, (int) width, (int) height);
	}
	
	public boolean contains(Sprite sprite){
		return spriteBounds.contains(sprite.getBounds());
	}
	
	public boolean contains(Rectangle rectangle){
		return spriteBounds.contains(rectangle);
	}
	
	public boolean contains(Point point){
		return spriteBounds.contains(point);
	}
	
	public boolean contains(int x, int y){
		return spriteBounds.contains(x,y);
	}
	
	public boolean contains(int x, int y, int width, int height){
		return spriteBounds.contains(x,y,width,height);
	}
	
	public boolean intersects(Sprite sprite){
		return spriteBounds.intersects(sprite.getBounds());
	}
	
	public boolean intersects(Rectangle rectangle){
		return spriteBounds.intersects(rectangle);
	}
	
	public boolean intersectsLine(int x1, int y1, int x2, int y2){
		return spriteBounds.intersectsLine(x1, y1, x2, y2);
	}
	
	public boolean intersects(int x, int y, int width, int height){
		return spriteBounds.intersects(x,y,width,height);
	}
	
	public void setSpeedModifier(double speed){
		speedMod = speed;
	}
	
	public double getSpeedModifier(){
		return speedMod;
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
	
	public Rectangle getBounds(){
		return spriteBounds;
	}
	
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
		spriteBounds.setLocation((int) x, (int) y);
	}
	
	public void setPosition(Point point){
		setPosition(point.x,point.y);
	}
	
	public void setX(int x){
		setPosition(x, (int) this.y);
	}
	
	public void setY(int y){
		setPosition((int) this.x, y);
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
			System.err.println("Failed to open: "+filePath);
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

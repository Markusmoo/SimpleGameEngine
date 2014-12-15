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

import org.imgscalr.Scalr;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay.DebugInfo;


//TODO Add missing texture, scales accordingly. update paint methods to graphics2D, fix moveTo, implement missing methods
public class Sprite implements EngineFrame{
	
	int x, y;
	int width, height;
	BufferedImage spriteImage;
	boolean spriteMoving = false;
	
	Queue<String> moveTo = new LinkedList<String>();
	
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
		// TODO Auto-generated method stub
		debra = new DebugInfo("",Color.red,6);
		DebugOverlay.addDebug(debra);//TODO DEBUG
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(spriteImage, x, y, null);
	}

	@Override
	public void update() {
		moveToUpdate();
		debra.setDebugText("Moving:"+spriteMoving+" TargetX:"+moveToTargetX+" TargetY:"+moveToTargetY+" CurrentX:"+moveToCurrentX+" CurrentY:"+moveToCurrentY+" Xspeed:"+moveToXSpeed+" Yspeed:"+moveToYSpeed);
	}
	
	DebugInfo debra;; //TODO DEBUG
	
	private float moveToTargetX = 0;
	private float moveToTargetY = 0;
	private float moveToCurrentX = 0.0f;
	private float moveToCurrentY = 0.0f;
	private float moveToXSpeed = 0.0f;
	private float moveToYSpeed = 0.0f;
	
	//TODO Change string puller accordingly to moveToAI
	private void moveToUpdate(){
		if(!spriteMoving && !moveTo.isEmpty()){
			String info = moveTo.poll();
			System.out.println("\nSprite "+this.toString()+" received moveTo command("+info+").");
			moveToTargetX = Integer.parseInt(info.substring(0, info.indexOf(':')));
			moveToTargetY = Integer.parseInt(info.substring(info.indexOf(':')+1, info.lastIndexOf(':')));
			spriteMoving = true;
			float speed = Float.parseFloat(info.substring(info.lastIndexOf(':')+1));
			
			System.out.println("TargetX:"+moveToTargetX+" TargetY:"+moveToTargetY+" SpeedMultiplier:"+speed); //TODO DEBUG
			
			if(moveToTargetX > moveToTargetY){
				moveToCurrentX = x;
				moveToCurrentY = y;
				moveToXSpeed = (moveToTargetX/moveToTargetY)*speed;
				moveToYSpeed = speed;
				System.out.println("x>y xSpeed:"+moveToXSpeed+" ySpeed:"+moveToYSpeed);
			}else{
				moveToCurrentX = x;
				moveToCurrentY = y;
				moveToXSpeed = speed;
				moveToYSpeed = (moveToTargetY/moveToTargetX)*speed;
				System.out.println("y>x xSpeed:"+moveToXSpeed+" ySpeed:"+moveToYSpeed);
			}
		}else if(spriteMoving){
			if(moveToCurrentX != moveToTargetX){
				if(moveToTargetX>moveToCurrentX){
					moveToCurrentX += moveToXSpeed;
				}else{
					moveToCurrentX -= moveToXSpeed;
				}
				this.setX(Math.round(moveToCurrentX));
			}
			
			if(this.moveToCurrentY != moveToTargetY){
				if(moveToTargetY>moveToCurrentY){
					moveToCurrentY += moveToYSpeed;
				}else{
					moveToCurrentY -= moveToYSpeed;
				}
				this.setY(Math.round(moveToCurrentY));
			}
			
			//System.out.println(moveToTargetX-moveToCurrentX<moveToXSpeed);
			
			if(moveToCurrentY == moveToTargetY && moveToCurrentX == moveToTargetX){
				spriteMoving = false;
			}
		}
	}
	
	public void moveTo(int x, int y, float speed){
		moveTo.add(x+":"+y+":"+speed);
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
	 * @deprecated Broken Method
	 * @param degree X <= 360 Degrees
	 * @param speed Amount of pixels.
	 */ //TODO Finish
	public void move(double degree, float speed){
		if(degree < 45.0 && degree > 0){
			y+=speed;
			degree = Math.toRadians(degree);
			x+=Math.round(1/Math.tan(degree));
		}else if(degree == 45.0){
			x+=speed;
			y+=speed;
		}else if(degree > 45.0 && degree < 90.0){
			
		}
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
		return new Point(x, y);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
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
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Point point){
		setPosition(point.x,point.y);
	}
	
	public void setX(int x){
		setPosition(x, this.y);
	}
	
	public void setY(int y){
		setPosition(this.x, y);
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

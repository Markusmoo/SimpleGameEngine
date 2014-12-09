package ca.tonsaker.SimpleGameEngine.engine.graphics;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;


//TODO Add missing texture, scales accordingly
public class Sprite implements EngineFrame{
	
	int x, y;
	int width, height;
	BufferedImage spriteImage;
	
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
		
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(spriteImage, x, y, null);
	}

	@Override
	public void update() {
		moveToUpdate();
	}
	
	private int moveToX = 0;
	private int moveToY = 0;
	private int currentMoveToXSpeed = 0;
	private int currentMoveToYSpeed = 0;
	private float moveToSpeed = 0.0f;
	private boolean moveToReady = true;
	private void moveToUpdate(){
		if(!moveTo.isEmpty()){
			if(moveToReady){
				String info = moveTo.remove();
				moveToX = Integer.parseInt(info.substring(0, info.indexOf(':')));
				moveToY = Integer.parseInt(info.substring(info.indexOf(':')+1, info.lastIndexOf(':')));
				moveToSpeed = Float.parseFloat(info.substring(info.lastIndexOf(':')+1));
				moveToReady = false;
			}
			
			if(this.x != moveToX){
				currentMoveToXSpeed += moveToSpeed;
				if(currentMoveToXSpeed >= 1.0f){
					this.moveAmount(1, 0);
					currentMoveToXSpeed -= 1.0f;
				}
			}
			if(this.y != moveToY){
				currentMoveToYSpeed += moveToSpeed;
				if(currentMoveToYSpeed >= 1.0f){
					this.moveAmount(0, 1);
					currentMoveToYSpeed -= 1.0f;
				}
			}
			if(this.y == moveToY && this.x == moveToX){
				moveToReady = true;
			}
		}
	}
	
	public void moveTo(int x, int y, float speed){
		moveTo.add(x+":"+y+":"+speed);
	}
	
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
}

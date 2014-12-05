package ca.tonsaker.SimpleGameEngine.engine.graphics;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;

public class Sprite implements EngineFrame{
	
	int x, y;
	int width, height;
	BufferedImage image;
	
	public Sprite(int x, int y, int width, int height, String file){
		this.setPosition(x, y);
		this.setImage(file);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public Sprite(int x, int y, String file){
		this.setPosition(x, y);
		this.setImage(file);
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
	}
	
	public Sprite(String file){
		this.setPosition(0, 0);
		this.setImage(file);
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
		//g.drawImage(image, x, y, width, height, null);
		g.fillOval(x,y,30,30); //TODO debug
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void move(double degree, int speed){
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
	 * @deprecated
	 * 
	 * @param widthPercent
	 * @param heightPercent
	 */
	public void scale(double widthPercent, double heightPercent){
		//TODO
	}
	
	/**
	 * @deprecated
	 * 
	 * @param width
	 * @param height
	 */
	public void scale(int width, int height){
		//TODO
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
		return image;
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
		if(image != null){
			//TODO Scale image
		}else{
			this.width = width;
		}
	}

	public void setHeight(int height){
		if(image != null){
			//TODO Scale image
		}else{
			this.height = height;
		}
	}
	
	public void setImage(String file){
		FileInputStream inputStream = null;
		try{
			inputStream = new FileInputStream(file);
			this.image = ImageIO.read(inputStream);
		} catch(Exception e) {
			e.printStackTrace();
	   	}finally{
	    	try {
	    		if(inputStream != null) inputStream.close();
	    	}catch(Exception e) {
	        	e.printStackTrace();
	        }
	    }
	}
}

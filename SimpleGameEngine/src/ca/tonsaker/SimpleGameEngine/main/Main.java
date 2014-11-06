package ca.tonsaker.SimpleGameEngine.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.InputHandler;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends GameEngine implements EngineFrame{
	
	public static final String title = "Color Grid";
	
	public Main(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public static void main(String[] args){
		Main main = new Main(100,100,800,600); //Creates the JFrame at X,Y, and then sets the WIDTH and HEIGHT
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets the default close operation to erase all memory and close the Program
		main.setResizable(false); //Disallows the parent JFrame to be resized
		main.setTitle(title); //Changes the title of the parent JFrame
		main.setFPS(60); //The FPS (at the moment) will affect how fast something will move across the screen as well
		main.run(); //Starts the update and drawing loop at the set FPS
		System.exit(0); //If loop stops then the program will exit with status 0
	}
	
	public int xPos, yPos;
	public int red, green, blue;
	
	@Override
	public void init(){
		super.init(); //Always call super.init() first!
		
	}
	
	@Override
	public void update() {
		Point mouseP = this.getMousePosition();
		if(mouseP != null){
			xPos = mouseP.x;
			yPos = mouseP.y;
			red = (int) (xPos/(this.getWidth()/255.0));
			double xSq = Math.pow(xPos, 2.0);
			double ySq = Math.pow(yPos, 2.0);
			double widthSq = Math.pow(this.getWidth(), 2.0);
			double heightSq = Math.pow(this.getHeight(), 2.0);
			double diagonalMouse = Math.sqrt(xSq + ySq);
			double diagonalWindow = Math.sqrt(widthSq + heightSq);
			green = (int) (diagonalMouse/(diagonalWindow/255.0));
			blue = (int) (yPos/(this.getHeight()/255.0));
			this.setTitle(title+"X:"+xPos+" Y:"+yPos+"   R:"+red+" G:"+green+" B:"+blue);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g); //Always call super.draw(g) first!
		g.setColor(new Color(red,120,blue));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

}

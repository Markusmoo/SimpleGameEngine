package ca.tonsaker.SimpleGameEngine.main;

import java.awt.Graphics;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends GameEngine implements EngineFrame{
	
	public Main(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	public static void main(String[] args){
		Main main = new Main(100,100,800,600); //Creates the JFrame at X,Y, and then sets the WIDTH and HEIGHT
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets the default close operation to erase all memory and close the Program
		main.setResizable(false); //Disallows the parent JFrame to be resized
		main.setTitle("SIMPLE TITLE"); //Changes the title of the parent JFrame
		main.setFPS(60); //The FPS (at the moment) will affect how fast something will move across the screen as well
		main.run(); //Starts the update and drawing loop at the set FPS
		System.exit(0); //If loop stops then the program will exit with status 0
	}
	
	@Override
	public void init(){
		super.init(); //Always call super.init() first!
		
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g); //Always call super.draw(g) first!
		
	}

}

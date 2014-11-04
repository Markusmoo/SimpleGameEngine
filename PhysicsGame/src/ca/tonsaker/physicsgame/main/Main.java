package ca.tonsaker.physicsgame.main;

import java.awt.Graphics;

import ca.tonsaker.physicsgame.engine.EngineFrame;
import ca.tonsaker.physicsgame.engine.GameEngine;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends GameEngine implements EngineFrame{
	
	public Main(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	public static void main(String[] args){
		Main main = new Main(100,100,800,600);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setResizable(true);
		main.setTitle("SIMPLE TITLE");
		main.setFPS(60); //The FPS (at the moment) will affect how fast something will move across the screen as well.
		main.run();
		System.exit(0);
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g); //Always call super.draw(g) first!
		
	}

}

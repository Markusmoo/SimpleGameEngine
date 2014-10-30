package ca.tonsaker.physicsgame.main;

import java.awt.Graphics;

import ca.tonsaker.physicsgame.engine.EngineFrame;
import ca.tonsaker.physicsgame.engine.GameEngine;

public class Main extends GameEngine implements EngineFrame{

	public Main(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public static void main(String[] args){
		Main main = new Main(100,100,500,600);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);
		main.setResizable(false);
		main.run();
		System.exit(0);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics g) {
		
	}

}

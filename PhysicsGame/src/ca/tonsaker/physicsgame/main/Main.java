package ca.tonsaker.physicsgame.main;

import java.awt.Graphics;

import ca.tonsaker.physicsgame.engine.EngineFrame;
import ca.tonsaker.physicsgame.engine.GameEngine;

public class Main extends GameEngine implements EngineFrame{

	public Main(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public static void main(String[] args){
		Main main = new Main(100,100,800,600);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);
		main.setResizable(false);
		main.setTitle("Physics Game");
		main.run();
		System.exit(0);
	}
	
	@Override
	public void update() {
		y1++;
		y2++;
	}

	int y1 = 0;
	int y2 = 0;
	
	
	@Override
	public void draw(Graphics g) {
		g.drawLine(0, y1, WIDTH, y2);
		super.draw(g);
	}

}

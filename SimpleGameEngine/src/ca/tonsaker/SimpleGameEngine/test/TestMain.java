package ca.tonsaker.SimpleGameEngine.test;

import java.awt.Color;
import java.awt.Graphics;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.graphics.Sprite;
import ca.tonsaker.SimpleGameEngine.engine.graphics.Triangle;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay;

import javax.swing.JFrame;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Client;

@SuppressWarnings("serial")
public class TestMain extends GameEngine implements EngineFrame{
	
	DebugOverlay debug;
	Client client;
	Server server;
	
	public TestMain(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	public static void main(String[] args){
		TestMain main = new TestMain(0,0,800,600); //Creates the JFrame at X,Y, and then sets the WIDTH and HEIGHT
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets the default close operation to erase all memory and close the Program
		main.setResizable(true); //Disallows the parent JFrame to be resized
		main.setTitle("SAMPLE TITLE"); //Changes the title of the parent JFrame
		main.setFPS(60); //The FPS (Frames per second)
		main.setUPS(100); //The UPS (Updates per second)
		main.run(); //Starts the update and drawing loop at the set FPS
	}
	
	@Override
	public void init(){
		super.init(); //Always call super.init() first!
		debug = new DebugOverlay(this);
		sprite = new Sprite(100, 100, "res/TestPic.png");
		sprite.moveTo(400, 400, 1.0f);
		sprite.moveTo(600, 300, 0.5f);
	}
	
	Sprite sprite;
	
	@Override
	public void update() {
		//double degree = 22.5;
		debug.update();
		sprite.update();
		//sprite.move(degree, 1); //TODO debug
		//this.setTitle("x+="+Math.round(1/Math.tan(Math.toRadians(degree))));  //Math.round(1*Math.atan(11.25))));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); //Always call super.draw(g) first!
		sprite.paint(g);
		debug.debugText(g, "!This is some sample debug text", Color.red, 1);
		debug.debugText(g, "!This is some sample debug text", Color.orange, 2);
	}

}
package ca.tonsaker.SimpleGameEngine.test;

import java.awt.Color;
import java.awt.Graphics2D;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.graphics.Sprite;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay.DebugInfo;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TestMain extends GameEngine implements EngineFrame{
	
	DebugOverlay debug;
	
	//TODO Fix NullError from graphic rendering and fix sprite.moveTo not queuing and acting weird
	
	public TestMain(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	public static void main(String[] args){
		TestMain main = new TestMain(0,0,800,600); //Creates the JFrame at X,Y, and then sets the WIDTH and HEIGHT
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets the default close operation to erase all memory and close the Program
		main.setResizable(true); //Disallows the parent JFrame to be resized
		main.setTitle("SAMPLE TITLE"); //Changes the title of the parent JFrame
		main.setFPS(60); //The FPS (Frames per second)
		main.setUPS(60); //The UPS (Updates per second)
		main.run(); //Starts the update and drawing loop at the set FPS
	}
	
	DebugInfo d1;
	
	@Override
	public void init(){
		super.init(); //Always call super.init() first!
		sprite = new Sprite(100, 100, "res/TestPic.png");
		sprite.init();
		sprite.moveTo(400, 400, 1.0f);
		sprite.moveTo(600, 300, 1.0f);
		d1 = new DebugInfo("Sprite x:"+sprite.getX()+" Sprite y:"+sprite.getY(), Color.red, 0);
		DebugOverlay.addDebug(d1);
	}
	
	Sprite sprite;
	
	@Override
	public void update() {
		super.update();
		d1.setDebugText("Sprite x:"+sprite.getX()+"Sprite y:"+sprite.getY());
		//double degree = 22.5;
		sprite.update();
		//sprite.move(degree, 1); //TODO debug
		//this.setTitle("x+="+Math.round(1/Math.tan(Math.toRadians(degree))));  //Math.round(1*Math.atan(11.25))));
	}

	@Override
	public void render(Graphics2D g) {
		sprite.render(g);
		g.drawRoundRect(350, 200, 200, 100,	20, 40);
	}

}
package ca.tonsaker.SimpleGameEngine.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.InputHandler;


public class DebugOverlay implements EngineFrame{
	
	Main panel;
	InputHandler input;
	public boolean showing = false;
	
	public boolean hasDebugged = false;

	public DebugOverlay(Main panel) {
		this.panel = panel;
		this.input = new InputHandler(panel.getFrame());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
		if(showing){
			Color org = g.getColor();
			g.setColor(Color.white);
			g.setColor(Color.green);
			g.drawString("DodgeBall Test Game by Markus Tonsaker", 10, 20);
			g.setColor(Color.blue);
			g.drawString("Mouse X Coordinate: "+panel.xPos, 10, 35);
			g.drawString("Mouse Y Coordinate: "+panel.yPos, 10, 50);
			g.setColor(Color.white);
			g.drawString("Background Colour: ", 10, 65);
			g.setColor(Color.red);
			g.drawString("R: "+panel.red, 125, 65);
			g.setColor(Color.green);
			g.drawString("G: "+panel.green, 165, 65);
			g.setColor(Color.blue);
			g.drawString("B: "+panel.blue, 205, 65);
			g.setColor(Color.orange);
			g.drawString("Number of balls on screen: "+panel.numBalls+"/"+panel.MAX_BALLS, 10, 80);
			g.setColor(Color.pink);
			g.drawString("Collision Testing = "+panel.collisionTesting+" (Press F5)", 10, 95);
			g.setColor(org);
		}
	}

	private boolean canToggleCollision = true;
	private boolean canToggleControlInvert = true;
	@Override
	public void update() {
		if(input.isKeyDown(KeyEvent.VK_F3)){
			showing = true;
		}else{
			showing = false;
		}
		if(input.isKeyDown(KeyEvent.VK_F5) && canToggleCollision){
			panel.collisionTesting = !panel.collisionTesting;
			canToggleCollision = false;
			hasDebugged = true;
		}else if(!input.isKeyDown(KeyEvent.VK_F5)){
			canToggleCollision = true;
		}
		if(input.isKeyDown(KeyEvent.VK_F6) && canToggleControlInvert){
			panel.controlInvert = !panel.controlInvert;
			canToggleControlInvert = false;
		}else if(!input.isKeyDown(KeyEvent.VK_F6)){
			canToggleControlInvert = true;
		}
	}

}

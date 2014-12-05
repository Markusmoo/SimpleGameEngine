package ca.tonsaker.SimpleGameEngine.engine.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.InputHandler;


public class DebugOverlay{
	
	GameEngine panel;
	InputHandler input;
	boolean showing = false;
	boolean toggle = true;

	public DebugOverlay(GameEngine panel) {
		this.panel = panel;
		this.input = new InputHandler(panel.getFrame());
	}
	
	public void setDebugScreenToggable(boolean on){
		toggle = on;
	}
	
	public boolean isDebugScreenToggable(){
		return toggle;
	}

	public void debugText(Graphics g, String text, Color col, int index) {
		if(index <= 0) index = 1;
		if(showing){
			Color org = g.getColor();
			g.setColor(Color.blue);
			g.drawString("Game created with SimpleGameEngine by Markus Tonsaker", 15, 20);
			g.setColor(org);
			if(col != null) g.setColor(col);
			g.drawString(text, 15, (17*index)+20);
			g.setColor(org);
		}
	}

	private boolean canToggle = false; 
	
	public void update() {
		if(!toggle){
			if(input.isKeyDown(KeyEvent.VK_F3)){
				showing = true;
			}else{
				showing = false;
			}
		}else{
			if(input.isKeyDown(KeyEvent.VK_F3) && canToggle){
				showing = !showing;
				canToggle = false;
			}else if(!input.isKeyDown(KeyEvent.VK_F3)){
				canToggle = true;
			}
		}
	}
}

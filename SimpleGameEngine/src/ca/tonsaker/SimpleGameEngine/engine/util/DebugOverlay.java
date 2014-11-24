package ca.tonsaker.SimpleGameEngine.engine.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.InputHandler;


public class DebugOverlay implements EngineFrame{
	
	GameEngine panel;
	InputHandler input;
	boolean showing = false;
	boolean toggle = true;

	public DebugOverlay(GameEngine panel) {
		this.panel = panel;
		this.input = new InputHandler(panel.getFrame());
	}

	@Override
	public void init() {}
	
	public void setDebugScreenToggable(boolean on){
		toggle = on;
	}
	
	public boolean isDebugScreenToggable(){
		return toggle;
	}

	@Override
	public void paint(Graphics g) {
		if(showing){
			Color org = g.getColor();
			
			g.setColor(org);
		}
	}

	private boolean canToggle = false; 
	
	@Override
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

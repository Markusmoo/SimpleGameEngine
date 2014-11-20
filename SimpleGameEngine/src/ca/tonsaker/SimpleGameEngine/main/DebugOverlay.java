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

	public DebugOverlay(Main panel) {
		this.panel = panel;
		this.input = new InputHandler(panel);
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
			g.drawString("THIS IS DEBUG TEXT", 10, 10);
			g.setColor(org);
		}
	}

	private boolean canToggle = true;
	@Override
	public void update() {
		if(canToggle && input.isKeyDown(KeyEvent.VK_F3)){
			showing = !showing;
			canToggle = false;
		}else{
			canToggle = true;
		}
	}

}

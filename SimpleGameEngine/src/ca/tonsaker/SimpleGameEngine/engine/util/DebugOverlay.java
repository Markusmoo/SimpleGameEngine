package ca.tonsaker.SimpleGameEngine.engine.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.InputHandler;


public class DebugOverlay implements EngineFrame{
	
	public class DebugInfo{
		
		private String debugText;
		private Color colour; 
		private int index;
		
		public DebugInfo(String text, Color col, int idx){
			if(idx <= 0) idx = 1;
			debugText = text;
			colour = col;
			index = idx;
		}
	}
	
	protected static ArrayList<DebugInfo> infoList = new ArrayList<DebugInfo>();
	
	protected static GameEngine panel;
	protected static InputHandler input;
	protected static boolean showing = false;
	protected static boolean toggle = true;
	private static boolean canToggle = false; 

	public DebugOverlay(GameEngine panel) {
		DebugOverlay.panel = panel;
		DebugOverlay.input = new InputHandler(panel.getFrame());
	}
	
	public static void setDebugScreenToggable(boolean on){
		toggle = on;
	}
	
	public static boolean isDebugScreenToggable(){
		return toggle;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
		if(showing){
			Color org = g.getColor();
			g.setColor(Color.blue);
			g.drawString("Game created with SimpleGameEngine by Markus Tonsaker", 15, 20);
			g.setColor(org);
			for(DebugInfo d : (DebugInfo[]) infoList.toArray()){
				if(d.colour != null) g.setColor(d.colour);
				g.drawString(d.debugText, 15, (17*d.index)+20);
			}
			g.setColor(org);
		}
	}

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

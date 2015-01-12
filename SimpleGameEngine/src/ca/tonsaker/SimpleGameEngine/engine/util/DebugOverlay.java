package ca.tonsaker.SimpleGameEngine.engine.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.InputHandler;

//TODO Allow change of debug key (F3), add FPS counter
public class DebugOverlay implements EngineFrame{
	
	public static class DebugInfo{

		private String debugText;
		private Color colour; 
		private int index;
		
		public DebugInfo(String text, Color col, int index){
			this.setIndex(index);
			this.debugText = text;
			this.colour = col;
		}
		
		private DebugInfo(){
			debugText = "Game created with SimpleGameEngine by Markus Tonsaker";
			index = 0;
			colour = Color.blue;
		}
		
		public String getDebugText() {
			return debugText;
		}

		public void setDebugText(String debugText) {
			this.debugText = debugText;
		}

		public Color getColour() {
			return colour;
		}

		public void setColour(Color colour) {
			this.colour = colour;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			if(index <= 0){
				index = 1;
				throw new IllegalArgumentException("Cannot use DebugOverlay index "+index+" as it is reserved.  Setting to index 1.");
			}
			this.index = index;
		}
		
	}
	
	protected static ArrayList<DebugInfo> infoList = new ArrayList<DebugInfo>();
	
	protected static InputHandler input;
	protected static boolean showing = false;
	protected static boolean toggle = true;
	private static boolean canToggle = false; 

	public DebugOverlay(InputHandler in){
		DebugOverlay.input = in;
	}
	
	public static void setDebugScreenToggable(boolean on){
		toggle = on;
	}
	
	public static boolean isDebugScreenToggable(){
		return toggle;
	}
	
	public static void addDebug(DebugInfo debug){
		infoList.add(debug);
	}

	public static boolean debugging(){
		return showing;
	}

	@Override
	public void init() {
		DebugOverlay.addDebug(new DebugInfo());
	}

	@Override
	public void render(Graphics2D g) {
		if(showing){
			Color org = g.getColor();
			for(DebugInfo d : infoList){
				if(d != null){
					if(d.colour != null) g.setColor(d.colour);
					if(d.index > 0){
						g.drawString(d.debugText, 15, (17*d.index)+20);
					}else{
						g.drawString(d.debugText, 15, 20);
					}
				}
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

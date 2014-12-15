package ca.tonsaker.SimpleGameEngine.engine;

import java.awt.Graphics2D;


public interface EngineFrame {
	
	public void init();
	public void render(Graphics2D g);
	public void update();
}

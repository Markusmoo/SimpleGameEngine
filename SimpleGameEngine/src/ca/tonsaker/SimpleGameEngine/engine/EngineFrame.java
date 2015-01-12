package ca.tonsaker.SimpleGameEngine.engine;

import java.awt.Graphics2D;


public interface EngineFrame {
	
	public abstract void init();
	public abstract void render(Graphics2D g);
	public abstract void update();
}

package ca.tonsaker.SimpleGameEngine.engine;

import java.awt.Graphics;

public interface EngineFrame {
	
	public void init();
	public void paint(Graphics g);
	public void update();
}
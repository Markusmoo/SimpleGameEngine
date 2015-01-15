package ca.tonsaker.simplegameengine.engine;

import java.awt.Graphics2D;


public interface EngineFrame {
	
	void init();
	void render(Graphics2D g);
	void update();
}

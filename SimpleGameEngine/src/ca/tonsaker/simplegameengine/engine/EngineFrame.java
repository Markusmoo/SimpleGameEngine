package ca.tonsaker.simplegameengine.engine;

import java.awt.Graphics2D;

public interface EngineFrame {
	void init();
	void update();
	void render(Graphics2D g);
}
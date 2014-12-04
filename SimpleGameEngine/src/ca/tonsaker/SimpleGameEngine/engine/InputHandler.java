package ca.tonsaker.SimpleGameEngine.engine;

import java.awt.Component; 
import java.awt.Point;
import java.awt.event.*; 

/** 
 * Makes handling input a lot simpler 
 */ 
public class InputHandler implements KeyListener{
	
	private boolean[] keys;
	Component comp;
	
	public Point getMousePos(){
		return comp.getMousePosition();
	}
	
	public double getMouseX(){
		return comp.getMousePosition().getX();
	}
	
	public double getMouseY(){
		return comp.getMousePosition().getY();
	}
	
    /** 
     * Assigns the newly created InputHandler to a Component 
     * @param c Component to get input from 
     */ 
    public InputHandler(Component c){ 
    	keys = new boolean[256];
    	c.addKeyListener(this);
    	comp = c;
    } 
    
    /** 
     * Checks whether a specific key is down 
     * @param keyCode The key to check 
     * @return Whether the key is pressed or not 
     */ 
    public boolean isKeyDown(int keyCode){ 
        if (keyCode > 0 && keyCode < 256){ 
        	return keys[keyCode]; 
        }
        return false; 
    } 
    
    /** 
     * Called when a key is pressed while the component is focused 
     * @param e KeyEvent sent by the component 
     */ 
    public void keyPressed(KeyEvent e){ 
        if (e.getKeyCode() > 0 && e.getKeyCode() < 256){ 
        	keys[e.getKeyCode()] = true; 
        } 
    } 

    /** 
     * Called when a key is released while the component is focused 
     * @param e KeyEvent sent by the component 
     */ 
    public void keyReleased(KeyEvent e){ 
        if (e.getKeyCode() > 0 && e.getKeyCode() < 256){ 
        	keys[e.getKeyCode()] = false; 
        } 
    } 

    /** 
     * Not used 
     */ 
    public void keyTyped(KeyEvent e){} 
} 
package ca.tonsaker.SimpleGameEngine.engine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A simple to use Game Engine that runs in a {@link JPanel} inside of a {@link JFrame}
 * 
 * @author Markus Tonsaker
 * @version 1.0
 * @since 2014/11/03
 */
@SuppressWarnings("serial")
public abstract class GameEngine extends JPanel implements EngineFrame {        
        private int fps;
        private int ups;
        
        private Timer fpsTimer;
        private Timer upsTimer;
        
        protected JFrame frame; 
        public InputHandler input; 
        
        public GameEngine(int x, int y, int width, int height){
    		fpsTimer = new Timer(0, new ActionListener(){

    			@Override
    			public void actionPerformed(ActionEvent e) {
    				repaint();
    			}
    			
    		});
    		
    		upsTimer = new Timer(0, new ActionListener(){

    			@Override
    			public void actionPerformed(ActionEvent e) {
    				update();
    			}
    			
    		});
        	setFPS(60); //Sets default FPS
        	setUPS(30); //Sets default UPS
        	frame = new JFrame(); //Creates a JFrame that will contain this (JPanel)
        	frame.setLocation(x, y); //Sets the location of the JFrame
        	frame.setSize(width, height); //Sets the size of the JFrame
        	frame.add(this); //Adds this object (JPanel) to the JFrame
        }
	 
	        
    /** 
	 * Starts FPS (Frames per Second) and UPS (Updates per something) loop.
	 */ 
	public void run(){ 
        init();
		
		fpsTimer.start();
		upsTimer.start();
	}
	
	/** 
	 * Stops FPS (Frames per Second) and UPS (Updates per something) loop.
	 */ 
	public void stop(){
		fpsTimer.stop();
		upsTimer.stop();
	}
	
	/**
	 * Determines if the FPS (Frames per Second) and UPS (Updates per something) loops are running.
	 * 
	 * @return True if game engine is running.
	 */
	public boolean isRunning(){
		return fpsTimer.isRunning();
	}
	
	/**
	 * Sets the target updates per second for this game engine.
	 * 
	 * @param ups - The number of frames per second this game can run.
	 */
	public void setUPS(int ups){
		this.ups = ups;
		upsTimer.setDelay(1000 / this.ups);
	}
	
	/**
	 * Gets the target updates per second for this game engine.
	 * 
	 * @return the UPS value
	 */
	public int getUPS(){
		return this.ups;
	}
	
	/**
	 * Sets the target frames per second to display for this game engine.
	 * 
	 * @param fps - The number of frames per second this game can run.
	 */
	public void setFPS(int fps){
		this.fps = fps;
		fpsTimer.setDelay(1000 / this.fps);
	}
	
	/**
	 * Gets the target frames per second to display for this game engine.
	 * 
	 * @return the FPS value
	 */
	public int getFPS(){
		return this.fps;
	}
	
	/**
	 * Gets the parent JFrame component that contains this JPanel
	 * 
	 * @return the parent JFrame component
	 */
	public JFrame getFrame(){
		return this.frame;
	}
	
	/**
	 * Specifies to the JFrame how to handle a close request.
	 * 
	 * @param operation - The JFrame close operation value
	 * @see JFrame
	 */
	public void setDefaultCloseOperation(int operation){
		frame.setDefaultCloseOperation(operation);
		
	}
	
	/**
	 * Gets the default close operation.
	 * 
	 * @return the default close operation
	 */
	public int getDefaultCloseOperation(){
		return frame.getDefaultCloseOperation();
	}
	
	/**
	 * Sets the size of the parent JFrame with a {@link Dimension} object
	 * 
	 * @param d - a dimension
	 */
	public void setFrameSize(Dimension d){
		frame.setSize(d);
	}
	

	/**
	 * Sets the size of the parent JFrame
	 * 
	 * @param width - The width of the parent JFrame
	 * @param height - The height of the parent JFrame
	 */
	public void setFrameSize(int width, int height){
		frame.setSize(width, height);
	}
	
	/**
	 * Gets the size of the parent JFrame in a {@link Dimension} object
	 * 
	 * @return a dimension object
	 */
	public Dimension getFrameSize(){
		return frame.getSize();
	}
	
	/**
	 * Sets the title of the parent JFrame.
	 * 
	 * @param title - The title of the JFrame
	 */
	public void setTitle(String title){
		frame.setTitle(title);
	}
	
	/**
	 * Gets the title of the parent JFrame.
	 * 
	 * @return the title of the parent JFrame
	 */
	public String getTitle(){
		return frame.getTitle();
	}
	
	/**
	 * Sets whether or not the parent JFrame can be resized.
	 * 
	 * @param resizable - If true allow the parent JFrame to be resized
	 */
	public void setResizable(boolean resizable){
		frame.setResizable(resizable);
	}
	
	/**
	 * Whether or not the parent JFrame can be resized.
	 * 
	 * @return true if parent JFrame can be resized
	 */
	public boolean isResizable(){
		return frame.isResizable();
	}
	
	/** 
	 * This method will set up everything need for the game to run.
	 */ 
	public void init(){ 
		frame.setVisible(true);
		setVisible(true);
		input = new InputHandler(this);
		setDoubleBuffered(true);
	}
	
	/** 
	 * This method will check for input, move things 
	 * around and check for win conditions, etc.
	 */ 
	public void update(){
		
	}
	
	/** 
	 * This method will draw everything.  It is a good idea
	 * to call super.draw(g) in subclasses with this method overridden, first.
	 * 
	 * @param g - The graphics device to paint to.
	 */ 
	public void paint(Graphics g){
		super.paint(g);
	}
} 
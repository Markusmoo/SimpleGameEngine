package ca.tonsaker.physicsgame.engine;

import java.awt.*; 
import java.awt.image.BufferedImage; 

import javax.swing.JFrame; 
import javax.swing.JPanel;

/** 
 * Main class for the game 
 */ 
public abstract class GameEngine extends JFrame {        
        private boolean isRunning = true; 
        private int fps;
        public static int WIDTH;
        public static int HEIGHT;
        
        protected JPanel panel; 
        public InputHandler input; 
        
        public GameEngine(int x, int y, int width, int height){
        	fps = 30;
        	this.setSize(width, height);
        	this.setLocation(x, y);
        	WIDTH = width;
        	HEIGHT = height;
        	panel = new JPanel();
        	this.add(panel);
        }
	 
	        
	        /** 
	 * This method starts the game and runs it in a loop 
	 */ 
	public void run(){ 
        init(); 
        
        while(isRunning){ 
            long time = System.currentTimeMillis(); 
            
            update(); 
            draw(panel.getGraphics()); 
            
            //  delay for each frame  -   time it took for one frame 
            time = (1000 / fps) - (System.currentTimeMillis() - time); 
            
            if (time > 0){ 
                try{ 
                	Thread.sleep(time); 
                }catch(Exception e){} 
            } 
        } 
        
        setVisible(false); 
	}
	
	public void setFPS(int fps){
		this.fps = fps;
	}
	
	public void setSize(Dimension d){
		WIDTH = (int) d.getHeight();
		HEIGHT = (int) d.getWidth();
		super.setSize(d);
	}
	
	public void setSize(int width, int height){
		WIDTH = width;
		HEIGHT = height;
		super.setSize(width, height);
	}
	
	/** 
	 * This method will set up everything need for the game to run 
	 */ 
	public void init(){ 
		setVisible(true);
		input = new InputHandler(this);
		panel.setDoubleBuffered(true);
	}
	
	/** 
	 * This method will check for input, move things 
	 * around and check for win conditions, etc 
	 */ 
	public void update(){
		
	}
	
	/** 
	 * This method will draw everything 
	 */ 
	public void draw(Graphics g){
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
} 
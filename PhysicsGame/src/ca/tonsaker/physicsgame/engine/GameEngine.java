package ca.tonsaker.physicsgame.engine;

import java.awt.*; 
import java.awt.image.BufferedImage; 
import javax.swing.JFrame; 

/** 
 * Main class for the game 
 */ 
public abstract class GameEngine extends JFrame 
{        
        boolean isRunning = true; 
        int fps = 30; 
        int WIDTH;
        int HEIGHT;
        
        BufferedImage backBuffer; 
        Insets insets; 
        InputHandler input; 
        
        public GameEngine(int x, int y, int width, int height){
        	this.setSize(width, height);
        	this.setLocation(x, y);
        }
 
        
        /** 
         * This method starts the game and runs it in a loop 
         */ 
        public void run(){ 
                init(); 
                
                while(isRunning){ 
                        long time = System.currentTimeMillis(); 
                        
                        update(); 
                        draw(getGraphics()); 
                        
                        //  delay for each frame  -   time it took for one frame 
                        time = (1000 / fps) - (System.currentTimeMillis() - time); 
                        
                        if (time > 0) 
                        { 
                                try 
                                { 
                                        Thread.sleep(time); 
                                } 
                                catch(Exception e){} 
                        } 
                } 
                
                setVisible(false); 
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
                
                insets = getInsets(); 
                setSize(insets.left + WIDTH + insets.right, 
                                insets.top + HEIGHT + insets.bottom); 
                
                backBuffer = new BufferedImage(WIDTH, WIDTH, BufferedImage.TYPE_INT_RGB); 
                input = new InputHandler(this); 
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
        	
        }
} 
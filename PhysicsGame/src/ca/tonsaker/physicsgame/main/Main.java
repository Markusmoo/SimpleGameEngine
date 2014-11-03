package ca.tonsaker.physicsgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import ca.tonsaker.physicsgame.engine.EngineFrame;
import ca.tonsaker.physicsgame.engine.GameEngine;
import javax.swing.JCheckBox;

public class Main extends GameEngine implements EngineFrame{

	Random r;
	
	public Main(int x, int y, int width, int height) {
		super(x, y, width, height);
		r = new Random();
	}

	public static void main(String[] args){
		Main main = new Main(0,0,1920,1080);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);
		main.setResizable(true);
		main.setTitle("Physics Game");
		main.setFPS(60);
		main.run();
		System.exit(0);
	}
	
	int a = 0;
	
	@Override
	public void update() {

		if(y1 < Main.HEIGHT && y2 < Main.HEIGHT){
			y1++;
			y2++;
		
			if(a >= 10){
				bCol+=6;
				a=0;
			}else{
				a++;
			}
			
			if(rCol < 255 && xUp){
				rCol+=interval;
			}
			if(rCol > 0 && !xUp){
				rCol-=interval;
			}
			if(rCol >= 255) xUp = false;
			if(rCol <= 0) xUp = true;
			
			if(gCol < 255 && yUp){
				gCol+=interval;
			}
			if(gCol > 0 && !yUp){
				gCol-=interval;
			}
			if(gCol >= 255) yUp = false;
			if(gCol <= 0) yUp = true;
			
			System.out.println("R:"+rCol+" G:"+gCol+" B:"+bCol);
		}
	}
	
	boolean xUp = true;
	boolean yUp = false;

	int y1 = 0;
	int y2 = 0;
	
	int interval = 10;
	int rCol = 0;
	int gCol = 255;
	int bCol = 0;
	
	private void safetyColour(){
		if(rCol < 0){
			rCol = 0;
		}
		if(rCol > 255){
			rCol = 255;
		}
		
		if(gCol < 0){
			gCol = 0;
		}
		if(gCol > 255){
			gCol = 255;
		}
		
		if(bCol < 0){
			bCol = 0;
		}
		if(bCol > 255){
			bCol = 255;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		safetyColour();
		g.setColor(new Color(r.nextInt(rCol+1), r.nextInt(gCol+1), r.nextInt(bCol+1)));
		g.drawLine(0, y1, WIDTH, y2);
		super.draw(g);
	}

}

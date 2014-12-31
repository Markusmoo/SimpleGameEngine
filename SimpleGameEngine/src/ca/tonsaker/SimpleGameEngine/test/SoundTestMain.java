package ca.tonsaker.SimpleGameEngine.test;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.util.audio.SimpleMIDI;
import ca.tonsaker.SimpleGameEngine.engine.util.audio.SimpleMIDIPlayer;

public class SoundTestMain extends GameEngine implements EngineFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3039731260994504257L;
	
	//TODO Fix NullError from graphic rendering and fix sprite.moveTo not queuing and acting weird
	
	public SoundTestMain(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	public static void main(String[] args){
		SoundTestMain main = new SoundTestMain(0,0,640,480); //Creates the JFrame at X,Y, and then sets the WIDTH and HEIGHT
		main.centerWindow(); //Centers window on screen
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets the default close operation to erase all memory and close the Program
		main.setResizable(false); //Disallows the parent JFrame to be resized
		main.setTitle("SAMPLE TITLE"); //Changes the title of the parent JFrame
		main.setFPS(60); //The FPS (Frames per second)
		main.setUPS(100); //The UPS (Updates per second)
		main.run(); //Starts the update and drawing loop at the set FPS
	}
	
	boolean midiClicked = false;
	SimpleMIDIPlayer midiPlayer;
	
	Rectangle midi;
	Rectangle audio;
	
	Rectangle start;
	Rectangle pause;
	Rectangle stop;
	
	@Override
	public void init(){
		super.init(); //Always call super.init() first!
		midi = new Rectangle(this.getWidth()/2-110, this.getHeight()/2-50, 100, 100);
		audio = new Rectangle(this.getWidth()/2+10, this.getHeight()/2-50, 100, 100);
		
		try {
			midiPlayer = new SimpleMIDIPlayer();
		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		}
		
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				clicked(getMousePosition());
			}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
	}
	
	@Override
	public void update() {
		super.update();
		
	}

	@Override
	public void render(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D text;
		if(midi != null && audio != null){
			g.draw(midi);
			g.draw(audio);
			text = fm.getStringBounds("MIDI", g);
			g.drawString("MIDI", (int)(midi.x+midi.getWidth()/2-text.getWidth()/2), (int)(midi.y+midi.getHeight()/2+text.getHeight()/2));
			text = fm.getStringBounds("Audio", g);
			g.drawString("Audio", (int)(audio.x+audio.getWidth()/2-text.getWidth()/2), (int)(audio.y+audio.getHeight()/2+text.getHeight()/2));
		}else if(start != null && pause != null && stop != null){
			g.draw(start);
			g.draw(pause);
			g.draw(stop);
			text = fm.getStringBounds("Play", g);
			g.drawString("Play", (int)(start.x+start.getWidth()/2-text.getWidth()/2), (int)(start.y+start.getHeight()/2+text.getHeight()/2));
			text = fm.getStringBounds("Play", g);
			g.drawString("Stop", (int)(stop.x+stop.getWidth()/2-text.getWidth()/2), (int)(stop.y+stop.getHeight()/2+text.getHeight()/2));
			text = fm.getStringBounds("Pause", g);
			g.drawString("Pause", (int)(pause.x+pause.getWidth()/2-text.getWidth()/2), (int)(pause.y+pause.getHeight()/2+text.getHeight()/2));
		}
	}
	
	public void clicked(Point p){
		if(midi != null && audio != null){
			if(midi.contains(p)){
				midiClicked = true;
				midi = null;
				audio = null;
				start = new Rectangle(this.getWidth()/2-50, this.getHeight()/2-50, 100, 100);
				pause = new Rectangle((int)(start.getX()-start.getWidth()-10), this.getHeight()/2-50, 100, 100);
				stop = new Rectangle((int)(start.getX()+start.getWidth()+10), this.getHeight()/2-50, 100, 100);
				try {
					SimpleMIDI mid = new SimpleMIDI("soundTest", "res/HIP_HOP.mid", false, 127, 2);
					midiPlayer = new SimpleMIDIPlayer();
					SimpleMIDIPlayer.loadSimpleMIDI(mid);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}else if(audio.contains(p)){
				midiClicked = false;
				midi = null;
				audio = null;
				start = new Rectangle(this.getWidth()/2-50, this.getHeight()/2-50, 100, 100);
				pause = new Rectangle((int)(start.getX()-start.getWidth()-10), this.getHeight()/2-50, 100, 100);
				stop = new Rectangle((int)(start.getX()+start.getWidth()+10), this.getHeight()/2-50, 100, 100);
			}
		}else if(start != null && pause != null && stop != null){
			if(start.contains(p)){
				if(!midiPlayer.isPlaying()){
					try {
						midiPlayer.play("soundTest");
					} catch (InvalidMidiDataException e) {
						e.printStackTrace();
					}
				}else{
					midiPlayer.play();
				}
			}else if(pause.contains(p)){
				midiPlayer.pause();
			}else if(stop.contains(p)){
				midiPlayer.stop();
			}
		}
	}

}
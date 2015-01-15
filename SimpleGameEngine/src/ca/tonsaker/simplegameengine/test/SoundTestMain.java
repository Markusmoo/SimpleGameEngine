package ca.tonsaker.simplegameengine.test;

import java.awt.Color;
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
import javax.swing.JOptionPane;

import ca.tonsaker.simplegameengine.engine.EngineFrame;
import ca.tonsaker.simplegameengine.engine.GameEngine;
import ca.tonsaker.simplegameengine.engine.util.DebugOverlay;
import ca.tonsaker.simplegameengine.engine.util.DebugOverlay.DebugInfo;
import ca.tonsaker.simplegameengine.engine.util.audio.SimpleMidi;
import ca.tonsaker.simplegameengine.engine.util.audio.SimpleMidiPlayer;

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
		main.setTitle("SimpleMidiPlayer/SimpleAudioPlayer Example"); //Changes the title of the parent JFrame
		main.setFPS(60); //The FPS (Frames per second)
		main.setUPS(100); //The UPS (Updates per second)
		main.run(); //Starts the update and drawing loop at the set FPS
	}
	
	DebugInfo debugTrackPos = new DebugInfo("",Color.black,1);
	
	int trackPosY = this.getHeight()/2+this.getHeight()/4+40; //Y coordinate of seek bar/seek indicator/end and begin points
	int trackLineLength = (this.getWidth()/4+this.getWidth()/2 - this.getWidth()/4); //Length of the line, not sure if necessary
	
	boolean midiClicked = false;
	SimpleMidiPlayer midiPlayer;
	
	Rectangle midi;
	Rectangle audio;
	
	Rectangle volUp;
	Rectangle volDown;
	Rectangle tempoUp;
	Rectangle tempoDown;
	
	Rectangle seekBar;
	
	Rectangle load;
	
	Rectangle start;
	Rectangle pause;
	Rectangle stop;
	
	SimpleMidi simpleMidi;
	
	@Override
	public void init(){
		midi = new Rectangle(this.getWidth()/2-110, this.getHeight()/2-50, 100, 100);
		audio = new Rectangle(this.getWidth()/2+10, this.getHeight()/2-50, 100, 100);
		
		DebugOverlay.addDebug(debugTrackPos);
		
		try {
			midiPlayer = new SimpleMidiPlayer();
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
				clicked(getMousePosition(), e.getButton());
			}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
	}
	
	@Override
	public void update() {}

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
		
		}else if(start != null && pause != null && stop != null && volUp != null && volDown != null && load != null){
			
			g.draw(start);
			g.draw(pause);
			g.draw(stop);
			g.draw(volUp);
			g.draw(volDown);
			g.draw(load);
			
			if(tempoUp != null && tempoDown != null){
				g.draw(tempoUp);
				g.draw(tempoDown);
				text = fm.getStringBounds("Tempo Up", g);
				g.drawString("Tempo Up", (int)(tempoUp.x+tempoUp.getWidth()/2-text.getWidth()/2), (int)(tempoUp.y+tempoUp.getHeight()/2+text.getHeight()/2));
				text = fm.getStringBounds("Tempo Down", g);
				g.drawString("Tempo Down", (int)(tempoDown.x+tempoDown.getWidth()/2-text.getWidth()/2), (int)(tempoDown.y+tempoDown.getHeight()/2+text.getHeight()/2));
			}
			
			text = fm.getStringBounds("Play", g);
			g.drawString("Play", (int)(start.x+start.getWidth()/2-text.getWidth()/2), (int)(start.y+start.getHeight()/2+text.getHeight()/2));
			text = fm.getStringBounds("Stop", g);
			g.drawString("Stop", (int)(stop.x+stop.getWidth()/2-text.getWidth()/2), (int)(stop.y+stop.getHeight()/2+text.getHeight()/2));
			text = fm.getStringBounds("Pause", g);
			g.drawString("Pause", (int)(pause.x+pause.getWidth()/2-text.getWidth()/2), (int)(pause.y+pause.getHeight()/2+text.getHeight()/2));
			text = fm.getStringBounds("Volume Up", g);
			g.drawString("Volume Up", (int)(volUp.x+volUp.getWidth()/2-text.getWidth()/2), (int)(volUp.y+volUp.getHeight()/2+text.getHeight()/2));
			text = fm.getStringBounds("Volume Down", g);
			g.drawString("Volume Down", (int)(volDown.x+volDown.getWidth()/2-text.getWidth()/2), (int)(volDown.y+volDown.getHeight()/2+text.getHeight()/2));
			text = fm.getStringBounds("Load Track", g);
			g.drawString("Load Track", (int)(load.x+load.getWidth()/2-text.getWidth()/2), (int)(load.y+load.getHeight()/2+text.getHeight()/2));
			
			g.drawLine(this.getWidth()/4, trackPosY, this.getWidth()/2+this.getWidth()/4, trackPosY);  //Draws Seek bar
			g.drawLine(this.getWidth()/4, trackPosY-10, this.getWidth()/4, trackPosY+10); //Draws Begin point
			g.drawLine(this.getWidth()/2+this.getWidth()/4+1, trackPosY-10, this.getWidth()/2+this.getWidth()/4+1, trackPosY+10); //Draws End line (Not sure why I have to add 1 to the x but if I don't the seek bar breaches the width of the end line.)
			
			if(midiPlayer != null && (midiPlayer.getState() == SimpleMidiPlayer.PLAY || midiPlayer.getState() == SimpleMidiPlayer.PAUSE)){
				
				
				
				
				
				int trackPosX = (int)( ((double)midiPlayer.getTickPosition())/midiPlayer.getTicksTotal() * trackLineLength + this.getWidth()/4); //Attempt at finding X Coordinate
				
				
				
				
				
				g.fillOval( trackPosX-3, trackPosY-3, 6, 6); //Draws Seek position indicator
				debugTrackPos.setDebugText("TrackPosX: "+trackPosX+"/"+(this.getWidth()/4+this.getWidth()/2)+" Tick: "+midiPlayer.getTickPosition()+"/"+midiPlayer.getTicksTotal()); //Debugging purposes (top left of pic)
			}
			
			if(midiClicked){
				switch(midiPlayer.getState()){
					case SimpleMidiPlayer.PLAY:
						g.drawString("SimpleMidiPlayer State: PLAY", 10, 100); break;
					case SimpleMidiPlayer.STOP:
						g.drawString("SimpleMidiPlayer State: STOP", 10, 100); break;
					case SimpleMidiPlayer.PAUSE:
						g.drawString("SimpleMidiPlayer State: PAUSE", 10, 100); break;
				}
				g.drawString("SimpleMidiPlayer Volume (0-127): "+midiPlayer.getVolume(), 10, 120);
				g.drawString("SimpleMidiPlayer Tempo (BPM): "+midiPlayer.getTempoBPM(), 10, 140);
			}
		}
	}
	
	public void clicked(Point p, int button){
		if(midi != null && audio != null){
			//MIDI Clicked
			if(midi.contains(p)){
				midiClicked = true;
				midi = null;
				audio = null;
				start = new Rectangle(this.getWidth()/2-50, this.getHeight()/2-50, 100, 100);
				pause = new Rectangle((int)(start.getX()-start.getWidth()-10), this.getHeight()/2-50, 100, 100);
				stop = new Rectangle((int)(start.getX()+110), this.getHeight()/2-50, 100, 100);
				volUp = new Rectangle((int)(pause.getX()-110), this.getHeight()/2-60, 100, 50);
				volDown = new Rectangle((int)(pause.getX()-110), this.getHeight()/2+10, 100, 50);
				tempoUp = new Rectangle((int)(stop.getX()+stop.getWidth()+10), this.getHeight()/2-60, 100, 50);
				tempoDown = new Rectangle((int)(stop.getX()+stop.getWidth()+10), this.getHeight()/2+10, 100, 50);
				seekBar = new Rectangle(this.getWidth()/4, trackPosY-10, trackLineLength, 20);
				load = new Rectangle(this.getWidth()/4+this.getWidth()/2, this.getHeight()/4-100, 100, 50);
				try {
					simpleMidi = new SimpleMidi("soundTest", "res/HIP_HOP.mid", false, 100, SimpleMidi.LOOP_CONTINUOUSLY);
					midiPlayer = new SimpleMidiPlayer();
					SimpleMidiPlayer.loadSimpleMIDI(simpleMidi);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			//AUDIO Clicked
			}else if(audio.contains(p)){
				midiClicked = false;
				midi = null;
				audio = null;
				start = new Rectangle(this.getWidth()/2-50, this.getHeight()/2-50, 100, 100);
				pause = new Rectangle((int)(start.getX()-start.getWidth()-10), this.getHeight()/2-50, 100, 100);
				stop = new Rectangle((int)(start.getX()+start.getWidth()+10), this.getHeight()/2-50, 100, 100);
				volUp = new Rectangle((int)(pause.getX()-110), this.getHeight()/2-60, 100, 50);
				volDown = new Rectangle((int)(pause.getX()-110), this.getHeight()/2+10, 100, 50);
				
			}
		}else if(start != null && pause != null && stop != null && volUp != null && volDown != null && load != null){
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
			}else if(volUp.contains(p) && midiPlayer.getVolume() < 127){
				if(button == 1){
					midiPlayer.setVolume(midiPlayer.getVolume()+1);
				}else if(button == 3){
					if(midiPlayer.getVolume()+10 > 127){
						midiPlayer.setVolume(127);
					}else{
						midiPlayer.setVolume(midiPlayer.getVolume()+10);
					}
				}
			}else if(volDown.contains(p) && midiPlayer.getVolume() > 0){
				if(button == 1){
					midiPlayer.setVolume(midiPlayer.getVolume()-1);
				}else if(button == 3){
					if(midiPlayer.getVolume()-10 < 0){
						midiPlayer.setVolume(0);
					}else{
						midiPlayer.setVolume(midiPlayer.getVolume()-10);
					}
				}
			}else if(tempoUp != null && tempoUp.contains(p)){
				if(button == 1){
					midiPlayer.setTempoBPM(midiPlayer.getTempoBPM()+1);
				}else if(button == 3){
					midiPlayer.setTempoBPM(midiPlayer.getTempoBPM()+10);
				}
			}else if(tempoDown != null && tempoDown.contains(p) && midiPlayer.getTempoBPM() > 0){
				if(button == 1){
					midiPlayer.setTempoBPM(midiPlayer.getTempoBPM()-1);
				}else if(button == 3){
					if(midiPlayer.getTempoBPM()-10 < 0){
						midiPlayer.setTempoBPM(0);
					}else{
						midiPlayer.setTempoBPM(midiPlayer.getTempoBPM()-10);
					}
				}
			}else if(seekBar != null && seekBar.contains(p)){
				midiPlayer.setTickPosition((long)( (p.x*(midiPlayer.getTicksTotal()/trackLineLength)) - ((this.getWidth()/4)*(midiPlayer.getTicksTotal()/trackLineLength))));
			}else if(load.contains(p)){
				String filePath = JOptionPane.showInputDialog(this, "Example: %offline% res/HIP_HOP.mid      Use %online% if file is online", "Load .mid file", JOptionPane.INFORMATION_MESSAGE);
				boolean online;
				if(filePath.contains("%offline%")){
					online = false;
				}else if(filePath.contains("%online%")){
					online = true;
				}else{
					throw new IllegalArgumentException("Didn't include %online% or %offline%");
				}
				filePath = filePath.trim().substring(filePath.indexOf("line%")+5);
				System.out.println("Loading "+filePath);
				try {
					simpleMidi = new SimpleMidi("soundTest", filePath, online, 100, SimpleMidi.LOOP_CONTINUOUSLY);
					SimpleMidiPlayer.loadSimpleMIDI(simpleMidi);
				} catch (InvalidMidiDataException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
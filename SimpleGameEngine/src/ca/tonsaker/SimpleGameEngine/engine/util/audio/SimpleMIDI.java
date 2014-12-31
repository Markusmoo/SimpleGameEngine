package ca.tonsaker.SimpleGameEngine.engine.util.audio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;


/**
 * A SimpleMIDI is a MIDI file handler that can be played back through a {@link SimpleMIDIPlayer} object.
 * 
 * @author Markus Tonsaker
 * @version 1.0
 * @since 2014/12/28
 */
public class SimpleMIDI {
	
	String name;
	protected Sequence sequence;
	protected int volume = 127;
	protected int loopTimes = 0;
	protected boolean isOnline = false;
	
	protected int tempo = 0;
	protected boolean tempoSet = false;
	
	/**
	 * Constructs a SimpleMIDI object from specified <em>name</em> ID, <em>path</em>, <em>isOnline</em>, <em>volume</em>, and <em>loopTimes</em>.
	 * <P>
	 * The volume of the SimpleMIDI ranges from 0 to 127 due to MIDI 1.0 specifications.
	 * 
	 * @param name - the ID for the {@link SimpleMIDIPlayer} to know which SimpleMIDI to playback.
	 * @param path - the location of the MIDI file
	 * @param isOnline - is the MIDI file online
	 * @param volume - the volume (from 0 to 127) for the MIDI playback
	 * @param loopTimes the amount of times the MIDI is played back repeatedly
	 * @throws MalformedURLException if an unknown protocol is specified
	 * @throws InvalidMidiDataException if the URL does not point to valid MIDI file data recognized by the system
	 * @throws IOException if an I/O exception occurs while accessing the URL 
	 */
	public SimpleMIDI(String name, String path, boolean isOnline, int volume, int loopTimes) throws MalformedURLException, InvalidMidiDataException, IOException{
		if(isOnline){
			this.sequence = MidiSystem.getSequence(new URL(path));
		}else{
			this.sequence = MidiSystem.getSequence(new FileInputStream(path));
		}
		this.setVolume(volume);
		this.isOnline = isOnline;
		this.name = name;
	}
	
	/**
	 * Constructs a SimpleMIDI object from specified <em>name</em> ID, <em>path</em>, <em>isOnline</em>, and <em>volume</em>.
	 * <P>
	 * The volume of the SimpleMIDI ranges from 0 to 127 due to MIDI 1.0 specifications.
	 * 
	 * @param name - the ID for the {@link SimpleMIDIPlayer} to know which SimpleMIDI to playback.
	 * @param path - the location of the MIDI file
	 * @param isOnline - is the MIDI file online
	 * @param volume - the volume (from 0 to 127) for the MIDI playback
	 * @throws MalformedURLException if an unknown protocol is specified
	 * @throws InvalidMidiDataException if the URL does not point to valid MIDI file data recognized by the system
	 * @throws IOException if an I/O exception occurs while accessing the URL 
	 */
	public SimpleMIDI(String name, String path, boolean isOnline, int volume) throws MalformedURLException, InvalidMidiDataException, IOException{
		this(name,path,isOnline,volume,0);
	}
	
	/**
	 * Constructs a SimpleMIDI object from specified <em>name</em> ID, <em>path</em> and <em>isOnline</em>.
	 * <P>
	 * The <em>volume</em> of the SimpleMIDI is set to the default value of 127.
	 * 
	 * @param name - the ID for the {@link SimpleMIDIPlayer} to know which SimpleMIDI to playback.
	 * @param path - the location of the MIDI file
	 * @param isOnline - is the MIDI file online
	 * @throws MalformedURLException if an unknown protocol is specified
	 * @throws InvalidMidiDataException if the URL does not point to valid MIDI file data recognized by the system
	 * @throws IOException if an I/O exception occurs while accessing the URL 
	 */
	public SimpleMIDI(String name, String path, boolean isOnline) throws MalformedURLException, InvalidMidiDataException, IOException{
		this(name,path,isOnline,127,0);
	}
	
	/**
	 * Sets the volume of the SimpleMIDI.
	 * 
	 * @param volume - the volume (from 0 to 127) of the SimpleMIDI
	 */
	public void setVolume(int volume){
		if(volume < 0) throw new IllegalArgumentException("(Volume "+volume+") cannot be less than 0");
		else if(volume > 127) throw new IllegalArgumentException("(Volume "+volume+") cannot be greater than 127");
		else this.volume = volume;
	}
	
	/**
	 * Obtains the volume of this SimpleMIDI.
	 * 
	 * @return the volume of this SimpleMIDI
	 */
	public int getVolume(){
		return this.volume;
	}
	
	/**
	 * Determines if the SimpleMIDI infinitely loops.
	 * 
	 * @return true if the SimpleMIDI infinitely loops
	 */
	public boolean isInfiniteLoop(){
		if(loopTimes == -1) return true;
		else return false;
	}
	
	/**
	 * Sets the amount of times of the SimpleMIDI to loop.
	 * 
	 * @param times - the amount of times to repeat.  Set to -1 to infinitely repeat
	 */
	public void setLoopAmount(int times){
		if(times < -1) throw new IllegalArgumentException("Cannot set loop value less than -1 (infinite)");
		else loopTimes = times;
	}
	
	/**
	 * Obtains the amount of times the SimpleMIDI will loop.
	 * 
	 * @return the amount of times the SimpleMIDI will repeat its playback
	 */
	public int getLoopAmount(){
		return loopTimes;
	}
	
	/**
	 * Determines if the path for the MIDI file is online or not.
	 * 
	 * @return true if the MIDI file is online
	 */
	public boolean isOnline(){
		return isOnline;
	}
	
	/**
	 * Sets the identifier of the SimpleMIDI
	 * 
	 * @param name - the identifier of the SimpleMIDI
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Obtains the identifier name of the SimpleMIDI
	 * 
	 * @return the identifier of the SimpleMIDI
	 */
	public String getName(){
		return this.name;
	}
	
	
}

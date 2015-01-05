package ca.tonsaker.SimpleGameEngine.engine.util.audio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

/**
 * A SimpleMidi is a MIDI file handler that can be played back through a {@link SimpleMidiPlayer} object.
 * 
 * @author Markus Tonsaker
 * @version 1.0
 * @since 2014/12/28
 */
public class SimpleMidi {
	
	/**
	 * Indicates that the MIDI should loop forever until stopped.
	 */
	public static final int LOOP_CONTINUOUSLY = -1;
	
	String name;
	String filePath;
	
	protected Sequence sequence;

	protected int volume = 127;
	protected int loopTimes = 0;
	protected boolean isOnline = false;
	
	protected float tempoBPM = 0;
	protected float tempoMPQ = 0;
	protected float tempoFactor = 0;
	protected boolean tempoSet = false;
	
	/**
	 * Constructs a SimpleMidi object from specified <em>name</em> ID, <em>path</em>, <em>isOnline</em>, <em>volume</em>, and <em>loopTimes</em>.
	 * <P>
	 * The volume of the SimpleMidi ranges from 0 to 127 due to MIDI 1.0 specifications.
	 * 
	 * @param name - the ID for the {@link SimpleMidiPlayer} to know which SimpleMidi to playback.
	 * @param path - the location of the MIDI file
	 * @param isOnline - is the MIDI file online
	 * @param volume - the volume (from 0 to 127) for the MIDI playback
	 * @param loopTimes the amount of times the MIDI is played back repeatedly
	 * @throws MalformedURLException if an unknown protocol is specified
	 * @throws InvalidMidiDataException if the URL does not point to valid MIDI file data recognized by the system
	 * @throws IOException if an I/O exception occurs while accessing the URL 
	 */
	public SimpleMidi(String name, String path, boolean isOnline, int volume, int loopTimes) throws MalformedURLException, InvalidMidiDataException, IOException{
		if(isOnline){
			this.sequence = MidiSystem.getSequence(new URL(path));
		}else{
			this.sequence = MidiSystem.getSequence(new FileInputStream(path));
		}
		this.setVolume(volume);
		this.setLoopAmount(loopTimes);
		this.isOnline = isOnline;
		this.name = name;
		this.filePath = path;
	}
	
	/**
	 * Constructs a SimpleMidi object from specified <em>name</em> ID, <em>path</em>, <em>isOnline</em>, and <em>volume</em>.
	 * <P>
	 * The volume of the SimpleMidi ranges from 0 to 127 due to MIDI 1.0 specifications.
	 * 
	 * @param name - the ID for the {@link SimpleMidiPlayer} to know which SimpleMidi to playback.
	 * @param path - the location of the MIDI file
	 * @param isOnline - is the MIDI file online
	 * @param volume - the volume (from 0 to 127) for the MIDI playback
	 * @throws MalformedURLException if an unknown protocol is specified
	 * @throws InvalidMidiDataException if the URL does not point to valid MIDI file data recognized by the system
	 * @throws IOException if an I/O exception occurs while accessing the URL 
	 */
	public SimpleMidi(String name, String path, boolean isOnline, int volume) throws MalformedURLException, InvalidMidiDataException, IOException{
		this(name,path,isOnline,volume,0);
	}
	
	/**
	 * Constructs a SimpleMidi object from specified <em>name</em> ID, <em>path</em> and <em>isOnline</em>.
	 * <P>
	 * The <em>volume</em> of the SimpleMidi is set to the default value of 127.
	 * 
	 * @param name - the ID for the {@link SimpleMidiPlayer} to know which SimpleMidi to playback.
	 * @param path - the location of the MIDI file
	 * @param isOnline - is the MIDI file online
	 * @throws MalformedURLException if an unknown protocol is specified
	 * @throws InvalidMidiDataException if the URL does not point to valid MIDI file data recognized by the system
	 * @throws IOException if an I/O exception occurs while accessing the URL 
	 */
	public SimpleMidi(String name, String path, boolean isOnline) throws MalformedURLException, InvalidMidiDataException, IOException{
		this(name,path,isOnline,127,0);
	}
	
	/**
	 * Sets the volume of the SimpleMidi.
	 * 
	 * @param volume - the volume (from 0 to 127) of the SimpleMidi
	 */
	public void setVolume(int volume){
		if(volume < 0) throw new IllegalArgumentException("(Volume "+volume+") cannot be less than 0");
		else if(volume > 127) throw new IllegalArgumentException("(Volume "+volume+") cannot be greater than 127");
		else this.volume = volume;
	}
	
	/**
	 * Obtains the volume of this SimpleMidi.
	 * 
	 * @return the volume of this SimpleMidi
	 */
	public int getVolume(){
		return this.volume;
	}
	
	/**
	 * Determines if the SimpleMidi infinitely loops.
	 * 
	 * @return true if the SimpleMidi infinitely loops
	 */
	public boolean isInfiniteLoop(){
		if(loopTimes == SimpleMidi.LOOP_CONTINUOUSLY) return true;
		else return false;
	}
	
	/**
	 * Sets the amount of times of the SimpleMidi to loop.
	 * 
	 * @param times - the amount of times to repeat.  Set to -1 to infinitely repeat
	 */
	public void setLoopAmount(int times){
		if(times < -1) throw new IllegalArgumentException("Cannot set loop value less than -1 (infinite)");
		else loopTimes = times;
	}
	
	/**
	 * Obtains the amount of times the SimpleMidi will loop.
	 * 
	 * @return the amount of times the SimpleMidi will repeat its playback
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
	 * Sets the identifier of the SimpleMidi
	 * 
	 * @param name - the identifier of the SimpleMidi
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Obtains the identifier name of the SimpleMidi
	 * 
	 * @return the identifier of the SimpleMidi
	 */
	public String getName(){
		return this.name;
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	public Sequence getMidiSequence(){
		return sequence;
	}
	
	/**
	 * Sets the tempo using a speed multiplier (or factor) for the SimpleMidi
	 * 
	 * @param factor - 0.5f would half the speed, 2.0f would double, 3.0f would triple, ext.
	 */
	public void setTempoFactor(float factor){
		this.tempoFactor = factor;
		this.tempoBPM = 0;
		this.tempoMPQ = 0;
		tempoSet = true;
	}
	
	/**
	 * Sets the tempo in BPM (Beats per minute) of the SimpleMidi
	 * 
	 * @param bpm - beats per minute
	 */
	public void setTempoBPM(float bpm){
		this.tempoBPM = bpm;
		this.tempoFactor = 0;
		this.tempoMPQ = 0;
		tempoSet = true;
	}
	
	/**
	 * Sets the tempo in MPQ (Microseconds per quarter note) of the SimpleMidi
	 * 
	 * @param mpq - microseconds per quarter note
	 */
	public void setTempoMPQ(float mpq){
		this.tempoMPQ = mpq;
		this.tempoFactor = 0;
		this.tempoBPM = 0;
		tempoSet = true;
	}
}

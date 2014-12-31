package ca.tonsaker.SimpleGameEngine.engine.util.audio;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

//TODO loops, volume, ext.
public class SimpleMIDIPlayer{
	
	protected Sequencer sequencer;
	protected Synthesizer synth;
	
	protected static Map<String, SimpleMIDI> simpleMidi = new HashMap<String, SimpleMIDI>();
	
	public SimpleMIDIPlayer() throws MidiUnavailableException{
		sequencer = MidiSystem.getSequencer();
		synth = MidiSystem.getSynthesizer();
		sequencer.open();
		synth.open();
	}
	
	public static SimpleMIDI getSimpleMIDI(String name){
		return simpleMidi.get(name);
	}

	public static boolean loadSimpleMIDI(SimpleMIDI midi){
		simpleMidi.put(midi.getName(), midi);
		return true;
	}
	
	public static boolean unloadSimpleMIDI(String name){
		if(simpleMidi.containsKey(name)){
			simpleMidi.remove(name);
			return true;
		}else{
			return false;
		}
	}
	
	public void play(SimpleMIDI midi) throws InvalidMidiDataException{
		sequencer.setSequence(midi.sequence);
		sequencer.setLoopCount(midi.getLoopAmount());
		sequencer.start();
	}
	
	public void play(String name) throws InvalidMidiDataException{
		SimpleMIDI midi = simpleMidi.get(name);
		this.play(midi);
	}
	
	public void play(){
		sequencer.start();
	}
	
	public void pause(){
		sequencer.stop();
	}
	
	public void stop(){
		sequencer.stop();
		sequencer.setTickPosition(0);
	}
	
	public boolean isPlaying(){
		return sequencer.isRunning();
	}
}

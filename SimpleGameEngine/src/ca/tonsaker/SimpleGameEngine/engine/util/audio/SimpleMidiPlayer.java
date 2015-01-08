package ca.tonsaker.SimpleGameEngine.engine.util.audio;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

//TODO JavaDoc, SoundBank Loader
public class SimpleMidiPlayer{
	
	/**
	 * Indicates that the MIDI should loop forever until stopped.
	 */
	public static final int LOOP_CONTINUOUSLY = -1;
	
	public static final int PLAY = 0;
	public static final int PAUSE = 1;
	public static final int STOP = 2;
	public static final int UNAVAILABLE = 3;
	
	protected Sequencer sequencer;
	protected Synthesizer synth;
	protected Receiver receiver;
	
	protected int loopCount = 0;
	protected SimpleMidi currentMidi = null;
	
	protected int lastVolume = 0;
	
	protected int state = SimpleMidiPlayer.STOP;
	
	protected static Map<String, SimpleMidi> simpleMidi = new HashMap<String, SimpleMidi>();
	
	public SimpleMidiPlayer() throws MidiUnavailableException{
		sequencer = MidiSystem.getSequencer(false);
		synth = MidiSystem.getSynthesizer();
		receiver = synth.getReceiver();
		sequencer.open();
		synth.open();
		sequencer.getTransmitter().setReceiver(receiver);
		this.setupMeta();
	}
	
	public static SimpleMidi getSimpleMIDI(String name){
		return simpleMidi.get(name);
	}

	public static boolean loadSimpleMIDI(SimpleMidi midi){
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
	
	public void setVolume(int volume){
		for(MidiChannel chan : synth.getChannels()){
			chan.controlChange(7, volume);
		}
	}
	
	public void setVolume(int volume, int channel){
		synth.getChannels()[channel].controlChange(7, volume);
	}
	
	public int getVolume(int channel){
		return synth.getChannels()[channel].getController(7);
	}
	
	public int getVolume(){
		return synth.getChannels()[0].getController(7);
	}
	
	protected void setupMeta(){
		sequencer.addMetaEventListener(new MetaEventListener(){

			private static final byte END_OF_TRACK = 0x2f;
			
			@Override
			public void meta(MetaMessage e) {
				if(e.getType() == END_OF_TRACK){
					if(loopCount > 0 || loopCount == -1){
						loopCount--;
						sequencer.setTickPosition(0);
						sequencer.start();
					}
					if(currentMidi.tempoSet){
						if(currentMidi.tempoBPM != 0){
							setTempoBPM(currentMidi.tempoBPM);
						}else if(currentMidi.tempoFactor != 0){
							setTempoFactor(currentMidi.tempoFactor);
						}else if(currentMidi.tempoMPQ != 0){
							setTempoMPQ(currentMidi.tempoMPQ);
						}else{
							setTempoBPM(0);
						}
					}
				}
			}
			
		});
	}
	
	public void play(SimpleMidi midi) throws InvalidMidiDataException{
		sequencer.setSequence(midi.getMidiSequence());
		loopCount = midi.getLoopAmount();
		this.setVolume(midi.getVolume());
		if(midi.tempoSet){
			if(midi.tempoBPM != 0){
				this.setTempoBPM(midi.tempoBPM);
			}else if(midi.tempoFactor != 0){
				this.setTempoFactor(midi.tempoFactor);
			}else if(midi.tempoMPQ != 0){
				this.setTempoMPQ(midi.tempoMPQ);
			}else{
				this.setTempoBPM(0);
			}
		}
		sequencer.start();
		midi.setTempoBPM(sequencer.getTempoInBPM());
		currentMidi = midi;
		state = SimpleMidiPlayer.PLAY;
	}
	
	public void play(String name) throws InvalidMidiDataException{
		SimpleMidi midi = simpleMidi.get(name);
		this.play(midi);
	}
	
	public void play(){
		sequencer.start();
		this.setVolume(lastVolume);
		state = SimpleMidiPlayer.PLAY;
	}
	
	public void pause(){
		lastVolume = this.getVolume();
		sequencer.stop();
		state = SimpleMidiPlayer.PAUSE;
	}
	
	public void stop(){
		sequencer.stop();
		sequencer.setTickPosition(0);
		state = SimpleMidiPlayer.STOP;
	}
	
	public boolean isPlaying(){
		return sequencer.isRunning();
	}
	
	public int getState(){
		return this.state;
	}
	
	public void setTempoFactor(float factor){
		sequencer.setTempoFactor(factor);
		currentMidi.setTempoFactor(factor);
	}
	
	public void setTempoBPM(float bpm){
		sequencer.setTempoInBPM(bpm);
		currentMidi.setTempoBPM(bpm);
	}
	
	public void setTempoMPQ(float mpq){
		sequencer.setTempoInMPQ(mpq);
		currentMidi.setTempoMPQ(mpq);
	}
	
	public void setTickPosition(long tick){
		sequencer.setTickPosition(tick);
	}
	
	public float getTempoFactor(){
		return sequencer.getTempoFactor();
	}
	
	public float getTempoBPM(){
		return sequencer.getTempoInBPM();
	}
	
	public float getTempoMPQ(){
		return sequencer.getTempoInMPQ();
	}
	
	public long getTickPosition(){
		return sequencer.getTickPosition();
	}
	
	public long getTicksTotal(){
		return sequencer.getTickLength();
	}
	
	public long getMicrosecondPosition(){
		return sequencer.getMicrosecondPosition();
	}
	
	public long getMicrosecondsTotal(){
		return sequencer.getMicrosecondLength();
	}
}

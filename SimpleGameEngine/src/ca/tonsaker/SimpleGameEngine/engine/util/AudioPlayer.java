package ca.tonsaker.SimpleGameEngine.engine.util;


import java.applet.Applet;
import java.applet.AudioClip;

//TODO WIP

public class AudioPlayer {
	public static final AudioPlayer SAMPLE = new AudioPlayer("/path.au");

	private AudioClip clip;
	
	public AudioPlayer(String filename){
		try{
			clip = Applet.newAudioClip(AudioPlayer.class.getResource(filename));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void play(){
		try{
			new Thread(){
				public void run(){
					clip.play();
				}
			}.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
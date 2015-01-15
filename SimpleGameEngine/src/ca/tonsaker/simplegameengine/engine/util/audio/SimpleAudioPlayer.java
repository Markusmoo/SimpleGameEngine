package ca.tonsaker.simplegameengine.engine.util.audio;

import java.util.ArrayList;

public abstract class SimpleAudioPlayer {
	
	protected static ArrayList<SimpleAudio> audioArray = new ArrayList<SimpleAudio>();
	
	public static boolean loadAudio(String path, String name){
		//TODO
		return false;
		
	}
	
	public static boolean removeLoadedAudio(String name){
		int idx = 0;
		for(SimpleAudio aud : audioArray){
			if(name.equals(aud.getName())){
				audioArray.remove(idx);
				return true;
			}
			idx++;
		}
		System.err.println("ERROR: SimpleAudio file index name \""+name+"\" not found!");
		return false;
	}
	
	public static String[] getAudioNames(){
		ArrayList<String> strings = new ArrayList<String>();
		for(SimpleAudio aud : audioArray){
			strings.add(aud.getName());
		}
		return (String[]) strings.toArray();
	}
}
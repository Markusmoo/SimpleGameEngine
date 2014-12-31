package ca.tonsaker.SimpleGameEngine.engine.util.audio;

import java.net.MalformedURLException;
import java.net.URL;

public class SimpleAudio{
	
	protected String audioName;
	
	public SimpleAudio(String name, URL file){
		this.audioName = name;
		//TODO
	}
	
	public SimpleAudio(String name, String path) throws MalformedURLException{
		this(name, new URL(path));
	}
	
	/**
	 *  Sets the index name of the audio file
	 * 
	 * @param name - Index name of the audio file
	 */
	public void setName(String name){
		this.audioName = name;
	}
	
	/**
	 *  Gets the index name of the audio file.
	 * 
	 * @return Index name of the audio file.
	 */
	public String getName(){
		return this.audioName;
	}
}
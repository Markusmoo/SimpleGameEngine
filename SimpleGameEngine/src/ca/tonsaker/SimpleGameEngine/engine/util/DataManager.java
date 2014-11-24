package ca.tonsaker.SimpleGameEngine.engine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

//TODO WIP

public abstract class DataManager {

	private static Formatter x;
	private static Scanner y;
	
	public static void createFile(File path){
		try{
			y = new Scanner(path);
			y.close();
		}catch(Exception e){
			try{
				path.mkdirs();
				x = new Formatter(path);
				x.close();
			}catch(Exception ev){
				System.out.println("Failed to create file.");
				ev.printStackTrace();
			}
		}
	}
	
	public static void saveFile(File path, long highScore){
		openNewFile(path);
		writeNewFile(highScore);
		closeNewFile();
	}
	
	public static void readFile(File path) throws FileNotFoundException{
		openExistingFile(path);
		readExistingFile();
		closeExistingFile();
	}

	private static void openNewFile(File path){
		try{
			x = new Formatter(path);
		}catch(Exception e){
			System.out.println("Failed to create file.");
			e.printStackTrace();
		}
	}
	
	private static void writeNewFile(long highScore){
		x.format("%s", Long.toString(highScore));
	}
	
	private static void closeNewFile(){
		x.close();
	}
	
	private static void openExistingFile(File path) throws FileNotFoundException{
		y = new Scanner(path);
	}
	
	private static int readExistingFile(){
		while(y.hasNext()){
			return Integer.parseInt(y.next());
		}
		return 0;
	}
	
	private static void closeExistingFile(){
		y.close();
	}
	
	
	
}

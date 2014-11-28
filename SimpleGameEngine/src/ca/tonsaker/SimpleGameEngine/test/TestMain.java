package ca.tonsaker.SimpleGameEngine.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.graphics.Triangle;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay;
import ca.tonsaker.SimpleGameEngine.engine.util.network.Client;
import ca.tonsaker.SimpleGameEngine.engine.util.network.Server;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TestMain extends GameEngine implements EngineFrame{
	
	DebugOverlay debug;
	Client client;
	Server server;
	
	public TestMain(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	public static void main(String[] args){
		TestMain main = new TestMain(100,100,800,600); //Creates the JFrame at X,Y, and then sets the WIDTH and HEIGHT
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets the default close operation to erase all memory and close the Program
		main.setResizable(false); //Disallows the parent JFrame to be resized
		main.setTitle("SAMPLE TITLE"); //Changes the title of the parent JFrame
		main.setFPS(60); //The FPS (Frames per second)
		main.setUPS(30); //The UPS (Updates per second)
		main.run(); //Starts the update and drawing loop at the set FPS
	}
	
	@Override
	public void init(){
		super.init(); //Always call super.init() first!
		
		debug = new DebugOverlay(this);
		
		//client = new Client(new Socket(InetAddress.getLocalHost(), 3048));
			//server = new Server(InetAddress.getLocalHost(), 3048, 5);
			//server = new Server(30480);
			client = new Client("localhost", 30480, "MarkusClient");
		//} catch (IOException e) {
			//e.printStackTrace();
		//}
		//System.out.println(client.isConnected());
		System.out.println("Hi");
	}
	
	@Override
	public void update() {
		debug.update();
		if(input.isKeyDown(KeyEvent.VK_SPACE)){
			//System.out.println("Space");
			client.sendCommand("Hello buddy");
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); //Always call super.draw(g) first!		
		debug.debugText(g, "!This is some sample debug text", Color.red, 1);
		debug.debugText(g, "!This is some sample debug text", Color.orange, 2);
	}

}
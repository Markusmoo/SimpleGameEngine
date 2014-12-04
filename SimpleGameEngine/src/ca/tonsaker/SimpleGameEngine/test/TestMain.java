package ca.tonsaker.SimpleGameEngine.test;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;

import ca.tonsaker.SimpleGameEngine.engine.EngineFrame;
import ca.tonsaker.SimpleGameEngine.engine.GameEngine;
import ca.tonsaker.SimpleGameEngine.engine.util.DebugOverlay;

import javax.swing.JFrame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Client;

@SuppressWarnings("serial")
public class TestMain extends GameEngine implements EngineFrame{
	
	DebugOverlay debug;
	Client client;
	Server server;
	
	public TestMain(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	public static void main(String[] args){
		TestMain main = new TestMain(0,0,1920,1080); //Creates the JFrame at X,Y, and then sets the WIDTH and HEIGHT
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets the default close operation to erase all memory and close the Program
		main.setResizable(true); //Disallows the parent JFrame to be resized
		main.setTitle("SAMPLE TITLE"); //Changes the title of the parent JFrame
		main.setFPS(60); //The FPS (Frames per second)
		main.setUPS(100); //The UPS (Updates per second)
		main.run(); //Starts the update and drawing loop at the set FPS
	}
	
	@Override
	public void init(){
		super.init(); //Always call super.init() first!
		
		debug = new DebugOverlay(this);
		
		try {	
			///*
			server = new Server();
			server.start();
			server.getKryo().register(Point.class);
			server.bind(30480);
			server.addListener(new Listener(){
				
				public void connected(Connection connection){
					System.out.println("Client "+connection.getID()+" CONNECTED");
				}
				
			});
			//*/
			/*
			Client client = new Client();
		    client.start();
		    client.getKryo().register(Point.class);
		    client.connect(5000, "10.18.10.54", 30480);

		    client.addListener(new Listener() {
		        public void received (Connection connection, Object object) {
		        	if(object instanceof Point){
		        		point = (Point) object;
		        	}
		        }
		     });
			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		point = new Point(0,600);
		
		System.out.println("Finished INIT");
	}
	
	Point point;
	boolean moveRight = true;
	
	@Override
	public void update() {
		debug.update();
		this.setTitle(Integer.toString(point.x));
		if(moveRight && input.isKeyDown(KeyEvent.VK_SPACE)){
			point.x+=10;
			
		}else if(input.isKeyDown(KeyEvent.VK_SPACE)){
			point.x-=10;
		}
		
		if(point.x > (server.getConnections().length*this.getWidth())){
			moveRight = false;
		}else if(point.x <= 0){
			moveRight = true;
		}
		for(Connection id : server.getConnections()){
			Point p = new Point(point.x-id.getID()*this.getWidth(), point.y);
			server.sendToTCP(id.getID(), p);
		}
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); //Always call super.draw(g) first!
		//g.fillOval(point.x, point.y, 30, 30);
		//debug.debugText(g, "!This is some sample debug text", Color.red, 1);
		//debug.debugText(g, "!This is some sample debug text", Color.orange, 2);
	}

}
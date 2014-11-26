package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private BufferedReader bufferedReader;
	private String inputLine;
	private int port = 63400;
	
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	private boolean isRunning = false;
	
	public Server(int port){
		try{
			if(port != 0) this.port = port;
			serverSocket = new ServerSocket(this.port);
			clientSocket = serverSocket.accept();
			bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.run();
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	public boolean stopServer(){
		isRunning = false;
		try {
			this.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean startServer(){
		this.run();
		return true;
	}
	
	public void run(){
		isRunning = true;
		while(isRunning){
			try {
				inputLine = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(inputLine != null) System.out.println(inputLine);
		}
	}
}


package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
	
	private class ServerInput implements Runnable{
		
		public Socket socket;
		public BufferedReader in;
		public PrintWriter out;
		public String id;
		
		public ServerInput(Socket socket){
			this.socket = socket;
			System.out.println("SERVER: Client "+socket.getInetAddress()+":"+socket.getPort()+" connected");
		}
		
		public void run(){
	    	String line;
	    	try{
	    		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	    		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    		while ((line = in.readLine()) != null) {
	        		System.out.println(line);
	        		//TODO
	    		}
	    		socket.close();
	        }catch(IOException e){
	        	System.err.println("SERVER ERROR: Failed to get client input");
	        }
	    }
		
	}
	
	ServerSocket serverSocket;
	ArrayList<Thread> inputs = new ArrayList<Thread>();
	
    public Server(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("SERVER: Running server(IP:"+serverSocket.getInetAddress().getHostAddress()+" Port:"+port+")");
            this.start();
        } catch (IOException e) {
            System.err.println("SERVER ERROR: Could not listen on port");
            //TODO
        }
    }
    
    public void sendCommand(String command) throws IOException{
    	System.out.println("SERVER: Sending command \""+command+"\" to clients.");
    	for(ServerInput i : (ServerInput[]) inputs.toArray()){
    		i.out.write(command);
    	}
    }
    
    public void run(){
    	while(true){
    		try {
				Socket socket = serverSocket.accept();
				ServerInput serverInput = new ServerInput(socket);
				Thread inputThread = new Thread(serverInput);
				inputThread.start();
				inputs.add(inputThread);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
}

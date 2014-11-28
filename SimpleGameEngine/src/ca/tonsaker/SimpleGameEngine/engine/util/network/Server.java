package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	
	private class ServerInput extends Thread{
		
		Socket socket;
		
		public ServerInput(Socket socket){
			this.socket = socket;
			System.out.println("Client "+socket.getInetAddress()+":"+socket.getPort()+" connected");
		}
		
		public void run(){
	    	String line;
	    	try{
	    		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    		while ((line = in.readLine()) != null) {
	        		if(line != null) System.out.println(line);
	        		//TODO
	    		}
	    		socket.close();
	        }catch(IOException e){
	        	System.err.println("Failed to get client input");
	        }
	    }
		
	}
	
	ServerSocket serverSocket;
	BufferedReader in;
	//DataOutputStream out;
	Socket clientSocket;
	
    public Server(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Running server(IP:"+serverSocket.getInetAddress().getHostAddress()+" Port:"+port+")");
            this.run();
        } catch (IOException e) {
            System.err.println("Could not listen on port");
            //TODO
        }
    }
    
    public void sendCommand(String command){
    	//out.write(id+":"+command); TODO
    }
    
    public void run(){
    	while(true){
    		try {
				Socket socket = serverSocket.accept();
				new ServerInput(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
}

package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	
	ServerSocket serverSocket;
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	
    public Server(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Running server on port "+port);
        } catch (IOException e) {
            System.err.println("Could not listen on port");
            System.exit(1);
        }

        try {
            clientSocket = serverSocket.accept();
            System.out.println("Connected");
            
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            
            this.run();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
        	try{
	        	if(out != null) out.close();
	            if(in != null) in.close();
	            if(serverSocket != null) serverSocket.close();
        	}catch(IOException e){
        		System.err.println("Failed to close "+e.getClass().toString());
        		e.printStackTrace();
        	}
        }  
    }
    
    public void run(){
    	while (true) {      
           try {
        	String line = in.readUTF();
        	if(line != null) System.out.println(line);
		} catch (IOException e) {
			//TODO ERRRRRRRRRRRRRORS
		}
        }
    }
}

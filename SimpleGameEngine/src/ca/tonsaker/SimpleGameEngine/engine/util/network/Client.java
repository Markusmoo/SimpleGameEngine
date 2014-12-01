package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	
	Socket clientSocket;
	String id;
	PrintWriter out;
	BufferedReader in;
	
    public Client(String host, int port, String id){
    	this.id = id;
        try {
            clientSocket = new Socket(host, port);
            this.start();
        } catch (UnknownHostException e) {
            System.err.println("CLIENT ERROR: Unknown host");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("CLIENT ERROR: Couldn't get I/O");
            e.printStackTrace();
        }
    }
    
    public void sendCommand(String command){
    	out.write(id+":"+command);
    }
    
    public void run(){
    	String line;
    	try{
    		System.out.println("CLIENT: Getting Server Input Stream..");
    		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    		out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true); // Stuff to send to server
    		System.out.println(in.ready());
    		System.out.println("CLIENT: Listening for incoming server messages..");
    		while ((line = in.readLine()) != null) {
        		System.out.println(line);
        		//TODO
    		}
    		System.out.println("CLIENT: Closing Connections..");
    		clientSocket.close();
    		System.out.println("CLIENT: Connection Closed");
        }catch(IOException e){
        	System.err.println("CLIENT ERROR: Failed to get server input");
        }
    }
}

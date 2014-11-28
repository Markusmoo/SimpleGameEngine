package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            out = new PrintWriter(clientSocket.getOutputStream(), true); // Stuff to send to server
            //in = new BufferedReader(new InputStreamReader(new InputStream()))); // Stuff server sends
            this.start();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O");
            e.printStackTrace();
        }
    }
    
    public void sendCommand(String command){
    	out.write(id+":"+command);
    }
    
    public void run(){
    	/*String line;
    	try{
    		System.out.println("Getting Server Input");
    		while ((line = in.readLine()) != null) {
        		if(line != null) System.out.println(line);
        		//TODO
    		}
    		clientSocket.close();
        }catch(IOException e){
        	System.err.println("Failed to get server input");
        }*/
    }
}

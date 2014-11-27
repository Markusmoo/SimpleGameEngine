package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	
	Socket clientSocket;
	DataOutputStream out;
	DataInputStream in;
	
    public Client(String host, int port){
        try {
            clientSocket = new Socket(host, port);
            out = new DataOutputStream(clientSocket.getOutputStream()); // Stuff to send to server
            in = new DataInputStream(clientSocket.getInputStream()); // Stuff server sends
        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O");
            e.printStackTrace();
        } finally {
        	try { //TODO
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public void sendObject(String command) throws IOException{
    	out.writeUTF(command);
		out.flush();
    }
    
    public void run(){
    	while(true){
    		try {
				in.readUTF();
			} catch (IOException e) {
				System.err.println("Failed to read ClientData");
				e.printStackTrace();
			}
    	}
    }
}

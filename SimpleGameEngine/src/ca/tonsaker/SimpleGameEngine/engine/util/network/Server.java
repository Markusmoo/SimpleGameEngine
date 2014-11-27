package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ca.tonsaker.SimpleGameEngine.test.DataTest;

public class Server extends Thread{
	
	ServerSocket serverSocket;
	ObjectInputStream in;
	ObjectOutputStream out;
	Socket clientSocket;
	
	
    public Server(){
        try {
            serverSocket = new ServerSocket(4441);
        } catch (IOException e) {
            System.err.println("Could not listen on port");
            System.exit(1);
        }

        try {
            clientSocket = serverSocket.accept();
            System.out.println("Connected");
            
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream()); 
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        DataTest go;
        while (true) {      
            try {
				go = (DataTest) in.readObject();
				System.out.println( go.x + " " + go.y + " " + go.string );
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public void run(){
    	
    }
}

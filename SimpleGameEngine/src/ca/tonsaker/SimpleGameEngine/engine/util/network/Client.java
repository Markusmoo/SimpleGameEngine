package ca.tonsaker.SimpleGameEngine.engine.util.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import ca.tonsaker.SimpleGameEngine.test.DataTest;

public class Client{
	
	Socket clientSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
	
    public Client(String host, int port){
        try {
            clientSocket = new Socket(host, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream()); // Stuff to send to server
            in = new ObjectInputStream(clientSocket.getInputStream()); // Stuff server sends

        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
            System.exit(1); //TODO
        } catch (IOException e) {
            System.err.println("Couldn't get I/O");
            System.exit(1); //TODO
        } finally {
        	try { //TODO
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        while (true) {
            try {
				out.writeObject(new DataTest(4,4,"Hello :)"));
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}

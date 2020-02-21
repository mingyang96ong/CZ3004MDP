package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import config.Constant;

public class ConnectionSocket {
    // initialize socket and input output streams 
    private Socket socket              = null; 
    private DataInputStream  input     = null; 
    private DataOutputStream output    = null;
    private static ConnectionSocket cs = null;
    
    private ConnectionSocket() {
    	
    }
    
    public static ConnectionSocket getInstance() {
    	if (cs == null) {
    		cs = new ConnectionSocket();
    		cs.connectToRPI();
    	}
    	return cs;
    }
    
    public void connectToRPI() {
    	if (socket == null) {
	    	try {
	    		socket = new Socket(Constant.IP_ADDRESS, Constant.PORT);
	    		
	    		System.out.println("Connected to " + Constant.IP_ADDRESS + ":" + Integer.toString(Constant.PORT));
	    		
	    		input  = new DataInputStream(socket.getInputStream()); 
	    		
	    		output = new DataOutputStream(socket.getOutputStream()); 
	    		
	    	}
	    	catch(UnknownHostException UHEx) { 
	    		System.out.println("UnknownHostException in ConnectionSocket connectToRPI Function"); 
	        } 
	    	catch (IOException IOEx) {
	    		System.out.println("IOException in ConnectionSocket connectToRPI Function");
	    	}
    	}
    }
    
    public void sendMessage(String message) {
    	try {
    		output.writeUTF(message);
    		output.flush();
    		System.out.println('"' + message + '"' + " sent successfully");
    	}
    	catch (IOException IOEx) {
    		System.out.println("IOException in ConnectionSocket sendMessage Function");
    	}
    }
    
    public String receiveMessage() {
    	String message = "";
    	try {
    		message = input.readUTF();
    	}
    	catch (IOException IOEx) {
    		System.out.println("IOException in ConnectionSocket receiveMessage Function");
    	}
    	return message;
    }
    
    public void closeConnection() {
    	if (socket != null) {
    		try {
    			socket.close();
    			input.close();
    			output.close();
    			socket = null;
    			input = null;
    			output = null;
    		}
    		catch (IOException IOEx) {
        		System.out.println("IOException in ConnectionSocket closeConnection Function");
        	}
    	}
    }
}

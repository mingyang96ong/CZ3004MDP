package connection;
//package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import config.Constant;

public class ConnectionSocket {
    // initialize socket and input output streams 
    private Socket socket              = null; 
    private DataInputStream  input     = null; 
    private DataOutputStream output    = null;
    private InputStream  din     = null; 
    private PrintStream dout    = null;
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
    
    public static boolean checkConnection() {
    	if (cs == null) {
    		return false;
    	}
    	return true;
    }
    
    public boolean connectToRPI() {
    	boolean result = true;
    	if (socket == null) {
	    	try {
	    		socket = new Socket(Constant.IP_ADDRESS, Constant.PORT);	
	    		System.out.println("Connected to " + Constant.IP_ADDRESS + ":" + Integer.toString(Constant.PORT));
//	    		input  = new DataInputStream(socket.getInputStream()); 
//	    		output = new DataOutputStream(socket.getOutputStream());
	    		din  = socket.getInputStream(); 
	    		dout = new PrintStream(socket.getOutputStream()); 
	    		
	    	}
	    	catch(UnknownHostException UHEx) { 
	    		System.out.println("UnknownHostException in ConnectionSocket connectToRPI Function"); 
	    		result = false;
	        } 
	    	catch (IOException IOEx) {
	    		System.out.println("IOException in ConnectionSocket connectToRPI Function");
	    		result = false;
	    	}
    	}
    	return result;
    }
    
    public void sendMessage(String message) {
    	try {
    		dout.write(message.getBytes());
    		dout.flush();
//    		output.writeUTF(message);
//    		output.flush();
    		System.out.println('"' + message + '"' + " sent successfully");
    	}
    	catch (IOException IOEx) {
    		System.out.println("IOException in ConnectionSocket sendMessage Function");
    	}
    }
    
    public String receiveMessage() {
//    	String message = "";
    	byte[] byteData = new byte[2048];
    	try {
    		int size = 0;
    		din.read(byteData);
    		
    		// This is to get rid of junk bytes
    		while (size < 2048) {
    			if (byteData[size] == 0) {
    				break;
    			}
    			size++;
    		}
    		String message = new String(byteData, 0, size, "UTF-8");
//    		System.out.println(size);
    		
//    		message = input.readUTF();
    		return message;
    	}
    	catch (IOException IOEx) {
    		System.out.println("IOException in ConnectionSocket receiveMessage Function");
    	}
    	return "Error";
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
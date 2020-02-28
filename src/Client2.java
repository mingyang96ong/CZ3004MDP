import java.util.Scanner;

import java.util.regex.Pattern;

import config.Constant;
import connection.ConnectionSocketJava;
import connection.ConnectionSocket;

public class Client2 {
	public static void main(String args[]) {
		
		ConnectionSocket cs = ConnectionSocket.getInstance();
		String message = "";
		Scanner sc = new Scanner (System.in);
		while (message.toUpperCase().compareTo("bye".toUpperCase()) != 0) {
			System.out.print("Enter your message: ");
			message = sc.nextLine();
			cs.sendMessage(message);
			System.out.println("Waiting for server to reply...");
			System.out.println("Server: " + cs.receiveMessage());
		}
		cs.closeConnection();
	}
}

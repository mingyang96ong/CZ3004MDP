package connection;
import java.util.Scanner;

public class Server {
	public static void main(String args[]) {
		ConnectionServer server = ConnectionServer.getInstance();
		String message = "";
		Scanner sc = new Scanner (System.in);
		while (message.toUpperCase().compareTo("bye".toUpperCase()) != 0) {
			System.out.println("Waiting for Client to reply... ");
			System.out.println("Client: " + server.receiveMessage());
			
			System.out.print("Enter your message: ");
			message = sc.nextLine();
			server.sendMessage(message);
		}
		server.closeConnection();
	}
}

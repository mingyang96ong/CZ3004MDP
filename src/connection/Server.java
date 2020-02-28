package connection;
import java.util.Scanner;
import java.util.regex.Pattern;

import config.Constant;

public class Server {
	public static void main(String args[]) {
		ConnectionServer server = ConnectionServer.getInstance();
		String message = "";
		Scanner sc = new Scanner (System.in);
		boolean exploring = false,completed = false, fastestpath = false;
		int pos[] = new int[] {1,1};
		int direction = 2, count = 0;
		while (!completed) {
//			message = "ES|";
			System.out.print("Enter your command: ");
			message = sc.nextLine();
			server.sendMessage(message);
			if (message.equals(Constant.START_EXPLORATION)) {
				exploring = true;
			}
			if (message.equals(Constant.FASTEST_PATH)) {
				fastestpath = true;
			}
			if (message.equals(Constant.SEND_ARENA)) {
				completed = true;
			}
			while (exploring || fastestpath) {
				message = server.receiveMessage();
				System.out.println("Message received: " + message);
				Pattern p = Pattern.compile("W\\d+[|]");

				if (p.matcher(message).matches()) {
					server.sendMessage(Constant.FORWARD_ACK);
					pos[0] = pos[0] + Constant.SENSORDIRECTION[direction][0];
					pos[1] = pos[1] + Constant.SENSORDIRECTION[direction][1];
				}
				else if (message.equals(Constant.TURN_LEFT)) {
					server.sendMessage(Constant.LEFT_ACK);
					direction = (direction + 3)%4;
				}
				else if (message.equals(Constant.TURN_RIGHT)) {
					server.sendMessage(Constant.RIGHT_ACK);
					direction = (direction + 1)%4;
				}
				else if(message.equals(Constant.SENSE_ALL)) {
					System.out.println(pos[0] + ", " + pos[1]);
					if ((pos[0] == 1 && pos[1] == 11) || (pos[0] == 16 && pos[1] == 13) || (pos[0] == 18 && pos[1] == 3) || (pos[0] == 3 && pos[1] == 1)) {
						message = "23|23|23|3|3|84";
					}
					else if ((pos[0] == 1 && pos[1] == 12) || (pos[0] == 17 && pos[1] == 13) || (pos[0] == 18 && pos[1] == 2) || (pos[0] == 2 && pos[1] == 1)){
						message = "13|13|13|3|3|84";
					}
					else if (((pos[0] == 1 && pos[1] == 13) || (pos[0] == 18 && pos[1] == 13) || (pos[0] == 18 && pos[1] == 1)) && count < 2){
						message = "3|3|3|3|3|84";
						count++;
					}
					else {
						message = "84|84|84|3|3|84";
						count = 0;
					}
//						System.out.println("Enter sensor value:");
//						message = sc.nextLine();
					server.sendMessage(message);
				}
				else if (message.equals(Constant.END_TOUR)) {
					exploring = false;
					fastestpath = false;
				}
				else {
					System.out.println("Error.");
				}
			}
		}
//		server.sendMessage(Constant.SEND_ARENA);
		System.out.println(server.receiveMessage());
	}
}

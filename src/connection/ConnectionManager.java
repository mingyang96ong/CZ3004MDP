package connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import astarpathfinder.FastestPathThread;
import config.Constant;
import exploration.ExplorationThread;
import robot.RealRobot;

public class ConnectionManager extends Thread{
	private static RealRobot robot;
	private static ConnectionManager connectionManager = null;
	private ConnectionSocket connectionSocket = ConnectionSocket.getInstance();
	private static Thread thread = null;
	private static ArrayList<String> buffer = new ArrayList<String>();
	private static String[] bufferableCommand = new String[] {Constant.FORWARD_ACK, Constant.IMAGE_ACK, Constant.LEFT_ACK, Constant.RIGHT_ACK};
	private ConnectionManager() {
		robot = RealRobot.getInstance();
	}
	
	
	public static ConnectionManager getInstance() {
		if (connectionManager == null) {
			connectionManager = new ConnectionManager();
		}
		return connectionManager;
	}
	
	public boolean connectToRPi() {
		return connectionSocket.connectToRPI();
	}
	
	public void start() {
		while(true) {
			if (ExplorationThread.getRunning() || FastestPathThread.getRunning()) {
				try {
					thread.join();
				}
				catch (Exception e) {
					System.out.println("Error in start ConnectionManager");
				}
			}
			else {
				waitingForMessage();
			}
//			System.out.println("I am out");
		}
	}
	
	public static ArrayList<String> getBuffer(){
		return buffer;
	}
	
	public String waitingForMessage() {
		// Start Exploration/ Fastest Path/ Send_Arena/ Initializing/ Sensor Values
		String s = "";
		boolean complete = false;
		Pattern sensorPattern = Pattern.compile("\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+"); 
		while (!complete) {
			System.out.println("Waiting for orders");
			s = this.connectionSocket.receiveMessage().trim();
			System.out.print("Received message: ");
			System.out.println(s);
			if (!ExplorationThread.getRunning() && !FastestPathThread.getRunning() && s.contains(Constant.INITIALISING)) {
				Pattern p_s_s = Pattern.compile(Constant.INITIALISING + "[\\s]\\([1-9],[1-9],[0-3]{1}\\)");
				Pattern p_d_s = Pattern.compile(Constant.INITIALISING + "[\\s]\\([1][0-9],[1-9],[0-3]{1}\\)");
				Pattern p_s_d = Pattern.compile(Constant.INITIALISING + "[\\s]\\([1-9],[1][0-4],[0-3]{1}\\)");
				Pattern p_d_d = Pattern.compile(Constant.INITIALISING + "[\\s]\\([1][1-9],[1][0-4],[0-3]{1}\\)");
				if ((p_s_s.matcher(s).matches() || p_d_s.matcher(s).matches() || p_s_d.matcher(s).matches() || p_d_d.matcher(s).matches())){
					complete = true;
					String tmp = s.replace(Constant.INITIALISING + " (", "");
					tmp = tmp.replace(")", "");
					String[] arr = tmp.trim().split(",");
					robot.initialise(Integer.parseInt(arr[1]), Integer.parseInt(arr[0]), (Integer.parseInt(arr[2]) + 1 ) % 4);
					s = "Successfully set the robot's position: " + Integer.parseInt(arr[0]) + 
						"," + Integer.parseInt(arr[1]) + "," + Integer.parseInt(arr[2]);
					System.out.println(s);
				}
			}
			else if (!ExplorationThread.getRunning() && !FastestPathThread.getRunning() && s.equals(Constant.START_EXPLORATION) ) {
				s = "Exploration Started";
				thread = ExplorationThread.getInstance(robot, Constant.TIME, Constant.PERCENTAGE, Constant.SPEED, Constant.IMAGE_REC);
				thread.setPriority(Thread.MAX_PRIORITY);
				
				complete = true;
				
				try {
					thread.join();
				}
				catch(Exception e) {
					System.out.println("Error in start exploration in ConnectionManager");
				}
//					connectionSocket.sendMessage(Constant.END_TOUR);
			}
			else if (!ExplorationThread.getRunning() && !FastestPathThread.getRunning() && s.equals(Constant.FASTEST_PATH) ){
				thread = FastestPathThread.getInstance(robot, robot.getWaypoint(), 1);
				thread.setPriority(Thread.MAX_PRIORITY);
				
				s = "Fastest Path started";
				try {
					thread.join();
					
				}
				catch(Exception e) {
					System.out.println("Error in fastest path in ConnectionManager");
				}
				complete = true;
			}
			else if (!ExplorationThread.getRunning() && !FastestPathThread.getRunning() && 
					s.contains(Constant.SETWAYPOINT) && ExplorationThread.getCompleted()) {
				Pattern wp_s_s = Pattern.compile(Constant.SETWAYPOINT + " \\([1-9],[1-9]\\)");
				Pattern wp_d_s = Pattern.compile(Constant.SETWAYPOINT + " \\([1][0-9],[1-9]\\)");
				Pattern wp_s_d = Pattern.compile(Constant.SETWAYPOINT + " \\([1-9],[1][0-4]\\)");
				Pattern wp_d_d = Pattern.compile(Constant.SETWAYPOINT + " \\([1][1-9],[1][0-4]\\)");
				if ((wp_s_s.matcher(s).matches() || wp_d_s.matcher(s).matches() || wp_s_d.matcher(s).matches() || wp_d_d.matcher(s).matches())){
					complete = true;
					String tmp = s.replace(Constant.SETWAYPOINT + "(", "");
					tmp = tmp.replace(")", "");
					String[] arr = tmp.trim().split(",");
					robot.setWaypoint(Integer.parseInt(arr[1]), Integer.parseInt(arr[0]));
					s = "Successfully set the waypoint: " + Integer.parseInt(arr[0]) + 
							"," + Integer.parseInt(arr[1]);
					System.out.println(s);
				}
			}
			else if (s.equals(Constant.SEND_ARENA)) {
				String[] arr = robot.getMDFString();
				connectionSocket.sendMessage("{\"map\":[{\"explored\": \"" + arr[0] + "\",\"length\":" + arr[1] + ",\"obstacle\":\"" + arr[2] +
						"\"}]}");
				complete = true;
			}
			else if (Arrays.asList(bufferableCommand).contains(s) || sensorPattern.matcher(s).matches()) {
				// If the command is an acknowledgement or sensor values, put into buffer
				buffer.add(s);
				System.out.println("Placed command" + s + " into buffer");
			}
			else {
				System.out.println("Unknown command: " + s);
			}
			
		}
		return s;
	}
}

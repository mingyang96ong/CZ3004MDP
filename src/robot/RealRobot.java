package robot;

import java.util.ArrayList;

import java.util.regex.Pattern;
import config.Constant;
import connection.ConnectionManager;
import connection.ConnectionSocket;
import map.Map;

public class RealRobot extends Robot{
	private ConnectionSocket connectionSocket = ConnectionSocket.getInstance();
	private static RealRobot r = null;
	
	private RealRobot() {
		super();
		this.map = new Map();
		initialise(1,1,Constant.SOUTH);
	}
	
	
	public static RealRobot getInstance() {
		if (r == null) {
			r = new RealRobot();
		}
		return r;
	}

	@Override
	protected String[] getSensorValues() {
		// FL, FM, FR, RB, RF, LF
		
		Pattern sensorPattern = Pattern.compile("\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+"); 
		Pattern sensorPattern2 = Pattern.compile("\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+");
		String s;
		String[] arr = null;
		
		connectionSocket.sendMessage(Constant.SENSE_ALL);
		boolean completed = false;
		ArrayList<String> buffer = ConnectionManager.getBuffer();
		while (!completed) {
			s = connectionSocket.receiveMessage().trim();
			System.out.println("In get sensor values");
			System.out.println(s);
			if (sensorPattern.matcher(s).matches() || sensorPattern2.matcher(s).matches()) {
				arr = s.split("\\|");
				break;
			}
			else {
				if (s.equals(Constant.SEND_ARENA)) {
					buffer.add(s);
				}
				for (int i = 0; i < buffer.size(); i++) {
					if (sensorPattern.matcher(buffer.get(i)).matches()) {
						arr = sensorPattern.split("|");
						completed = true;
						buffer.remove(i);
						break;
					}
				}
			}
		}
		return arr;
	}


	@Override
	public void forward(int step) {
		connectionSocket.sendMessage("W" + Integer.toString(step)+ "|");
		boolean completed = false;
		ArrayList<String> buffer = ConnectionManager.getBuffer();
		while (!completed) {
			String s = connectionSocket.receiveMessage().trim();
			completed = checkForwardAcknowledge(s);
			
			if (completed) {
				break;
			}
			else {
				if (s.equals(Constant.SEND_ARENA)) {
					buffer.add(s);
				}
				for (int i = 0; i < buffer.size(); i++) {
					completed = checkForwardAcknowledge(buffer.get(i));
					if (completed) {
						buffer.remove(i);
						break;
					}
				}
			}
		}
		toggleValid();
	}
	
	private boolean checkForwardAcknowledge(String s) {
		if (s.equals(Constant.FORWARD_ACK)) {
			this.x = checkValidX(this.x + Constant.SENSORDIRECTION[this.getDirection()][0]);
			this.y = checkValidX(this.y + Constant.SENSORDIRECTION[this.getDirection()][1]);
			return true;
		}
		return false;
	}

	@Override
	public void rotateRight() {
		connectionSocket.sendMessage(Constant.TURN_RIGHT);
		boolean completed = false;
		String s;
		ArrayList <String> buffer = ConnectionManager.getBuffer();
		while (!completed) {
			s = connectionSocket.receiveMessage().trim();
			completed = checkRightAcknowledge(s);
			if (completed) {
				break;
			}
			else {
				for (int i = 0; i < buffer.size(); i++) {
					completed = checkRightAcknowledge(buffer.get(i));
					if (completed) {
						buffer.remove(i);
						break;
					}
				}
			}
		}
	}
	
	private boolean checkRightAcknowledge(String s) {
		if (s.equals(Constant.RIGHT_ACK)) {
			setDirection((this.getDirection() + 1) % 4);
			return true;
		}
		return false;
	}

	@Override
	public void rotateLeft() {
		connectionSocket.sendMessage(Constant.TURN_LEFT);
		boolean completed = false;
		String s;
		ArrayList <String> buffer = ConnectionManager.getBuffer();
		while (!completed) {
			s = connectionSocket.receiveMessage().trim();
			completed = checkLeftAcknowledge(s);
			if (completed) {
				break;
			}
			else {
				for (int i = 0; i < buffer.size(); i++) {
					completed = checkLeftAcknowledge(buffer.get(i));
					if (completed) {
						buffer.remove(i);
						break;
					}
				}
			}
		}
	}
	
	private boolean checkLeftAcknowledge(String s) {
		if (s.equals(Constant.LEFT_ACK)) {
			setDirection((this.getDirection() + 3) % 4);
			return true;
		}
		return false;
	}
	
	public void captureImage() {
		connectionSocket.sendMessage("C[" + y + "," + x + "," + ((this.getDirection()+3) % 4) + "]"); // This is to do converting for Real Run
		boolean completed = false;
		String s;
		ArrayList <String> buffer = ConnectionManager.getBuffer();
		while (!completed) {
			s = connectionSocket.receiveMessage().trim();
			completed = checkImageAcknowledge(s);
			if (completed) {
				break;
			}
			else {
				for (int i = 0; i < buffer.size(); i++) {
					completed = checkImageAcknowledge(buffer.get(i));
					if (completed) {
						buffer.remove(i);
						break;
					}
				}
			}
		}
	}
	
	private boolean checkImageAcknowledge(String s) {
		if (s.equals(Constant.IMAGE_ACK)) {
			return true;
		}
		return false;
	}
}

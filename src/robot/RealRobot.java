package robot;

import java.util.ArrayList;

import java.util.regex.Pattern;
import config.Constant;
import connection.ConnectionManager;
import connection.ConnectionSocket;
import map.Map;
import sensor.RealSensor;

public class RealRobot extends Robot{
	private ConnectionSocket connectionSocket = ConnectionSocket.getInstance();
	private static RealRobot r = null;
	
	private RealRobot() {
		super();
		this.map = new Map();
		initialise(1,1,Constant.SOUTH);
		this.sensor = new RealSensor();
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

		Pattern sensorPattern = Pattern.compile("\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+");
		Pattern sensorPattern2 = Pattern.compile("\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+");
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
		System.arraycopy(arr, 0, sensorValues, 0, 6);
//		this.sensorValues = arr;
		this.sensePosition[0] = x;
		this.sensePosition[1] = y;
		this.sensePosition[2] = getDirection();
		return arr;
	}

	private boolean acknowledge(){
		Pattern sensorPattern = Pattern.compile("\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+");
		Pattern sensorPattern2 = Pattern.compile("\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+");
		String s;
		String[] arr = null;

//		connectionSocket.sendMessage(Constant.SENSE_ALL);
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
		
		System.out.print("The SensorValues received: \n");
		for (int i = 0; i < 6; i ++) {
			System.out.print(sensorValues[i]);
			if (i != sensorValues.length - 1 ) {
				System.out.print(" ");
			}
		}
		
		if (Integer.parseInt(arr[6]) == 1) {
			System.arraycopy(arr, 0, sensorValues, 0, 6);
			this.sensorValues = arr;
			this.sensePosition[0] = x;
			this.sensePosition[1] = y;
			this.sensePosition[2] = getDirection();
			String[] arr2 = this.getMDFString();
			connectionSocket.sendMessage("M{\"map\":[{\"explored\": \"" + arr2[0] + "\",\"length\":" + arr2[1] + ",\"obstacle\":\"" + arr2[2] +
					"\"}]}");
			return true;
		}
		return false;
//		System.out.println("{\"map\":[{\"explored\": \"" + arr2[0] + "\",\"length\":" + arr2[1] + ",\"obstacle\":\"" + arr2[2] +
//				"\"}]}");
		
	}

	@Override
	public void forward(int step) {
		connectionSocket.sendMessage("W" + Integer.toString(step)+ "|");
		toggleValid();
		if (acknowledge()) {
			this.x = checkValidX(this.x + Constant.SENSORDIRECTION[this.getDirection()][0]);
			this.y = checkValidX(this.y + Constant.SENSORDIRECTION[this.getDirection()][1]);
		}
	}
	
//	private boolean checkForwardAcknowledge(String s) {
//		if (s.equals(Constant.FORWARD_ACK)) {
//			this.x = checkValidX(this.x + Constant.SENSORDIRECTION[this.getDirection()][0]);
//			this.y = checkValidX(this.y + Constant.SENSORDIRECTION[this.getDirection()][1]);
//			return true;
//		}
//		return false;
//	}

	@Override
	public void rotateRight() {
		connectionSocket.sendMessage(Constant.TURN_RIGHT);
		setDirection((this.getDirection() + 1) % 4);
		acknowledge();
	}
	
//	private boolean checkRightAcknowledge(String s) {
//		if (s.equals(Constant.RIGHT_ACK)) {
//			setDirection((this.getDirection() + 1) % 4);
//			return true;
//		}
//		return false;
//	}

	@Override
	public void rotateLeft() {
		connectionSocket.sendMessage(Constant.TURN_LEFT);
		setDirection((this.getDirection() + 3) % 4);
//		boolean completed = false;
//		String s;
//		ArrayList <String> buffer = ConnectionManager.getBuffer();
//		while (!completed) {
//			s = connectionSocket.receiveMessage().trim();
//			completed = checkLeftAcknowledge(s);
//			if (completed) {
//				break;
//			}
//			else {
//				for (int i = 0; i < buffer.size(); i++) {
//					completed = checkLeftAcknowledge(buffer.get(i));
//					if (completed) {
//						buffer.remove(i);
//						break;
//					}
//				}
//			}
//		}
		acknowledge();
	}
	
//	private boolean checkLeftAcknowledge(String s) {
//		if (s.equals(Constant.LEFT_ACK)) {
//			setDirection((this.getDirection() + 3) % 4);
//			return true;
//		}
//		return false;
//	}
	
	public boolean captureImage(int[][] image_pos) {
//		connectionSocket.sendMessage("C[" + y + "," + x + "," + ((this.getDirection()+3) % 4) + "]"); // This is to do converting for Real Run
		connectionSocket.sendMessage("C["+ image_pos[0][1] + "," + image_pos[0][0] + "|" + image_pos[1][1] + "," + image_pos[1][0] +
				"|" + image_pos[2][1] + "," + image_pos[2][0] + "]"); // This is left middle right. x and y is inverted in Real Run.

		boolean completed = false;
		String s;
		ArrayList <String> buffer = ConnectionManager.getBuffer();
		while (!completed) {
			s = connectionSocket.receiveMessage().trim();
			completed = checkImageAcknowledge(s);
			if (completed && s.equals(Constant.IMAGE_ACK)) {
				System.out.println(s);
				return false;
			}
			else if (completed && s.equals(Constant.IMAGE_STOP)){
				System.out.println(s);
				return true;
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
			System.out.println(s);
		}
		return false;
	}
	
	private boolean checkImageAcknowledge(String s) {
		if (s.equals(Constant.IMAGE_ACK) || s.equals(Constant.IMAGE_STOP)) {
			return true;
		}
		return false;
	}

	public void calibrate() {
		connectionSocket.sendMessage(Constant.CALIBRATE);
		acknowledge();
	}

	public void right_align() {
		connectionSocket.sendMessage(Constant.RIGHTALIGN);
		acknowledge();
	}
}

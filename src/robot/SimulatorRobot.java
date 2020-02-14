package robot;
import java.util.Timer;

import javax.swing.JFrame;

import config.Constant;
import map.*;
import imagecomponent.RobotImageComponent;
import imagecomponent.ImageComponent;
import sensor.SimulatorSensor;
import main.AddJButtonActionListener;
import timertask.MoveImageTask;

public class SimulatorRobot extends Robot{
	
	private ImageComponent robotImage;
	private JFrame frame;
	private SimulatorMap smap;
	private AddJButtonActionListener buttonListener;
	private Timer t = new Timer();
	private int delay = 25;
	
	public SimulatorRobot(JFrame frame, int x, int y, String direction) {
		super(x, y, direction);
		this.frame = frame;
		this.sensor = new SimulatorSensor();
		
		/* THE ORDER OF ADDING THE ROBOT INTO THE FRAME MATTERS OTHERWISE IT WILL APPEAR UNDERNEATH THE GRID
		 * Hence, initialiseRobotImage must run before SimulatorMap create the UI grid
		 */
		
		initialiseRobotImage(this.x, this.y);
		map = new Map();
		smap = SimulatorMap.getInstance(frame, map.copy());
		buttonListener = new AddJButtonActionListener(frame, this);
		setDirection(direction);
	}
	
	private void initialiseRobotImage(int x, int y) {
		robotImage = new RobotImageComponent(Constant.ROBOTIMAGEPATH, Constant.ROBOTWIDTH, Constant.ROBOTHEIGHT);
		frame.add(robotImage);
		robotImage.setLocation(Constant.MARGINLEFT + Constant.GRIDWIDTH/2 + (x-1) * Constant.GRIDWIDTH, Constant.MARGINTOP + Constant.GRIDHEIGHT/2 + (y-1) * Constant.GRIDHEIGHT);
	}

	protected String[] getSensorValues() {
		// TODO Auto-generated method stub
		// FL, FM, FR, RB, RF, LF
		String[] sensorValues = sensor.getAllSensorsValue(this.x, this.y, getDirection());
		return sensorValues;
	}

	@Override
	public void setDirection(String direction) {
		// TODO Auto-generated method stub
		super.setDirection(direction);
		for (int i = 0; i < Constant.POSSIBLEROBOTDIRECTION.length; i++) {
			if (direction.compareTo(Constant.POSSIBLEROBOTDIRECTION[i]) == 0) {
				robotImage.setImage(Constant.ROBOTIMAGEPATHS[i]);
				return;
			}
		}
	}
	
	public boolean[] updateMap() {
		boolean[] isObstacle = super.updateMap();
		smap.setMap(map);
		return isObstacle;
	}
	
	
	
	public void moveUp() {
		// TODO Auto-generated method stub
		int step = 1;
		if (getDirection().compareTo(Constant.POSSIBLEROBOTDIRECTION[0]) != 0) {
			setDirection(Constant.POSSIBLEROBOTDIRECTION[0]);
			updateMap();
		}
		else {
			this.y = checkValidY(this.y - 1);
			for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
				t.schedule(new MoveImageTask(robotImage, "Up", 1), delay * (i + 1));
			}
			updateMap();
		}
	}
	

	public void moveRight() {
		// TODO Auto-generated method stub
		int step = 1;
		if (getDirection().compareTo(Constant.POSSIBLEROBOTDIRECTION[1]) != 0) {
			setDirection(Constant.POSSIBLEROBOTDIRECTION[1]);
			updateMap();
		}
		else {
			this.x = checkValidX(this.x + 1);
			for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
				t.schedule(new MoveImageTask(robotImage, "Right", 1), delay * (i + 1));
			}
			updateMap();
		}
	}
	
	public void moveDown() {
		// TODO Auto-generated method stub
		int step = 1;
		if (getDirection().compareTo(Constant.POSSIBLEROBOTDIRECTION[2]) != 0) {
			setDirection(Constant.POSSIBLEROBOTDIRECTION[2]);
			updateMap();
		}
		else {
			this.y = checkValidY(this.y + 1);
			for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
				t.schedule(new MoveImageTask(robotImage, "Down", 1), delay * (i + 1));
			}
			updateMap();
		}
	}

	public void moveLeft() {
		// TODO Auto-generated method stub
		int step = 1;
		if (getDirection().compareTo(Constant.POSSIBLEROBOTDIRECTION[3]) != 0) {
			setDirection(Constant.POSSIBLEROBOTDIRECTION[3]);
			updateMap();
		}
		else {
			this.x = checkValidX(this.x - 1);
			for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
				t.schedule(new MoveImageTask(robotImage, "Left", 1), delay * (i + 1));
			}
			updateMap();
		}
	}
	
	
	// Simulator purposes
	public String checkMap() {
		Map temp = sensor.getTrueMap();
		if (Map.compare(temp, map)) {
			return "The maps are the same!";
		}
		else {
			return "The maps are different!";
		}
	}
	
	public String toggleMap() {
		Map temp = sensor.getTrueMap();
		if (Map.compare(temp, smap.getMap())) {
			smap.setMap(map.copy());
			return "robot";
		}
		else {
			smap.setMap(temp.copy());
			return "simulated";
		}
	}
	public void restartRobot() {
		this.x = checkValidX(0);
		this.y = checkValidY(0);
		robotImage.setLocation(Constant.MARGINLEFT + Constant.GRIDWIDTH/2 + (x-1) * Constant.GRIDWIDTH, Constant.MARGINTOP + Constant.GRIDHEIGHT/2 + (y-1) * Constant.GRIDHEIGHT);
		setDirection(Constant.POSSIBLEROBOTDIRECTION[2]);
		this.sensor = new SimulatorSensor();
		this.map = new Map();
		smap.setMap(map);
	}

	@Override
	public void forward() {
		// TODO Auto-generated method stub
		int directionIndex = super.getDirectionIndex(), step = 1;
		String s;
		this.x = checkValidX(this.x + Constant.SENSORDIRECTION[directionIndex][0]);
		this.y = checkValidX(this.y + Constant.SENSORDIRECTION[directionIndex][1]);
		switch (directionIndex) {
			case 0:
				s = "Up";
				break;
			case 1:
				s = "Right";
				break;
			case 2:
				s = "Down";
				break;
			case 3:
				s = "Left";
				break;
			default:
				s = "Error";
		}
		for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
			t.schedule(new MoveImageTask(robotImage, s, 1), delay * (i + 1));
		}
	}

	@Override
	public void rotateRight() {
		// TODO Auto-generated method stub
		int directionIndex = super.getDirectionIndex();
		// In the actual robot, this will also send the command to rotate right
		setDirection(Constant.POSSIBLEROBOTDIRECTION[(directionIndex+1) % Constant.POSSIBLEROBOTDIRECTION.length]);
	}

	@Override
	public void rotateLeft() {
		// TODO Auto-generated method stub
		int directionIndex = super.getDirectionIndex();
		// In the actual robot, this will also send the command to rotate left
		setDirection(Constant.POSSIBLEROBOTDIRECTION[(directionIndex-1+Constant.POSSIBLEROBOTDIRECTION.length) % Constant.POSSIBLEROBOTDIRECTION.length]);
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
}

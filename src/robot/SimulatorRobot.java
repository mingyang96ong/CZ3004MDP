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
	
	public SimulatorRobot(JFrame frame, int x, int y, int direction) {
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
	public void setDirection(int direction) {
		// TODO Auto-generated method stub
		super.setDirection(direction);
		robotImage.setImage(Constant.ROBOTIMAGEPATHS[direction]);
	}
	
	public boolean[] updateMap() {
		boolean[] isObstacle = super.updateMap();
		smap.setMap(map);
		return isObstacle;
	}
	
	
	
	public void moveUp() {
		// TODO Auto-generated method stub
		int step = 1;
		if (getDirection() == Constant.NORTH) {
			setDirection(Constant.NORTH);
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
		setDirection(Constant.SOUTH);
		this.sensor = new SimulatorSensor();
		this.map = new Map();
		smap.setMap(map);
	}

	@Override
	public void forward() {
		// TODO Auto-generated method stub
		int step = 1;
		String s;
		this.x = checkValidX(this.x + Constant.SENSORDIRECTION[this.getDirection()][0]);
		this.y = checkValidX(this.y + Constant.SENSORDIRECTION[this.getDirection()][1]);
		switch (this.getDirection()) {
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
		// In the actual robot, this will also send the command to rotate right
		setDirection((this.getDirection()+1) % 4);
	}

	@Override
	public void rotateLeft() {
		// TODO Auto-generated method stub
		// In the actual robot, this will also send the command to rotate left
		setDirection((this.getDirection() + 3) % 4);
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
}

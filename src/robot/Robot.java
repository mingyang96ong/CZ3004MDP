package robot;
import config.Constant;
import connection.ConnectionSocket;
import map.Map;
import sensor.Sensor;

import java.io.*;

public abstract class Robot {
	
	// This assumes the Robot have 3 front sensors, 2 left sensor and 1 right far sensor
	
	protected Sensor sensor;
	
	private int direction;
	
	protected int x, y;

	protected Map map;
	
	protected boolean validObstacleValue;
	
	protected int[] isObstacle = new int[6];

	protected String[] sensorValues = new String[6];

	protected int[] sensePosition = new int[] {-1, -1, -1};

	private OutputStreamWriter writer;

	public Robot() {
	}
	
	public void initialise(int x, int y, int direction) {
		this.x = checkValidX(x);
		this.y = checkValidY(y);
		this.direction = direction;
		this.validObstacleValue = false;

		try {
			this.writer = new OutputStreamWriter( new FileOutputStream("Output.txt"), "UTF-8");
			writer.write("");
		}
		catch (Exception e) {
			System.out.println("Unable to write into output");
		}
	}
	
	protected abstract String[] getSensorValues();
	public abstract void forward(int step);
	public abstract void rotateRight();
	public abstract void rotateLeft();

	public abstract void captureImage(int[][] image_pos);
	public abstract void calibrate();
	
	public void setDirection(int direction) {
		this.direction = direction;
		toggleValid();
	}
	
	protected int checkValidX(int x) {
		if (x >= Constant.BOARDWIDTH - 1) {
			x = Constant.BOARDWIDTH - 2;
		}
		if (x <= 0) {
			x = 1;
		}
		
		return x;
	}
	
	protected int checkValidY(int y) {
		if (y >= Constant.BOARDHEIGHT - 1) {
			y = Constant.BOARDHEIGHT - 2;
		}
		
		if (y <= 0) {
			y = 1;
		}
		return y;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int[] getPosition() {
		return new int[] {x, y};
	}
	

	
//	public int[] updateMap2() {
//		if (validObstacleValue) {
//			return this.isObstacle;
//		}
//		Map newMap = map;
////		System.out.println(this.x);
////		System.out.println(this.y);
//		Sensor.updateSensorDirection(this.getDirection());
//		if (!(sensePosition[0] == x && sensePosition[1] == y && sensePosition[2] == direction) || !ConnectionSocket.checkConnection()){
//			this.sensorValues = getSensorValues(); // THIS VALUES IS BY CM (GRID * 10)
//		}
//		int [][] sensorLocation = Sensor.sensorLocation;
//		int [][] sensorDirection = Sensor.sensorDirection;
//		int sensorDirectionValueX, sensorDirectionValueY, s, e;
//		boolean isObstacle[] = new boolean[6];
//
//		System.out.print("The SensorValues are: \n");
//		for (int i = 0; i < sensorValues.length; i ++) {
//			System.out.print(sensorValues[i]);
//			if (i != sensorValues.length - 1 ) {
//				System.out.print(" ");
//			}
//		}
//		System.out.println("\n");
//		setGridDist(newMap);
//		for (int i = 0; i < sensorValues.length; i++) {
//			double value = Double.parseDouble(sensorValues[i]);
//			switch(i) {
//				case 0:
//				case 2:
//					sensorDirectionValueX = sensorDirection[0][0];
//					sensorDirectionValueY = sensorDirection[0][1];
//					s = Constant.SHORTSENSOROFFSET;
//					e = Constant.SHORTSENSORMAXRANGE * 10;
//					break;
//				case 1:
//					sensorDirectionValueX = sensorDirection[0][0];
//					sensorDirectionValueY = sensorDirection[0][1];
//					s = 1;
//					e = Constant.SHORTSENSORMAXRANGE * 10;
//					break;
//				case 3:
//				case 4:
//					sensorDirectionValueX = sensorDirection[1][0];
//					sensorDirectionValueY = sensorDirection[1][1];
//					s = Constant.SHORTSENSOROFFSET - 1;
//					e = Constant.SHORTSENSORMAXRANGE * 10;
//					break;
//				case 5:
//					sensorDirectionValueX = sensorDirection[2][0];
//					sensorDirectionValueY = sensorDirection[2][1];
//					s = 7;
//					e = Constant.FARSENSORMAXRANGE * 10;
//					break;
//				default:
//					if (i < sensorValues.length-1) {
//						if (i < 3) {
//							sensorDirectionValueX = sensorDirection[0][0];
//							sensorDirectionValueY = sensorDirection[0][1];
//						}
//						else {
//							sensorDirectionValueX = sensorDirection[1][0];
//							sensorDirectionValueY = sensorDirection[1][1];
//						}
//						s = Constant.SHORTSENSOROFFSET;
//						e = Constant.SHORTSENSORMAXRANGE * 10;
//					}
//					else {
//						sensorDirectionValueX = sensorDirection[2][0];
//						sensorDirectionValueY = sensorDirection[2][1];
//						s = Constant.FARSENSOROFFSET;
//						e = Constant.FARSENSORMAXRANGE * 10;
//					}
//
//			}
//
//			if (value > e) {
//				isObstacle[i] = false;
//			}
//			for (int d = s, g = 1; d <= e; d = d + 10, g++) {
//				int x = this.x + sensorLocation[i][0] + sensorDirectionValueX * g;
//				int y = this.y + sensorLocation[i][1] + sensorDirectionValueY * g;
////				String gridType = newMap.getGrid(x, y);
//				double old_dist = newMap.getDist(x, y);
//
//				if (value >= d && value < d + 10 && value <= e) {
////					updateMapGridObstacle(newMap, x, y, gridType);
//					if (more_accurate(value, old_dist)) {
//						newMap.setGrid(x, y, Constant.OBSTACLE);
//						newMap.setDist(x, y, value);
//					}
//					if (g == 1) {
//						isObstacle[i] = true;
//					}
//					break;
//				}
//				else {
////					updateMapGridExplored(newMap, x, y, gridType);
//					if (more_accurate(value, old_dist)) {
//						newMap.setGrid(x, y, Constant.EXPLORED);
//						newMap.setDist(x, y, (10* (g - 1)) + value%10);
//					}
//				}
//			}
//		}
//		for (int i = 0; i < isObstacle.length; i++) {
//			System.out.print(isObstacle[i]);
//			System.out.print(" ");
//		}
//		System.out.println();
//
//		try {
//			File file = new File("Output.txt");
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			String st = "", tmp = "";
//			while ((tmp = br.readLine()) != null) {
//				st += tmp + "\n";
//			}
//			this.writer = new OutputStreamWriter( new FileOutputStream("Output.txt"), "UTF-8");
//			writer.write(st + "\n\n");
//			writer.write("Pos : [" + x + ", " + y + ", " + direction + "]\n");
//			writer.write("The sensor values are: ");
//			for (int i = 0; i < 6; i++){
//				writer.write(sensorValues[i] + " ");
//			}
//			writer.write("\n");
//
//
//			writer.write(newMap.print() + "\r\n\n");
//			writer.close();
//		}
//		catch (Exception error) {
//			System.out.println("Unable to write in output.txt");
//		}
//
//		newMap.print();
//		System.arraycopy(isObstacle, 0, this.isObstacle, 0, isObstacle.length);
//		validObstacleValue = true;
//		return isObstacle;
//	}

	public int[] updateMap() {
		if (validObstacleValue) {
			return this.isObstacle;
		}
		Map newMap = map;
//		System.out.println(this.x);
//		System.out.println(this.y);
		Sensor.updateSensorDirection(this.getDirection());
		if (!(sensePosition[0] == x && sensePosition[1] == y && sensePosition[2] == direction) || !ConnectionSocket.checkConnection()){
			this.sensorValues = getSensorValues(); // THIS VALUES IS BY CM (GRID * 10)
		}
		int [][] sensorLocation = Sensor.sensorLocation;
		int [][] sensorDirection = Sensor.sensorDirection;
		int sensorDirectionValueX, sensorDirectionValueY, s, e;
		int[] isObstacle = new int[] {-1, -1, -1, -1, -1, -1};

		System.out.print("The SensorValues are: \n");
		for (int i = 0; i < sensorValues.length; i ++) {
			System.out.print(sensorValues[i]);
			if (i != sensorValues.length - 1 ) {
				System.out.print(" ");
			}
		}
		System.out.println("\n");
		setGridDist(newMap);
		for (int i = 0; i < sensorValues.length; i++) {
			double value = Double.parseDouble(sensorValues[i]);
			switch(i) {
				case 0:
				case 2:
					sensorDirectionValueX = sensorDirection[0][0];
					sensorDirectionValueY = sensorDirection[0][1];
					s = Constant.SHORTSENSOROFFSET;
					e = Constant.SHORTSENSORMAXRANGE * 10;
					break;
				case 1:
					sensorDirectionValueX = sensorDirection[0][0];
					sensorDirectionValueY = sensorDirection[0][1];
					s = 1;
					e = Constant.SHORTSENSORMAXRANGE * 10;
					break;
				case 3:
				case 4:
					sensorDirectionValueX = sensorDirection[1][0];
					sensorDirectionValueY = sensorDirection[1][1];
					s = Constant.SHORTSENSOROFFSET - 1;
					e = Constant.SHORTSENSORMAXRANGE * 10;
					break;
				case 5:
					sensorDirectionValueX = sensorDirection[2][0];
					sensorDirectionValueY = sensorDirection[2][1];
					s = 7;
					e = Constant.FARSENSORMAXRANGE * 10;
					break;
				default:
					if (i < sensorValues.length-1) {
						if (i < 3) {
							sensorDirectionValueX = sensorDirection[0][0];
							sensorDirectionValueY = sensorDirection[0][1];
						}
						else {
							sensorDirectionValueX = sensorDirection[1][0];
							sensorDirectionValueY = sensorDirection[1][1];
						}
						s = Constant.SHORTSENSOROFFSET;
						e = Constant.SHORTSENSORMAXRANGE * 10;
					}
					else {
						sensorDirectionValueX = sensorDirection[2][0];
						sensorDirectionValueY = sensorDirection[2][1];
						s = Constant.FARSENSOROFFSET;
						e = Constant.FARSENSORMAXRANGE * 10;
					}

			}

//			if (value > e) {
//				isObstacle[i] = false;
//			}
			double[] sensor_thres = Constant.SENSOR_RANGES[i];
			for (int h = 0; h < sensor_thres.length; h++) {
				int g = h+1;
				int x = this.x + sensorLocation[i][0] + sensorDirectionValueX * g;
				int y = this.y + sensorLocation[i][1] + sensorDirectionValueY * g;
//				String gridType = newMap.getGrid(x, y);
				double old_dist = newMap.getDist(x, y);

				if (value <= sensor_thres[h]) {
//					updateMapGridObstacle(newMap, x, y, gridType);
					if (more_accurate(value, old_dist)) {
						newMap.setGrid(x, y, Constant.OBSTACLE);
						newMap.setDist(x, y, g);
					}
//					if (g == 1) {
//						isObstacle[i] = true;
//					}
					isObstacle[i] = g;
					break;
				}
				else {
//					updateMapGridExplored(newMap, x, y, gridType);
					if (more_accurate(value, old_dist)) {
						newMap.setGrid(x, y, Constant.EXPLORED);
						newMap.setDist(x, y, g);
					}
				}
			}
		}
		for (int i = 0; i < isObstacle.length; i++) {
			System.out.print(isObstacle[i]);
			System.out.print(" ");
		}
		System.out.println();

		try {
			File file = new File("Output.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st = "", tmp = "";
			while ((tmp = br.readLine()) != null) {
				st += tmp + "\n";
			}
			this.writer = new OutputStreamWriter( new FileOutputStream("Output.txt"), "UTF-8");
			writer.write(st + "\n\n");
			writer.write("Pos : [" + x + ", " + y + ", " + direction + "]\n");
			writer.write("The sensor values are: ");
			for (int i = 0; i < 6; i++){
				writer.write(sensorValues[i] + " ");
			}
			writer.write("\n");


			writer.write(newMap.print() + "\r\n\n");
			writer.close();
		}
		catch (Exception error) {
			System.out.println("Unable to write in output.txt");
		}

//		newMap.print();
		System.arraycopy(isObstacle, 0, this.isObstacle, 0, isObstacle.length);
		validObstacleValue = true;
		return isObstacle;
	}
	
	private void setGridDist(Map map) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				map.setDist(i+x, j+y, -1);
				if (!((map.getGrid(i+x, j+y).equals(Constant.STARTPOINT)) ||
						(map.getGrid(i+x, j+y).equals(Constant.ENDPOINT)))){
					map.setGrid(i+x, j+y, Constant.EXPLORED);
				}
			}
		}
	}

	private boolean more_accurate(double new_dist, double old_dist) {
		if (new_dist < old_dist) {
			return true;
		} else {
			return false;
		}
	}
	
	private void updateMapGridObstacle(Map newMap, int x, int y, String gridType) {

		if (gridType.compareTo(Constant.POSSIBLEGRIDLABELS[0]) == 0) {
			// Means it is the obstacle and u cannot see anything behind the obstacle
			// Prevent overwriting waypoint
			newMap.setGrid(x, y, Constant.POSSIBLEGRIDLABELS[2]);
		}

		if (gridType.compareTo(Constant.POSSIBLEGRIDLABELS[1]) == 0) {
			// Just for debugging simulator sensor and update map
			System.out.println("ERROR in ROBOT UPDATE MAP: EXPECTED OBSTACLE BUT ALREADY LABELLED EXPLORED");
		}
	}
	
	private void updateMapGridExplored(Map newMap, int x, int y, String gridType) {
		
		if (gridType.compareTo(Constant.POSSIBLEGRIDLABELS[0]) == 0) {
			// Means it is unexplored
			// Prevent overwriting waypoint
			newMap.setGrid(x, y, Constant.POSSIBLEGRIDLABELS[1]);
		}
		
		if (gridType.compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
			System.out.println("ERROR in ROBOT UPDATE MAP: EXPECTED EXPLORED BUT ALREADY LABELLED OBSTACLE");
		}
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setWaypoint(int x, int y) {
		this.map.setWayPoint(x, y);
	}
	
	public int[] getWaypoint() {
		return map.getWayPoint();
	}
	
	public void setTrueMap(Map map) {
		this.sensor.setTrueMap(map);
	}
	
	public String[] getMDFString() {
		return map.getMDFString();
	}
	
	protected void toggleValid() {
		validObstacleValue = false;
	}
}

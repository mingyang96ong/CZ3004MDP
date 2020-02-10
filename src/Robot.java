
public abstract class Robot {
	
	// This assumes the Robot have 3 front sensors, 2 left sensor and 1 right far sensor
	protected Sensor sensor;
	
	private String direction;
	
	protected int x, y;
	
	protected Map map;
	
	public Robot(int x, int y, String direction) {
		this.x = checkValidX(x);
		this.y = checkValidY(y);
		this.direction = direction;
	}
	
	protected abstract String[] getSensorValues();
	public abstract void forward();
	public abstract void rotateRight();
	public abstract void rotateLeft();
	
	public void setDirection(String direction) {
		this.direction = direction;
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
	
	public String getDirection() {
		return direction;
	}
	
	public int[] getPosition() {
		return new int[] {x, y};
	}
	

	
	public boolean[] updateMap() {
		Map newMap = map;
		System.out.println(this.x);
		System.out.println(this.y);
		String[] sensorValues = getSensorValues(); // THIS VALUES IS BY CM (GRID * 10)
		int [][] sensorLocation = sensor.sensorLocation;
		int [][] sensorDirection = sensor.sensorDirection;
		int sensorDirectionValueX, sensorDirectionValueY, s, e;
		boolean isObstacle[] = new boolean[6];
		
		System.out.print("The SensorValues are: \n");
		for (int i = 0; i < sensorValues.length; i ++) {
			System.out.print(sensorValues[i]);
			if (i != sensorValues.length - 1 ) {
				System.out.print(" ");
			}
		}
		System.out.println("\n");
		
		
		
		for (int i = 0; i < sensorValues.length; i++) {
			boolean no_obstacle = false;
			int value = Integer.parseInt(sensorValues[i]);
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
			
			if (value > e) {
				no_obstacle = true;
				isObstacle[i] = false;
			}
			for (int d = s, g = 1; d <= e; d = d + 10, g++) {
				int x = this.x + sensorLocation[i][0] + sensorDirectionValueX * g;
				int y = this.y + sensorLocation[i][1] + sensorDirectionValueY * g;
				String gridType = newMap.getGrid(x, y);
//				System.out.println("x : "+ x);
//				System.out.println("y : "+ y);
////				System.out.println(!found);
//				System.out.println("g : "+ g);
//				System.out.println("value : "+ value);
//				System.out.println("d: " + d + "\n");
				
				if (value >= d && value < d + 10 && value <= e) {
					updateMapGridObstacle(newMap, x, y, gridType);
					if (g == 1) {
						isObstacle[i] = true;
					}
					break;
				}
				else {
					updateMapGridExplored(newMap, x, y, gridType);
					no_obstacle = true;
				}
			}
		}
		newMap.print();
		return isObstacle;
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
	
	public void setTrueMap(Map map) {
		this.sensor.setTrueMap(map);
	}
	
	public int getDirectionIndex() { 		
		for (int i = 0; i < Constant.POSSIBLEROBOTDIRECTION.length; i ++) {
			if (this.direction.compareTo(Constant.POSSIBLEROBOTDIRECTION[i]) == 0) {
				return i;
			}
		}
		return -1;
	}
}

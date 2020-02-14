package sensor;
import config.Constant;
import map.Map;

public abstract class Sensor {
	// More like sensor interface class to arduino
	
	// Far sensor range = 80-150
	// Short sensor range = 10-50
	// FL, FC, FR, RB, RF, LF
	
	public int[][] sensorLocation = new int[6][2];
	public int[][] sensorDirection = new int[3][2]; // Assume NORTH is the direction, Represent front, left and right sensor direction
	
	
	public void updateSensorDirection(String direction) {
		int i = 0;
		for (i = 0; i < Constant.POSSIBLEROBOTDIRECTION.length; i++) {
			if (direction.compareTo(Constant.POSSIBLEROBOTDIRECTION[i]) == 0){
				sensorDirection[0] = Constant.SENSORDIRECTION[i];
				break;
			}
		}
		
		sensorLocation[0] = new int[] {sensorDirection[0][0] + Constant.SENSORDIRECTION[((i-1) % Constant.SENSORDIRECTION.length + Constant.SENSORDIRECTION.length) % Constant.SENSORDIRECTION.length][0], 
										sensorDirection[0][1] + Constant.SENSORDIRECTION[((i-1) % Constant.SENSORDIRECTION.length + Constant.SENSORDIRECTION.length) % Constant.SENSORDIRECTION.length][1]};
		sensorLocation[1] = sensorDirection[0];
		sensorLocation[2] = new int[] {sensorDirection[0][0]+ Constant.SENSORDIRECTION[(i+1) % Constant.SENSORDIRECTION.length][0], 
										sensorDirection[0][1]+ Constant.SENSORDIRECTION[(i+1) % Constant.SENSORDIRECTION.length][1]};
		sensorLocation[3] = new int[] {Constant.SENSORDIRECTION[(i+1) % Constant.SENSORDIRECTION.length][0] +
									   Constant.SENSORDIRECTION[(i+2) % Constant.SENSORDIRECTION.length][0],
									   Constant.SENSORDIRECTION[(i+1) % Constant.SENSORDIRECTION.length][1] +
									   Constant.SENSORDIRECTION[(i+2) % Constant.SENSORDIRECTION.length][1]};
		sensorLocation[4] = new int[] {sensorLocation[2][0], sensorLocation[2][1]};
		sensorLocation[5] = new int[] {sensorLocation[0][0], sensorLocation[0][1]};
		
		sensorDirection[1] = new int[] {Constant.SENSORDIRECTION[(i+1) % Constant.SENSORDIRECTION.length][0], Constant.SENSORDIRECTION[(i+1) % Constant.SENSORDIRECTION.length][1]};
		sensorDirection[2] = new int[] {Constant.SENSORDIRECTION[((i-1) % Constant.SENSORDIRECTION.length + Constant.SENSORDIRECTION.length) % Constant.SENSORDIRECTION.length][0],
										Constant.SENSORDIRECTION[((i-1) % Constant.SENSORDIRECTION.length + Constant.SENSORDIRECTION.length) % Constant.SENSORDIRECTION.length][1]};
	}
	
	public abstract String[] getAllSensorsValue(int x, int y, String direction);
	public abstract Map getTrueMap(); 	// Only for simulator
	public abstract void setTrueMap(Map map); // Only for simulator to load map
}

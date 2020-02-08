import java.util.ArrayList;

public class SimulatorSensor extends Sensor{
	// Simulate the Ardurino sensor
	private Map trueMap;
	private String sensorValue;
	private String[] sensorArray;
	
	public SimulatorSensor() {
		trueMap = new Map();
		trueMap.generateRandomMap();
		trueMap.print();
	}
	
	private void updateSensorsValue(int x, int y, String direction) {
		// sensorValue will have this: FL, FC, FR, RB, RF, LF
		
		String [] sensorValue = new String[6];
		updateSensorDirection(direction);
		
		
		// "North", "East", "South", "West"
		for (int i = 1; i <= Constant.SHORTSENSORMAXRANGE; i ++ ) {
			if (sensorValue[0] == null && trueMap.getGrid(x+sensorLocation[0][0] + i * sensorDirection[0][0], y+sensorLocation[0][1] + i * sensorDirection[0][1]).compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
				sensorValue[0] = getSensorValue(i, "SHORT");
			}
			if (sensorValue[1] == null && trueMap.getGrid(x+sensorLocation[1][0] + i * sensorDirection[0][0], y+sensorLocation[1][1] + i * sensorDirection[0][1]).compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
				sensorValue[1] = getSensorValue(i, "SHORT");
			}
			if (sensorValue[2] == null && trueMap.getGrid(x+sensorLocation[2][0] + i * sensorDirection[0][0], y+sensorLocation[2][1] + i * sensorDirection[0][1]).compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
				sensorValue[2] = getSensorValue(i, "SHORT");
			}
			if (sensorValue[3] == null && trueMap.getGrid(x+sensorLocation[3][0] + i * sensorDirection[1][0], y+sensorLocation[3][1] + i * sensorDirection[1][1]).compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
				sensorValue[3] = getSensorValue(i, "SHORT");
			}
			if (sensorValue[4] == null && trueMap.getGrid(x+sensorLocation[4][0] + i * sensorDirection[1][0], y+sensorLocation[4][1] + i * sensorDirection[1][1]).compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
				sensorValue[4] = getSensorValue(i, "SHORT");
			}
		}
		
		for (int i = 1; i <= Constant.FARSENSORMAXRANGE; i ++ ) {
			if (sensorValue[5] == null && trueMap.getGrid(x+sensorLocation[5][0] + i * sensorDirection[2][0], y+sensorLocation[5][1] + i * sensorDirection[2][1]).compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
				sensorValue[5] = getSensorValue(i, "FAR");
			}
		}
		
		sensorValue = padSensorValue(sensorValue);
		this.sensorValue = String.join(" ", sensorValue);
	}
	
	private String getSensorValue(int i, String mode) {
		if (mode.toUpperCase().compareTo("SHORT") == 0) {
			return "" + ((i-1) * 10 + Constant.SHORTSENSOROFFSET);
		}
		else if (mode.toUpperCase().compareTo("FAR") == 0) {
			return "" + ((i-1) * 10 + Constant.FARSENSOROFFSET);
		}
		else {
			return "";
		}
	}
	
	public String[] getAllSensorsValue(int x, int y, String direction) {
		// This is to simulate getting values from Arduino
		updateSensorsValue(x, y, direction);
		sensorArray = sensorValue.split(" ");
		return sensorArray;
	}
	
	private String[] padSensorValue(String [] arr) {
		// Pad whatever value that has no obstacle
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == null) {
				// As long as they are greater than FARSENSORMAXRANGE THEY CAN BE DEEMED AS NO OBSTACLE
				arr[i] = "" + (Constant.FARSENSORMAXRANGE * 10 + Constant.FARSENSOROFFSET + 1);
			}
		}
		return arr;
	}
	
	public Map getTrueMap() {
		return trueMap;
	}
}

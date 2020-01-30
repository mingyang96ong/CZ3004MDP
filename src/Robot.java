
public abstract class Robot {
	
	// This assumes the Robot have 3 front sensors, 2 left sensor and 1 right far sensor
	protected String [] leftSensors = new String[2];
	protected String [] frontSensor = new String[3];
	protected String rightSensor = "";
	
	protected String direction;
	
	protected int x, y;
	
	protected Map map;
	
	public abstract void getSensorValues();
	
	public abstract void setDirection(String direction);
	
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
	
//	public String getDirection() {
//		return direction;
//	}
//	
//	public int[] getPosition() {
//		return new int[] {x, y};
//	}
//	
//	public Map getMap() {
//		return map;
//	}
}

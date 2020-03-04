import map.Map;

import config.Constant;
import robot.RealRobot;

public class test {
	public static void main(String[] args) {
		RealRobot r = RealRobot.getInstance();
		Map map = r.getMap();
		int x = 1, y = 3;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				System.out.println("" + checkValidX(i+x) + ", " + checkValidY(j+y) + " updated to -1");
				map.setDist(checkValidX(i+x), checkValidY(j+y), -1);
				if (!(map.getGrid(checkValidX(i+x), checkValidY(j+y)).equals(Constant.STARTPOINT)) ||
						(map.getGrid(checkValidX(i+x), checkValidY(j+y)).equals(Constant.ENDPOINT))){
					map.setGrid(checkValidX(i+x), checkValidY(j+y), Constant.EXPLORED);
					System.out.println("" + checkValidX(i+x) + ", " + checkValidY(j+y) + " updated to explored");
				}
			}
		}
		map.print();
	}
	
	protected static int checkValidX(int x) {
		if (x >= Constant.BOARDWIDTH - 1) {
			x = Constant.BOARDWIDTH - 2;
		}
		if (x <= 0) {
			x = 1;
		}
		
		return x;
	}
	
	protected static int checkValidY(int y) {
		if (y >= Constant.BOARDHEIGHT - 1) {
			y = Constant.BOARDHEIGHT - 2;
		}
		
		if (y <= 0) {
			y = 1;
		}
		return y;
	}
}

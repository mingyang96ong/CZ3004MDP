import map.Map;

import java.util.Arrays;

import config.Constant;
import robot.RealRobot;

public class test {
	public static void main(String[] args) {
//		RealRobot r = RealRobot.getInstance();
//		Map map = r.getMap();
//		int x = 1, y = 3;
//		for (int i = -1; i < 2; i++) {
//			for (int j = -1; j < 2; j++) {
//				System.out.println("" + checkValidX(i+x) + ", " + checkValidY(j+y) + " updated to -1");
//				map.setDist(checkValidX(i+x), checkValidY(j+y), -1);
//				if (!(map.getGrid(checkValidX(i+x), checkValidY(j+y)).equals(Constant.STARTPOINT)) ||
//						(map.getGrid(checkValidX(i+x), checkValidY(j+y)).equals(Constant.ENDPOINT))){
//					map.setGrid(checkValidX(i+x), checkValidY(j+y), Constant.EXPLORED);
//					System.out.println("" + checkValidX(i+x) + ", " + checkValidY(j+y) + " updated to explored");
//				}
//			}
//		}
//		map.print();
		int[] path = new int[] {1, 1, 1, 2, 0, 1, 1, 3, 1, 2, 3};
		path(path);
		
		StringBuilder sb = new StringBuilder();
    	int count = 0;
        for (int i = 0; i < path.length; i++) {
        	int direction = path[i];
            if (direction == Constant.FORWARD) {
                count++;
            } else if (direction == Constant.RIGHT) {
            	sb.append("W"+count + "|" + Constant.TURN_RIGHT);
            	count = 1;
            	
//                robot.updateMap();
//                robot.rotateRight();
//                if (ex.check_front_empty(robot)) {
//                    robot.forward(1);
//                } else {
//                    return false;
//                }
            } else if (direction == Constant.LEFT) {
            	sb.append("W"+count + "|" + Constant.TURN_LEFT);
            	count = 1;
//                robot.updateMap();
//                robot.rotateLeft();
//                if (ex.check_front_empty(robot)) {
//                    robot.forward(1);
//                } else {
//                    return false;
//                }
            } else {
            	sb.append("W"+count + "|" + Constant.TURN_RIGHT + Constant.TURN_RIGHT);
            	count = 1;
//                robot.updateMap();
//                robot.rotateRight();
//                robot.updateMap();
//                robot.rotateRight();
//                if (ex.check_front_empty(robot)) {
//                    robot.forward(1);
//                } else {
//                    return false;
//                }
            }
        }
        if (count > 1) {
        	sb.append("W"+count + "|");
        }
        String msg = sb.toString();
        System.out.println(msg);
        
        
        
        
	}
	
	protected static void path(int[] path) {
		String[] arr = new String[path.length];
    	
        for (int i = 0; i < path.length; i++) {
        	int direction = path[i];
            if (direction == Constant.FORWARD) {
                arr[i] = "W1|";
            } else if (direction == Constant.RIGHT) {
            	arr[i] = Constant.TURN_RIGHT + "W1|";
//                robot.updateMap();
//                robot.rotateRight();
//                if (ex.check_front_empty(robot)) {
//                    robot.forward(1);
//                } else {
//                    return false;
//                }
            } else if (direction == Constant.LEFT) {
            	arr[i] = Constant.TURN_LEFT + "W1|";
//                robot.updateMap();
//                robot.rotateLeft();
//                if (ex.check_front_empty(robot)) {
//                    robot.forward(1);
//                } else {
//                    return false;
//                }
            } else {
            	arr[i] = Constant.TURN_RIGHT + Constant.TURN_RIGHT + "W1|";
//                robot.updateMap();
//                robot.rotateRight();
//                robot.updateMap();
//                robot.rotateRight();
//                if (ex.check_front_empty(robot)) {
//                    robot.forward(1);
//                } else {
//                    return false;
//                }
            }
        }
        
        
        String tmp = String.join("", Arrays.asList(arr));
        System.out.println(tmp);
        
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

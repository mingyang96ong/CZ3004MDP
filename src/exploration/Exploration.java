package exploration;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import robot.Robot;
import map.Map;
import astarpathfinder.AStarPathFinder;
import config.Constant;
import connection.ConnectionSocket;

public class Exploration {
	public static int step = 1;
    public void Exploration(Robot robot, int time, int percentage){
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        AStarPathFinder astar = new AStarPathFinder();
        robot.setDirection(2);

        do {
        	if (time != -1) {
                // to account for the time-limited exploration
                int time_taken = (int) stopwatch.getElapsedTime();
                if (time_taken >= time) {
                    return;
                }
            }
            if (percentage != 100) {
                // to account for the coverage-limited exploration
                if (percent_complete(robot) >= percentage) {
                    return;
                }
            }
            System.out.println("Phase 1");
            move(robot);
        } while (!at_start(robot) && ExplorationThread.getRunning());

        int[] unexplored = unexplored(robot, Constant.START);
//        System.out.println(Arrays.toString(unexplored));
//        astar.AStarPathFinder(robot, robot.getPosition(), unexplored, false);
//        robot.updateMap();

        while (unexplored != null && ExplorationThread.getRunning()) {
        	if (time != -1) {
                // to account for the time-limited exploration
                int time_taken = (int) stopwatch.getElapsedTime();
                if (time_taken >= time) {
                    return;
                }
            }
            if (percentage != 100) {
                // to account for the coverage-limited exploration
                if (percent_complete(robot) >= percentage) {
                    return;
                }
            }

            // fastest path to nearest unexplored square
            System.out.println("Phase 2");
            astar.AStarPathFinder(robot, robot.getPosition(), unexplored, false);
            unexplored = unexplored(robot, unexplored);
            robot.updateMap();
        }

        if (!at_start(robot) && ExplorationThread.getRunning()) {
//            // fastest path to start point
            System.out.println("Phase 3");
            System.out.println(Arrays.toString(robot.getPosition()));
            astar.AStarPathFinder(robot, robot.getPosition(), Constant.START, true);
        }
        if (!ExplorationThread.getRunning()) {
        	System.out.println("Exploration terminated");
        }
        else {
        	System.out.println("Exploration Complete!");
        }
        stopwatch.stop();
        // generate MDF String to confirm final map
    };

    private boolean move(Robot robot) {
        System.out.println(Arrays.toString(robot.getPosition()));
        if (!ConnectionSocket.checkConnection()) {
	        try {
	            TimeUnit.MILLISECONDS.sleep(500);
	        }
	        catch (Exception e){
	            System.out.println(e.getMessage());
	        }
        }
        if (!ExplorationThread.getRunning()) {
        	return false;
        }

        if (check_right_empty(robot)) {
            robot.rotateRight();
            if (check_front_empty(robot)) {
                robot.forward(step);
                return true;
            } else {
                robot.rotateLeft();
            }
        }
        if (check_front_empty(robot)) {
            robot.forward(step);
            return true;
        }
        robot.rotateLeft();
        if (check_front_empty(robot)) {
            robot.forward(step);
            return true;
        }
        robot.rotateLeft();
        if (check_front_empty(robot)) {
            robot.forward(step);
        } else {
            System.out.println("Error during exploration phase 1. All 4 sides blocked.");
        }
        return true;
    }

    private boolean check_right_empty(Robot robot) {
        boolean[] obstacles = robot.updateMap();
        return (!obstacles[3]) && (!obstacles[4]);
    }

    public boolean check_front_empty(Robot robot){
        boolean[] obstacles = robot.updateMap();
        return (!obstacles[0]) && (!obstacles[1]) && (!obstacles[2]);
    };

    private boolean at_start(Robot robot){
        int[] pos = robot.getPosition();
        return (Arrays.equals(pos,Constant.START));
    };

    private int[] unexplored(Robot robot, int[] start) {
        Map map = robot.getMap();
        for (int i=0; i<Constant.BOARDWIDTH; i++) {
            for (int j=0; j<Constant.BOARDHEIGHT; j++) {
                if (map.getGrid(i,j).equals("Unexplored")) {
                    return new int[] {i,j};
                }
            }
        }
        return null;
    }
    private int percent_complete(Robot robot) {
        Map map = robot.getMap();
        int unexplored = 0;
        for (int i=0; i<Constant.BOARDWIDTH; i++) {
            for (int j=0; j<Constant.BOARDHEIGHT; j++) {
                if (map.getGrid(i,j).equals("Unexplored")) {
                    unexplored ++;
                }
            }
        }
        return ((300-unexplored)/3);
    }
}


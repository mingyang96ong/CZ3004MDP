package exploration;

import java.util.concurrent.TimeUnit;

import robot.SimulatorRobot;
import map.Map;
import config.Constant;

public class Exploration {

    public void Exploration(SimulatorRobot robot){
        do {
            System.out.println("Exploration started!");
            try {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            if (check_right_empty(robot)) {
                robot.rotateRight();
                if (check_front_empty(robot)) {
                    robot.forward();
                    continue;
                }
                else {
                    robot.rotateLeft();
                }
            }
            if (check_front_empty(robot)) {
                robot.forward();
                continue;
            }
            robot.rotateLeft();
            if (check_front_empty(robot)) {
                robot.forward();
                continue;
            }
            robot.rotateLeft();
            if (check_front_empty(robot)) {
                robot.forward();
            }
            else {
                System.out.println("Error during exploration phase 1. All 4 sides blocked.");
            }
        } while (!at_start(robot));

        while (!is_exploration_complete(robot)) {
            // fastest path to nearest unexplored square
        }

        if (!at_start(robot)) {
            // fastest path to start point
        }

        // signal completion
        // generate MDF String to confirm final map
    };

    private boolean check_right_empty(SimulatorRobot robot) {
        boolean[] obstacles = robot.updateMap();
        return (!obstacles[3]) && (!obstacles[4]);
    }

    private boolean check_front_empty(SimulatorRobot robot){
        boolean[] obstacles = robot.updateMap();
        return (!obstacles[0]) && (!obstacles[1]) && (!obstacles[2]);
    };

    private boolean at_start(SimulatorRobot robot){
        int[] pos = robot.getPosition();
        if (pos == Constant.START)
            return true;
        return false;
    };

    private boolean is_exploration_complete(SimulatorRobot robot) {
        Map map = robot.getMap();
        for (int i=0; i<Constant.BOARDWIDTH; i++) {
            for (int j=0; j<Constant.BOARDHEIGHT; j++) {
                if (map.getGrid(i,j).equals("Unexplored")) {
                    return false;
                }
            }
        }
        return true;
    }
}


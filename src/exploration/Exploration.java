package exploration;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import robot.SimulatorRobot;
import map.Map;
import config.Constant;

public class Exploration {

    public void Exploration(SimulatorRobot robot){

        robot.setDirection(2);

        do {
            System.out.println("Phase 1");
            move(robot);
        } while (!at_start(robot));

        Map map = robot.getMap();
        int[] next = is_exploration_complete(robot, Constant.START);

        while (!(Arrays.equals(next,Constant.START))) {
            // fastest path to nearest unexplored square
            System.out.println("Phase 2");
            map.setGrid(next[0], next[1], "Explored");
            next = is_exploration_complete(robot, next);
        }

        if (!at_start(robot)) {
            // fastest path to start point
            System.out.println("Phase 3");
        }

        System.out.println("Exploration Complete!");
        // generate MDF String to confirm final map
    };

    private boolean move(SimulatorRobot robot) {
        System.out.println(Arrays.toString(robot.getPosition()));

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
                return true;
            } else {
                robot.rotateLeft();
            }
        }
        if (check_front_empty(robot)) {
            robot.forward();
            return true;
        }
        robot.rotateLeft();
        if (check_front_empty(robot)) {
            robot.forward();
            return true;
        }
        robot.rotateLeft();
        if (check_front_empty(robot)) {
            robot.forward();
        } else {
            System.out.println("Error during exploration phase 1. All 4 sides blocked.");
        }
        return true;
    }

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
        return (Arrays.equals(pos,Constant.START));
    };

    private int[] is_exploration_complete(SimulatorRobot robot, int[] start) {
        Map map = robot.getMap();
        for (int i=start[0]; i<Constant.BOARDWIDTH; i++) {
            for (int j=start[1]; j<Constant.BOARDHEIGHT; j++) {
                if (map.getGrid(i,j).equals("Unexplored")) {
                    return new int[] {i,j};
                }
            }
        }
        return Constant.START;
    }
}


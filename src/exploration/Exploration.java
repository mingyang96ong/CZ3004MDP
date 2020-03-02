package exploration;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import robot.Robot;
import map.Map;
import config.Constant;
import astarpathfinder.AStarPathFinder;

public class Exploration {

    public void Exploration(Robot robot, int time, int percentage, int speed, boolean image_recognition){
        if ((speed == 1)&&(time == -1)&&(percentage == 100)) {
            if (image_recognition) {
                ImageRecognition_Exploration(robot);
            } else {
                Normal_Exploration(robot);
            }
        } else {
            Limited_Exploration(robot, time, percentage, speed);
        }
    };

    private void Limited_Exploration(Robot robot, int time, int percentage, int speed) {
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
            move(robot, speed);
        } while (!at_start(robot));

        int[] unexplored = unexplored(robot, Constant.START);

        while (unexplored != null) {
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
            astar.AStarPathFinder(robot, robot.getPosition(), unexplored, false, speed);
            unexplored = unexplored(robot, unexplored);
            robot.updateMap();
        }

        if (!at_start(robot)) {
            // fastest path to start point
            System.out.println("Phase 3");
            System.out.println(Arrays.toString(robot.getPosition()));
            astar.AStarPathFinder(robot, robot.getPosition(), Constant.START, true, speed);
        }

        stopwatch.stop();
        System.out.println("Exploration Complete!");
        // generate MDF String to confirm final map
    }

    private void Normal_Exploration(Robot robot) {
        AStarPathFinder astar = new AStarPathFinder();
        robot.setDirection(2);

        do {
            move(robot, 1);
        } while (!at_start(robot));

        int[] unexplored = unexplored(robot, Constant.START);

        while (unexplored != null) {
            // fastest path to nearest unexplored square
            System.out.println("Phase 2");
            astar.AStarPathFinder(robot, robot.getPosition(), unexplored, false, 1);
            unexplored = unexplored(robot, unexplored);
            robot.updateMap();
        }

        if (!at_start(robot)) {
            // fastest path to start point
            System.out.println("Phase 3");
            System.out.println(Arrays.toString(robot.getPosition()));
            astar.AStarPathFinder(robot, robot.getPosition(), Constant.START, true, 1);
        }

        System.out.println("Exploration Complete!");
        // generate MDF String to confirm final map
    }

    private void ImageRecognition_Exploration(Robot robot) {
        AStarPathFinder astar = new AStarPathFinder();
        robot.setDirection(2);

        do {
            move(robot, 1);
            captureImage(robot);
        } while (!at_start(robot));

        int[] unexplored = unexplored(robot, Constant.START);

        while (unexplored != null) {
            // fastest path to nearest unexplored square
            System.out.println("Phase 2");
            astar.AStarPathFinder(robot, robot.getPosition(), unexplored, false, 1);
            unexplored = unexplored(robot, unexplored);
            robot.updateMap();
        }

        if (!at_start(robot)) {
            // fastest path to start point
            System.out.println("Phase 3");
            System.out.println(Arrays.toString(robot.getPosition()));
            astar.AStarPathFinder(robot, robot.getPosition(), Constant.START, true, 1);
        }

        System.out.println("Exploration Complete!");
        // generate MDF String to confirm final map
    }

    private boolean move(Robot robot, int speed) {
        System.out.println(Arrays.toString(robot.getPosition()));

        try {
            TimeUnit.SECONDS.sleep(speed);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        if (check_right_empty(robot)) {
            robot.rotateRight();
            if (check_front_empty(robot)) {
                robot.forward(1);
                return true;
            } else {
                robot.rotateLeft();
            }
        }
        if (check_front_empty(robot)) {
            robot.forward(1);
            return true;
        }
        robot.rotateLeft();
        if (check_front_empty(robot)) {
            robot.forward(1);
            return true;
        }
        robot.rotateLeft();
        if (check_front_empty(robot)) {
            robot.forward(1);
        } else {
            System.out.println("Error during exploration phase 1. All 4 sides blocked.");
        }
        return true;
    }
    private void captureImage(Robot robot) {

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


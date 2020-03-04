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

            if (at_corner(robot)) {
                robot.calibrate();
            }
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

            if (at_corner(robot)) {
                robot.calibrate();
            }
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

        int[] last_obstacle = {-1, -1};

        do {
            move(robot, 1);
            last_obstacle = image_recognition(robot, last_obstacle);

            if (at_corner(robot)) {
                robot.calibrate();
            }
        } while (!at_start(robot));

        int[] unexplored = unexplored(robot, Constant.START);

        while (unexplored != null) {
            // fastest path to nearest unexplored square
            System.out.println("Phase 2");
            astar.AStarPathFinder(robot, robot.getPosition(), unexplored, false, 1);
            last_obstacle = image_recognition(robot, last_obstacle);
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

        if (!connection.ConnectionSocket.checkConnection()) {
            try {
                TimeUnit.SECONDS.sleep(speed);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
        return false;
    }

    private boolean check_right_empty(Robot robot) {
        boolean[] obstacles = robot.updateMap();
        return (!obstacles[3]) && (!obstacles[4]);
    }

    public boolean check_front_empty(Robot robot){
        boolean[] obstacles = robot.updateMap();
        return (!obstacles[0]) && (!obstacles[1]) && (!obstacles[2]);
    };

    private int[] image_recognition(Robot robot, int[] last_obstacle) {
        if ((!check_right_empty(robot)) && (!right_is_wall(robot))) {
            int[] pos = robot.getPosition();
            if (!Arrays.equals(pos, last_obstacle)) {
                if ((pos[0] == last_obstacle[0]) & (Math.abs(pos[1]-last_obstacle[1]) > 2)) {
                    robot.captureImage();
                    last_obstacle = pos;
                } else if ((pos[1] == last_obstacle[1]) & (Math.abs(pos[0]-last_obstacle[0]) > 2)) {
                    robot.captureImage();
                    last_obstacle = pos;
                }
            }
        }
        return last_obstacle;
    }

    private boolean right_is_wall(Robot robot) {
        int direction = robot.getDirection();
        int[] pos = robot.getPosition();

        if (((direction == Constant.NORTH) && (pos[0] == 18)) ||
                ((direction == Constant.EAST) && (pos[1] == 13)) ||
                ((direction == Constant.SOUTH) && (pos[0] == 1)) ||
                ((direction == Constant.WEST) && (pos[1] == 1))) {
            return true;
        }
        return false;
    }

    private boolean at_corner(Robot robot) {
        int[] pos = robot.getPosition();
        return (((pos[0] == 1) || (pos[0] == 18)) && ((pos[1] == 1) || (pos[1] == 13)));
    }

    private boolean at_start(Robot robot){
        int[] pos = robot.getPosition();
        return (Arrays.equals(pos,Constant.START));
    };

    private int[] unexplored(Robot robot, int[] start) {
        Map map = robot.getMap();
        int lowest_cost = 9999;
        int[] cheapest_pos = null;
        for (int i=0; i<Constant.BOARDWIDTH; i++) {
            for (int j=0; j<Constant.BOARDHEIGHT; j++) {
                if (map.getGrid(i,j).equals("Unexplored")) {
                    int cost = Math.abs(start[0] - i) + Math.abs(start[1] - j);
                    if (cost < lowest_cost) {
                        cheapest_pos = new int[] {i, j};
                        lowest_cost = cost;
                    }
                }
            }
        }
        return cheapest_pos;
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


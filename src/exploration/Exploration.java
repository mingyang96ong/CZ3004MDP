package exploration;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import astarpathfinder.FastestPath;
import robot.Robot;
import map.Map;
import config.Constant;
import astarpathfinder.AStarPathFinder;

public class Exploration {
    private FastestPath fp = new FastestPath();

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
        corner_calibration(robot);

//        switch (robot.getDirection()) {
//            case Constant.NORTH:
//                robot.rotateLeft();
//                break;
//            case Constant.EAST:
//                robot.rotateRight();
//                robot.rotateRight();
//                break;
//            case Constant.SOUTH:
//                robot.rotateRight();
//                break;
//            default:
//                break;
//        }
//        robot.calibrate();

        int[] path = fp.FastestPath(robot, robot.getWaypoint(), Constant.END, 1, false);

        switch (path[0]) {
            case Constant.LEFT:
                robot.rotateLeft();
                break;
            case Constant.RIGHT:
                robot.rotateRight();
                break;
            case Constant.BACKWARD:
                robot.rotateRight();
                robot.rotateRight();
                break;
            default:
                break;
        }
    };

    private void Limited_Exploration(Robot robot, int time, int percentage, int speed) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
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
            move(robot, speed, null);
            corner_calibration(robot);
        } while (!at_pos(robot, Constant.START));

        int[] unexplored = unexplored(robot, robot.getPosition());

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
            fp.FastestPath(robot, null, unexplored, speed, true);
            unexplored = unexplored(robot, robot.getPosition());
            crash(robot.updateMap());
        }

        if (!at_pos(robot, Constant.START)) {
            // fastest path to start point
            System.out.println("Phase 3");
            System.out.println(Arrays.toString(robot.getPosition()));
            fp.FastestPath(robot, null, Constant.START, speed, true);
        }

        stopwatch.stop();
        System.out.println("Exploration Complete!");
        // generate MDF String to confirm final map
    }

    private void Normal_Exploration(Robot robot) {
        AStarPathFinder astar = new AStarPathFinder();
        robot.setDirection(2);

        do {
            move(robot, 1, null);
            corner_calibration(robot);
        } while (!at_pos(robot, Constant.START));

        int[] unexplored = unexplored(robot, robot.getPosition());

        while (unexplored != null) {
            // fastest path to nearest unexplored square
            System.out.println("Phase 2");
            fp.FastestPath(robot, null, unexplored, 1, true);
            unexplored = unexplored(robot, robot.getPosition());
            crash(robot.updateMap());
        }

        if (!at_pos(robot, Constant.START)) {
            // fastest path to start point
            System.out.println("Phase 3");
            System.out.println(Arrays.toString(robot.getPosition()));
            fp.FastestPath(robot, null, Constant.START, 1, true);
        }

        System.out.println("Exploration Complete!");
        // generate MDF String to confirm final map
    }

    private void ImageRecognition_Exploration(Robot robot) {
        AStarPathFinder astar = new AStarPathFinder();
        robot.setDirection(2);

        int[][] checked_obstacles = {{0}};

        do {
            checked_obstacles = move(robot, 1, checked_obstacles);
            System.out.println(Arrays.deepToString(checked_obstacles));
            corner_calibration(robot);
        } while (!at_pos(robot, Constant.START));

        int[] need_take = picture_taken(robot, robot.getPosition(), checked_obstacles);
        int[] go_to = next_to_obstacle(robot, need_take);

        if (go_to == null) {
            go_to = unexplored(robot, robot.getPosition());
        }

        while (go_to != null) {
            // fastest path to nearest unexplored square
            System.out.println("Phase 2");
            fp.FastestPath(robot, null, go_to, 1, true);
            obstacle_on_right(robot, need_take);

            do {
                checked_obstacles = move(robot, 1, checked_obstacles);
                System.out.println(Arrays.deepToString(checked_obstacles));
            } while (!at_pos(robot, go_to));

            need_take = picture_taken(robot, robot.getPosition(), checked_obstacles);
            go_to = next_to_obstacle(robot, need_take);

            if (go_to == null) {
                go_to = unexplored(robot, robot.getPosition());
            }

            robot.updateMap();
        }

        if (!at_pos(robot, Constant.START)) {
            // fastest path to start point
            System.out.println("Phase 3");
            System.out.println(Arrays.toString(robot.getPosition()));
            fp.FastestPath(robot, null, Constant.START, 1, true);
        }

        System.out.println("Exploration Complete!");
        // generate MDF String to confirm final map
    }

    private boolean[] crash(int[] isObstacle) {
        boolean[] crash = new boolean[6];
        for (int i=0; i<6; i++) {
            if (isObstacle[i] == 1) {
                crash[i] = true;
            } else {
                crash[i] = false;
            }
        }
        return crash;
    }

    private int[][] move(Robot robot, int speed, int[][] checked_obstacles) {
        System.out.println(Arrays.toString(robot.getPosition()));
        int[][] checked = checked_obstacles;
        int[] sensors = robot.updateMap();

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
                return checked;
            } else {
                if ((checked != null)&&(!front_wall(robot))) {
                    checked = image_recognition(robot, checked);
                } else {
                    robot.rotateLeft();
                }
            }
        } else if ((checked != null)&&(!right_wall(robot))&&(!((sensors[3] == -1)&&(check_front_empty(robot))))) {
            robot.rotateRight();
            checked = image_recognition(robot, checked);
        }
        if (check_front_empty(robot)) {
            robot.forward(1);
            return checked;
        } else if ((checked != null)&&(!front_wall(robot))) {
            checked = image_recognition(robot, checked);
        } else {
            robot.rotateLeft();
        }
        if (check_front_empty(robot)) {
            robot.forward(1);
            return checked;
        } else if ((checked != null)&&(!front_wall(robot))) {
            checked = image_recognition(robot, checked);
        } else {
            robot.rotateLeft();
        }
        if (check_front_empty(robot)) {
            robot.forward(1);
        } else {
            System.out.println("Error during exploration phase 1. All 4 sides blocked.");
        }
        return checked;
    }

    private int[][] image_recognition(Robot robot, int[][] checked_obstacles) {
        int[] sensors = robot.updateMap();
        int x = robot.getPosition()[0];
        int y = robot.getPosition()[1];
        int[][] obs_pos = new int[3][3];
        int[] default_pos = new int[] {-1, -1, -1};
        boolean take_pic = false;
        robot.rotateLeft();
        int direction = robot.getDirection();

        switch (direction) {
            case Constant.NORTH:
                obs_pos = new int[][] {{x+1+sensors[0], y+1, Constant.NORTH},
                        {x+1+sensors[1], y, Constant.NORTH}, {x+1+sensors[2], y-1, Constant.NORTH}};
                break;
            case Constant.SOUTH:
                obs_pos = new int[][] {{x-1-sensors[0], y-1, Constant.SOUTH} ,
                        {x-1-sensors[1], y, Constant.SOUTH}, {x-1-sensors[2], y+1, Constant.SOUTH}};
                break;
            case Constant.EAST:
                obs_pos = new int[][] {{x-1, y+1+sensors[0], Constant.EAST},
                        {x, y+1+sensors[1], Constant.EAST}, {x+1, y+1+sensors[2], Constant.EAST}};
                break;
            case Constant.WEST:
                obs_pos = new int[][] {{x+1, y-1-sensors[0], Constant.WEST},
                        {x, y-1-sensors[1], Constant.WEST}, {x-1, y-1-sensors[2], Constant.WEST}};
                break;
        }

        for (int i=0; i<3; i++) {
            if ((sensors[i] == -1) || (!within_map(obs_pos[i][0], obs_pos[i][1]))) {
                obs_pos[i] = default_pos;
            } else {
                for (int[] obstacles : checked_obstacles) {
                    if (Arrays.equals(obstacles, obs_pos[i])) {
                        obs_pos[i] = default_pos;
                        break;
                    }
                }
            }
        }

        // obs_pos: L, M, R
        int[] copy = obs_pos[2];
        obs_pos[2] = obs_pos[0];
        obs_pos[0] = copy;

        for (int j=0; j<3; j++) {
            if (!(Arrays.equals(obs_pos[j], default_pos))) {
                if (check_front_empty(robot)) {
                    checked_obstacles[0][0] = j + 1;
                }
                if ((checked_obstacles[0][0] > 2) || (!check_front_empty(robot))) {
                    take_pic = true;
                }
            }
        }

        if (take_pic) {
            for (int[] obs : obs_pos) {
                if (!(Arrays.equals(obs, default_pos))) {
                    int len = checked_obstacles.length;
                    int[][] temp = new int[len + 1][3];
                    System.arraycopy(checked_obstacles, 0, temp, 0, len);
                    temp[len] = obs;
                    checked_obstacles = temp;
                }
            }
            checked_obstacles[0][0] = 0;
            robot.captureImage(obs_pos);
        }

        return checked_obstacles;
    }

    private boolean within_map(int x, int y) {
        if ((x > 19) || (x < 0) || (y > 14) || (y < 0)) {
            // if out of map
            return false;
        }
        else {
            return true;
        }
    }

    private boolean right_wall(Robot robot) {
        int direction = robot.getDirection();
        int[] pos = robot.getPosition();
        switch (direction) {
            case Constant.NORTH:
                return pos[0] == 18;
            case Constant.SOUTH:
                return pos[0] == 1;
            case Constant.EAST:
                return pos[1] == 13;
            case Constant.WEST:
                return pos[1] == 1;
            default:
                return true;
        }
    }

    private boolean front_wall(Robot robot) {
        int direction = robot.getDirection();
        int[] pos = robot.getPosition();
        switch (direction) {
            case Constant.EAST:
                return pos[0] == 18;
            case Constant.WEST:
                return pos[0] == 1;
            case Constant.SOUTH:
                return pos[1] == 13;
            case Constant.NORTH:
                return pos[1] == 1;
            default:
                return true;
        }
    }

    private boolean check_right_empty(Robot robot) {
        boolean[] obstacles = crash(robot.updateMap());
        return (!obstacles[3]) && (!obstacles[4]);
    }

    public boolean check_front_empty(Robot robot){
        boolean[] obstacles = crash(robot.updateMap());
        return (!obstacles[0]) && (!obstacles[1]) && (!obstacles[2]);
    };

    private void corner_calibration(Robot robot) {
        int[] pos = robot.getPosition();
        if (!(((pos[0] == 1) || (pos[0] == 18)) && ((pos[1] == 1) || (pos[1] == 13)))) {
            return;
        }
        robot.updateMap();
        int direction = robot.getDirection();
        if ((pos[0] == 1) && (pos[1] == 13)) {
            switch (direction) {
                case Constant.NORTH:
                    robot.rotateRight();
                    robot.rotateRight();
                    break;
                case Constant.EAST:
                    robot.rotateRight();
                    break;
                case Constant.WEST:
                    robot.rotateLeft();
                    break;
                default:
                    break;
            }
        } else if ((pos[0] == 18) && (pos[1] == 13)) {
            switch (direction) {
                case Constant.WEST:
                    robot.rotateRight();
                    robot.rotateRight();
                    break;
                case Constant.NORTH:
                    robot.rotateRight();
                    break;
                case Constant.SOUTH:
                    robot.rotateLeft();
                    break;
                default:
                    break;
            }
        } else if ((pos[0] == 18) && (pos[1] == 1)) {
            switch (direction) {
                case Constant.SOUTH:
                    robot.rotateRight();
                    robot.rotateRight();
                    break;
                case Constant.WEST:
                    robot.rotateRight();
                    break;
                case Constant.EAST:
                    robot.rotateLeft();
                    break;
                default:
                    break;
            }
        } else if ((pos[0] == 1) && (pos[1] == 1)) {
            switch (direction) {
                case Constant.EAST:
                    robot.rotateRight();
                    robot.rotateRight();
                    break;
                case Constant.SOUTH:
                    robot.rotateRight();
                    break;
                case Constant.NORTH:
                    robot.rotateLeft();
                    break;
                default:
                    break;
            }
        }
    }

    private boolean at_pos(Robot robot, int[] goal){
        int[] pos = robot.getPosition();
        return (Arrays.equals(pos, goal));
    };

    private int[] unexplored(Robot robot, int[] start) {
        Map map = robot.getMap();
        int lowest_cost = 9999;
        int[] cheapest_pos = null;
        for (int i=0; i<Constant.BOARDWIDTH; i++) {
            for (int j=0; j<Constant.BOARDHEIGHT; j++) {
                if (map.getGrid(i,j).equals(Constant.UNEXPLORED)) {
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

    private int[] next_to_obstacle(Robot robot, int[] next) {
        if (next == null) {
            return null;
        }

        int x = next[0];
        int y = next[1];
        int[][] order = new int[][] {{x-1, y-2}, {x, y-2}, {x+1, y-2}, {x+2, y-1}, {x+2, y},
                {x+2, y+1}, {x+1, y+2}, {x, y+2}, {x-1, y+2}, {x-2, y+1}, {x-2, y}, {x-2, y-1}};
        Map map = robot.getMap();

        for (int[] pos : order) {
            if ((within_map(pos[0], pos[1])) && (map.getGrid(pos[0], pos[1]).equals(Constant.EXPLORED))) {
                return pos;
            }
        }
        return null;
    }

    private int[] picture_taken(Robot robot, int[] start, int[][] checked_obstacles) {
        Map map = robot.getMap();
        int lowest_cost = 9999;
        int[] cheapest_pos = null;

        for (int i=0; i<Constant.BOARDWIDTH; i++) {
            for (int j=0; j<Constant.BOARDHEIGHT; j++) {
                if (map.getGrid(i,j).equals(Constant.OBSTACLE)) {
                    boolean not_inside = true;
                    for (int k=1; k<checked_obstacles.length; k++) {
                        int[] o_pos = {checked_obstacles[k][0], checked_obstacles[k][1]};
                        int[] cur = {i, j};
                        if (Arrays.equals(o_pos, cur)) {
                            not_inside = false;
                            break;
                        }
                    }
                    if (not_inside) {
                        int cost = Math.abs(start[0] - i) + Math.abs(start[1] - j);
                        if (cost < lowest_cost) {
                            cheapest_pos = new int[]{i, j};
                            lowest_cost = cost;
                        }
                    }
                }
            }
        }
        return cheapest_pos;
    }

    private void obstacle_on_right(Robot robot, int[] obstacle) {
        int direction = robot.getDirection();
        int[] pos = robot.getPosition();

        switch (direction) {
            case Constant.NORTH:
                if (obstacle[0] == (pos[0] - 2)) {
                    break;
                } else if (obstacle[1] == (pos[1] + 2)) {
                    robot.rotateRight();
                    break;
                } else if (obstacle[1] == (pos[1] - 2)) {
                    robot.rotateLeft();
                    break;
                } else {
                    robot.rotateRight();
                    robot.rotateRight();
                    break;
                }
            case Constant.SOUTH:
                if (obstacle[0] == (pos[0] + 2)) {
                    break;
                } else if (obstacle[1] == (pos[1] - 2)) {
                    robot.rotateRight();
                    break;
                } else if (obstacle[1] == (pos[1] + 2)) {
                    robot.rotateLeft();
                    break;
                } else {
                    robot.rotateRight();
                    robot.rotateRight();
                    break;
                }
            case Constant.EAST:
                if (obstacle[1] == (pos[1] + 2)) {
                    break;
                } else if (obstacle[0] == (pos[0] - 2)) {
                    robot.rotateRight();
                    break;
                } else if (obstacle[0] == (pos[0] + 2)) {
                    robot.rotateLeft();
                    break;
                } else {
                    robot.rotateRight();
                    robot.rotateRight();
                    break;
                }
            case Constant.WEST:
                if (obstacle[1] == (pos[1] + 2)) {
                    break;
                } else if (obstacle[0] == (pos[0] + 2)) {
                    robot.rotateRight();
                    break;
                } else if (obstacle[0] == (pos[0] - 2)) {
                    robot.rotateLeft();
                    break;
                } else {
                    robot.rotateRight();
                    robot.rotateRight();
                    break;
                }
        }

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
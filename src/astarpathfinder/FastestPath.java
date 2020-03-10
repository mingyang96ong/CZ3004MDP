package astarpathfinder;

import config.Constant;
import connection.ConnectionSocket;
import exploration.Exploration;
import robot.Robot;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class FastestPath {
    public int[] FastestPath(Robot robot, int[] waypoint, int[] goal, int speed, boolean on_grid, boolean move) {
        AStarPathFinder astar = new AStarPathFinder();
        astar.set_direction(robot.getDirection());
        astar.set_first(true);
        int[] path, path1, path2;

        if (astar.is_valid(robot, waypoint)) {
            // move is false when running fastest path
            // removing first turn penalty to find fastest path due to initial calibration before timing
            if (!move) {
                astar.set_first_turn_penalty(false);
            }

            // if way point was set: go to way point first
            path = astar.AStarPathFinder(robot, robot.getPosition(), waypoint, on_grid);

            if (path != null) {
                astar.set_first_turn_penalty(true);
                path1 = path;
                path2 = astar.AStarPathFinder(robot, waypoint, goal, on_grid);
                path = new int[path1.length + path2.length];
                System.arraycopy(path1, 0, path, 0, path1.length);
                System.arraycopy(path2, 0, path, path1.length, path2.length);
            }
        } else {
            astar.set_first_turn_penalty(true);
            path = astar.AStarPathFinder(robot, robot.getPosition(), goal, on_grid);
        }

        if ((path != null) && move) {
            if (ConnectionSocket.checkConnection() && FastestPathThread.getRunning()) {
                realFPmove(path);
            } else {
                move(robot, path, speed);
            }
        }
        System.out.println(Arrays.toString(path));
        System.out.println(Arrays.toString(robot.getWaypoint()));
        System.out.println("Finished Fastest Path");
        return path;
    }

    private void realFPmove(int[] path) {

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int direction : path) {
            if (direction == Constant.FORWARD) {
                count++;
            } else if (direction == Constant.RIGHT) {
                if (count > 0) {
                    sb.append("W").append(count).append("|").append(Constant.TURN_RIGHT);
                } else {
                    sb.append(Constant.TURN_RIGHT);
                }
                count = 1;

            } else if (direction == Constant.LEFT) {
                if (count > 0) {
                    sb.append("W").append(count).append("|").append(Constant.TURN_LEFT);
                } else {
                    sb.append(Constant.TURN_LEFT);
                }
                count = 1;

            } else {
                if (count > 0) {
                    sb.append("W").append(count).append("|").append(Constant.TURN_RIGHT).append(Constant.TURN_RIGHT);
                } else {
                    sb.append(Constant.TURN_RIGHT).append(Constant.TURN_RIGHT);
                }
                count = 1;
            }
        }
        if (count >= 1) {
            sb.append("W").append(count).append("|");
        }
        String msg = sb.toString();
        System.out.println("Message sent for FastestPath real run: " + msg);
        ConnectionSocket.getInstance().sendMessage(msg);
    }

    private void move(Robot robot, int[] path, int speed) {
        Exploration ex = new Exploration();

        for (int direction : path) {
            if (!connection.ConnectionSocket.checkConnection()) {
                try {
                    TimeUnit.SECONDS.sleep(speed);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (direction == Constant.FORWARD) {
                if (ex.check_front_empty(robot)) {
                    robot.forward(1);
                } else {
                    return;
                }
            } else if (direction == Constant.RIGHT) {
                robot.updateMap();
                robot.rotateRight();
                if (ex.check_front_empty(robot)) {
                    robot.forward(1);
                } else {
                    return;
                }
            } else if (direction == Constant.LEFT) {
                robot.updateMap();
                robot.rotateLeft();
                if (ex.check_front_empty(robot)) {
                    robot.forward(1);
                } else {
                    return;
                }
            } else {
                robot.updateMap();
                robot.rotateRight();
                robot.updateMap();
                robot.rotateRight();
                if (ex.check_front_empty(robot)) {
                    robot.forward(1);
                } else {
                    return;
                }
            }
        }

        robot.updateMap();
    }
}

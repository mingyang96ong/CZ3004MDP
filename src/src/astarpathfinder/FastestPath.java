package astarpathfinder;

import config.Constant;
import robot.Robot;

public class FastestPath {
    public void FastestPath(Robot robot, int[] waypoint, int speed) {
        AStarPathFinder astar = new AStarPathFinder();

        if (astar.is_valid(robot, waypoint)) {
            // if way point was set: go to way point first
            astar.AStarPathFinder(robot, robot.getPosition(), waypoint, true, speed);
        }
        astar.AStarPathFinder(robot, robot.getPosition(), Constant.END, true, speed);

    }
}

package astarpathfinder;

import config.Constant;
import robot.Robot;

public class FastestPath {
    public void FastestPath(Robot robot, int[] waypoint) {
        AStarPathFinder astar = new AStarPathFinder();

//        // set way point
//        astar.AStarPathFinder(robot, robot.getPosition(), WAYPOINT, true);
//        astar.AStarPathFinder(robot, robot.getPosition(), Constant.END, true);
        if (astar.is_valid(robot, waypoint)) {
            // if way point was set: go to way point first
            astar.AStarPathFinder(robot, robot.getPosition(), waypoint, true);
        }

        // while there is no way point setter
        astar.AStarPathFinder(robot, robot.getPosition(), Constant.END, true);
    }
}

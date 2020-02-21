package astarpathfinder;

import config.Constant;
import robot.SimulatorRobot;

public class FastestPath {
    public void FastestPath(SimulatorRobot robot) {
        AStarPathFinder astar = new AStarPathFinder();

//        // set way point
//        astar.AStarPathFinder(robot, robot.getPosition(), WAYPOINT, true);
//        astar.AStarPathFinder(robot, robot.getPosition(), Constant.END, true);

        // while there is no way point setter
        astar.AStarPathFinder(robot, robot.getPosition(), Constant.END, true);
    }
}

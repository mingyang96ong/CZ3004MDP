import java.util.Map;

import config.Constant;
import robot.RealRobot;

public class test {
	public static void main(String[] args) {
		int waypoint[] = new int[] {-1, -1};
		RealRobot r = RealRobot.getInstance();
		r.setWaypoint(5, 6);
		waypoint = r.getWaypoint();
		System.out.println("[" + waypoint[0] + "," + waypoint[1] + "]");
		r.setWaypoint(6, 7);
		waypoint = r.getWaypoint();
		System.out.println("[" + waypoint[0] + "," + waypoint[1] + "]");
		r.getMap().setGrid(6, 7, Constant.POSSIBLEGRIDLABELS[1]);
		waypoint = r.getWaypoint();
		System.out.println("[" + waypoint[0] + "," + waypoint[1] + "]");
		System.out.println(r.getMap().getGrid(6, 7));
	}
}

package policyIteration;

import config.Constant;

public class WayPointGrid extends Grid{
	public WayPointGrid() {
		this.setGridType(Constant.POSSIBLEGRIDLABELS[3]);
		this.setReward(Constant.WAYPOINTREWARD);
	}
	
	public void print() {
		System.out.print("  Waypoint  |");
	}
}

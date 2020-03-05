package policyIteration;

import config.Constant;

public class ObstacleGrid extends Grid{
	public ObstacleGrid() {
		this.setGridType(Constant.POSSIBLEGRIDLABELS[2]);
		this.setReward(Constant.OBSTACLEREWARD);
	}
	
	public void print() {
		System.out.print("  Obstacle  |");
	}
}

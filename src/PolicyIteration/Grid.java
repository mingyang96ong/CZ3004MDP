package PolicyIteration;

import config.Constant;

public abstract class Grid {
	protected double reward;
	protected double utility = 0;
	protected String gridType;
	protected String policy = Constant.POSSIBLEROBOTDIRECTION[0];
	
	public abstract void print();
	
	public double getReward() {
		return reward;
	}
	public String getGridType() {
		return gridType;
	}
	
	public String getPolicy() {
		return policy;
	}
	
	public double getUtility() {
		return utility;
	}
	
	
	public void setReward(double value) {
		this.reward = value;
	}
	
	public void setUtility(double value) {
		this.utility = value;
	}
	
	public void setGridType(String value) {
		this.gridType = value;
	}
	
	public void setPolicy(String value) {
		this.policy = value;
	}
	
	public void printUtility() {
		System.out.print(" " + utility +  " |");
	}
	
	public void printPolicy() {
		System.out.print(" " + policy +  " |");
	}
}

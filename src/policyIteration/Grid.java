package policyIteration;

import config.Constant;

public abstract class Grid {
	protected double reward;
	protected double utility = 0;
	protected String gridType;
	protected int policy = Constant.NORTH;
	
	public abstract void print();
	
	public double getReward() {
		return reward;
	}
	public String getGridType() {
		return gridType;
	}
	
	public int getPolicy() {
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
	
	public void setPolicy(int value) {
		this.policy = value;
	}
	
	public void printUtility() {
		System.out.print(" " + utility +  " |");
	}
	
	public void printPolicy() {
		String p;
		switch (policy) {
			case Constant.NORTH:
				p = "^";
				break;
			case Constant.EAST:
				p = ">";
				break;
			case Constant.SOUTH:
				p = "v";
				break;
			case Constant.WEST:
				p = "<";
				break;
			default:
				p = "" + policy;
		}
		System.out.print(" " + p +  " |");
	}
}

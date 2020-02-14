package PolicyIteration;

import config.Constant;

public class EmptyGrid extends Grid{
	public EmptyGrid() {
		this.setGridType(Constant.POSSIBLEGRIDLABELS[1]);
		this.setReward(Constant.NORMALREWARD);
	}
	
	public void print() {
		System.out.print("    Empty   |");
	}
}

package policyIteration;
import config.Constant;

public class StartGrid extends Grid{
	public StartGrid() {
		this.setGridType(Constant.POSSIBLEGRIDLABELS[4]);
		this.setReward(Constant.NORMALREWARD);
	}
	
	public void print() {
		System.out.print(" Startpoint |");
	}
}

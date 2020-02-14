package PolicyIteration;

import config.Constant;

public class EndGrid extends Grid{
	public EndGrid() {
		this.setGridType(Constant.POSSIBLEGRIDLABELS[5]);
		this.setReward(Constant.ENDREWARD);
	}
	
	public void print() {
		System.out.print("  Endpoint  |");
	}
}

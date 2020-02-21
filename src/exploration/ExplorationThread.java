package exploration;

import robot.SimulatorRobot;

public class ExplorationThread extends Thread{
	private SimulatorRobot r;
	public ExplorationThread(SimulatorRobot r) {
		super("ExplorationThread");
		this.r = r;
		start();
	}
	
	public void run() {
		Exploration e = new Exploration();
		e.Exploration(r);
	}
	
	
}

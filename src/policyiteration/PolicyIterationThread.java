package policyiteration;

import map.Map;
import policyiteration.PolicyIteration;
import robot.SimulatorRobot;

public class PolicyIterationThread extends Thread{
	private SimulatorRobot r;
	private Map loadedMap;
	public PolicyIterationThread(SimulatorRobot r, Map loadedMap) {
		super("PolicyIterationThread");
		this.r = r;
		this.loadedMap = loadedMap;
		start();
	}
	
	public void run() {
		r.setMap(loadedMap);
		r.toggleMap();
		r.toggleMap();
		PolicyIteration pi = new PolicyIteration(r);
		pi.convoluteMap();
		pi.printGrids();
		pi.run();
		pi.printUtilities();
		pi.printPolicies();
	}
	
}

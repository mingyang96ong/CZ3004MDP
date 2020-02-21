package policyiteration;

import map.Map;
import policyiteration.PolicyIteration;

import robot.SimulatorRobot;
import robot.Robot;

public class PolicyIterationThread extends Thread{
	private SimulatorRobot r;
	private Map loadedMap;
	
	public PolicyIterationThread(SimulatorRobot r, Map map) {
		super("PolicyIterationThread");
		this.r = r;
		this.loadedMap = map;
		start();
	}
	
	public void run() {
		r.setMap(loadedMap);
		r.toggleMap();
		r.toggleMap();
		PolicyIteration pi = new PolicyIteration(r);
		pi.run();
		
	}
	
}

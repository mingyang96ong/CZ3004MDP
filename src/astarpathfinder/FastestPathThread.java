package astarpathfinder;

import java.util.concurrent.atomic.AtomicBoolean;

import config.Constant;
import connection.ConnectionSocket;
import exploration.Exploration;
import map.Map;
import robot.Robot;

public class FastestPathThread extends Thread {
    private Robot r;
    private static final AtomicBoolean running = new AtomicBoolean(false);
    private static final AtomicBoolean completed = new AtomicBoolean(false);
    private static FastestPathThread thread = null;
    private int[] waypoint;
    private FastestPathThread(Robot r, int[] waypoint) {
        super();
        this.r = r;
        start();
        this.waypoint = waypoint;
    }
    
    public static FastestPathThread getInstance(Robot r , int[] waypoint) {
    	if (thread == null) {
    		thread = new FastestPathThread(r, waypoint);
    	}
    	return thread;
    }

    public void run() {
    	running.set(true);
        FastestPath fp = new FastestPath();
        fp.FastestPath(r, waypoint);
        if (running.get()) {
        	completed.set(true);
        }
        else {
        	completed.set(false);
        }
        stopThread();
		if (ConnectionSocket.checkConnection()) {
			ConnectionSocket.getInstance().sendMessage(Constant.END_TOUR);
		}
    }
	
	public static boolean getRunning() {
		return running.get();
	}
	
	public static void stopThread() {
		running.set(false);
		thread = null;
	}
	
	public static boolean getCompleted() {
		return completed.get();
	}
}

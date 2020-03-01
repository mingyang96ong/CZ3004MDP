package exploration;

import java.util.concurrent.atomic.AtomicBoolean;

import config.Constant;
import connection.ConnectionManager;
import connection.ConnectionSocket;
import robot.Robot;

public class ExplorationThread extends Thread{
	private Robot r;
	private static final AtomicBoolean running = new AtomicBoolean(false);
	private static final AtomicBoolean completed = new AtomicBoolean(false);
	private int time;
	private int percentage;
	private static ExplorationThread thread = null;
	private ExplorationThread(Robot r , int time, int percentage) {
		super();
		this.r = r;
		this.time = time;
		this.percentage = percentage;
		start();
	}
	
	public static ExplorationThread getInstance(Robot r, int time, int percentage) {
		if (thread == null) {
			thread = new ExplorationThread(r, time, percentage);
		}
		return thread;
	}
	
	public void run() {
		running.set(true);
		Exploration e = new Exploration();
		e.Exploration(r, time, percentage);
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

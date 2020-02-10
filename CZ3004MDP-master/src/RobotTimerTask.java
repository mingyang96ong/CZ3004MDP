import java.util.TimerTask;

public class RobotTimerTask extends TimerTask{
	private Robot r;
	private String command;
	public RobotTimerTask(Robot r, String command) {
		this.r = r;
		this.command = command;
	}
	@Override
	public void run() {
		if (command == "Update") {
			r.updateMap();
		}
		// TODO Auto-generated method stub
		r.moveRight();
	}
	
}

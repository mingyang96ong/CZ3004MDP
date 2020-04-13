package timertask;
import java.util.TimerTask;

import simulator.AddJButtonActionListener;

public class EnableButtonTask extends TimerTask{
	private AddJButtonActionListener AL;
	// This class basically enables all the button after the movement
	public EnableButtonTask(AddJButtonActionListener AL) {
		this.AL = AL;
	}
	public void run() {
		AL.enableButtons();
	}
}

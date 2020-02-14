package timertask;
import java.util.TimerTask;
import main.AddJButtonActionListener;

public class EnableButtonTask extends TimerTask{
	private AddJButtonActionListener AL;
	public EnableButtonTask(AddJButtonActionListener AL) {
		this.AL = AL;
	}
	public void run() {
		AL.enableButtons();
	}
}

import java.util.TimerTask;

public class MoveImageWithButtonTask extends TimerTask{
	private ImageComponent item;
	private String command;
	private int pixel;
	private AddJButtonActionListener AL;
	public MoveImageWithButtonTask(ImageComponent i, String s, int p, AddJButtonActionListener AL) {
		item = i;
		command = s;
		pixel = p;
		this.AL = AL;
	}
	public void run() {
		if (command.toUpperCase().compareTo("ENABLE") == 0) {
			AL.enableButtons();
		}
		else {
			item.moveTo(pixel, command);
		}
	}
}

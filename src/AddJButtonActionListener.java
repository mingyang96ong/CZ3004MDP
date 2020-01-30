import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.ArrayList;

public class AddJButtonActionListener implements ActionListener{
	private JFrame frame;
	private int x = 1000;
	private int y = 200;
	private ImageComponent robotImage;
	private ArrayList<JButton> Buttons = new ArrayList<JButton>();
	private Timer t = new Timer();
	
	public AddJButtonActionListener(JFrame frame, ImageComponent robotImage) {
		this.frame = frame;
		this.robotImage = robotImage;
		
		// Create the UI Component
		JLabel l = new JLabel("Manual Control:");
		JButton right = new JButton();
		JButton left = new JButton();
		JButton down = new JButton();
		JButton up = new JButton();
		
		// Set Icon or Image to the UI Component
		right.setIcon(new ImageIcon(new ImageIcon(".\\images\\right.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		left.setIcon(new ImageIcon(new ImageIcon(".\\images\\left.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		down.setIcon(new ImageIcon(new ImageIcon(".\\images\\down.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		up.setIcon(new ImageIcon(new ImageIcon(".\\images\\up.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		
		// For the Button to do something, you need to add the button to this Action Listener and set the command for the ActionListener to receive
		right.addActionListener(this);
		right.setActionCommand("Right");
		left.addActionListener(this);
		left.setActionCommand("Left");
		down.addActionListener(this);
		down.setActionCommand("Down");
		up.addActionListener(this);
		up.setActionCommand("Up");
		
		
		// Set the size (x, y, width, height) of the UI label
		
		l.setBounds(x, y, 100, 50);
		left.setBounds(x, y, 50, 50);
		right.setBounds(x + 100, y, 50, 50);
		up.setBounds(x + 100, y - 100, 50, 50);
		down.setBounds(x + 100, y, 50, 50);
		
		// Set location of the UI component
		l.setLocation(x, y - 100);
		left.setLocation(x, y);
		right.setLocation(x + 100, y);
		up.setLocation(x + 50, y - 50);
		down.setLocation(x + 50, y);
		
		// Add the UI component to the frame
		frame.add(l);
		frame.add(right);
		frame.add(left);
		frame.add(up);
		frame.add(down);
		
		// Set Visibility of UI Component
		l.setVisible(true);
		right.setVisible(true);
		left.setVisible(true);
		up.setVisible(true);
		down.setVisible(true);
		
		// Add to the list of buttons
		Buttons.add(right);
		Buttons.add(left);
		Buttons.add(up);
		Buttons.add(down);
	}
	
	public void disableButtons() {
		for (int i = 0; i < Buttons.size(); i++){
			Buttons.get(i).setEnabled(false);
		}
	}
	
	public void enableButtons() {
		for (int i = 0; i < Buttons.size(); i++){
			Buttons.get(i).setEnabled(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		// TODO Auto-generated method stub
		int step = 1;
		int delay = 50;
		if (action.equals("Right")) {
			System.out.println("Right clicked");
			disableButtons();
			for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
				t.schedule(new MoveImageWithButtonTask(robotImage, "Right", 1, this), delay * (i + 1));
			}
			t.schedule(new MoveImageWithButtonTask(robotImage, "Enable", 1, this), delay * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.equals("Left")) {
			System.out.println("Left clicked");
			disableButtons();
			for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
				t.schedule(new MoveImageWithButtonTask(robotImage, "Left", 1, this), delay * (i + 1));
			}
			t.schedule(new MoveImageWithButtonTask(robotImage, "Enable", 1, this), delay * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.equals("Up")) {
			System.out.println("Up clicked");
			disableButtons();
			for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
				t.schedule(new MoveImageWithButtonTask(robotImage, "Up", 1, this), delay * (i + 1));
			}
			t.schedule(new MoveImageWithButtonTask(robotImage, "Enable", 1, this), delay * (step * Constant.GRIDWIDTH + 1));

		}
		if (action.equals("Down")) {
			System.out.println("Down clicked");
			disableButtons();
			for (int i = 0; i < step * Constant.GRIDWIDTH; i++) {
				t.schedule(new MoveImageWithButtonTask(robotImage, "Down", 1, this), delay * (i + 1));
			}
			t.schedule(new MoveImageWithButtonTask(robotImage, "Enable", 1, this), delay * (step * Constant.GRIDWIDTH + 1));
		}
	}
}

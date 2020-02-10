import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.HashMap;

public class AddJButtonActionListener implements ActionListener{
	private JFrame frame;
	private int x = 1000;
	private int y = 200;
	private SimulatorRobot r;
	private ArrayList<JButton> Buttons = new ArrayList<JButton>();
	private HashMap<String, JLabel> Labels = new HashMap<String, JLabel>();
	private Timer t = new Timer();
	private int step = 1;
	
	public AddJButtonActionListener(JFrame frame, SimulatorRobot r) {
		this.frame = frame;
		this.r = r;
		
		// Create the UI Component
		JLabel l = new JLabel("Manual Control:");
		JButton right = new JButton();
		JButton left = new JButton();
		JButton down = new JButton();
		JButton up = new JButton();
		JButton update = new JButton();
		JButton checkMap = new JButton();
		JButton toggleMap = new JButton();
		JButton resetRobot = new JButton();
		JLabel robotView = new JLabel("Robot's View");
		JLabel simulatedMap = new JLabel("Simulated Map");
		JButton exploration = new JButton();
		
		// Set Icon or Image to the UI Component
		right.setIcon(new ImageIcon(new ImageIcon(".\\images\\right.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		left.setIcon(new ImageIcon(new ImageIcon(".\\images\\left.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		down.setIcon(new ImageIcon(new ImageIcon(".\\images\\down.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		up.setIcon(new ImageIcon(new ImageIcon(".\\images\\up.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		update.setText("Update");
		checkMap.setText("Check Map");
		toggleMap.setText("Toggle Map");
		resetRobot.setText("Restart");
		exploration.setText("Exploration");
		
		// For the Button to do something, you need to add the button to this Action Listener and set the command for the ActionListener to receive
		right.addActionListener(this);
		right.setActionCommand("Right");
		left.addActionListener(this);
		left.setActionCommand("Left");
		down.addActionListener(this);
		down.setActionCommand("Down");
		up.addActionListener(this);
		up.setActionCommand("Up");
		update.addActionListener(this);
		update.setActionCommand("Update");
		checkMap.addActionListener(this);
		checkMap.setActionCommand("Check Map");
		toggleMap.addActionListener(this);
		toggleMap.setActionCommand("Toggle Map");
		resetRobot.addActionListener(this);
		resetRobot.setActionCommand("Restart");
		exploration.addActionListener(this);
		exploration.setActionCommand("Exploration");
		
		
		// Set the size (x, y, width, height) of the UI label
		
		l.setBounds(x, y, 100, 50);
		left.setBounds(x, y, 50, 50);
		right.setBounds(x + 100, y, 50, 50);
		up.setBounds(x + 100, y - 100, 50, 50);
		down.setBounds(x + 100, y, 50, 50);
		update.setBounds(x + 200, y + 200, 100, 100);
		checkMap.setBounds(x + 200, y + 200, 100, 100);
		toggleMap.setBounds(x, y + 100, 150, 100);
		resetRobot.setBounds(x + 200, y + 100, 150, 100 );
		robotView.setFont(new Font(robotView.getFont().getName(), Font.BOLD, 30));
		robotView.setBounds(x + 100, y - 100, 200, 50);
		simulatedMap.setFont(new Font(simulatedMap.getFont().getName(), Font.BOLD, 30));
		simulatedMap.setBounds(x + 100, y - 100, 300, 50);
		exploration.setFont(new Font(exploration.getFont().getName(), Font.BOLD, 30));
		exploration.setBounds(x + 100, y - 100, 300, 50);
		
		// Set location of the UI component
		l.setLocation(x, y - 100);
		left.setLocation(x, y);
		right.setLocation(x + 100, y);
		up.setLocation(x + 50, y - 50);
		down.setLocation(x + 50, y);
		update.setLocation(x + 200, y - 50);
		checkMap.setLocation(x + 350, y - 50);
		toggleMap.setLocation(x, y + 100);
		resetRobot.setLocation(x + 200, y + 100);
		robotView.setLocation(x - 600, y - 185);
		simulatedMap.setLocation(x - 600, y - 185);
		exploration.setLocation(x, y + 250);
		
		// Add the UI component to the frame
		frame.add(l);
		frame.add(right);
		frame.add(left);
		frame.add(up);
		frame.add(down);
		frame.add(update);
		frame.add(checkMap);
		frame.add(toggleMap);
		frame.add(resetRobot);
		frame.add(robotView);
		frame.add(simulatedMap);
		frame.add(exploration);
		
		// Set Visibility of UI Component
		l.setVisible(true);
		right.setVisible(true);
		left.setVisible(true);
		up.setVisible(true);
		down.setVisible(true);
		update.setVisible(true);
		checkMap.setVisible(true);
		toggleMap.setVisible(true);
		resetRobot.setVisible(true);
		robotView.setVisible(true);
		simulatedMap.setVisible(false);
		exploration.setVisible(true);
		
		// Add button to the list of buttons
		Buttons.add(right);
		Buttons.add(left);
		Buttons.add(up);
		Buttons.add(down);
		Buttons.add(update);
		Buttons.add(checkMap);
		Buttons.add(toggleMap);
		Buttons.add(resetRobot);
		Buttons.add(exploration);
		
		// Add label to the hashmap
		Labels.put("l", l);
		Labels.put("robotView", robotView);
		Labels.put("simulatedMap", simulatedMap);
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
	
//	public void moveRight() {
//		for (int i = 0; i < Constant.GRIDWIDTH; i++) {
//			t.schedule(new MoveImageWithButtonTask(robotImage, "Right", 1, this), delay * (i + 1));
//		}
//		t.schedule(new MoveImageWithButtonTask(robotImage, "Enable", 1, this), delay * (step * Constant.GRIDWIDTH + 1));
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		// TODO Auto-generated method stub
		if (action.equals("Right")) {
			System.out.println("Right clicked");
			disableButtons();
			if (!Labels.get("robotView").isVisible()) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
			}
			r.moveRight();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.equals("Left")) {
			System.out.println("Left clicked");
			disableButtons();
			if (!Labels.get("robotView").isVisible()) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
			}
			r.moveLeft();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.equals("Up")) {
			System.out.println("Up clicked");
			disableButtons();
			if (!Labels.get("robotView").isVisible()) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
			}
			r.moveUp();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));

		}
		if (action.equals("Down")) {
			System.out.println("Down clicked");
			disableButtons();
			if (!Labels.get("robotView").isVisible()) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
			}
			r.moveDown();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.contentEquals("Update")) {
			disableButtons();
			if (!Labels.get("robotView").isVisible()) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
			}
			r.updateMap();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.contentEquals("Check Map")) {
			disableButtons();
			JOptionPane.showMessageDialog(null, r.checkMap(), "Result of checking map", JOptionPane.INFORMATION_MESSAGE);
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.contentEquals("Toggle Map")) {
			disableButtons();
			if (r.toggleMap().compareTo("robot") == 0) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
			}
			else {
				Labels.get("robotView").setVisible(false);
				Labels.get("simulatedMap").setVisible(true);
			}
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.contentEquals("Restart")) {
			disableButtons();
			Labels.get("robotView").setVisible(true);
			Labels.get("simulatedMap").setVisible(false);
			r.restartRobot();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.contentEquals("Exploration")) {
			disableButtons();
			Exploration E = new Exploration();
			E.Exploration(r);
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
	}
}

package main;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.Timer;
import java.util.ArrayList;
import java.util.HashMap;

import config.Constant;
import exploration.ExplorationThread;
import robot.SimulatorRobot;
import map.Map;
import timertask.EnableButtonTask;
//import policyiteration.PolicyIterationThread;
//import policyiteration.PolicyIteration;

public class AddJButtonActionListener implements ActionListener{
	private JFrame frame;
	private int x = 1000;
	private int y = 200;
	private SimulatorRobot r;
	private ArrayList<JComponent> Buttons = new ArrayList<JComponent>();
	private HashMap<String, JLabel> Labels = new HashMap<String, JLabel>();
	private HashMap  <String, String> filePath= new HashMap<String, String>();
	private Timer t = new Timer();
	private int step = 1;
	private Map loadedMap;
//	private PolicyIteration pi;
	
	public AddJButtonActionListener(JFrame frame, SimulatorRobot r) {
		this.frame = frame;
		this.r = r;
		String[] arr = getArenaMapFileNames();
		
		
		// Create the UI Component
		JLabel mcLabel = new JLabel("Manual Control:");
		JLabel loadMapLabel = new JLabel("Select the map you wish to load:");
		JButton right = new JButton();
		JButton left = new JButton();
		JButton up = new JButton();
		JButton update = new JButton();
		JButton checkMap = new JButton();
		JButton toggleMap = new JButton();
		JButton resetRobot = new JButton();
		JLabel robotView = new JLabel("Robot's View");
		JLabel simulatedMap = new JLabel("Simulated Map");
		JButton exploration = new JButton();
		JButton policyIteration = new JButton();
		JComboBox <String> arenaMap = new JComboBox<String>(arr);
		
		// Set Icon or Image to the UI Component
		right.setIcon(new ImageIcon(new ImageIcon(".\\images\\right.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		left.setIcon(new ImageIcon(new ImageIcon(".\\images\\left.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		up.setIcon(new ImageIcon(new ImageIcon(".\\images\\up.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT)));
		update.setText("Update");
		checkMap.setText("Check Map");
		toggleMap.setText("Toggle Map");
		resetRobot.setText("Restart");
		exploration.setText("Exploration");
		policyIteration.setText("Policy Iteration");
		
		// For the Button to do something, you need to add the button to this Action Listener and set the command for the ActionListener to receive
		right.addActionListener(this);
		right.setActionCommand("Right");
		left.addActionListener(this);
		left.setActionCommand("Left");
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
		policyIteration.addActionListener(this);
		policyIteration.setActionCommand("Policy Iteration");
		arenaMap.addActionListener(this);
		arenaMap.setActionCommand("Load Map");
		arenaMap.setSelectedIndex(-1); //  This line will print null in console

		
		// Set the size (x, y, width, height) of the UI label
		
		mcLabel.setBounds(x, y - 100, 100, 50);
		loadMapLabel.setBounds(x, y + 150, 300, 50);
		left.setBounds(x + 100, y - 100, 50, 50);
		right.setBounds(x + 200, y - 100, 50, 50);
		up.setBounds(x + 150, y - 100, 50, 50);
		update.setBounds(x + 275, y - 100, 100, 50);
		checkMap.setBounds(x, y - 25, 100, 50);
		toggleMap.setBounds(x + 150, y - 25, 110, 50);
		resetRobot.setBounds(x + 300, y - 25, 100, 50 );
		robotView.setBounds(x - 600, y - 185, 200, 50);
		simulatedMap.setBounds(x + 100, y - 100, 300, 50);
		exploration.setBounds(x, y + 50, 110, 50);
		policyIteration.setBounds(x + 150, y + 50, 150, 50);
		arenaMap.setBounds(x + 200, y + 160, 120, 30);
		
		// Set fonts for the labels
		mcLabel.setFont(new Font(mcLabel.getFont().getName(), Font.ITALIC, 13));
		robotView.setFont(new Font(robotView.getFont().getName(), Font.BOLD, 30));
		simulatedMap.setFont(new Font(simulatedMap.getFont().getName(), Font.BOLD, 30));
		
		// Set location of the UI component
		mcLabel.setLocation(x, y - 100);
		loadMapLabel.setLocation(x, y + 150);
		left.setLocation(x + 100, y - 100);
		right.setLocation(x + 200, y - 100);
		up.setLocation(x + 150, y - 100);
		update.setLocation(x + 275, y - 100);
		checkMap.setLocation(x, y - 25);
		toggleMap.setLocation(x + 150, y - 25);
		resetRobot.setLocation(x + 300, y - 25);
		robotView.setLocation(x - 600, y - 185);
		simulatedMap.setLocation(x - 600, y - 185);
		exploration.setLocation(x, y + 50);
		policyIteration.setLocation(x + 150, y + 50);
		arenaMap.setLocation(x + 200, y + 160);
		
		// Add the UI component to the frame
		frame.add(mcLabel);
		frame.add(loadMapLabel);
		frame.add(right);
		frame.add(left);
		frame.add(up);
		frame.add(update);
		frame.add(checkMap);
		frame.add(toggleMap);
		frame.add(resetRobot);
		frame.add(robotView);
		frame.add(simulatedMap);
		frame.add(exploration);
		frame.add(policyIteration);
		frame.add(arenaMap);
		
		// Set Visibility of UI Component
		mcLabel.setVisible(true);
		loadMapLabel.setVisible(true);
		right.setVisible(true);
		left.setVisible(true);
		up.setVisible(true);
		update.setVisible(true);
		checkMap.setVisible(true);
		toggleMap.setVisible(true);
		resetRobot.setVisible(true);
		robotView.setVisible(true);
		simulatedMap.setVisible(false);
		exploration.setVisible(true);
		policyIteration.setVisible(true);
		arenaMap.setVisible(true);
		
		// Add button to the list of buttons
		Buttons.add(right);
		Buttons.add(left);
		Buttons.add(up);
		Buttons.add(update);
		Buttons.add(checkMap);
		Buttons.add(toggleMap);
		Buttons.add(resetRobot);
		Buttons.add(exploration);
		Buttons.add(policyIteration);
		Buttons.add(arenaMap);
		
		// Add label to the hashmap
		Labels.put("mcLabel", mcLabel);
		Labels.put("loadMapLabel", loadMapLabel);
		Labels.put("robotView", robotView);
		Labels.put("simulatedMap", simulatedMap);
	}
	
	public String[] getArenaMapFileNames() {
		File folder = new File("./sample arena");
		filePath= new HashMap<String, String>();
		for (File file: folder.listFiles()) {
			if (file.getName().endsWith(".txt")) {
//				System.out.println(file.getName());
				filePath.put(file.getName().substring(0, file.getName().lastIndexOf(".txt")), file.getAbsolutePath());
			}
		}
//		System.out.println(filePath.size());
		String[] fileName = new String[filePath.size()+1];
		int i = filePath.size();
//		System.out.println("Printing key....");
		for (String key: filePath.keySet()) {
			if (i == 0) {
				fileName[i] = "Choose a map to load";
				break;
			}
//			System.out.println(key);
			fileName[i] = key;
			i--;
		}
		return fileName;
	}
	
	public Map getGridfromFile(String path, String fileName, String[][] grid) throws Exception, FileNotFoundException, IOException{
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		int heightCount = 0;
		while (line != null) {
			line = line.strip().toUpperCase();
//			System.out.println(line);
			if (line.length() != Constant.BOARDWIDTH) {
				throw new Exception("The format of the " + fileName + " does not match the board format.");
			}
			for (int i = 0; i < line.length(); i++) {
				switch(line.charAt(i)) {
					case 'S':
						grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[4];
						break;
					case 'U':
						// Here, we set to explored instead of Unexplored
						grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[1];
						break;
					case 'W':
						grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[3];
						break;
					case 'E':
						grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[5];
						break;
					case 'O':
						grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[2];
						break;
					default:
						throw new Exception("There is unrecognised character symbol in " + fileName + ".");
				}
			}
//			System.out.println(line);
			heightCount++;
			line = br.readLine();
		}
		if (heightCount != Constant.BOARDHEIGHT) {
			throw new Exception("The format of the " + fileName + " does not match the board format.");
		}
		br.close();
		loadedMap = new Map(grid);
		return loadedMap;
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
			r.rotateRight();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.equals("Left")) {
			System.out.println("Left clicked");
			disableButtons();
			if (!Labels.get("robotView").isVisible()) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
			}
			r.rotateLeft();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
		if (action.equals("Up")) {
			System.out.println("Up clicked");
			disableButtons();
			if (!Labels.get("robotView").isVisible()) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
				r.toggleMap();
			}
			r.forward();
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));

		}
//		if (action.equals("Down")) {
//			System.out.println("Down clicked");
//			disableButtons();
//			if (!Labels.get("robotView").isVisible()) {
//				Labels.get("robotView").setVisible(true);
//				Labels.get("simulatedMap").setVisible(false);
//			}
//			r.moveDown();
//			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
//		}
		if (action.contentEquals("Update")) {
			disableButtons();
			if (!Labels.get("robotView").isVisible()) {
				Labels.get("robotView").setVisible(true);
				Labels.get("simulatedMap").setVisible(false);
			}
			boolean isObstacle[] = r.updateMap();
			for (int i = 0; i < isObstacle.length; i++) {
				System.out.print(isObstacle[i] + " ");
			}
			System.out.println();
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
			ExplorationThread et = new ExplorationThread(r);
		}
		
		if (action.contentEquals("Load Map")) {
			JComboBox <String> arenaMap = (JComboBox <String>)e.getSource();
			String selectedFile = (String) arenaMap.getSelectedItem();
			if (selectedFile == null || selectedFile.compareTo("Choose a map to load") == 0) {
				return;
			}
			String[][] grid = new String[Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
			disableButtons();
			try {
				Map map = getGridfromFile(filePath.get(selectedFile), selectedFile, grid);
				r.setTrueMap(map);
				if (Labels.get("simulatedMap").isVisible()) {
					Labels.get("robotView").setVisible(true);
					Labels.get("simulatedMap").setVisible(false);
					r.toggleMap();
					r.toggleMap();
				}
			}
			catch (FileNotFoundException f){
				System.out.println("File not found");
			}
			catch (IOException IO) {
				System.out.println("IOException when reading" + selectedFile);
			}
			catch (Exception eX) {
				System.out.println(eX.getMessage());
			}
			
			t.schedule(new EnableButtonTask(this), Constant.DELAY * (step * Constant.GRIDWIDTH + 1));
		}
//		if (action.contentEquals("Policy Iteration")) {
//			PolicyIterationThread tt = new PolicyIterationThread(r, loadedMap);
//
//		}
	}
}

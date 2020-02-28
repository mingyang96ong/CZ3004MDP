package simulator;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import config.Constant;
import map.Map;
import policyIteration.PolicyIteration;
import robot.SimulatorRobot;

public class Simulator {
	public final static String TITLE = "Group 15 Simulator";
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Simulator() {
		JFrame frame = new JFrame(TITLE);
		frame.setIconImage(new ImageIcon(Constant.SIMULATORICONIMAGEPATH).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(Constant.MARGINLEFT + Constant.GRIDWIDTH * Constant.BOARDWIDTH + 100, Constant.MARGINTOP + Constant.GRIDHEIGHT * Constant.BOARDHEIGHT + 100);
//		System.out.print(Constant.TURNCOST);
		boolean simulation = true; // May be used in the future
		
		/* Set the starting position by the center block 
		 * The value can only range from 1, 1 to 18, 13
		 */
		// NORTH, EAST, SOUTH, WEST
		SimulatorRobot sr = new SimulatorRobot(frame, 0, 0, 0); 

		frame.setVisible(true);
		
	}
	
}


import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Simulator {
	public final static String TITLE = "Group 15 Simulator";
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static void main(String[] args) {
		JFrame frame = new JFrame(TITLE);
		frame.setIconImage(new ImageIcon(Constant.SIMULATORICONIMAGEPATH).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		boolean simulation = true; // May be used in the future
		
		/* Set the starting position by the center block 
		 * The value can only range from 1, 1 to 18, 13
		 */
		// NORTH, EAST, SOUTH, WEST
		SimulatorRobot sr = new SimulatorRobot(frame, 0, 0, Constant.POSSIBLEROBOTDIRECTION[2]); 
		
//		This is to create a fake map for the exploration run.
//		int x = 1;
//		int y = 1;
//		RobotImageComponent robotImage = new RobotImageComponent(Constant.ROBOTIMAGEPATH, Constant.ROBOTWIDTH, Constant.ROBOTHEIGHT);
//		frame.add(robotImage);
//		robotImage.setLocation(Constant.MARGINLEFT + Constant.GRIDWIDTH/2 + (x-1) * Constant.GRIDWIDTH, Constant.MARGINTOP + Constant.GRIDHEIGHT/2 + (y-1) * Constant.GRIDHEIGHT);
//		
//		Map map = new Map();
//		map.generateRandomMap();
//		map.print();
//		
//		SimulatorMap smap = SimulatorMap.getInstance(frame, map);
		
		
		frame.setVisible(true);

	}
}


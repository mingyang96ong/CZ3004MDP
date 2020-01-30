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
		
		SimulatorRobot sr = new SimulatorRobot(frame, 20, 15); 
		
		/* This is to create a fake map for the exploration run.
		Map map = new Map();
		map.generateRandomMap();
		map.print();
		*/
		
		frame.setVisible(true);

	}
}


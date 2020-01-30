import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Simulator {
	public final static String TITLE = "Simulator";
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Random r = new Random();
	
	public static void main(String[] args) {
		JFrame frame = new JFrame(TITLE);
		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// THE ORDER OF ADDING THE ROBOT INTO THE FRAME MATTERS OTHERWISE IT WILL APPEAR UNDERNEATH THE GRID
		ImageComponent robotImage = new RobotImageComponent(".\\images\\robot.png", Constant.ROBOTWIDTH, Constant.ROBOTHEIGHT);
		frame.add(robotImage);
		frame.setForeground(robotImage.getForeground());
		robotImage.setLocation(Constant.MARGINLEFT + Constant.GRIDWIDTH/2, Constant.MARGINTOP + Constant.GRIDHEIGHT/2);
		
		int[][] gridvalue = new int [Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
		
		for (int i = 0; i < Constant.BOARDWIDTH; i++) {
			for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
				gridvalue[i][j] = r.nextInt(Constant.POSSIBLEGRIDLABELS.length);
				if (j != Constant.BOARDHEIGHT - 1) {
					System.out.print(gridvalue[i][j] + ", " );
				}
				else {
					System.out.print(gridvalue[i][j]);
				}
			}
			System.out.println();
		}
		
		
		
		SimulatorMap smap = new SimulatorMap(frame, new Map(gridvalue));
		AddJButtonActionListener AL = new AddJButtonActionListener(frame, robotImage);
		frame.setVisible(true);

	}
}


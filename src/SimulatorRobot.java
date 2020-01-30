import javax.swing.JFrame;

public class SimulatorRobot extends Robot{
	
	ImageComponent robotImage;
	JFrame frame;
	SimulatorMap smap;
	AddJButtonActionListener buttonListener;
	
	public SimulatorRobot(JFrame frame, int x, int y) {
		this.frame = frame;
		this.x = checkValidX(x);
		this.y = checkValidY(y);
		
		/* THE ORDER OF ADDING THE ROBOT INTO THE FRAME MATTERS OTHERWISE IT WILL APPEAR UNDERNEATH THE GRID
		 * Hence, initialiseRobotImage must run before SimulatorMap create the UI grid
		 */
		
		initialiseRobotImage(this.x, this.y);
		map = new Map();
		smap = SimulatorMap.getInstance(frame, map);
		buttonListener = new AddJButtonActionListener(frame, robotImage);
	}
	
	private void initialiseRobotImage(int x, int y) {
		robotImage = new RobotImageComponent(Constant.ROBOTIMAGEPATH, Constant.ROBOTWIDTH, Constant.ROBOTHEIGHT);
		frame.add(robotImage);
		robotImage.setLocation(Constant.MARGINLEFT + Constant.GRIDWIDTH/2 + (x-1) * Constant.GRIDWIDTH, Constant.MARGINTOP + Constant.GRIDHEIGHT/2 + (y-1) * Constant.GRIDHEIGHT);
	}

	@Override
	public void getSensorValues() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDirection(String direction) {
		// TODO Auto-generated method stub
		
	}
	
}

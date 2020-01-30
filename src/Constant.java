
public class Constant {
	
	// Used for all Real and Simulator Events
	public static final int BOARDWIDTH = 20; // By default, this should be 20. This must be greater than 3 as the Robot assumes to take 3x3 grid.
	public static final int BOARDHEIGHT = 15; // By default, this should be 15. This must be greater than 3 as the Robot assumes to take 3x3 grid.
	public static final int STARTPOINTHEIGHT = 3;
	public static final int STARTPOINTWIDTH = 3;
	public static final int ENDPOINTHEIGHT = 3;
	public static final int ENDPOINTWIDTH = 3;
	
	// For Generating Random Map for Simulator
	public static final int MAXOBSTACLECOUNT = 16;
	
	// For UI Simulator Display
	public static final int GRIDHEIGHT = 40;
	public static final int GRIDWIDTH = 40;
	public static final int MARGINLEFT = 100;
	public static final int MARGINTOP = 100;
	public static final String SIMULATORICONIMAGEPATH = ".\\images\\simulator_icon.ico";
	
	// Image path for UI Simulator
	public static final String UNEXPLOREDIMAGEPATH = ".\\images\\unexplored_grid.png";
	public static final String EXPLOREDIMAGEPATH = ".\\images\\explored_grid.png";
	public static final String OBSTACLEIMAGEPATH = ".\\images\\obstacle_grid.png";
	public static final String WAYPOINTIMAGEPATH = ".\\images\\waypoint_grid.png";
	public static final String STARTPOINTIMAGEPATH = ".\\images\\start_grid.png";
	public static final String ENDPOINTIMAGEPATH = ".\\images\\end_grid.png";
	public static final String ROBOTIMAGEPATH = ".\\images\\robot.png";
	
	public static final String[] GRIDIMAGEPATH = new String[]{UNEXPLOREDIMAGEPATH,
															  EXPLOREDIMAGEPATH,
															  OBSTACLEIMAGEPATH,
															  WAYPOINTIMAGEPATH,
															  STARTPOINTIMAGEPATH,
															  ENDPOINTIMAGEPATH};
	
	// Used in Map and possibly used in real run and simulator
	public static final String[] POSSIBLEGRIDLABELS = new String[]{"Unexplored", "Explored", "Obstacle", "Waypoint", "Startpoint", "Endpoint"};
	public static final String[] POSSIBLEROBOTDIRECTION = new String[]{"North", "East", "South", "West"};

	
	// Avoid changing these values below
	public static final int ROBOTHEIGHT = GRIDHEIGHT * 2; // By default, this should be twice of the grid height.
	public static final int ROBOTWIDTH = GRIDWIDTH * 2; // By default, this should be twice of the grid width.
	public static final int HEIGHT = BOARDHEIGHT * GRIDHEIGHT + MARGINTOP;
	public static final int WIDTH = BOARDWIDTH * GRIDWIDTH + MARGINLEFT;
}

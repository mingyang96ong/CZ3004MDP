package config;
	
public class Constant {
	
	// Used for all Real and Simulator Events
	public static final int BOARDWIDTH = 20; // By default, this should be 20. This must be greater than 3 as the Robot assumes to take 3x3 grid.
	public static final int BOARDHEIGHT = 15; // By default, this should be 15. This must be greater than 3 as the Robot assumes to take 3x3 grid.
	public static final int STARTPOINTHEIGHT = 3;
	public static final int STARTPOINTWIDTH = 3;
	public static final int ENDPOINTHEIGHT = 3;
	public static final int ENDPOINTWIDTH = 3;
	public static final int SHORTSENSORMINRANGE = 1; // This is in number of grid.
	public static final int SHORTSENSORMAXRANGE = 3; // This is in number of grid.
	public static final int SHORTSENSOROFFSET = 3; // This is in cm.
	public static final int FARSENSORMINRANGE = 2; // This is in number of grid.
	public static final int FARSENSORMAXRANGE = 7; // This is in number of grid.
	public static final int FARSENSOROFFSET = 13; // This is in cm.
	
	public static final int[][] SENSORPLACEMENTNORTH = new int[][]{{-1, -1}, {0, -1}, {1, -1},
																   {1, 1}, {1, -1}, {-1, -1}};
																   
	public static final int[][] SENSORDIRECTION = new int [][]{{0, -1},
														       {1, 0},
														       {0, 1},
														       {-1, 0}};
	
	// For Timertask in UI Simulator
	public static final int DELAY = 15;
	
														       
	// For Generating Random Map for Simulator
	public static final int MAXOBSTACLECOUNT = 16;
	
	// For UI Simulator Display
	public static final int GRIDHEIGHT = 40;
	public static final int GRIDWIDTH = 40;
	public static final int MARGINLEFT = 100;
	public static final int MARGINTOP = 100;
	public static final String SIMULATORICONIMAGEPATH = ".\\images\\simulator_icon.ico";
	
	
	// Used in Map and possibly used in real run and simulator
	public static final String[] POSSIBLEGRIDLABELS = new String[]{"Unexplored", "Explored", "Obstacle", "Waypoint", "Startpoint", "Endpoint"};
	public static final String[] POSSIBLEROBOTDIRECTION = new String[]{"North", "East", "South", "West"};
	
	// Image path for UI Simulator
	public static final String UNEXPLOREDIMAGEPATH = ".\\images\\unexplored_grid.png";
	public static final String EXPLOREDIMAGEPATH = ".\\images\\explored_grid.png";
	public static final String OBSTACLEIMAGEPATH = ".\\images\\obstacle_grid.png";
	public static final String WAYPOINTIMAGEPATH = ".\\images\\waypoint_grid.png";
	public static final String STARTPOINTIMAGEPATH = ".\\images\\start_grid.png";
	public static final String ENDPOINTIMAGEPATH = ".\\images\\end_grid.png";
	public static final String ROBOTIMAGEPATH = ".\\images\\robot.png";
	public static final String ROBOTNIMAGEPATH = ".\\images\\robotN.png";
	public static final String ROBOTEIMAGEPATH = ".\\images\\robotE.png";
	public static final String ROBOTSIMAGEPATH = ".\\images\\robotS.png";
	public static final String ROBOTWIMAGEPATH = ".\\images\\robotW.png";
	
	
	public static final String[] GRIDIMAGEPATH = new String[]{UNEXPLOREDIMAGEPATH,
															  EXPLOREDIMAGEPATH,
															  OBSTACLEIMAGEPATH,
															  WAYPOINTIMAGEPATH,
															  STARTPOINTIMAGEPATH,
															  ENDPOINTIMAGEPATH};
	
	public static final String[] ROBOTIMAGEPATHS = new String[] {ROBOTNIMAGEPATH,
																 ROBOTEIMAGEPATH,
																 ROBOTSIMAGEPATH,
																 ROBOTWIMAGEPATH};
	

	
	// Avoid changing these values below
	public static final int ROBOTHEIGHT = GRIDHEIGHT * 2; // By default, this should be twice of the grid height.
	public static final int ROBOTWIDTH = GRIDWIDTH * 2; // By default, this should be twice of the grid width.
	public static final int HEIGHT = BOARDHEIGHT * GRIDHEIGHT + MARGINTOP;
	public static final int WIDTH = BOARDWIDTH * GRIDWIDTH + MARGINLEFT;
	
	// Values in PolicyIteration
	public static final double NORMALREWARD = -0.04;
	public static final double ENDREWARD = 10;
	public static final double WAYPOINTREWARD = 5;
	public static final double OBSTACLEREWARD = -3;
}

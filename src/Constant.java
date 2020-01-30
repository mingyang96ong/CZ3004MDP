
public class Constant {
	
	// Used for all Real and Simulator Events
	public static final int BOARDWIDTH = 20; // By default, this should be 20.
	public static final int BOARDHEIGHT = 15; // By default, this should be 15.
	
	// Used in Map and possibly used in real run and simulator
	public static final String[] POSSIBLEGRIDLABELS = new String[]{"Unexplored", "Explored", "Obstacle", "Waypoint"};
	
	
	// For UI Simulator Display
	public static final int GRIDHEIGHT = 40;
	public static final int GRIDWIDTH = 40;
	public static final int MARGINLEFT = 100;
	public static final int MARGINTOP = 100;
	public static final String UNEXPLOREDIMAGEPATH = ".\\images\\unexplored_grid.png";
	public static final String EXPLOREDIMAGEPATH = ".\\images\\explored_grid.png";
	public static final String OBSTACLEIMAGEPATH = ".\\images\\obstacle_grid.png";
	public static final String WAYPOINTIMAGEPATH = ".\\images\\waypoint_grid.png";
	
	// Avoid changing these values below
	public static final int ROBOTHEIGHT = GRIDHEIGHT * 2; // By default, this should be twice of the grid height.
	public static final int ROBOTWIDTH = GRIDWIDTH * 2; // By default, this should be twice of the grid width.
	public static final int HEIGHT = BOARDHEIGHT * GRIDHEIGHT + MARGINTOP;
	public static final int WIDTH = BOARDWIDTH * GRIDWIDTH + MARGINLEFT;
}

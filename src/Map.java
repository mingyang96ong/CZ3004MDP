import java.util.Random;

public class Map{
		
	// possibleGridLabels - 0 = unexplored, 1 = explored, 2 = obstacle, 3 = way point, 4 = start point and 5 = end point
	// Note that grid is by x, y coordinate and this is opposite of the Array position in Java
	
	private String[][] grid = new String[Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
	
	// Only used for simulation
	public static Random r = new Random();
	
	public Map() {
		resetMap();
	}
	
	public Map(String[][] grid) {
		initializeMap(grid);
	}
	
	public Map(int [][] grid) {
		for (int i = 0; i< Constant.BOARDWIDTH; i++) {
			for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
				if (grid[i][j] < Constant.POSSIBLEGRIDLABELS.length) {
					setGrid(i, j, Constant.POSSIBLEGRIDLABELS[grid[i][j]]);
				}
				else {
					setGrid(i, j, Constant.POSSIBLEGRIDLABELS[0]);
				}
			}
		}
	}
	
	public void print() {
		System.out.println("The current map is: ");
		for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
			for (int i = 0; i < Constant.BOARDWIDTH; i++) {
				if (i != Constant.BOARDWIDTH - 1) {
					System.out.print(grid[i][j] + ", " );
				}
				else {
					System.out.print(grid[i][j]);
				}
			}
			System.out.println();
		}
	}
	
	public void initializeMap(String[][] grid) {
		this.grid = grid;
	}
	
	
	public void resetMap() {
		
		/* According to the algorithm_briefing_19S1(1).pdf, 
		 * start point is always 3x3 grid at the top left corner
		 * and end point is always 3x3 grid diagonally opposite the start point*/
		
		for (int i = 0; i< Constant.BOARDWIDTH; i++) {
			for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
				// Set the start point grids
				if (i < Constant.STARTPOINTWIDTH && j < Constant.STARTPOINTHEIGHT) {
					setGrid(i, j, Constant.POSSIBLEGRIDLABELS[4]);
				}
				// Set the end point grids
				else if (i >= Constant.BOARDWIDTH - Constant.ENDPOINTWIDTH && j >= Constant.BOARDHEIGHT - Constant.ENDPOINTHEIGHT) {
					setGrid(i, j, Constant.POSSIBLEGRIDLABELS[5]);
				}
				// Set the remaining grids unexplored
				else {
					setGrid(i, j, Constant.POSSIBLEGRIDLABELS[0]);
				}
			}
		}
	}
	
	private void setGrid(int x, int y, String command) {
		for (int i = 0; i < Constant.POSSIBLEGRIDLABELS.length; i++) {
			if (command.toUpperCase().compareTo(Constant.POSSIBLEGRIDLABELS[i].toUpperCase()) == 0) {
				grid[x][y] = command;
				return;
			}
		}
		System.out.println("error when setting grid");
		
	}
	
	public void generateRandomMap() {
		int i = 0;
		while (i <= Constant.MAXOBSTACLECOUNT) {
			int x = r.nextInt(Constant.BOARDWIDTH);
			int y = r.nextInt(Constant.BOARDHEIGHT);
			if (getGrid(x, y).compareTo(Constant.POSSIBLEGRIDLABELS[0]) == 0) {
				
				if (i == Constant.MAXOBSTACLECOUNT) {
					// Randomly set one way point
					setGrid(x, y, Constant.POSSIBLEGRIDLABELS[3]);
				}
				else {
					// Randomly set the obstacle
					setGrid(x, y, Constant.POSSIBLEGRIDLABELS[2]);
				}
				i++;
			}
			
		}
		
		
	}
	
	public void setWayPoint(int x, int y) {
		setGrid(x, y, Constant.POSSIBLEGRIDLABELS[3]);
	}
	
	protected String[][] getGridMap() {
		return grid;
	}
	
	protected String getGrid(int x, int y) {
		return grid[x][y];
	}
}

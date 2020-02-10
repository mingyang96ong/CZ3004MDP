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
	
	public Map copy() {
		return new Map(this.grid);
	}
	
	public void print() {
		System.out.println("The current map is: \n");
		
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
		System.out.println("");
	}
	
	public void initializeMap(String[][] grid) {
		for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
			for (int i = 0; i < Constant.BOARDWIDTH; i++) {
				setGrid(i, j, grid[i][j]);
			}
		}
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
	
	public void setGrid(int x, int y, String command) {
		
		if (x < 0 || x >= Constant.BOARDWIDTH || y < 0 || y >= Constant.BOARDHEIGHT) {
			return;
		}
		
		for (int i = 0; i < Constant.POSSIBLEGRIDLABELS.length; i++) {
			if (command.toUpperCase().compareTo(Constant.POSSIBLEGRIDLABELS[i].toUpperCase()) == 0) {
				grid[x][y] = command;
				return;
			}
		}
		System.out.println("grid label error when setting grid");
		
	}
	
	public void generateRandomMap() {
		int k = 0;
		
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
				// Set the remaining grids explored
				else {
					setGrid(i, j, Constant.POSSIBLEGRIDLABELS[1]);
				}
			}
		}
		
		while (k <= Constant.MAXOBSTACLECOUNT) {
			int x = r.nextInt(Constant.BOARDWIDTH);
			int y = r.nextInt(Constant.BOARDHEIGHT);
			if (getGrid(x, y).compareTo(Constant.POSSIBLEGRIDLABELS[1]) == 0) {
//				if (i == Constant.MAXOBSTACLECOUNT) {
//					// Randomly set one way point
//					setGrid(x, y, Constant.POSSIBLEGRIDLABELS[3]);
//				}
//				else {
//					// Randomly set the obstacle
//					setGrid(x, y, Constant.POSSIBLEGRIDLABELS[2]);
//				}
				setGrid(x, y, Constant.POSSIBLEGRIDLABELS[2]);
				k++;
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
		
		// If the x, y is outside the board, it returns an obstacle.
		if (x < 0 || x >= Constant.BOARDWIDTH || y < 0 || y >= Constant.BOARDHEIGHT) {
			return Constant.POSSIBLEGRIDLABELS[2];
		}
		return grid[x][y];
	}
	
	// Only for simulator purposes
	public static boolean compare(Map a, Map b) {
		String [][]a_grid = a.getGridMap();
		String [][]b_grid = b.getGridMap();
		for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
			for (int i = 0; i < Constant.BOARDWIDTH; i++) {
				if (a_grid[i][j].compareTo(b_grid[i][j])!=0) {
					return false;
				}
			}
		}
		return true;
	}
}

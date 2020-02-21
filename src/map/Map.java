package map;
import java.util.ArrayList;
import java.util.Random;
import config.Constant;

public class Map{
		
	// possibleGridLabels - 0 = unexplored, 1 = explored, 2 = obstacle, 3 = way point, 4 = start point and 5 = end point
	// Note that grid is by x, y coordinate and this is opposite of the Array position in Java
	
	private String[][] grid = new String[Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
	private int[] waypoint = new int[] {-1, -1};
	
	
	// Only used for simulation
	public static Random r = new Random();
	
	public Map() {
		resetMap();
	}
	
	public Map(String[][] grid) {
		initializeMap(grid);
	}
	
	public Map(int [][] grid) {
		for (int i = 0; i < Constant.BOARDWIDTH; i++) {
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
				if (i == 3) {
					setWayPoint(x, y);
				}
				else {
					grid[x][y] = command;
				}
				
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
		if (x >= Constant.BOARDWIDTH - 1 || x <= 0 || y >= Constant.BOARDHEIGHT - 1 || y <= 0 
			|| (getGrid(x, y) != null && getGrid(x, y).compareTo(Constant.POSSIBLEGRIDLABELS[1]) != 0)) {
			return;
		}
		if (this.waypoint[0] == -1 && this.waypoint[1] == -1) {
			grid[x][y] =  Constant.POSSIBLEGRIDLABELS[3];
			this.waypoint[0] = x;
			this.waypoint[1] = y;
		}
		else {
			grid[this.waypoint[0]][this.waypoint[1]] =  Constant.POSSIBLEGRIDLABELS[1]; // This set to explored, assuming we set waypoint after exploration
			grid[x][y] =  Constant.POSSIBLEGRIDLABELS[3];
			this.waypoint[0] = x;
			this.waypoint[1] = y;
		}
		
	}
	
	public int[] getWayPoint() {
		return waypoint;
	}
	
	protected String[][] getGridMap() {
		return grid;
	}
	
	public String getGrid(int x, int y) {
		
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
	
	public String[] getMDFString() {
		String MDFBitStringPart1 = "11", MDFBitStringPart2 = "";
		String temp1 = "", temp2 = "";
		ArrayList <String> tempArr = new ArrayList<String> ();
		System.out.println("11");
		String[] MDFHexString = new String[2];
		for (int j = 0; j < Constant.BOARDWIDTH; j++) {
			for (int i = 0; i < Constant.BOARDHEIGHT; i++) {
//				if (grid[j][i].compareTo(Constant.POSSIBLEGRIDLABELS[0]) == 0) {
//					MDFBitStringPart1 += "0";
//				}
//				else {
//					MDFBitStringPart1 += "1";
//				}
				if (grid[j][i].compareTo(Constant.POSSIBLEGRIDLABELS[2])==0) { // Obstacle
					MDFBitStringPart1 += "1";
					MDFBitStringPart2 += "1";
					temp1 += "1";
					temp2 += "1";
					
				}
				else if (grid[j][i].compareTo(Constant.POSSIBLEGRIDLABELS[0]) == 0) { // Unexplored
					MDFBitStringPart1 += "0";
					temp1 += "0";
				}
				else {
					MDFBitStringPart1 += "1";
					MDFBitStringPart2 += "0";
					temp1 += "1";
					temp2 += "0";
				}
				
			}
			System.out.println(temp1);
			tempArr.add(temp2);
			temp1 = "";
			temp2 = "";
		}
		System.out.println("11\n");
		
		
		for (String s : tempArr) {
			System.out.println(s);
		}
		
		MDFBitStringPart1 += "11";
		
		
		System.out.println(MDFBitStringPart2);
		for (int i = 0; i < MDFBitStringPart1.length(); i += 4) {
			MDFHexString[0] += Integer.toString(Integer.parseInt(MDFBitStringPart1.substring(i, i + 4), 2), 16);
		}

		MDFBitStringPart2 += "0".repeat(MDFBitStringPart2.length() % 8);
		
		for (int i = 0; i < MDFBitStringPart2.length(); i += 4) {
			MDFHexString[1] += Integer.toString(Integer.parseInt(MDFBitStringPart2.substring(i, i + 4), 2), 16);
		}
		
		
		return MDFHexString;

	}
}

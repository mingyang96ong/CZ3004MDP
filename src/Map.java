
public class Map {
	
	// possibleGridLabels - 0 = unexplored, 1 = explored, 2 = obstacle and 3 = way point
	
	private String[][] grid = new String[Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
	
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
	
	public void initializeMap(String[][] grid) {
		this.grid = grid;
	}
	
	public void resetMap() {
		for (int i = 0; i< Constant.BOARDWIDTH; i++) {
			for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
				setGrid(i, j, Constant.POSSIBLEGRIDLABELS[0]);
			}
		}
	}
	
	public void setGrid(int x, int y, String command) {
		for (int i = 0; i < Constant.POSSIBLEGRIDLABELS.length; i++) {
			if (command.toUpperCase().compareTo(Constant.POSSIBLEGRIDLABELS[i].toUpperCase()) == 0) {
				grid[x][y] = command;
				return;
			}
		}
		System.out.println("error when setting grid");
		
	}
	
	protected String[][] getGridMap() {
		return grid;
	}
	
	protected String getGrid(int x, int y) {
		return grid[x][y];
	}
}

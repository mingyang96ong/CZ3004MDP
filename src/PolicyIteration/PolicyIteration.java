package PolicyIteration;

import java.util.concurrent.TimeUnit;

import config.Constant;
import map.Map;
import robot.Robot;

public class PolicyIteration {
	private Map map;
	private Robot r;
	private int[] robotPosition;
	private Grid [][] gridMap;
	private final static double discountFactor = 0.99;
	public PolicyIteration(Robot r) {
		this.r = r;
		map = r.getMap();
		this.gridMap = new Grid[Constant.BOARDWIDTH - 3 + 1][Constant.BOARDHEIGHT - 3 + 1];
	}
	
	public void run() {
		
		policyIteration();
		setRobotPosition();
		while (gridMap[robotPosition[0]][robotPosition[1]].getGridType().compareTo(Constant.POSSIBLEGRIDLABELS[5]) != 0) {
			String policy = gridMap[robotPosition[0]][robotPosition[1]].getPolicy();
			String robotDirection = r.getDirection();
			
			if (robotDirection.compareTo(policy) != 0){
				int rDirectionIndex = -1;
				int policyDirectionIndex = -1;
				for (int i = 0; i < Constant.POSSIBLEROBOTDIRECTION.length; i ++) {
					if (robotDirection.compareTo(Constant.POSSIBLEROBOTDIRECTION[i])==0) {
						rDirectionIndex = i;
					}
					if (policy.compareTo(Constant.POSSIBLEROBOTDIRECTION[i])==0) {
						policyDirectionIndex = i;
					}
				}
				
				if (policyDirectionIndex - rDirectionIndex == 1) {
					r.rotateRight();
				}
				else if (policyDirectionIndex - rDirectionIndex == -1) {
					r.rotateLeft();
				}
				else {
					r.rotateRight();
					r.rotateRight();
				}
			}
			r.forward();
			setRobotPosition();
			System.out.println("Moving");
			try {
				TimeUnit.SECONDS.sleep(1);
			}
			catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void setRobotPosition() {
		robotPosition = new int[] {r.getPosition()[0] - 1, r.getPosition()[1] - 1};
	}
	
	public void policyIteration() {
		boolean notCompleted = true;
		int iteration = 0;
		while (notCompleted) {
			++iteration;
			policyEvaluation();
			notCompleted = policyImprovement();
		}
		System.out.println("The program has completed " + iteration + " runs.\n\n");
	}
	
	public void policyEvaluation() {
		int [] nextPoint; 
		for (int j = 0; j < Constant.BOARDHEIGHT - 3 + 1; j++) {
			for (int i = 0; i < Constant.BOARDWIDTH - 3 + 1; i++) {
				Grid tmp = gridMap[i][j];
				String policy = tmp.getPolicy();
				if (policy.compareTo(Constant.POSSIBLEROBOTDIRECTION[0]) == 0) {
					nextPoint = validGrid(i, j, Constant.SENSORDIRECTION[0][0], Constant.SENSORDIRECTION[0][1]);
				}
				else if (policy.compareTo(Constant.POSSIBLEROBOTDIRECTION[1]) == 0) {
					nextPoint = validGrid(i, j, Constant.SENSORDIRECTION[1][0], Constant.SENSORDIRECTION[1][1]);
				}
				else if (policy.compareTo(Constant.POSSIBLEROBOTDIRECTION[2]) == 0) {
					nextPoint = validGrid(i, j, Constant.SENSORDIRECTION[2][0], Constant.SENSORDIRECTION[2][1]);
				}
				else {
					nextPoint = validGrid(i, j, Constant.SENSORDIRECTION[3][0], Constant.SENSORDIRECTION[3][1]);
				}
				tmp.setUtility(tmp.getReward() + discountFactor * gridMap[nextPoint[0]][nextPoint[1]].getUtility());
			}
		}
	}
	
	private int[] validGrid(int i, int j, int offsetx, int offsety) {
		int x = i + offsetx;
		int y = j + offsety;
		if (x < 0 || x >= Constant.BOARDWIDTH - 3 + 1 || y < 0 || y >= Constant.BOARDHEIGHT - 3 + 1) {
			return new int[] {i, j};
		}
		else {
			return new int[] {x, y};
		}
	}
	
	public boolean policyImprovement() {
		boolean changed = false;
		int[] nextPoint;
		for (int j = 0; j < Constant.BOARDHEIGHT - 3 + 1 && changed == false; j++) {
			double policyEU = 0;
			double EU = 0;
			for (int i = 0; i < Constant.BOARDWIDTH - 3 + 1 && changed == false; i++) {
				Grid tmp = gridMap[i][j];
				String policy = tmp.getPolicy();
				
				for (int d = 0; d < Constant.POSSIBLEROBOTDIRECTION.length; d++) {
					nextPoint = validGrid(i, j, Constant.SENSORDIRECTION[d][0], Constant.SENSORDIRECTION[d][1]);
					if (Constant.POSSIBLEROBOTDIRECTION[d].compareTo(policy) == 0) {
						policyEU = gridMap[nextPoint[0]][nextPoint[1]].getUtility();
					}
				}
				
				for (int d = 0; d < Constant.POSSIBLEROBOTDIRECTION.length; d++) {
					nextPoint = validGrid(i, j, Constant.SENSORDIRECTION[d][0], Constant.SENSORDIRECTION[d][1]);
					if (Constant.POSSIBLEROBOTDIRECTION[d].compareTo(policy) != 0) {
						EU = gridMap[nextPoint[0]][nextPoint[1]].getUtility();
						if (EU > policyEU) {
							changed = true;
							policyEU = EU;
							tmp.setPolicy(Constant.POSSIBLEROBOTDIRECTION[d]);
						}
					}
				}
			}
		}
		return changed;
	}
	
	
	public void convoluteMap() {
		/* General rule
		 * 1. if any of the 9 grid is obstacle, set as obstacle
		 * 2. if all 9 grid is start, set as start
		 * 3. if all 9 grid is end, set as end
		 * 4. if the center grid is way point, set as way point
		 * 5. else set as empty
		 * */
		
		//String topLeft, topMid, topRight, midLeft, midMid, midRight, botLeft, botMid, botRight;
		boolean check[] = new boolean[] {false, true, true}; // found/set?, start?, end?
		for (int i = 0; i  < Constant.BOARDHEIGHT - 3 + 1; i++) {
			for (int j = 0; j < Constant.BOARDWIDTH - 3 + 1; j++) {
				check = new boolean[] {false, true, true};
				// Note that in actual height and width is reversed
				// {"Unexplored", "Explored", "Obstacle", "Waypoint", "Startpoint", "Endpoint"};
				check = convoluteGrid(j, i, 0, 0, check, false);
				if (check[0]) {
					continue;
				}
				check = convoluteGrid(j, i, 0, 1, check, false);
				if (check[0]) {
					continue;
				}
				check = convoluteGrid(j, i, 0, 2, check, false);
				if (check[0]) {
					continue;
				}
				check = convoluteGrid(j, i, 1, 0, check, false);
				if (check[0]) {
					continue;
				}
				check = convoluteGrid(j, i, 1, 1, check, true);
				if (check[0]) {
					continue;
				}
				check = convoluteGrid(j, i, 1, 2, check, false);
				if (check[0]) {
					continue;
				}
				check = convoluteGrid(j, i, 2, 0, check, false);
				if (check[0]) {
					continue;
				}
				check = convoluteGrid(j, i, 2, 1, check, false);
				if (check[0]) {
					continue;
				}
				check = convoluteGrid(j, i, 2, 2, check, false);
				if (check[0]) {
					continue;
				}
				else if (check[1]) {
					gridMap[j][i] = new StartGrid();
					continue;
				}
				else if (check[2]) {
					gridMap[j][i] = new EndGrid();
					continue;
				}
				else {
					gridMap[j][i] = new EmptyGrid();
				}
//				topLeft = map.getGrid(j, i);
//				topMid = map.getGrid(j, i + 1);
//				topRight = map.getGrid(j, i + 2);
//				midLeft = map.getGrid(j + 1, i);
//				midMid = map.getGrid(j + 1, i + 1);
//				midRight = map.getGrid(j + 1, i + 2);
//				botLeft = map.getGrid(j + 1, i);
//				botMid = map.getGrid(j + 1, i + 1);
//				botRight = map.getGrid(j + 1, i + 2);
				
				
			}
		}
		
	}
	
	private boolean[] convoluteGrid(int x, int y, int offsetx, int offsety, boolean[] check, boolean wp) {
		String gridType = map.getGrid(x + offsetx, y + offsety);
		if (gridType.compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
			gridMap[x][y] = new ObstacleGrid();
			check[0] = true;
		}
		else if (check[1] && gridType.compareTo(Constant.POSSIBLEGRIDLABELS[4]) == 0) {
			check[2] = false;
		}
		else if (check[2] && gridType.compareTo(Constant.POSSIBLEGRIDLABELS[5]) == 0) {
			check[1] = false;
		}
		else {
			check[1] = false;
			check[2] = false;
		}
		
		if (wp && gridType.compareTo(Constant.POSSIBLEGRIDLABELS[3]) == 0) {
			gridMap[x][y] = new WayPointGrid();
			check[0] = true;
		}
		return check;
	}
	
	
	public void printGrids() {
		System.out.println("The current grid is: \n");
		for (int j = 0; j < Constant.BOARDHEIGHT - 3 + 1; j++) {
			System.out.print("|");
			for (int i = 0; i < Constant.BOARDWIDTH - 3 + 1; i++) {
				if (i != Constant.BOARDWIDTH - 1) {
					gridMap[i][j].print();
				}
			}
			System.out.println();
		}
		System.out.println("");
	}
	
	public void printUtilities() {
		System.out.println("The current utilites is: \n");
		for (int j = 0; j < Constant.BOARDHEIGHT - 3 + 1; j++) {
			System.out.print("|");
			for (int i = 0; i < Constant.BOARDWIDTH - 3 + 1; i++) {
				if (i != Constant.BOARDWIDTH - 1) {
					gridMap[i][j].printUtility();
				}
			}
			System.out.println();
		}
		System.out.println("");
	}
	
	public void printPolicies() {
		System.out.println("The current policies is: \n");
		for (int j = 0; j < Constant.BOARDHEIGHT - 3 + 1; j++) {
			System.out.print("|");
			for (int i = 0; i < Constant.BOARDWIDTH - 3 + 1; i++) {
				if (i != Constant.BOARDWIDTH - 1) {
					gridMap[i][j].printPolicy();
				}
			}
			System.out.println();
		}
		System.out.println("");
	}
}

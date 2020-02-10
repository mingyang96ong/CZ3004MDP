public class Exploration {
    private int[][] AMap = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    private int[][] EMap = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    public static final int OBSTACLE = 1;
    public static final int EMPTY = 0;
    public static final int EXPLORED = 1;
    public static final int UNEXPLORED = 0;
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int[] START = {2,2};

    public void Exploration(SimulatorRobot robot){

        int[] pos = get_pos(robot);
        int facing = get_facing(robot);

        while (!at_start(pos)) {
            if (check_right_empty(pos, facing)) {
                // update map
                // update pos & facing
                // turn right and move (to the right)
            }
            else if (check_front_empty(pos, facing)) {
                // update map
                // update pos & facing
                // move (forward)
            }
            else {
                facing = (facing-2)%4;
                if (check_right_empty(pos, facing)) {
                    // update map
                    // update pos & facing
                    // turn left and move (to the left)
                }
                else {
                    // update map
                    // update pos & facing
                    // turn 180 degrees and move (backwards)
                }
                // generate MDF String to update android
            }

            while (!is_exploration_complete(EMap)) {
                // fastest path to nearest unexplored square
                // generate MDF String to update android
            }

            if (!at_start(pos)) {
                // fastest path to start point
            }

            // signal completion
            // generate MDF String to confirm final map
        }
    };

    private int[] get_pos(SimulatorRobot robot) {
        int[] pos = robot.getPosition();
        pos[0]++;
        pos[1]++;
        return pos;
    }

    private int get_facing(SimulatorRobot robot) {
        String direction = robot.getDirection();
        if (direction.equals("North")) {
            return NORTH;
        }
        else if (direction.equals("East")) {
            return EAST;
        }
        else if (direction.equals("South")) {
            return SOUTH;
        }
        else {
            return WEST;
        }
    }

    public boolean check_right_empty(int[] pos, int facing){
        int x = pos[0];
        int y = pos[1];
        int i;

        if ((facing == NORTH) || (facing == SOUTH)) {
            if (facing == NORTH) {
                x = x + 2;
            } else {
                x = x - 2;
            }
            for (i = (y - 1); i <= (y + 1); i++) {
                if (AMap[x][i] == 1) {
                    return false;
                }
            }
        }
        else if ((facing == EAST) || (facing == WEST)) {
            if (facing == EAST) {
                y = y - 2;
            } else {
                y = y + 2;
            }
            for (i = (x - 1); i <= (x + 1); i++) {
                if (AMap[i][y] == 1) {
                    return false;
                }
            }
        }
        return true;
    };

    public boolean check_front_empty(int[] pos, int facing){
        facing = (facing - 1) % 4;
        return check_right_empty(pos, facing);
    };

    public boolean at_start(int[] pos){
        if (pos == START)
            return true;
        return false;
    };

//    public boolean is_grid_empty(int x, int y){
//        if (AMap[x][y]){
//            return false;
//        } else {
//            return true;
//        }
//    }

    public boolean is_exploration_complete(int[][] map) {
        for (int i=0; i<17; i++) {
            for (int j=0; j<22;j++) {
                if (map[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    };
}


package astarpathfinder;

import robot.SimulatorRobot;
import map.Map;
import config.Constant;

public class AStarPathFinder {

    public void AStarPathFinder(SimulatorRobot robot, int[] start_pos, int[] end_pos) {
        Node start = new Node(start_pos);
        Node cur;

        Node[] open = {start};
        Node[] closed = {};

        while (true) {
            cur = lowest_cost(open);
            open = remove_node(open, cur);
            closed = add_node(closed, cur);

            if (cur.pos == end_pos) {
                break;
            }

            open = add_neighbours(robot, open, closed, cur, end_pos);
        }

        int[] path = get_path(robot, cur);

        for (int direction : path) {
            if (direction == Constant.FORWARD) {
                robot.forward();
            } else if (direction == Constant.RIGHT) {
                robot.rotateRight();
                robot.forward();
            } else if (direction == Constant.LEFT) {
                robot.rotateLeft();
                robot.forward();
            } else {
                robot.rotateRight();
                robot.rotateRight();
                robot.forward();
            }
        }
    }

    private Node lowest_cost(Node[] list) {
        int cost;
        int l_cost=9999999;
        Node l_node = null;

        for (int i=0; i<list.length; i++) {
            cost = list[i].cost;
            if (cost<l_cost) {
                l_cost = cost;
                l_node = list[i];
            }
        }

        return l_node;
    }

    private Node[] remove_node(Node[] list, Node node) {
        int index=-1;
        Node[] new_list = new Node[list.length-1];

        for (int i=0; i<list.length; i++) {
            if (list[i] == node) {
                index = i;
                break;
            }
        }

        if (index>=0) {
            System.arraycopy(list, 0, new_list, 0, index+1);
            for (int j = index; j < (list.length - 1); j++) {
                new_list[j] = list[j + 1];
            }
        }

        return new_list;
    }

    private Node[] add_node(Node[] list, Node node) {
        Node[] new_list = new Node[list.length+1];

        System.arraycopy(list, 0, new_list, 0, list.length);
        new_list[list.length] = node;

        return new_list;
    }

    private Node[] add_neighbours(SimulatorRobot robot, Node[] open, Node[] closed, Node cur, int[] end_pos) {
        Node[] neighbours = new Node[8];
        int count = 0;
        int x = cur.pos[0];
        int y = cur.pos[1];
        int[][] neighbours_pos = {{x,y+1}, {x-1,y}, {x+1,y}, {x,y-1}};

        for (int i=0; i<4; i++) {
            if (is_valid(robot, neighbours_pos[i])) {
                // add neighbours (must be a valid grid to move to)
                Node neighbour = new Node(neighbours_pos[i]);
                neighbour.parent = cur;
                neighbour.cost = find_cost(neighbour, end_pos, robot);
                neighbours[i] = neighbour;
                count++;
            }
        }

        for (int j=0; j<count; j++) {
            Node node = neighbours[j];
            if (find_index(node,closed)>-1) {
                int index = find_index(node, open);
                if ((index > -1) && (node.cost < open[index].cost)) {
                    open[index] = node;
                }
                if (index == -1) { // if not in open
                    open = add_node(open, node);
                }
            }
        }

        return open;
    }

    private boolean is_valid(SimulatorRobot robot, int[] pos){
        Map map = robot.getMap();

        if ((0<pos[0])&&(pos[0]<19)&&(0<pos[1])&&(pos[1]<14) // within the boundaries
                &&(!map.getGrid(pos[0],pos[1]).equals("Obstacle"))) { // not an obstacle
            return true;
        }
        else {
            return false;
        }
    }

    private int find_cost (Node node, int[] end_pos, SimulatorRobot robot) {
        return find_h_cost(node, end_pos) + find_g_cost(node, robot);
    }

    private int find_h_cost(Node cur, int[] end) {
        int x = Math.abs(cur.pos[0] - end[0]);
        int y = Math.abs(cur.pos[1] - end [1]);

        if ((cur.parent.pos[0] == cur.pos[0])&&(x==0)) {
            return y;
        }
        else if ((cur.parent.pos[1] == cur.pos[1])&&(y==0)) {
            return x;
        }
        else {
            return (x + y + 2);
        }
    }

    private int find_g_cost(Node cur, SimulatorRobot robot) {
        Node prev = cur.parent;
        int direction = go_where(robot, cur);

        if (prev == null) {
            // cur node is the start
            return 0;
        }
        else {
            if (direction == Constant.FORWARD) {
                return 1;
            } else if ((direction == Constant.LEFT) || (direction == Constant.RIGHT)) {
                return 3;
            } else {
                return 5;
            }
        }
    }

    private int go_where(SimulatorRobot robot, Node cur) {
        Node second = cur.parent;
        if (second == null) {
            return -1;
        }
        Node first = second.parent;
        if (first == null) {
            int direction = robot.getDirection();

            if (second.pos[0] == cur.pos[0]) {
                if (second.pos[1] > cur.pos[1]) {
                    if (direction == Constant.NORTH) {
                        return Constant.BACKWARD;
                    }
                    else if (direction == Constant.EAST) {
                        return Constant.RIGHT;
                    }
                    else if (direction == Constant.SOUTH) {
                        return Constant.FORWARD;
                    }
                    else if (direction == Constant.WEST) {
                        return Constant.LEFT;
                    }
                }
                else if (second.pos[1] < cur.pos[1]) {
                    if (direction == Constant.NORTH) {
                        return Constant.FORWARD;
                    }
                    else if (direction == Constant.EAST) {
                        return Constant.LEFT;
                    }
                    else if (direction == Constant.SOUTH) {
                        return Constant.BACKWARD;
                    }
                    else if (direction == Constant.WEST) {
                        return Constant.RIGHT;
                    }
                }
            }
            else if (second.pos[1] == cur.pos[1]) {
                if (second.pos[0] > cur.pos[0]) {
                    if (direction == Constant.NORTH) {
                        return Constant.LEFT;
                    }
                    else if (direction == Constant.EAST) {
                        return Constant.BACKWARD;
                    }
                    else if (direction == Constant.SOUTH) {
                        return Constant.RIGHT;
                    }
                    else if (direction == Constant.WEST) {
                        return Constant.FORWARD;
                    }
                } else if (second.pos[0] < cur.pos[0]) {
                    if (direction == Constant.NORTH) {
                        return Constant.RIGHT;
                    }
                    else if (direction == Constant.EAST) {
                        return Constant.FORWARD;
                    }
                    else if (direction == Constant.SOUTH) {
                        return Constant.LEFT;
                    }
                    else if (direction == Constant.WEST) {
                        return Constant.BACKWARD;
                    }
                }
            }
        }
        else {
            if ((first.pos[0] == second.pos[0])&&(second.pos[0]==cur.pos[0])) {
                if (((first.pos[1] > second.pos[1])&&(second.pos[1] > cur.pos[1])) ||
                        ((first.pos[1] < second.pos[1])&&(second.pos[1] < cur.pos[1]))){
                    return Constant.FORWARD;
                }
                else {
                    return Constant.BACKWARD;
                }
            }
            else if ((first.pos[1] == second.pos[1])&&(second.pos[1]==cur.pos[1])) {
                if (((first.pos[0] > second.pos[0])&&(second.pos[0] > cur.pos[0])) ||
                        ((first.pos[0] < second.pos[0])&&(second.pos[0] < cur.pos[0]))){
                    return Constant.FORWARD;
                }
                else {
                    return Constant.BACKWARD;
                }
            }
            else if (first.pos[0] == second.pos[0]) {
                if (second.pos[0] < cur.pos[0]) {
                    return Constant.RIGHT;
                }
                else {
                    return Constant.LEFT;
                }
            }
            else {
                if (second.pos[1] < cur.pos[1]) {
                    return Constant.LEFT;
                }
                else {
                    return Constant.RIGHT;
                }
            }
        }
        return -2; // error
    }

    private int find_index(Node node, Node[] list) {
        for (int i=0; i<list.length; i++) {
            if (list[i].pos == node.pos) {
                return i;
            }
        }
        return -1;
    }

    private int[] get_path(SimulatorRobot robot, Node node) {
        int[] path = {};
        int[] temp_path = new int[path.length+1];
        Node cur = node;

        while (cur != null) {
            System.arraycopy(path, 0, temp_path, 1, path.length);
            temp_path[0] = go_where(robot, cur);
            cur = cur.parent;
        }

        return path;
    }
}

package astarpathfinder;

public class Node {
    int[] pos;
    Node parent=null;
    int cost=99999999;

    public Node(int[] pos) {
        this.pos = pos;
    }

    public Node get_parent(Node node) {
        return node.parent;
    }

    public void set_parent(Node node, Node parent) {
        node.parent = parent;
    }

    public int get_cost(Node node) {
        return node.cost;
    }

    private void set_cost(Node node, int cost) {
        node.cost = cost;
    }
}

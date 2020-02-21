package astarpathfinder;

import map.Map;
import robot.SimulatorRobot;

public class FastestPathThread extends Thread {
    private SimulatorRobot r;
    public FastestPathThread(SimulatorRobot r, Map loadedMap) {
        super("FastestPathThread");
        this.r = r;
        start();
    }

    public void run() {
        FastestPath fp = new FastestPath();
        fp.FastestPath(r);
    }
}

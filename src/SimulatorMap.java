import javax.swing.JFrame;

public class SimulatorMap{
	private ImageComponent[][] gridcells = new ImageComponent[Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
	private JFrame frame;
	private Map map;
	public SimulatorMap(JFrame frame, Map map) {
		this.frame = frame;
		this.map = map;
		initializeMap(frame);
	}
	
	public void initializeMap(JFrame frame) {
		for (int i = 0; i < Constant.BOARDWIDTH; i++) {
			for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
				String gridvalue = map.getGrid(i, j);
				if (gridvalue.compareTo(Constant.POSSIBLEGRIDLABELS[0]) == 0) {
					createUIGrid(i, j, Constant.UNEXPLOREDIMAGEPATH);
				}
				if (gridvalue.compareTo(Constant.POSSIBLEGRIDLABELS[1]) == 0) {
					createUIGrid(i, j, Constant.EXPLOREDIMAGEPATH);
				}
				
				if (gridvalue.compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
					createUIGrid(i, j, Constant.OBSTACLEIMAGEPATH);
				}
				
				if (gridvalue.compareTo(Constant.POSSIBLEGRIDLABELS[3]) == 0) {
					createUIGrid(i, j, Constant.WAYPOINTIMAGEPATH);
				}
			}
		}
	}
	
	public void createUIGrid(int i, int j, String path) {
		gridcells[i][j] = new GridImageComponent(path, Constant.GRIDWIDTH, Constant.GRIDHEIGHT);
		gridcells[i][j].setLocation(Constant.MARGINLEFT + i * Constant.GRIDWIDTH, Constant.MARGINTOP + j * Constant.GRIDHEIGHT);
		frame.add(gridcells[i][j]);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}

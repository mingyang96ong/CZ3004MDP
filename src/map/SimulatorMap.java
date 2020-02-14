package map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import config.Constant;
import imagecomponent.*;

import java.util.HashMap;

public class SimulatorMap{
	private ImageComponent[][] gridCells = new ImageComponent[Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
	private JLabel[] axis = new JLabel[Constant.BOARDWIDTH + Constant.BOARDHEIGHT];
	private JFrame frame;
	private Map map;
	private static SimulatorMap simulatorMap = null;
	private HashMap<String, String> gridToImagePath = new HashMap<String, String>();
	private SimulatorMap(JFrame frame, Map map) {
		this.frame = frame;
		this.map = map;
		for (int i = 0; i < Constant.POSSIBLEGRIDLABELS.length; i++) {
			gridToImagePath.put(Constant.POSSIBLEGRIDLABELS[i], Constant.GRIDIMAGEPATH[i]);
		}
		initializeMapOnUI(frame);
		for (int i = 0; i < axis.length; i++) {
			if (i < Constant.BOARDWIDTH) {
				axis[i] = new JLabel("" + i);
				axis[i].setBounds(Constant.MARGINLEFT + 15 + i * Constant.GRIDWIDTH, Constant.MARGINTOP - 30, 20, 20);
				axis[i].setLocation(Constant.MARGINLEFT + 15 + i * Constant.GRIDWIDTH, Constant.MARGINTOP - 30);
			}
			else {
				axis[i] = new JLabel("" + (i-Constant.BOARDWIDTH));
				axis[i].setBounds(Constant.MARGINLEFT - 25, Constant.MARGINTOP  + (i-Constant.BOARDWIDTH) * Constant.GRIDHEIGHT, 20, 20);
				axis[i].setLocation(Constant.MARGINLEFT - 25, Constant.MARGINTOP  + (i-Constant.BOARDWIDTH) * Constant.GRIDHEIGHT);
			}
			frame.add(axis[i]);
			axis[i].setVisible(true);
		}
			
	}
	
	
	public static SimulatorMap getInstance(JFrame frame, Map map) {
		if (simulatorMap == null) {
			simulatorMap = new SimulatorMap(frame, map);
		}
		return simulatorMap;
	}
	
	public void initializeMapOnUI(JFrame frame) {
		for (int i = 0; i < Constant.BOARDWIDTH; i++) {
			for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
				String gridvalue = map.getGrid(i, j);
				if (gridToImagePath.containsKey(gridvalue)) {
					createUIGrid(i, j, gridToImagePath.get(gridvalue));
				}
//				if (gridvalue.compareTo(Constant.POSSIBLEGRIDLABELS[0]) == 0) {
//					createUIGrid(i, j, Constant.UNEXPLOREDIMAGEPATH);
//				}
//				if (gridvalue.compareTo(Constant.POSSIBLEGRIDLABELS[1]) == 0) {
//					createUIGrid(i, j, Constant.EXPLOREDIMAGEPATH);
//				}
//				
//				if (gridvalue.compareTo(Constant.POSSIBLEGRIDLABELS[2]) == 0) {
//					createUIGrid(i, j, Constant.OBSTACLEIMAGEPATH);
//				}
//				
//				if (gridvalue.compareTo(Constant.POSSIBLEGRIDLABELS[3]) == 0) {
//					createUIGrid(i, j, Constant.WAYPOINTIMAGEPATH);
//				}
			}
		}
	}
	
	public void setMap(Map map) {
		updateMapOnUI(this.map, map);
		this.map = map.copy();
	}
	
	private void createUIGrid(int i, int j, String path) {
		gridCells[i][j] = new GridImageComponent(path, Constant.GRIDWIDTH, Constant.GRIDHEIGHT);
		gridCells[i][j].setLocation(Constant.MARGINLEFT + i * Constant.GRIDWIDTH, Constant.MARGINTOP + j * Constant.GRIDHEIGHT);
		frame.add(gridCells[i][j]);
	}
	
	private void updateMapOnUI(Map oldMap, Map newMap) {
		String[][] oldGridValue = oldMap.getGridMap();
		String[][] newGridValue = newMap.getGridMap();
		for (int i = 0; i < Constant.BOARDWIDTH; i++) {
			for (int j = 0; j < Constant.BOARDHEIGHT; j++) {
				if (oldGridValue[i][j].compareTo(newGridValue[i][j]) != 0 && gridToImagePath.containsKey(newGridValue[i][j])) {
					gridCells[i][j].setImage(gridToImagePath.get(newGridValue[i][j]));
				}
			}
		}
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Map getMap() {
		return map;
	}
}

package main;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import config.Constant;
import map.Map;
//import policyiteration.PolicyIteration;
import robot.SimulatorRobot;

public class Simulator {
	public final static String TITLE = "Group 15 Simulator";
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static void main(String[] args) {
		JFrame frame = new JFrame(TITLE);
		frame.setIconImage(new ImageIcon(Constant.SIMULATORICONIMAGEPATH).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		boolean simulation = true; // May be used in the future
		
		/* Set the starting position by the center block 
		 * The value can only range from 1, 1 to 18, 13
		 */
		// NORTH, EAST, SOUTH, WEST
		SimulatorRobot sr = new SimulatorRobot(frame, 0, 0, 0); 
		
//		This is to create a fake map for the exploration run.
//		int x = 1;
//		int y = 1;
//		RobotImageComponent robotImage = new RobotImageComponent(Constant.ROBOTIMAGEPATH, Constant.ROBOTWIDTH, Constant.ROBOTHEIGHT);
//		frame.add(robotImage);
//		robotImage.setLocation(Constant.MARGINLEFT + Constant.GRIDWIDTH/2 + (x-1) * Constant.GRIDWIDTH, Constant.MARGINTOP + Constant.GRIDHEIGHT/2 + (y-1) * Constant.GRIDHEIGHT);
//		
//		Map map = new Map();
//		map.generateRandomMap();
//		map.print();
//		
//		SimulatorMap smap = SimulatorMap.getInstance(frame, map);
		
		
		frame.setVisible(true);
		
//		String[] test = getArenaMapFile();
////		System.out.println("\nPrinting test:");
////		for (int i = 0; i < test.length; i ++) {
////			System.out.println(test[i]);
////		}
//		
//		File folder = new File("./sample arena");
//		String[][] grid = new String[Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
//		HashMap  <String, String> filePath= new HashMap<String, String>();
//		boolean found = false;
//		for (File file: folder.listFiles()) {
//			if (!found && file.getName().endsWith(".txt")) {
//				filePath.put(file.getName().substring(0, file.getName().lastIndexOf(".txt")), file.getAbsolutePath());
//				try {
//					FileReader fr = new FileReader(file.getAbsolutePath());
//					BufferedReader br = new BufferedReader(fr);
//					String line = br.readLine();
//					int heightCount = 0;
//					while (line != null) {
//						line = line.strip().toUpperCase();
////						System.out.println(line);
//						if (line.length() != Constant.BOARDWIDTH) {
//							throw new Exception("The format of the " + file.getName() + " does not match the board format.");
//						}
//						for (int i = 0; i < line.length(); i++) {
//							switch(line.charAt(i)) {
//								case 'S':
//									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[4];
//									break;
//								case 'U':
//									// Here, we set to explored instead of Unexplored
//									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[1];
//									break;
//								case 'W':
//									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[3];
//									break;
//								case 'E':
//									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[5];
//									break;
//								case 'O':
//									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[2];
//									break;
//								default:
//									throw new Exception("There is unrecognised character symbol in " + file.getName() + ".");
//							}
//						}
////						System.out.println(line);
//						heightCount++;
//						line = br.readLine();
//						
//					}
//					if (heightCount != Constant.BOARDHEIGHT) {
//						throw new Exception("The format of the " + file.getName() + " does not match the board format.");
//					}
//					found = true;
////					Map map = new Map(grid);
////					map.print();
//				}
//				catch (FileNotFoundException f){
//					System.out.println("File not found");
//				}
//				catch (IOException e) {
//					System.out.println("IOException when reading" + file.getName());
//				}
//				catch (Exception e) {
//					System.out.println(e.getMessage());
//				}
//				finally {
//					
//				}
//			}
//		}
//		Map map = new Map(grid);
//		map.print();
//		sr.setMap(map);
//		sr.toggleMap();
//		sr.toggleMap();
//		
//		PolicyIteration pi = new PolicyIteration(sr);
//		pi.convoluteMap();
//		pi.printGrids();
//		pi.run();
//		pi.printUtilities();
//		pi.printPolicies();
	}
	
	public static String[] getArenaMapFile() {
		File folder = new File("./sample arena");
		HashMap  <String, String> filePath= new HashMap<String, String>();
		for (File file: folder.listFiles()) {
			if (file.getName().endsWith(".txt")) {
//				System.out.println(file.getName());
				filePath.put(file.getName().substring(0, file.getName().lastIndexOf(".txt")), file.getAbsolutePath());
			}
		}
		String[] fileName = new String[filePath.size()];
		int i = filePath.size()-1;
		for (String key: filePath.keySet()) {
//			System.out.println(key);
			fileName[i] = key;
			i--;
		}
		return fileName;
	}
}


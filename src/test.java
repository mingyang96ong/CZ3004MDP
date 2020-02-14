import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashMap;

import config.Constant;
import map.Map;
import robot.Robot;
public class test {
	public static void main(String args[]) {
		String[] test = getArenaMapFile();
		System.out.println("\nPrinting test:");
		for (int i = 0; i < test.length; i ++) {
			System.out.println(test[i]);
		}
		
		File folder = new File("./sample arena");
		String[][] grid = new String[Constant.BOARDWIDTH][Constant.BOARDHEIGHT];
		HashMap  <String, String> filePath= new HashMap<String, String>();
		for (File file: folder.listFiles()) {
			if (file.getName().endsWith(".txt")) {
				System.out.println(file.getName());
				filePath.put(file.getName().substring(0, file.getName().lastIndexOf(".txt")), file.getAbsolutePath());
				try {
					FileReader fr = new FileReader(file.getAbsolutePath());
					BufferedReader br = new BufferedReader(fr);
					String line = br.readLine();
					int heightCount = 0;
					while (line != null) {
						line = line.strip().toUpperCase();
						System.out.println(line);
						if (line.length() != Constant.BOARDWIDTH) {
							throw new Exception("The format of the " + file.getName() + " does not match the board format.");
						}
						for (int i = 0; i < line.length(); i++) {
							switch(line.charAt(i)) {
								case 'S':
									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[4];
									break;
								case 'U':
									// Here, we set to explored instead of Unexplored
									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[1];
									break;
								case 'W':
									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[3];
									break;
								case 'E':
									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[5];
									break;
								case 'O':
									grid[i][heightCount] = Constant.POSSIBLEGRIDLABELS[2];
									break;
								default:
									throw new Exception("There is unrecognised character symbol in " + file.getName() + ".");
							}
						}
//						System.out.println(line);
						heightCount++;
						line = br.readLine();
					}
					if (heightCount != Constant.BOARDHEIGHT) {
						throw new Exception("The format of the " + file.getName() + " does not match the board format.");
					}
					Map map = new Map(grid);
					map.print();
				}
				catch (FileNotFoundException f){
					System.out.println("File not found");
				}
				catch (IOException e) {
					System.out.println("IOException when reading" + file.getName());
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
				finally {
					
				}
			}
		}
		
	}
	public static String[] getArenaMapFile() {
		File folder = new File("./sample arena");
		HashMap  <String, String> filePath= new HashMap<String, String>();
		for (File file: folder.listFiles()) {
			if (file.getName().endsWith(".txt")) {
				System.out.println(file.getName());
				filePath.put(file.getName().substring(0, file.getName().lastIndexOf(".txt")), file.getAbsolutePath());
			}
		}
		String[] fileName = new String[filePath.size()];
		int i = filePath.size()-1;
		for (String key: filePath.keySet()) {
			System.out.println(key);
			fileName[i] = key;
			i--;
		}
		return fileName;
	}
	
}

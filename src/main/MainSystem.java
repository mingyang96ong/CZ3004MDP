package main;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import config.Constant;
import connection.ConnectionManager;
import connection.ConnectionSocket;
import robot.RealRobot;
import simulator.Simulator;

public class MainSystem {
	private static boolean realRun = false;
	public static void main(String[] args) {
		ImageIcon icon = new ImageIcon(new ImageIcon(".\\images\\letter-r.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT));
		int result = JOptionPane.CLOSED_OPTION;
		while (result == JOptionPane.CLOSED_OPTION) {
			result = JOptionPane.showConfirmDialog(null, "Is this the real run?", "Real Run", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
			if (result == JOptionPane.YES_OPTION) {
				realRun = true;
			}
			if (result == JOptionPane.NO_OPTION) {
				realRun = false;
			}
		}
		
		if (realRun) {
			ConnectionManager connectionManager = ConnectionManager.getInstance();
			boolean connected = false;
			while (!connected) {
				connected = connectionManager.connectToRPi();
			}
			connectionManager.start();
		}
		else {
			Simulator s = new Simulator();
		}
	}
}

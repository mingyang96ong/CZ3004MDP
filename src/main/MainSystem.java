package main;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import config.Constant;
import connection.ConnectionManager;
import connection.ConnectionSocket;
import simulator.Simulator;

public class MainSystem {
	private static boolean realRun = false;
	public static void main(String[] args) {
		ImageIcon icon = new ImageIcon(new ImageIcon(".\\images\\letter-r.png").getImage().getScaledInstance(Constant.GRIDWIDTH, Constant.GRIDHEIGHT, Image.SCALE_DEFAULT));
		int result = JOptionPane.CLOSED_OPTION;
		int debug = JOptionPane.CLOSED_OPTION;
		while (result == JOptionPane.CLOSED_OPTION) {
			result = JOptionPane.showConfirmDialog(null, "Is this the real run?", "Real Run", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
			if (result == JOptionPane.YES_OPTION) {
				realRun = true;
				debug = JOptionPane.showConfirmDialog(null, "Print debug?", "Debug", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
				while (debug == JOptionPane.CLOSED_OPTION) {
					debug = JOptionPane.showConfirmDialog(null, "Print debug?", "Debug", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
				}
			}
			if (result == JOptionPane.NO_OPTION) {
				realRun = false;
			}
		}
		
		if (realRun) {
			ConnectionManager connectionManager = ConnectionManager.getInstance();
			if (debug == JOptionPane.YES_OPTION) {
				ConnectionSocket.setDebugTrue();
				System.out.println("Debug is " + ConnectionSocket.getDebug());
			}
//			JFrame frame = new JFrame("Real Run");
//			frame.setIconImage(new ImageIcon(Constant.SIMULATORICONIMAGEPATH).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
//			frame.setLayout(null);
//			frame.setSize(200, 200);
//			
//			JButton stop = new JButton();
//			stop.setActionCommand("Stop");
//			stop.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					String action = e.getActionCommand();
//					// TODO Auto-generated method stub
//					if (action.equals("Stop")) {
//						try {
//							connectionManager.disconnectFromRPI();
//							frame.dispose();
//						}
//						catch (Exception f) {
//							
//						}
//					}
//				}
//			});
//			frame.add(stop);
//			stop.setText("Stop");
//			stop.setBounds(50, 50, 100, 50);
//			stop.setVisible(true);
//			frame.setVisible(true);
//
//			Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
//			    	connectionManager.disconnectFromRPI();
//			        System.out.println("The client is shut down!");
//			}});
			boolean connected = false;
			while (!connected) {
				connected = connectionManager.connectToRPi();
			}
			try {
				connectionManager.start();
			}
			catch (Exception e) {
				connectionManager.stopCM();
				System.out.println("ConnectionManager is stopped");
			}
			
		}
		else {
			Simulator s = new Simulator();
		}
	}
}

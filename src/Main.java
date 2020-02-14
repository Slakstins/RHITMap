import java.awt.BorderLayout;

import javax.swing.JFrame;



public class Main 
{
	static int screenHeight;
	static int screenWidth;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		
		
		JFrame frame = new JFrame("RHIT School Map");
		GUI gui = new GUI(frame);
		
		
		
		
		frame.add(gui, BorderLayout.CENTER);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setUndecorated(true);
		frame.setVisible(true);
		
		
		
		
		screenHeight = frame.getHeight();
		screenWidth = frame.getWidth();
		
		gui.calculatePath("n4", "n6");
		gui.savePath("N4ToN6");
		gui.loadSavedPath("n4ToN6");


		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

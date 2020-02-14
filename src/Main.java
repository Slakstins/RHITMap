import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main 
{
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		
		
		JFrame frame = new JFrame("RHIT School Map");
		frame.setSize(1000, 1100);
		GUI gui = new GUI(frame);
		
		
		
		
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
		
		
		
		gui.calculatePath("n5", "n6");

		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

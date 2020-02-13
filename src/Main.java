import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main 
{
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		
		XMLEditor xmlEditor = new XMLEditor();
		
		JFrame frame = new JFrame("RHIT School Map");
		frame.setSize(1000, 1100);
		GUI gui = new GUI(frame, xmlEditor.getNodeToEdgeMap());
		
		
		
		
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
		
		gui.calculatePath("Bob", "George");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		
	}
}

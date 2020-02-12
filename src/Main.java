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
		frame.setSize(500, 500);
		GUI gui = new GUI(frame, xmlEditor.getNodeToEdgeMap());
		
		gui.calculatePath("Bob", "George");
		
		
		
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		
	}
}

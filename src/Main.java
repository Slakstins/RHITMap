import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {


	public static void main(String[] args) {
		new Main();
	}

	public Main() {

		JFrame frame = new JFrame("RHIT School Map");

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		frame.setVisible(true);

		GUI gui = new GUI(frame);
		
		JPanel panel = new JPanel();
		panel.setBounds(new Rectangle(0, 0, 100, 50));
		panel.setLocation(frame.getWidth() - 150, frame.getHeight() - 75);
		panel.setOpaque(false);
		JButton zoomInButton = new JButton();
		JButton zoomOutButton = new JButton();

		zoomInButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				gui.zoom(.1);
			}
			
		});
		
		zoomOutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				gui.zoom(-.1);
				
			}
			
		});
		
		zoomInButton.setFocusable(false);
		zoomOutButton.setFocusable(false);

		
		
		zoomInButton.setText("+");
		zoomOutButton.setText("-");
		panel.add(zoomOutButton);

		panel.add(zoomInButton);
		frame.add(panel);
		
		new ClickHandler(frame, gui);
		
		frame.add(gui, BorderLayout.CENTER);
		

		gui.calculatePath("n4", "n6", false, false);

		frame.validate();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

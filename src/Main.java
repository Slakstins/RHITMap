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
				gui.zoomIn();
			}
			
		});
		
		zoomOutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				gui.zoomOut();
				
			}
			
		});
		
		zoomInButton.setFocusable(false);
		zoomOutButton.setFocusable(false);

		
		
		zoomInButton.setText("+");
		zoomOutButton.setText("-");
		panel.add(zoomOutButton);

		panel.add(zoomInButton);
		frame.add(panel);
		
		
		
		
		frame.addKeyListener(new KeyListener(){


			@Override
			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();

				if (keyCode == KeyEvent.VK_UP) {
					gui.moveMapUp();
				}
				
				if (keyCode == KeyEvent.VK_LEFT) {
					gui.moveMapLeft();
				}
				
				if (keyCode == KeyEvent.VK_RIGHT) {
					gui.moveMapRight();
				}
				
				if (keyCode == KeyEvent.VK_DOWN) {
					gui.moveMapDown();
				}
								
			}

			@Override
			public void keyReleased(KeyEvent event) {
				// TODO Auto-generated method stub
				int keyCode = event.getKeyCode();

				
				if (keyCode == event.VK_UP) {
					gui.setMapMoveUp(false);

					}
				
				if (keyCode == event.VK_LEFT) {
					gui.setMapMoveLeft(false);

					}
				
				if (keyCode == event.VK_RIGHT) {
					gui.setMapMoveRight(false);

					}
				
				if (keyCode == event.VK_DOWN) {
					gui.setMapMoveDown(false);

					}
				
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		


		frame.add(gui, BorderLayout.CENTER);
		

		gui.calculatePath("n5", "n6");

		gui.loadSavedPath("n4ToN6");
		frame.validate();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

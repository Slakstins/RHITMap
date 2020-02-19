import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
	
	private String className1;
	private String className2;
	private String className3;
	private String className4;
	private String className5;
	private String className6;
	private String className7;
	private GUI gui;
	static boolean calculatingMultiplePaths;


	public static void main(String[] args) {
		
		new Main();
	}

	public Main() {
		this.calculatingMultiplePaths = false; //prevents replacement of the shortest path in dijstra when
		//calculating multiple paths

		JFrame frame = new JFrame("RHIT School Map");

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		frame.setVisible(true);

		GUI gui = new GUI(frame);
		this.gui = gui;
		JPanel panel = new JPanel();
		panel.setBounds(new Rectangle(0, 0, 100, 50));
		panel.setLocation(frame.getWidth() - 150, frame.getHeight() - 75);
		panel.setOpaque(false);
		JButton zoomInButton = new JButton();
		JButton zoomOutButton = new JButton();
		
		
		JPanel panel2 = new JPanel();
		JTextField class1 = new JTextField(10);
		
		
		JTextField class2 = new JTextField(10);

		JTextField class3 = new JTextField(10);
		JTextField class4 = new JTextField(10);
		JTextField class5 = new JTextField(10);
		JTextField class6 = new JTextField(10);
		JTextField class7 = new JTextField(10);
		
		JButton submitButton = new JButton("Calculate");
		submitButton.setFocusable(false);
		panel2.add(submitButton);
		
		submitButton.addActionListener(new ClassNameListener(this, class1, class2, class3, class4, class5, class6, class7));





		class1.setFocusable(true);
		panel2.add(class1);
		panel2.add(class2);
		panel2.add(class3);
		panel2.add(class4);
		panel2.add(class5);
		panel2.add(class6);
		panel2.add(class7);



		panel2.setLocation(5,5);
		panel2.setSize(150, 300);
		frame.add(panel2);
		

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
		
		
		
		gui.calculatePath("George", "Av", false, false);
		
		
	


		frame.validate();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateClassNames(String class1, String class2,String class3,String class4,String class5,String class6,String class7 ) {
		this.className1 = class1;
		this.className2 = class2;
		this.className3 = class3;
		this.className4 = class4;
		this.className5 = class5;
		this.className6 = class6;
		this.className7 = class7;
		
		gui.calculatePath(className1, className2, true, true);
		this.calculatingMultiplePaths = true;

		gui.calculatePath(className2, className3, false, false);
		gui.calculatePath(className3, className4, false, false);
		gui.calculatePath(className4, className5, false, false);
		gui.calculatePath(className5, className6, false, false);
		gui.calculatePath(className6, className7, false, false);
		this.calculatingMultiplePaths = false;

		//dijstra does not have error handling for if a path is not found between 2 nodes. This will 
		//not be a problem eventually when all nodes can get to all other nodes
		



		
	}
}

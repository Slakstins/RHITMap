import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

		setUpZoomPannel(frame);
		setUpFloorPannel(frame);
		
		GUI gui = new GUI(frame);
		this.gui = gui;
		
		setUpSchedulePannel(frame);

		new ClickHandler(frame, gui);
		
		frame.add(gui, BorderLayout.CENTER);

		frame.validate();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateClassNames(String class1, String class2,String class3,String class4,String class5,String class6,String class7 , JTextField field1, 
			JTextField field2, JTextField field3, JTextField field4, JTextField field5, JTextField field6, JTextField field7) {
		this.className1 = class1;
		this.className2 = class2;
		this.className3 = class3;
		this.className4 = class4;
		this.className5 = class5;
		this.className6 = class6;
		this.className7 = class7;

		try {
		gui.calculatePath(className1, className2);
		} catch (Exception e) {
			field1.setBackground(Color.red);
			field2.setBackground(Color.red);
			return;
		}
		field1.setBackground(Color.GREEN);
		field2.setBackground(Color.GREEN);
		
		if(className3.length() > 0)
		{
			this.calculatingMultiplePaths = true;
			try {
				gui.calculatePath(className2, className3);
				} catch (Exception e) {
					field3.setBackground(Color.red);
					return;
				}
				field3.setBackground(Color.GREEN);
				
				if(className4.length() > 0)
				{
					try {
						gui.calculatePath(className3, className4);
						} catch (Exception e) {
							field4.setBackground(Color.red);		
							return;
						}
						field4.setBackground(Color.GREEN);
						
						if(className5.length() > 0)
						{
							try {
								gui.calculatePath(className4, className5);
								} catch (Exception e) {
									field5.setBackground(Color.red);
									return;
								}
								field5.setBackground(Color.GREEN);
								
								if(className6.length() > 0)
								{
									try {
										gui.calculatePath(className5, className6);
										} catch (Exception e) {
											field6.setBackground(Color.red);
											return;
										}
										field6.setBackground(Color.GREEN);
										
										if(className7.length() > 0)
										{
											try {
												gui.calculatePath(className6, className7);
												} catch (Exception e) {
													field7.setBackground(Color.RED);
													return;
												}
												field7.setBackground(Color.GREEN);
										}
								}
						}
				}
		}
		
		
		this.calculatingMultiplePaths = false;

		//dijstra does not have error handling for if a path is not found between 2 nodes. This will 
		//not be a problem eventually when all nodes can get to all other nodes
	}
	
	private void setUpZoomPannel(JFrame frame)
	{
		JPanel zoomPanel = new JPanel();
		
		//make position and size relative to screen size
		zoomPanel.setBounds(new Rectangle(0, 0, 200, 50));
		zoomPanel.setLocation(frame.getWidth() - 225, frame.getHeight() - 75);
		zoomPanel.setOpaque(false);
		
		JButton zoomInButton = new JButton();
		JButton zoomOutButton = new JButton();
		
		zoomInButton.setFocusable(false);
		zoomOutButton.setFocusable(false);

		zoomInButton.setText("Zoom in");
		zoomOutButton.setText("Zoom out");
		
		zoomInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gui.zoom(.1);
			}
		});
		
		zoomOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gui.zoom(-.1);
				
			}
		});
		
		zoomPanel.add(zoomInButton);
		zoomPanel.add(zoomOutButton);
		
		frame.add(zoomPanel);
	}
	
	private void setUpSchedulePannel(JFrame frame)
	{
		JPanel schedulePanel = new JPanel();
		
		JTextField class1 = new JTextField(10);
		JTextField class2 = new JTextField(10);
		JTextField class3 = new JTextField(10);
		JTextField class4 = new JTextField(10);
		JTextField class5 = new JTextField(10);
		JTextField class6 = new JTextField(10);
		JTextField class7 = new JTextField(10);
		
		JButton calculateButton = new JButton("Calculate");
		calculateButton.setFocusable(false);
		
		schedulePanel.add(calculateButton);
		
		calculateButton.addActionListener(new ClassNameListener(this, frame,class1, class2, class3, class4, class5, class6, class7));

		class1.setFocusable(true);
		schedulePanel.add(class1);
		schedulePanel.add(class2);
		schedulePanel.add(class3);
		schedulePanel.add(class4);
		schedulePanel.add(class5);
		schedulePanel.add(class6);
		schedulePanel.add(class7);
		JButton loadPathButton = new JButton("Load Saved Path");
		JButton savePathButton = new JButton("Save Path");
		loadPathButton.setFocusable(true);
		savePathButton.setFocusable(false);
		
		JCheckBox wcaBox = new JCheckBox("WCA");
		
		schedulePanel.add(wcaBox);
		wcaBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.getDijstra().wca = (wcaBox.isSelected());
				
				
			}
			
		});
		
		JCheckBox outsideBox = new JCheckBox("Outside");
		
		schedulePanel.add(outsideBox);
		outsideBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.getDijstra().outsidePath = (outsideBox.isSelected());
			}
			
		});
		
		JCheckBox parkour = new JCheckBox("Parkour");
		
		schedulePanel.add(parkour);
		parkour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				gui.setParkour(parkour.isSelected());
			}
		});
		
		savePathButton.addActionListener(new SaveButtonListener(frame, this.gui));
		loadPathButton.addActionListener(new LoadButtonListener(frame, this.gui));
		schedulePanel.add(loadPathButton);
		schedulePanel.add(savePathButton);

		//listener should give a text box that then when closed writes the edges to a new file of the name in the textbox
		
		
		//make position and size relative to screen size
		schedulePanel.setLocation(5,5);
		schedulePanel.setSize(150, 350);
		frame.add(schedulePanel);
	}
	
	private void setUpFloorPannel(JFrame frame)
	{
		JPanel floorPanel = new JPanel();
		
		//make position and size relative to screen size
		floorPanel.setBounds(new Rectangle(0, 0, 200, 50));
		floorPanel.setLocation(frame.getWidth() - 225, frame.getHeight() - 1050);
		floorPanel.setOpaque(false);
		
		JButton floorUpButton = new JButton();
		JButton floorDownButton = new JButton();
		
		floorUpButton.setFocusable(false);
		floorDownButton.setFocusable(false);

		floorUpButton.setText("Floor up");
		floorDownButton.setText("Floor down");
		
		floorUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Floor.increaseFloor(gui);
			}
		});
		
		floorDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Floor.decreaseFloor(gui);
			}
		});
		
		floorPanel.add(floorUpButton);
		floorPanel.add(floorDownButton);
		
		frame.add(floorPanel);
	}
}
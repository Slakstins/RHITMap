import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SaveButtonListener implements ActionListener {
	
	private JFrame frame;
	private GUI gui;
	
	public SaveButtonListener(JFrame frame, GUI gui) {
		this.frame = frame;
		this.gui = gui;
		
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

		JTextField saveNameBox = new JTextField("Save as", 20);
		saveNameBox.setFocusable(true);
		

		saveNameBox.setCaretColor(Color.pink);
		saveNameBox.setSize(100, 100);
		saveNameBox.setLocation(100, 100);
		JPanel panel = new JPanel();
		panel.add(saveNameBox);


		frame.getContentPane().add(panel);
		panel.setSize(100, 100);
		frame.validate();
		saveNameBox.grabFocus();
		saveNameBox.selectAll();
		saveNameBox.addActionListener(new SaveNameBoxListener(this.gui, frame, saveNameBox, panel));

		

	}

}

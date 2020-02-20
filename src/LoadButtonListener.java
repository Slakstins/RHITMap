import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LoadButtonListener implements ActionListener {
	private GUI gui;
	private Frame frame;
	

	public LoadButtonListener(JFrame frame, GUI gui) {
		// TODO Auto-generated constructor stub
		this.frame = frame;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		JTextField loadNameBox = new JTextField("Load file name:", 20);
		loadNameBox.setFocusable(true);
		

		loadNameBox.setCaretColor(Color.pink);
		loadNameBox.setSize(100, 100);
		loadNameBox.setLocation(100, 100);
		JPanel panel = new JPanel();
		panel.add(loadNameBox);


		this.frame.add(panel);
		panel.setSize(100, 100);
		frame.validate();
		loadNameBox.grabFocus();
		loadNameBox.selectAll();
		loadNameBox.addActionListener(new LoadNameBoxListener(this.gui, frame, loadNameBox, panel));

		

	}

}

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoadNameBoxListener implements ActionListener {

	private GUI gui;
	private Frame frame;
	private JTextField loadNameBox;
	private JPanel panel;

	public LoadNameBoxListener(GUI gui, Frame frame, JTextField loadNameBox, JPanel panel) {
		// TODO Auto-generated constructor stub
		this.gui = gui;
		this.frame = frame;
		this.loadNameBox = loadNameBox;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String saveName = this.loadNameBox.getText();
		ArrayList<Dijstra.Edge> path = new ArrayList<>();
		if ((!(saveName.equals("")) && (path = gui.loadSavedPath(saveName + ".xml")) != null)) {
			gui.draw(path);		
		} else {
			System.out.println("Invalid name");
		}
			this.panel.remove(loadNameBox);
		this.frame.remove(panel);
		this.frame.requestFocus();
		this.frame.repaint();

	}

}

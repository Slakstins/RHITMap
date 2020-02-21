import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NodeNameGetListener implements ActionListener {
	private Dijstra.Node toName;
	private JTextField textField;
	private JPanel panel;
	private JFrame frame;
	private GUI gui;

	public NodeNameGetListener(JTextField textField, JPanel panel, Dijstra.Node toName, JFrame frame, GUI gui) {
		this.toName = toName;
		this.textField = textField;
		this.panel = panel;
		this.frame = frame;
		this.gui = gui;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.setNameAsked(false); // might be good to associate this variable with something else to avoid passing
									// gui

		toName.setName(this.textField.getText());

		this.frame.getContentPane().remove(panel);
		this.frame.requestFocus();
		this.frame.repaint();
		
		//gui.getXMLEditor().updateMapXML();

	}

}

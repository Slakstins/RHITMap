import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EdgeCostGetListener implements ActionListener {
	private Dijstra.Edge toCost;
	private JTextField textField;
	private JPanel panel;
	private JFrame frame;
	private GUI gui;

	public EdgeCostGetListener(JTextField textField, JPanel panel, Dijstra.Edge toCost, JFrame frame, GUI gui) {
		this.toCost = toCost;
		this.textField = textField;
		this.panel = panel;
		this.frame = frame;
		this.gui = gui;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.setNameAsked(false); // might be good to associate this variable with something else to avoid passing
									// gui
		
		try {
			toCost.setCost(Integer.parseInt(this.textField.getText()));

		}catch (java.lang.NumberFormatException a) {
			System.out.println("MUST BE A NUMBER");
			toCost.setCost(Integer.parseInt(this.textField.getText()));

		}

		this.frame.getContentPane().remove(panel);
		this.frame.requestFocus();
		this.frame.repaint();
		
		//gui.getXMLEditor().updateMapXML();

	}

}

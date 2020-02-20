import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveNameBoxListener implements ActionListener {
	private GUI gui;
	private JFrame frame;
	private JTextField text;
	private JPanel panel;
	
	public SaveNameBoxListener(GUI gui, JFrame frame, JTextField text, JPanel panel) {
		this.gui = gui;
		this.frame = frame;
		this.text = text;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String saveName = this.text.getText();
		this.gui.savePath(saveName);
		this.panel.remove(text);
		this.frame.getContentPane().remove(panel);
		
		this.frame.requestFocus();
		this.frame.repaint();

		
		

	}

}

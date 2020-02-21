import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class ClassNameListener implements ActionListener {
	private JTextField class1;
	private JTextField class2;
	private JTextField class3;
	private JTextField class4;
	private JTextField class5;
	private JTextField class6;
	private JTextField class7;
	private JFrame frame;
	


	
	Main main;
	public ClassNameListener(Main main, JFrame frame, JTextField textClass1, JTextField textClass2,JTextField textClass3,JTextField textClass4,JTextField textClass5,JTextField textClass6,JTextField textClass7) {
		this.main = main;
		this.class1 = textClass1;
		this.class2 = textClass2;
		this.class3 = textClass3;
		this.class4 = textClass4;
		this.class5 = textClass5;
		this.class6 = textClass6;
		this.class7 = textClass7;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		main.updateClassNames(class1.getText(),  class2.getText(),class3.getText(),class4.getText(),class5.getText(),class6.getText(),class7.getText(), class1, class2,class3,class4,class5,class6,class7);
		this.frame.requestFocus();
	}

}
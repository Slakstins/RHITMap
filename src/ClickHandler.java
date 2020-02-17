import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

public class ClickHandler 
{
	public ClickHandler(JFrame frame, GUI gui)
	{
		
		frame.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent event) 
			{
				int keyCode = event.getKeyCode();

				switch(keyCode)
				{
					case KeyEvent.VK_UP:
						gui.moveUp();
						break;
					case KeyEvent.VK_DOWN:
						gui.moveDown();
						break;
					case KeyEvent.VK_LEFT:
						gui.moveLeft();
						break;
					case KeyEvent.VK_RIGHT:
						gui.moveRight();
						break;
					case KeyEvent.VK_ESCAPE:
						gui.close();
						break;
				}				
			}

			public void keyReleased(KeyEvent event) {
				int keyCode = event.getKeyCode();

				
				switch(keyCode)
				{
					case KeyEvent.VK_UP:
						gui.setMoveUp(false);
						break;
					case KeyEvent.VK_DOWN:
						gui.setMoveDown(false);
						break;
					case KeyEvent.VK_LEFT:
						gui.setMoveLeft(false);
						break;
					case KeyEvent.VK_RIGHT:
						gui.setMoveRight(false);
						break;
				}
			}

			public void keyTyped(KeyEvent arg0) 
			{
				
			}	
		});
		
		frame.addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e) 
			{
				gui.zoom(-e.getPreciseWheelRotation()*.1);
			}
		});
		
		frame.addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e) {
				gui.moveOffset(e.getX(), e.getY());
			}

			public void mouseMoved(MouseEvent e) {
				
			}
		});
		
		frame.addMouseListener(new MouseListener()
		{
			
			/**
			 * for creating nodes and edges
			 */
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Dijstra.Node newNode = gui.getXMLEditor().initializeNewNode(e.getX(), e.getY(), "TBD");
					gui.addNode(newNode);
					gui.askName(e.getX(), e.getY(), newNode );
					
					
					
				}
				
			}

			public void mousePressed(MouseEvent e) {
				gui.setMousePos();
			}

			public void mouseReleased(MouseEvent e) {
				
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}
			
		});
	}
}


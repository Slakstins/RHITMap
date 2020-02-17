import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

public class ClickHandler 
{
	private Dijstra.Node selNode1;
	private Dijstra.Node selNode2;
	public ClickHandler(JFrame frame, GUI gui)
	
	
	
	
	
	{
		selNode1 = null;
		selNode2 = null;
		
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
					case KeyEvent.VK_DELETE:
						gui.deleteNode(selNode1, selNode2);
						
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
					boolean foundContains = false;
					for (Dijstra.Node i :gui.getNodes()) {
						
						Rectangle bounds = new Rectangle((int)i.getX(), (int)i.getY(), (int)i.getSize(), (int)i.getSize());
						if (bounds.contains(new Point(e.getX(), e.getY()))) {
							
							foundContains = true;
							
							if (i.getSelected()) {
								i.setSelected(false);
								if (selNode1 == i) {
									selNode1 = null;
									
								}
								if (selNode2 == i ) {
									selNode2 = null;
								}
							} else {
							i.setSelected(true);
							gui.askName(i);
							if (selNode1 == null && selNode2 != i) {
								selNode1 = i;
							}
							else if (selNode2 == null && selNode1 != i) {
								selNode2 = i;
							}
							
							else {
								//replace the first selected node with the new one
								selNode1.setSelected(false);
								selNode1 = i;
							}
							}
						}
						
							
												

					}
					if (!foundContains) {
					Dijstra.Node newNode = gui.getXMLEditor().initializeNewNode(e.getX(), e.getY(), "TBD");
					gui.addNode(newNode);
					gui.askName(newNode );
					}
					
					frame.repaint();
					
					
					
					

					
					//need a method to select nodes that acts through gui
					//for creation of edges!
					
					
					
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


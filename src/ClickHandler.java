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

public class ClickHandler {
	private Dijstra.Node selNode1;
	private Dijstra.Node selNode2;
	private GUI gui;

	public ClickHandler(JFrame frame, GUI gui)

	{
		this.gui = gui;

		selNode1 = null;
		selNode2 = null;

		frame.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();

				switch (keyCode) {
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
				case KeyEvent.VK_DELETE:
					if (selNode1 != null) {

						gui.deleteNode(selNode1);

						selNode1 = null;
					} else if (selNode2 != null) {
						gui.deleteNode(selNode2);
						selNode2 = null;
					}

					break;
				case KeyEvent.VK_E:
					boolean doAdd = true;
					if (selNode1 != null && selNode2 != null && !(selNode1.equals(selNode2))) {
						for (Dijstra.Edge e : gui.getXMLEditor().edges) {
							if ((e.getN1().equals(selNode1) && e.getN2().equals(selNode2))
									|| (e.getN2().equals(selNode1) && e.getN1().equals(selNode2))) {
								doAdd = false;
								gui.askCost(e);
								break;
							}
						}
						if (doAdd) {
							//could set node 1 and 2 based on ratio and offset
							
							Dijstra.Edge newEdge = gui.getXMLEditor().initializeNewEdge(selNode1, selNode2);
							gui.addEdge(newEdge); // adding the edge does not add the edge to the nodes' array lists
							gui.askCost(newEdge);
							frame.repaint();
						}
					}
					break;

				case KeyEvent.VK_O:
					for (Dijstra.Edge e : gui.getXMLEditor().edges) {
						if ((e.getN1().equals(selNode1) && e.getN2().equals(selNode2))
								|| (e.getN2().equals(selNode1) && e.getN1().equals(selNode2))) {
							gui.getXMLEditor().setEdgeOutside(e, e.flipOutside());
							gui.repaint();
							gui.getXMLEditor().updateMapXML();
						}
					}
					break;

				case KeyEvent.VK_W:
					for (Dijstra.Edge e : gui.getXMLEditor().edges) {
						if ((e.getN1().equals(selNode1) && e.getN2().equals(selNode2))
								|| (e.getN2().equals(selNode1) && e.getN1().equals(selNode2))) {
							gui.getXMLEditor().setEdgeWCA(e, e.flipWCA());
							gui.repaint();
							gui.getXMLEditor().updateMapXML();
						}
					}
					break;

				}

			}

			public void keyReleased(KeyEvent event) {
				int keyCode = event.getKeyCode();

				switch (keyCode) {
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

			public void keyTyped(KeyEvent arg0) {

			}
		});

		frame.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				gui.zoom(-e.getPreciseWheelRotation() * .1);
			}
		});

		frame.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {

//				gui.moveOffset(e.getX(), e.getY() );
			}

			public void mouseMoved(MouseEvent e) {

			}
		});

		frame.addMouseListener(new MouseListener() {

			/**
			 * for creating nodes and edges
			 */
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {

					boolean foundContains = false;
					// check each node to see if it was clicked
					for (Dijstra.Node i : gui.getNodes()) {
		

						Rectangle bounds = new Rectangle((int) (i.specialGetEx() - i.getSize() / 2),
								(int) (i.specialGetWhy() - i.getSize() / 2), (int) i.getSize(), (int) i.getSize());

						// a node was clicked
						if (bounds.contains(new Point(e.getX(), e.getY()))) { 



							foundContains = true;

							if (i.getSelected()) {
								// the node clicked is already selected
								i.setSelected(false);
								if (selNode1 == i) {
									selNode1 = null;

								}
								if (selNode2 == i) {
									selNode2 = null;
								}
							} else {
								// the node clicked should be selected
								i.setSelected(true);
								gui.askName(i);

								if (selNode1 == null && selNode2 != i) {
									selNode1 = i;
								} else if (selNode2 == null && selNode1 != i) {
									selNode2 = i;
								}

								// either both are null or both are selected and neither is the new node
								else {
									// replace the first selected node with the new one
									selNode1.setSelected(false);
									selNode1 = i;
								}
							}
						}
						frame.repaint();

					}
					if (!foundContains) {
						int newX = (int) ((e.getX() / GUI.zoomLevel - GUI.xOffset));
						int newY = (int) ((e.getY() / GUI.zoomLevel - GUI.yOffset));

						Dijstra.Node newNode = gui.getXMLEditor().initializeNewNode(
								newX,

								newY, Floor.getFloor(), "TBD"); //saves with the correct value!
						gui.addNode(newNode);
						gui.askName(newNode);
					}

					frame.repaint();

				}

			}

			public void mousePressed(MouseEvent e) {

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

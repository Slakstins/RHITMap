import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JComponent {
	static double screenHeight;
	static double screenWidth;
	public static double xScreenRatio;
	public static double yScreenRatio;

	public static double xOffset = 0;
	public static double yOffset = 0;
	private double startX = 0;
	private double startY = 0;
	private double lastX = 0;
	private double lastY = 0;

	private double mapMoveAmount = 10;
	private JPanel currentText;
	private boolean nameAsked;

	private static ArrayList<Dijstra.Node> nodes = new ArrayList<>();
	private static ArrayList<Dijstra.Edge> edges = new ArrayList<Dijstra.Edge>();
	private HashMap<String, Dijstra.Node> nodeNameMap = new HashMap<>();
	private XMLEditor xmlEditor;
	private static JFrame frame;

	public static double zoomLevel = 1;

	private boolean moveUp = false;
	private boolean moveDown = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;

	private Floor floor = new Floor();

	private BufferedImage RHITMap;

	private Dijstra dijstra = new Dijstra();
	
	private boolean wca;
	private boolean outside;
	private boolean parkour;

	public GUI(JFrame frame) {
		this.outside = false;
		this.wca = false;
		this.parkour = false;

		this.edges = new ArrayList<Dijstra.Edge>();
		this.nodes = new ArrayList<Dijstra.Node>();
		this.nameAsked = false;
		screenHeight = frame.getHeight();
		screenWidth = frame.getWidth();
		xScreenRatio = screenWidth / 1920;
		yScreenRatio = screenHeight / 1080;

		xmlEditor = new XMLEditor();
		HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> nodeEdgeMap = xmlEditor.getNodeToEdgeMap();

		this.frame = frame;
		Set<Dijstra.Node> keys = nodeEdgeMap.keySet();
		for (Dijstra.Node i : keys) {
			nodes.add(i);
			for (Dijstra.Edge j : i.getEdges()) {
				if (!edges.contains(j))
					edges.add(j);
				// this adds duplicates of the edges, as multiple nodes have the same edge.
				// could store edges in a set (sets don't allow dulicates)
				// to avoid this problem
			}

		}
		try {
			RHITMap = ImageIO.read(new File("School Floor Plans/Background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("did not find RHITMap");
			e.printStackTrace();
		}
		generateNodeNameMap();

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		drawMap(g2);
		drawFloor(g2);
		for (Dijstra.Node n : nodes) {

			if(n.getOutside() || n.getFloor() == floor.getFloor())
			{
				n.drawOn(g2);
			}
		}
		for (Dijstra.Edge e : edges) {

			if(e.getOutside() || e.getFloorOne() == floor.getFloor() || e.getFLoorTwo() == floor.getFloor())
			{
				e.drawOn(g2);
			}
		}

		if (moveUp) {
			this.moveUp();
		}

		if (moveRight) {
			this.moveRight();
		}

		if (moveLeft) {
			this.moveLeft();
		}

		if (moveDown) {
			this.moveDown();
		}

		g2.dispose();
	}

	private void drawMap(Graphics2D g) {
		g.drawImage(RHITMap, (int) (xOffset * zoomLevel), (int) (yOffset * zoomLevel),
				(int) ((screenWidth + xOffset) * zoomLevel), (int) ((screenHeight + yOffset) * zoomLevel), 0, 0,
				RHITMap.getWidth(), RHITMap.getHeight(), null);
	}

	private void drawFloor(Graphics2D g) {
		BufferedImage temp = floor.getImage();
		g.drawImage(temp, (int) (xOffset * zoomLevel), (int) (yOffset * zoomLevel),
				(int) ((screenWidth + xOffset) * zoomLevel), (int) ((screenHeight + yOffset) * zoomLevel), 0, 0,
				temp.getWidth(), temp.getHeight(), null);
	}

	/**
	 * for use when adding additional nodes - not for initial rendering
	 * 
	 * @param newNode
	 */
	public void addNode(Dijstra.Node newNode) {

		this.nodes.add(newNode);

		this.repaint();
		//this.getXMLEditor().updateMapXML();

	}

	public void generateNodeNameMap() {
		for (int i = 0; i < this.nodes.size(); i++) {
			nodeNameMap.put(nodes.get(i).getName(), nodes.get(i));
		}
	}

	public void calculatePath(String startNode, String endNode) throws Exception {
		if(parkour)
		{
			parkour(startNode, endNode);
			return;
		}
		ArrayList<Dijstra.Edge> path = new ArrayList<>();
//		if ((path = loadSavedPath(startNode + endNode + outside + wca)) != null) {
//			draw(path);
//		} else {
		if (nodeNameMap.get(startNode) == null || nodeNameMap.get(endNode) == null) 
		{
			throw new Exception();
		}
		this.dijstra.calculatePath(nodeNameMap.get(startNode), nodeNameMap.get(endNode));
		// make sure nodenamepath communicated with XML
//		savePath(startNode + endNode + outside + wca);
		String empty = "";
	
		if ((nodeNameMap.get(startNode) == null && !(startNode.equals(empty))) && ((nodeNameMap.get(endNode) == null && !(endNode.equals(empty))))) 
		{
			System.out.println(startNode.toString());
			System.out.println(endNode.toString());
			throw new Exception();	
		}
	}
	
	public void parkour(String startNode, String endNode)
	{
		try
		{
			Dijstra.Node n1 = nodeNameMap.get(startNode);
			Dijstra.Node n2 = nodeNameMap.get(endNode);
			if(n1 == null || n2 == null)
			{
				throw new Exception();
			}
//			Line2D.Double edge = new Line2D.Double(new Point2D.Double(n1.getX() + Constants.nodeSize / 2 - Constants.nodeSize / 2, n1.getY() + Constants.nodeSize / 2 - Constants.nodeSize / 2),
//					new Point2D.Double(n2.getX() + Constants.nodeSize / 2 - Constants.nodeSize / 2, n2.getY() + Constants.nodeSize / 2 - Constants.nodeSize / 2));
			edges.clear();
			edges.add(new Dijstra.Edge(n1, n2, 0, true, false));
			draw(edges);
		}
		catch(Exception e)
		{
			
		}
	}

	public void savePath(String fileName) {
		try {

			xmlEditor.writeEdges(Dijstra.shortestPathEdges, fileName);
			if (Dijstra.shortestPathEdges.isEmpty()) {
				System.out.println("current path is empty but saved regardless");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot save path");
			e.printStackTrace();
		}
	}

	public ArrayList<Dijstra.Edge> loadSavedPath(String fileName) {
		try {

			xmlEditor.updateMapXML();
			this.edges.clear();
			xmlEditor.edges.clear();
			xmlEditor.read(fileName); // problem with loading paths because this returns this.edges instead of the
										// special path edges
			this.edges = this.xmlEditor.edges;
			this.xmlEditor.setFileNameToWrite(fileName);
			this.repaint();
		} catch (Exception e) {
			return null;
		}

		if (this.edges.size() == 0) {
			System.out.println("file not read " + fileName);
		}

		return this.edges;
	}

	public static void draw(ArrayList<Dijstra.Edge> pathEdges) {
		int cost = 0;
		if (Main.calculatingMultiplePaths == false) {
			edges = pathEdges;
		} else {
			for (Dijstra.Edge i : pathEdges) {
				edges.add(i);
			}
		}

		for (int i = 0; i < edges.size(); i++) {
			edges.get(i).setOnPath(true);
			cost += edges.get(i).getCost();
		}
		frame.repaint();
		System.out.println("cost: " + cost);
		JOptionPane.showMessageDialog(frame, "ETA: " + (int) Math.ceil(cost/120.0) + " minutes");

	}

	public void moveUp() {
		this.moveUp = true;
		GUI.yOffset += mapMoveAmount / zoomLevel;
		this.repaint();
	}

	public void moveDown() {
		this.moveDown = true;
		GUI.yOffset -= mapMoveAmount / zoomLevel;
		this.repaint();
	}

	public void moveRight() {
		this.moveRight = true;

		GUI.xOffset -= mapMoveAmount / zoomLevel;
		this.repaint();
	}

	public void moveLeft() {
		this.moveLeft = true;

		GUI.xOffset += mapMoveAmount / zoomLevel;
		this.repaint();
	}

	public void setMoveUp(boolean mapMoveUp) {
		this.moveUp = mapMoveUp;
		this.repaint();
	}

	public void setMoveRight(boolean mapMoveRight) {
		this.moveRight = mapMoveRight;
		this.repaint();
	}

	public void setMoveDown(boolean mapMoveDown) {
		this.moveDown = mapMoveDown;
		this.repaint();
	}

	public void setMoveLeft(boolean mapMoveLeft) {
		this.moveLeft = mapMoveLeft;
		this.repaint();
	}

	public void zoom(double zoom) {
		zoomLevel += zoom;
		this.repaint();
	}

	public void moveOffset(double x, double y) {
		double deltaX = x - startX - lastX;
		double deltaY = y - startY - lastY;

		lastX = x - startX;
		lastY = y - startY;

		xOffset += deltaX / zoomLevel;
		yOffset += deltaY / zoomLevel;
		repaint();
	}

	public void setMousePos() { // this method is not used

		startX = getMousePosition().getX();
		startY = getMousePosition().getY();

	}

	public void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public XMLEditor getXMLEditor() {
		return this.xmlEditor;
	}

	public ArrayList<Dijstra.Node> getNodes() {
		return this.nodes;
	}

	public void setNameAsked(boolean nameAsked) {
		this.nameAsked = nameAsked;
	}

	/**
	 * set the name for the node that was just created using a text box
	 * 
	 * @param x
	 * @param y
	 * @param node
	 */
	public void askName(Dijstra.Node node) {
		if (currentText != null) {
			frame.remove(currentText);
		}
		this.nameAsked = true;
		JTextField textGet = new JTextField(node.getName(), 40);
		textGet.selectAll();
		textGet.setToolTipText("Input node name");
		JPanel panel = new JPanel();
		this.currentText = panel;
		panel.add(textGet);
		textGet.setVisible(true);
		panel.setBounds(0, 40, 100, 100);
		this.frame.getContentPane().add(panel);
		this.frame.validate();

		textGet.addActionListener(new NodeNameGetListener(textGet, panel, node, this.frame, this));

		textGet.grabFocus();

	}

	public void askCost(Dijstra.Edge edge) {

		JTextField costGet = new JTextField(String.valueOf(edge.getCost()), 40);
		costGet.selectAll();
		costGet.setToolTipText("Input cost");
		JPanel panel = new JPanel();
		panel.add(costGet);
		costGet.setVisible(true);
		panel.setBounds(0, 40, 100, 100);
		this.frame.getContentPane().add(panel);
		this.frame.validate();
		costGet.addActionListener(new EdgeCostGetListener(costGet, panel, edge, this.frame, this));

		costGet.grabFocus();

	}

	/**
	 * when edges are deleted, they need to be deleted from all the nodes that have
	 * them too. IMPLEMENT this
	 * 
	 * @param selNode
	 */
	public void deleteNode(Dijstra.Node selNode) {
		ArrayList<Dijstra.Edge> tempEdges = selNode.getEdges();
		for (Dijstra.Edge e : tempEdges) {
			edges.remove(e);

		}

		for (int i = 0; i < xmlEditor.edges.size(); i++) {

			if (xmlEditor.edges.get(i).getN1().equals(selNode)) {
				for (int j = 0; j < xmlEditor.nodes.size(); j++) {
					for (int a = 0; a < xmlEditor.nodes.get(j).getEdges().size(); a++) {
						if (xmlEditor.nodes.get(j).getEdges().get(a).equals(xmlEditor.edges.get(i))) {
							xmlEditor.nodes.get(j).getEdges().remove(a);
						}
					}
				}
				xmlEditor.edges.remove(i);
			}
			if (i < xmlEditor.edges.size() && xmlEditor.edges.get(i).getN2().equals(selNode)) {
				for (int j = 0; j < xmlEditor.nodes.size(); j++) {
					for (int a = 0; a < xmlEditor.nodes.get(j).getEdges().size(); a++) {
						if (xmlEditor.nodes.get(j).getEdges().get(a).equals(xmlEditor.edges.get(i))) {
							xmlEditor.nodes.get(j).getEdges().remove(a);
						}
					}
				}
				xmlEditor.edges.remove(i);
			}
		}

		nodes.remove(selNode);
		this.xmlEditor.nodes.remove(selNode);

		frame.repaint();
//		this.getXMLEditor().updateMapXML();

	}

	public void addEdge(Dijstra.Edge newEdge) {
		// check to make sure there isn't already a node with this connection

		this.edges.add(newEdge);
		//this.getXMLEditor().updateMapXML();

	}

	public boolean getWca() {
		return wca;
	}

	public void setWca(boolean wca) {
		this.wca = wca;
	}

	public boolean getOutside() {
		return outside;
	}

	public void setOutside(boolean outside) {
		this.outside = outside;
	}

	public Dijstra getDijstra() {
		return dijstra;
	}
	
	public void save()
	{
		this.getXMLEditor().updateMapXML();
	}
	
	public void setParkour(boolean parkour)
	{
		this.parkour = parkour;
	}
}
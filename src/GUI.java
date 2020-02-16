import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowEvent;
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

public class GUI extends JComponent {
	static int screenHeight;
	static int screenWidth;
	public static double xScreenRatio;
	public static double yScreenRatio;
	
	public static double xOffset = 0;
	public static double yOffset = 0;
	private double startX = 0;
	private double startY = 0;
	private double lastX = 0;
	private double lastY = 0;
	
	private int mapMoveAmount = 1;
	
	
	private static ArrayList<Dijstra.Node> nodes = new ArrayList<>();
	private static ArrayList<Dijstra.Edge> edges = new ArrayList<>();
	private HashMap<String, Dijstra.Node> nodeNameMap = new HashMap<>();
	private XMLEditor xmlEditor;
	private JFrame frame;
	
	public static double zoomLevel = 1;
	
	private boolean moveUp = false;
	private boolean moveDown = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	
	private BufferedImage RHITMap;

	private Dijstra dijstra = new Dijstra();

	public GUI(JFrame frame) {
		screenHeight = frame.getHeight();
		screenWidth = frame.getWidth();
		xScreenRatio = screenWidth/1920;
		yScreenRatio = screenHeight/1080;

		// put xml inside of graphics since it looks like it will be handling all of the
		// clicks
		xmlEditor = new XMLEditor();
		HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> nodeEdgeMap = xmlEditor.getNodeToEdgeMap();
		

		this.frame = frame;
		Set<Dijstra.Node> keys = nodeEdgeMap.keySet();
		for (Dijstra.Node i : keys) {
			nodes.add(i);
			for (Dijstra.Edge j : i.getEdges()) {
				// edges.add(j);***
				// this adds duplicates of the edges, as multiple nodes have the same edge.
				// could store edges in a set (sets don't allow dulicates)
				// to avoid this problem
			}

		}
		try {
			RHITMap = ImageIO.read(new File("src/RHITMap-HighDef.jpeg"));
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
		for (Dijstra.Node n : nodes) {
			n.drawOn(g2);
		}
		for (Dijstra.Edge e : edges) {
			e.drawOn(g2);
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
		g.drawImage(RHITMap, (int)(xOffset  * zoomLevel), (int)(yOffset  * zoomLevel), (int)((screenWidth + xOffset) * zoomLevel), (int)((screenHeight + yOffset) * zoomLevel), 0, 0, RHITMap.getWidth(), RHITMap.getHeight(), null);
	}

	public void generateNodeNameMap() {
		for (int i = 0; i < this.nodes.size(); i++) {
			nodeNameMap.put(nodes.get(i).getName(), nodes.get(i));
		}
	}

	public void calculatePath(String startNode, String endNode) {
		ArrayList<Dijstra.Edge> path = new ArrayList<>();
		if((path = loadSavedPath(startNode + "To" + endNode)) != null)
		{
			draw(path);
		}
		else
		{
			this.dijstra.calculatePath(nodeNameMap.get(startNode), nodeNameMap.get(endNode));
			savePath(startNode + "To" + endNode);
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
		ArrayList<Dijstra.Edge> readEdges = null;
		try {
			readEdges = xmlEditor.read(fileName);
		} catch (Exception e) {
			return null;
		}

		if (readEdges == null) {
			System.out.println("file not read " + fileName);
		}
		return readEdges;
	}

	public static void draw(ArrayList<Dijstra.Edge> pathEdges) {
		int cost = 0;
		edges = pathEdges;
		for (int i = 0; i < edges.size(); i++) {
			cost += edges.get(i).getCost();
		}
		System.out.println(cost);
	}

	public void moveUp() {
		this.moveUp = true;
		GUI.yOffset += mapMoveAmount;
		this.repaint();
	}
	
	public void moveDown() {
		this.moveDown = true;
		GUI.yOffset -= mapMoveAmount;
		this.repaint();
	}
	
	public void moveRight() {
		this.moveRight = true;

		GUI.xOffset -= mapMoveAmount;
		this.repaint();
	}
	
	public void moveLeft() {
		this.moveLeft = true;

		GUI.xOffset += mapMoveAmount;
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
	
	public void zoom(double zoom) 
	{
		zoomLevel += zoom;
		this.repaint();
	}
	
	public void moveOffset(double x, double y)
	{
		double deltaX = x - startX - lastX;
		double deltaY = y - startY - lastY;
		
		lastX = x - startX;
		lastY = y - startY;
		
		xOffset += deltaX;
		yOffset += deltaY;
		repaint();
	}
	
	public void setMousePos()
	{
		startX = getMousePosition().getX();
		startY = getMousePosition().getY();
	}
	
	public void close()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}

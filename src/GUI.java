import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	private static ArrayList<Dijstra.Node> nodes = new ArrayList<>();
	private static ArrayList<Dijstra.Edge> edges = new ArrayList<>();
	private HashMap<String, Dijstra.Node> nodeNameMap = new HashMap<>();
	private XMLEditor xmlEditor;
	private JFrame frame;
	
	

	private Dijstra dijstra = new Dijstra();

	public GUI(JFrame frame) {
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

		generateNodeNameMap();

	}

	private void drawMap(Graphics2D g) {
		ImageObserver observer = null;
		BufferedImage RHITMap = null;
		try {
			RHITMap = ImageIO.read(new File("src/RHITMap.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("did not find RHITMap.png");
			e.printStackTrace();
		}
		g.drawImage(RHITMap, null, 0, 0);
		g.drawImage(RHITMap, 0, 0, Main.screenWidth, Main.screenHeight, 0, 0, RHITMap.getWidth(), RHITMap.getHeight(), null);
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
			edges = path;
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

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		drawMap(g2);
		for (Dijstra.Node n : nodes) {
			n.drawOn(g2);
			repaint();
		}
		if (edges != null && edges.size() > 0) {
			for (Dijstra.Edge e : edges) {
				e.drawOn(g2);
				repaint();
			}
		}

	}

	
	public static void draw(ArrayList<Dijstra.Edge> pathEdges) {
		int cost = 0;
		edges = pathEdges;
		for (int i = 0; i < edges.size(); i++) {
			cost += edges.get(i).getCost();
		}
		System.out.println(cost);
	}
}

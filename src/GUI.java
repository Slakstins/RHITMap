import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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

	public void generateNodeNameMap() {
		for (int i = 0; i < this.nodes.size(); i++) {
			nodeNameMap.put(nodes.get(i).getName(), nodes.get(i));
		}
	}

	public void calculatePath(String startNode, String endNode) {
		this.dijstra.calculatePath(nodeNameMap.get(startNode), nodeNameMap.get(endNode));
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

	public void loadSavedPath(String fileName) {
		ArrayList<Dijstra.Edge> readEdges = null;
		try {
			readEdges = xmlEditor.read(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("could not read " + fileName);
			e.printStackTrace();
		}

		if (readEdges == null) {
			System.out.println("file not read " + fileName);
		}
		edges = readEdges;
		dijstra.setShortestPathFromSave(readEdges);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

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

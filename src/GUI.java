import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class GUI extends JComponent
{
	private ArrayList<Dijstra.Node> nodes = new ArrayList<>();
	private ArrayList<Dijstra.Edge> edges = new ArrayList<>();
	private JFrame frame;
	
	private Dijstra dijstra = new Dijstra();
//	private Dijstra.Node node1 = new Dijstra.Node(100, 100, "Node 1", edges);
//	private Dijstra.Node node2 = new Dijstra.Node(300, 300, "Node 2", edges);
//	private Dijstra.Edge edge1 = new Dijstra.Edge(node1, node2, 1, false);
	
	public GUI(JFrame frame, HashMap<Dijstra.Node, ArrayList<Dijstra.Edge>> nodeEdgeMap) {
		this.frame = frame;
		Set<Dijstra.Node> keys = nodeEdgeMap.keySet();
		for (Dijstra.Node i : keys) {
			nodes.add(i);
			for (Dijstra.Edge j : i.getEdges()) {
				edges.add(j);
				//this adds duplicates of the edges, as multiple nodes have the same edge.
				//could store edges in a set (sets don't allow dulicates)
				//to avoid this problem
			}
			
		}
		
		
		
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		

		Graphics2D g2 = (Graphics2D) g;
		
		for (Dijstra.Node n : nodes) {
			n.drawOn(g2);
			repaint();
		}
		
		for (Dijstra.Edge e : edges) {
			e.drawOn(g2);
			repaint();
		}
		
	}
	
	public static void draw(ArrayList<Dijstra.Edge> edges)
	{
		for(int i = 0; i < edges.size(); i++)
		{
			//draw a line between both nodes
		}
	}
}

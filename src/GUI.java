import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GUI extends JComponent
{
	private ArrayList<Dijstra.Node> nodes = new ArrayList<>();
	private ArrayList<Dijstra.Edge> edges = new ArrayList<>();
	private JFrame frame;
	
	private Dijstra dijstra = new Dijstra();
	private Dijstra.Node node1 = dijstra.new Node(100, 100, "Node 1", edges);
	private Dijstra.Node node2 = dijstra.new Node(300, 300, "Node 2", edges);
	private Dijstra.Edge edge1 = dijstra.new Edge(node1, node2, 1, false);
	
	public GUI(JFrame frame) {
		this.frame = frame;
		this.nodes.add(node1);
		this.nodes.add(node2);
		this.edges.add(edge1);
		
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

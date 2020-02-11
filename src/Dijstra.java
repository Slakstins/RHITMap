import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

//import GUI.Edge;
//import GUI.Node;
import javafx.scene.shape.Line;


public class Dijstra 
{
	private HashMap<Node, ArrayList<Edge>> shortestPath = new HashMap<>();
	private PriorityQueue<Node> queue = new PriorityQueue<>();
	private ArrayList<Edge> currentPath = new ArrayList<>();
	
	public Dijstra()
	{
		
	}
	
//	public ArrayList<Edge> calculatePath(Node startNode, Node endNode)
//	{
//		GUI.draw(currentPath);
//	}
	
	
	
	public static class Edge implements Serializable
	{
		private Node startNode;
		private Node endNode;
		private int cost;
		private boolean outside;
		private Line2D.Double edge;
		

		
		public Edge() {
			
		}
		
		
		
		public Edge(Node startNode, Node endNode, int cost, boolean outside)
		{
			this.startNode = startNode;
			this.endNode = endNode;
			this.cost = cost;
			this.outside = outside;
			
			this.edge = new Line2D.Double(new Point2D.Double(startNode.x+startNode.size/2, startNode.y+startNode.size/2), new Point2D.Double(endNode.x+endNode.size/2, endNode.y+endNode.size/2));
			
		}
		
		public int getCost() {
			return cost;
		}
		
		public boolean getOutside() {
			return this.outside;
		}
		
		public void setOutside(boolean outside) {
			this.outside = outside;
		}
		

		
		public Node getStartNode() {
			return this.startNode;
		}
		
		public Node getEndNode() {
			return this.endNode;
		}
		
		public void setStartNode(Node startNode) {
			this.startNode = startNode;
		}
		
		public void setCost(int cost) {
			this.cost = cost;
		}
		
		public void drawOn(Graphics2D g) {
			g.setColor(Color.BLACK);
			g.fill(this.edge);
			g.draw(edge);
		}



		public void setEndNode(Node endNode) {
			this.endNode = endNode;
			
		}
		
	}
	public static class Node implements Serializable
	{

		private int x;
		private int y;
		private int size = 40;
		private String name;
		private ArrayList<Edge> edges;
		
		private Ellipse2D.Double node;
		
		public Node(int x, int y, String name, ArrayList<Edge> edges)
		{
			this.x = x;
			this.y = y;
			this.name = name;
			if (edges != null)
			this.edges = edges;
			
			this.node = new Ellipse2D.Double(x, y, size, size);
		}
		
		public Node() {
			
		}
		
		public void setEdges(ArrayList<Edge> edges) {
			this.edges = edges;
		}
		
		public ArrayList<Edge> getEdges(){
			return this.edges;
		}
		
		public int getX()
		{
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		
		public void setY(int y) {
			this.x = y;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getY()
		{
			return y;
		}
		public String getName()
		{
			return name;
		}
		
		public void drawOn(Graphics2D g) {
			g.setColor(Color.BLACK);
			//g.fill(this.node);
			g.draw(node);
		}
	}
}

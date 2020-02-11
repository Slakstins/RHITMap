import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import GUI.Edge;
import GUI.Node;


public class Dijstra 
{
	private HashMap<Node, ArrayList<Edge>> shortestPath = new HashMap<>();
	private PriorityQueue<Node> queue = new PriorityQueue<>();
	private ArrayList<Edge> currentPath = new ArrayList<>();
	
	public Dijstra()
	{
		
	}
	
	public ArrayList<Edge> calculatePath(Node startNode, Node endNode)
	{
		GUI.draw(currentPath);
	}
	
	public class Edge 
	{
		private Node startNode;
		private Node endNode;
		private int cost;
		private boolean outside;
		
		public Edge(Node startNode, Node endNode, int cost, boolean outside)
		{
			startNode = startNode;
			endNode = endNode;
			cost = cost;
			outside = outside;
		}
		
	}
	public class Node 
	{
		private int x;
		private int y;
		private String name;
		private ArrayList<Edge> edges;
		
		public Node(int x, int y, String name, ArrayList<Edge> edges)
		{
			x = x;
			y = y;
			name = name;
			edges = edges;
		}
		
		public int getX()
		{
			return x;
		}
		public int getY()
		{
			return y;
		}
		public String getName()
		{
			return name;
		}
	}
}

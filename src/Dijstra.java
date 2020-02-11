import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Dijstra 
{
	private HashMap<Node, String> nodes = new HashMap<>();
	
	private HashMap<Node, ArrayList<Edge>> shortestPath = new HashMap<>();
	private PriorityQueue<Node> queue = new PriorityQueue<>();
	
	public Dijstra()
	{
		
	}
	
	public void calculatePath(Node startNode, Node endNode)
	{
		queue.offer(startNode);
		while(!queue.isEmpty())
		{
			queue.poll().calculatePath();
		}
		GUI.draw(shortestPath.get(endNode));
	}
	
	public class Edge 
	{
		private Node n1;
		private Node n2;
		private int cost;
		private boolean outside;
		
		public Edge(Node n1, Node n2, int cost, boolean outside)
		{
			this.n1 = n1;
			this.n2 = n2;
			this.cost = cost;
			this.outside = outside;
		}
		
		public Node getEndNode(Node n)
		{
			if(n1 == n)
			{
				return n2;
			}
			return n1;
		}
		
		public int getCost()
		{
			return cost;
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
			this.x = x;
			this.y = y;
			this.name = name;
			this.edges = edges;
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
		
		public void calculatePath()
		{
			for(int i = 0; i < edges.size(); i++)
			{
				Node n = edges.get(i).getEndNode(this);
				queue.offer(n);
				if(shortestPath.containsKey(n))
				{
					int oldCost = 0;
					for(int j = 0; j < shortestPath.get(n).size(); j++)
					{
						oldCost += shortestPath.get(n).get(j).getCost();
					}
					
					int newCost = edges.get(i).getCost();
					for(int j = 0; j < shortestPath.get(this).size(); j++) 
					{
						newCost += shortestPath.get(this).get(j).getCost();
					}
					
					if(oldCost > newCost)
					{
						ArrayList<Edge> newPath = shortestPath.get(this);
						newPath.add(edges.get(i));
						shortestPath.put(n, newPath);
					}
				}
				else
				{
					ArrayList<Edge> newPath = shortestPath.get(this);
					newPath.add(edges.get(i));
					shortestPath.put(n, newPath);
				}
			}
		}
	}
}

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Dijstra 
{
	private static HashMap<Node, String> nodes = new HashMap<>();
	
	private static HashMap<Node, ArrayList<Edge>> shortestPath = new HashMap<>();
	private static PriorityQueue<Node> queue = new PriorityQueue<>();
	
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
	
	public static class Edge implements Serializable
	{
		private Node n1;
		private Node n2;
		private int cost;
		private boolean outside;
		private Line2D.Double edge;
		
		public Edge() 
		{
			
		}
		
		public Edge(Node n1, Node n2, int cost, boolean outside)
		{
			this.n1 = n1;
			this.n2 = n2;
			this.cost = cost;
			this.outside = outside;
		}
		
		public Node getN1() 
		{
			return n1;
		}
		
		public void setN1(Node n)
		{
			n1 = n;
		}

		public Node getN2() 
		{
			return n2;
		}
		
		public void setN2(Node n)
		{
			n2 = n;
		}
		
		public int getCost() 
		{
			return cost;
		}
		
		public void setCost(int cost) 
		{
			this.cost = cost;
		}
		
		public boolean getOutside() 
		{
			return this.outside;
		}
		
		public void setOutside(boolean outside) 
		{
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
		
		public void drawOn(Graphics2D g) 
		{
			g.setColor(Color.BLACK);
			g.fill(this.edge);
			g.draw(edge);
		}
	}
	public static class Node implements Serializable
	{

		private int x;
		private int y;
		private String name;
		private ArrayList<Edge> edges;
		private Ellipse2D.Double nodeToDraw;
		private final int size = 40;
		
		public Node() 
		{
			
		}
		
		public Node(int x, int y, String name, ArrayList<Edge> edges)
		{
			this.x = x;
			this.y = y;
			this.name = name;
			this.edges = edges;
			this.nodeToDraw = new Ellipse2D.Double(x, y, size, size);
		}
		
		public int getX()
		{
			return x;
		}
		public void setX(int x) 
		{
			this.x = x;
		}
		
		public int getY()
		{
			return y;
		}
		
		public void setY(int y) 
		{
			this.x = y;
		}
		
		public String getName()
		{
			return name;
		}
		
		public void setName(String name) 
		{
			this.name = name;
		}
		
		public ArrayList<Edge> getEdges()
		{
			return this.edges;
		}
		
		public void setEdges(ArrayList<Edge> edges) 
		{
			this.edges = edges;
		}
		
		public void addNodeEllipseToDraw() 
		{
			this.nodeToDraw = new Ellipse2D.Double(x, y, size, size);
		}
		
		public String toString() 
		{
			return ("Name: " + name + "/nX: " + x + "/nY: " + y );
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
		
		public void drawOn(Graphics2D g) 
		{
			
			g.setColor(Color.BLACK);
			//g.fill(this.node);
			nodeToDraw.toString();
			g.draw(nodeToDraw);
		}

		public void setPosition(int x, int y) 
		{
			this.x = x;
			this.y = y;
			
			this.nodeToDraw = new Ellipse2D.Double(this.x, this.y, size, size);
		}
	}
}

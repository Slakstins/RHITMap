import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Dijstra {

	private static HashMap<Node, ArrayList<Edge>> shortestPathMap = new HashMap<>();
	public static ArrayList<Edge> shortestPathEdges = new ArrayList<>();
	private static PriorityQueue<Node> queue = new PriorityQueue<>();
	public static boolean outsidePath = true;
	public static boolean wca = false;

	public Dijstra() {

	}
	
	private void setOutsidePath(boolean outsidePath) {
		this.outsidePath = outsidePath;
	}
	
	private void setWCA(boolean wca)
	{
		this.wca = wca;
	}

	public void calculatePath(Node startNode, Node endNode) {
		ArrayList<Edge> path = new ArrayList<>();
		path.add(startNode.zeroEdge);
		shortestPathMap.put(startNode, path);
		queue.offer(startNode);
		while (!queue.isEmpty()) {
			queue.poll().calculatePath();
		}
		GUI.draw(shortestPathMap.get(endNode));
		shortestPathEdges = shortestPathMap.get(endNode);
	}

	public void setShortestPathFromSave(ArrayList<Edge> edges) {
		Dijstra.shortestPathEdges = edges;
		GUI.draw(edges);
	}

	public static int cost(Node n) {
		int cost = 0;
		ArrayList<Edge> path = shortestPathMap.get(n);
		for (int i = 0; i < path.size(); i++) {
			if(outsidePath)
			{
				cost += path.get(i).getCost();
			}
			else
			{
				cost += path.get(i).getCost() * 3;
			}
		}
		return cost;
	}

	public static class Edge implements Serializable {
		private Node n1;
		private Node n2;
		private int cost;
		private boolean outside;
		private boolean wca;
		private Line2D.Double edge;
		private boolean onPath;

		public Edge() {

		}

		public Edge(Node n1, Node n2, int cost, boolean outside, boolean wca) {
			this.onPath = false;
			this.n1 = n1;
			this.n2 = n2;
			this.cost = cost;
			this.outside = outside;
			this.wca = wca;
			this.edge = new Line2D.Double(new Point2D.Double(n1.x + Constants.nodeSize / 2 - Constants.nodeSize / 2, n1.y + Constants.nodeSize / 2 - Constants.nodeSize / 2),
					new Point2D.Double(n2.x + Constants.nodeSize / 2 - Constants.nodeSize / 2, n2.y + Constants.nodeSize / 2 - Constants.nodeSize / 2));
		}

		public void addEdgeLineToDraw() {
			this.edge = new Line2D.Double(new Point2D.Double(n1.x + Constants.nodeSize / 2 - Constants.nodeSize / 2, n1.y + Constants.nodeSize / 2 - Constants.nodeSize / 2),
					new Point2D.Double(n2.x + Constants.nodeSize / 2 - Constants.nodeSize / 2, n2.y + Constants.nodeSize / 2 - Constants.nodeSize / 2));
		}
		
		public int getFloorOne() {
			return n1.getFloor();
		}
		
		public int getFLoorTwo() {
			return n2.getFloor();
		}

		public Node getN1() {
			return n1;
		}

		public void setN1(Node n) {
			n1 = n;
		}

		public Node getN2() {
			return n2;
		}

		public void setN2(Node n) {
			n2 = n;
		}

		public int getCost() {
			return cost;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}

		public boolean getOutside() {
			return this.outside;
		}

		public void setOutside(boolean outside) {
			this.outside = outside;
		}
		
		public boolean getWCA()
		{
			return wca;
		}
		
		public void setWCA(boolean wca)
		{
			this.wca = wca;
		}

		public Node getEndNode(Node n) {
			if (n1 == n) {
				return n2;
			}
			return n1;
		}

		public void drawOn(Graphics2D g) {
			this.addEdgeLineToDraw();
			edge.x1 = n1.specialGetEx();
			edge.y1 = n1.specialGetWhy();
			edge.x2 = n2.specialGetEx();
			edge.y2 = n2.specialGetWhy();
			if (this.onPath) {
				g.setColor(Color.RED);
			}
			else if (this.outside && this.wca) {
				g.setColor(Color.yellow);
			} else if (!this.wca) {
				g.setColor(Color.MAGENTA);
			}
			
			else {
			g.setColor(Color.BLACK);
			}
			g.fill(this.edge);
			g.draw(edge);
		}

		public String toString() {
			return "From: " + n1.name + "\nTo: " + n2.name + "\nCost: " + cost + '\n';
		}

		public void setOnPath(boolean b) {
			// TODO Auto-generated method stub
			this.onPath = b;
		}

		public boolean flipOutside() {
			this.outside = !this.outside;
			return this.outside;
			// TODO Auto-generated method stub
		}
		
		public boolean flipWCA() {
			this.wca = !this.wca;
			return this.wca;
		}
	}

	public static class Node implements Comparable<Node>, Serializable {
		private double x;
		private double y;
		private String name;
		private ArrayList<Edge> edges = new ArrayList<>();
		private Edge zeroEdge;
		private Ellipse2D.Double nodeToDraw;
		private boolean selected;
		private int floor;

		public Node() {
			zeroEdge = new Edge(this, this, 0, false, false);
			this.selected = false;
		}
		
		public double getX() {
			return this.x;
		}
		
		public double specialGetEx() {
			return (GUI.xScreenRatio * x + GUI.xOffset) * GUI.zoomLevel;	
		}
		
		public double specialGetWhy() {
			return (GUI.yScreenRatio * y + GUI.yOffset) * GUI.zoomLevel;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return this.y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public double getSize() {
			return Constants.nodeSize * GUI.zoomLevel;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Ellipse2D convertNodeEllipseToDraw() {
			return new Ellipse2D.Double(((GUI.xScreenRatio * x + GUI.xOffset) * GUI.zoomLevel) - (Constants.nodeSize * GUI.zoomLevel/ 2),
					(GUI.yScreenRatio * y + GUI.yOffset) * GUI.zoomLevel - (Constants.nodeSize * GUI.zoomLevel / 2), Constants.nodeSize * GUI.zoomLevel,
					Constants.nodeSize * GUI.zoomLevel);
		}
		
		public String toString() {
			return ("Name: " + name + "\nX: " + this.x + "\nY: " + this.y);
		}

		public void setEdges(ArrayList<Edge> edges) {
			this.edges = edges;
		}

		public ArrayList<Edge> getEdges() {
			return this.edges;
		}
		
		public int getFloor()
		{
			return floor;
		}
		
		public void setFloor(int floor)
		{
			this.floor = floor;
		}
		
		public boolean getOutside()
		{
			for(int i = 0; i < edges.size(); i++)
			{
				if(edges.get(i).getOutside())
				{
					return true;
				}
			}
			return false;
		}

		public void calculatePath() {
			for (int i = 0; i < edges.size(); i++) {
				if(edges.get(i).wca == wca)
				{
					Node n = edges.get(i).getEndNode(this);
					if (shortestPathMap.containsKey(n)) {
						int oldCost = cost(n);

						int newCost = edges.get(i).getCost() + cost(this);

						if (oldCost > newCost) {
							ArrayList<Edge> newPath = new ArrayList<>();
							newPath.addAll(shortestPathMap.get(this));
							newPath.add(edges.get(i));
							shortestPathMap.put(n, newPath);
							queue.offer(n);
						}
					} else {
						ArrayList<Edge> newPath = new ArrayList<>();
						newPath.addAll(shortestPathMap.get(this));
						newPath.add(edges.get(i));
						shortestPathMap.put(n, newPath);
						queue.offer(n);
					}
				}
			}
		}

		public void drawOn(Graphics2D g) {
			Ellipse2D converted = this.convertNodeEllipseToDraw();
			g.setColor(Color.BLACK);
			if (this.getSelected()) {
				g.setColor(Color.red);
			}
			g.fill(converted);
			g.draw(converted);
		}

		public int compareTo(Node n) {
			int thisCost = cost(this);
			int nCost = cost(n);
			if (thisCost < nCost) {
				return -1;
			}
			if (thisCost == nCost) {
				return 0;
			}
			return 1;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
			
		}

		public boolean getSelected() {
			return selected;
		}
	}

}
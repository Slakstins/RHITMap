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

	public Dijstra() {

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
			cost += path.get(i).getCost();
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
			edge.x1 = n1.getX();
			edge.y1 = n1.getY();
			edge.x2 = n2.getX();
			edge.y2 = n2.getY();
			if (this.onPath) {
				g.setColor(Color.RED);
			}
			else if (this.outside) {
				g.setColor(Color.yellow);
			} else {
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
	}

	public static class Node implements Comparable<Node>, Serializable {
		private double x;
		private double y;
		private String name;
		private ArrayList<Edge> edges = new ArrayList<>();
		private Edge zeroEdge;
		private Ellipse2D.Double nodeToDraw;
		private boolean selected;
		private Building building;
		private int floor;

		public Node() {
			zeroEdge = new Edge(this, this, 0, false, false);
			this.selected = false;
		}

		public Node(int x, int y, String name, ArrayList<Edge> edges, Building building, int floor) {
			this.x = x;
			this.y = y;
			this.name = name;
			zeroEdge = new Edge(this, this, 0, false, false);
			this.edges = edges;
			this.nodeToDraw = new Ellipse2D.Double(x, y, Constants.nodeSize, Constants.nodeSize);
			this.building = building;
			this.floor = floor;
		}

		public double getX() {
			return (GUI.xScreenRatio * x + GUI.xOffset) * GUI.zoomLevel;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return (GUI.yScreenRatio * y + GUI.yOffset) * GUI.zoomLevel;
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
			return new Ellipse2D.Double(((GUI.xScreenRatio * x + GUI.xOffset) * GUI.zoomLevel) - (Constants.nodeSize / 2),
					(GUI.yScreenRatio * y + GUI.yOffset) * GUI.zoomLevel - (Constants.nodeSize / 2), Constants.nodeSize * GUI.zoomLevel,
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
		
		public Building getBuilding()
		{
			return building;
		}
		
		public void setBuilding(Building building)
		{
			this.building = building;
		}
		
		public int getFloor()
		{
			return floor;
		}
		
		public void setFloor(int floor)
		{
			this.floor = floor;
		}

		public void calculatePath() {
			for (int i = 0; i < edges.size(); i++) {
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